package dominio.servicio;

import dominio.modelo.item.Item;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorValidacion;
import infraestructura.acl.adapters.ItemAdapter;
import infraestructura.acl.dto.RedimirCouponsDTO;
import infraestructura.acl.dto.RedimirCouponsRespuestaDTO;
import infraestructura.serviciosexternos.ServicioMeli;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import play.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;

public class ServicioRedencionCoupons {

    final Logger.ALogger logger = Logger.of(this.getClass());
    private ServicioMeli servicioMeli;

    @Inject
    public ServicioRedencionCoupons(ServicioMeli servicioMeli) {
        this.servicioMeli = servicioMeli;
    }

    public Future<Either<Error, RedimirCouponsRespuestaDTO>> maximizarUsoCupon(RedimirCouponsDTO dto){
        return consultarItemsMeli(dto)
                .map(itemsEither -> itemsEither
                        .map(items -> maximizarGastoItems(items, dto.getAmount()))
                        .mapLeft(ErrorValidacion::new)
                );
    }

    private RedimirCouponsRespuestaDTO maximizarGastoItems(List<Item> items, double montoMaximo){
        Tuple2<List<Item>, Double> itemsMax = maximizarGastoFuncion(items.toJavaList(), montoMaximo);

        System.out.println("Items seleccionados para maximizar el gasto:");
        for (Item item : itemsMax._1) {
            System.out.println("Item ID: " + item.getId() + ", Precio: $" + item.getPrice());
        }
        return new RedimirCouponsRespuestaDTO(itemsMax._1.map(Item::getId), itemsMax._2);
    }

    public Tuple2<List<Item>, Double> maximizarGastoFuncion(java.util.List<Item> items, double montoMaximo){
        Collections.sort(items);

        //Arreglo para las sumas de todos los montos
        double[] arr = new double[(int) Math.ceil(montoMaximo) + 1];

        // Sumar elementos
        for (Item item : items) {
            double precio = item.getPrice();
            for (int i = (int) (montoMaximo); i >= precio; i--) {
                double max =
                arr[i] = Math.max(arr[i - (int) (precio)] + precio, arr[i]);
            }
        }
        // Encontrar el monto gastado más cercano al monto máximo
        double montoGastado = arr[(int) (montoMaximo)];
        double montoGastadoMax = montoGastado;

        // Encontrar los items que cumplen con el monto gastado
        java.util.List<Item> listItemSeleccionados = new ArrayList<>();
        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = items.get(i);
            double precio = item.getPrice();
            if (montoGastado >= precio && arr[(int) ((montoGastado - precio))] + precio == montoGastado) {
                listItemSeleccionados.add(item);
                montoGastado -= precio;
            }
        }

        return Tuple.of(List.ofAll(listItemSeleccionados), montoGastadoMax);
    }

    private Future<Either<String, List<Item>>> consultarItemsMeli(RedimirCouponsDTO dto){
        Future<List<Either<String, Item>>> foldLeft = dto.getItem_ids().foldLeft(
                Future.successful(List.empty()),
                (acc, item) -> acc.flatMap(either -> obtenerInfoItemServicioMeli(item).map(either::prepend))
        );

        return foldLeft.map( itemsConsultados -> {
                    List<String> errores = itemsConsultados.filter(Either::isLeft).map(Either::getLeft).toList();
                    logger.warn("Se presento un error obteniendo los items para el calculo del maximo" + errores.toString());
                    List<Item> items = itemsConsultados.filter(Either::isRight).map(Either::get).toList();
                    return Option.of(items).toEither("Ocurrio un error consultando los items");
                }
        );
    }

    private Future<Either<String, Item>> obtenerInfoItemServicioMeli(String idItem){
        return servicioMeli
                .obtenerInfoItem(idItem, Future.DEFAULT_EXECUTOR_SERVICE)
                .map(either -> either.fold(
                        Either::left,
                        itemMeliDTOS -> itemMeliDTOS.map(ItemAdapter::transformar).toEither("No existe el item")
                ));
    }
}
