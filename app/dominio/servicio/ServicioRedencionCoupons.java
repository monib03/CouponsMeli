package dominio.servicio;

import dominio.respuestas.Error;
import infraestructura.acl.dto.DTO;
import infraestructura.acl.dto.ItemMeliDTO;
import infraestructura.acl.dto.RedimirCouponsDTO;
import infraestructura.serviciosexternos.ServicioMeli;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import play.Logger;

import javax.inject.Inject;


public class ServicioRedencionCoupons {

    final Logger.ALogger logger = Logger.of(this.getClass());
    private ServicioMeli servicioMeli;

    @Inject
    public ServicioRedencionCoupons(ServicioMeli servicioMeli) {
        this.servicioMeli = servicioMeli;
    }

    private Future<Either<Error, DTO>> maximizarUsoCupon(RedimirCouponsDTO dto){

        
    }

    private Future<List<ItemMeliDTO>> consultarItemsMeli(RedimirCouponsDTO dto){
       /* return Future.of(()-> dto.getItem_ids()
                .flatMap(idItem-> obtenerInfoItemMeli(idItem).map(l -> l))
        ));*/

    }

    private Future<List<ItemMeliDTO>> obtenerInfoItemMeli(String idItem){
        return servicioMeli
                .obtenerInfoItem(idItem, Future.DEFAULT_EXECUTOR_SERVICE)
                .map(either -> either.fold(
                        error -> {
                            logger.error("No se pudo obtener informacion del item " + error);
                            return List.empty();},
                        itemMeliDTOS -> itemMeliDTOS.toList()
                ));
    }
}
