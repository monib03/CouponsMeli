package dominio.servicios;

import dominio.modelo.item.Item;
import dominio.respuestas.Error;
import dominio.servicio.ServicioRedencionCoupons;
import infraestructura.acl.dto.ItemMeliDTO;
import infraestructura.acl.dto.RedimirCouponsDTO;
import infraestructura.acl.dto.RedimirCouponsRespuestaDTO;
import infraestructura.serviciosexternos.ServicioMeli;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServicioRedencionCouponsTest {

    private ServicioRedencionCoupons servicioRedencionCoupons;

    @Mock
    ServicioMeli servicioMeli;

    @Before
    public void setUp(){
        this.servicioRedencionCoupons = new ServicioRedencionCoupons(servicioMeli);
    }

    @Test
    public void maximizarUsoCuponConsultaCompletaTest(){

        when(servicioMeli.obtenerInfoItem(anyString(), any()))
                .thenReturn(Future.successful(Either.right(Option.of(new ItemMeliDTO("ID1", 100)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID2", 210)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID3", 260)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID4", 80)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID5", 90))))
                );

        RedimirCouponsDTO redimirCouponsDTO = new RedimirCouponsDTO(
                List.of("ID1", "ID2", "ID3", "ID4", "ID5" ), 500);
        final Either<Error, RedimirCouponsRespuestaDTO> either = servicioRedencionCoupons.maximizarUsoCupon(redimirCouponsDTO).get();
        final RedimirCouponsRespuestaDTO redimirCouponsRespuestaDTO = either.get();

        assertTrue("Contiene el id del item 1", redimirCouponsRespuestaDTO.getItems_ids().contains("ID1"));
        assertTrue("Contiene el item 2", redimirCouponsRespuestaDTO.getItems_ids().contains("ID2"));
        assertTrue("Contiene el item 4", redimirCouponsRespuestaDTO.getItems_ids().contains("ID4"));
        assertTrue("Contiene el item 5", redimirCouponsRespuestaDTO.getItems_ids().contains("ID5"));
        assertEquals("El monto maximo coincide", 480, redimirCouponsRespuestaDTO.getTotal(),1);

    }

    @Test
    public void maximizarUsoCuponConsultaCompletaErrorItemNoExistenteTest(){

        when(servicioMeli.obtenerInfoItem(anyString(), any()))
                .thenReturn(Future.successful(Either.left("no existe el item")));

        RedimirCouponsDTO redimirCouponsDTO = new RedimirCouponsDTO(
                List.of("ID1"), 200);
        final Either<Error, RedimirCouponsRespuestaDTO> either = servicioRedencionCoupons.maximizarUsoCupon(redimirCouponsDTO).get();
        final RedimirCouponsRespuestaDTO redimirCouponsRespuestaDTO = either.get();

        assertTrue("la lista esta vacia", redimirCouponsRespuestaDTO.getItems_ids().isEmpty());
        assertEquals("El monto maximo es 0", 0, redimirCouponsRespuestaDTO.getTotal(),1);

    }

    @Test
    public void maximizarUsoCuponConsultaCompletaErrorItemNoExistenteListaTest(){

        when(servicioMeli.obtenerInfoItem(anyString(), any()))
                .thenReturn(Future.successful(Either.left("no existe el item")),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID2", 210)))));

        RedimirCouponsDTO redimirCouponsDTO = new RedimirCouponsDTO(
                List.of("ID1", "ID2"), 410);
        final Either<Error, RedimirCouponsRespuestaDTO> either = servicioRedencionCoupons.maximizarUsoCupon(redimirCouponsDTO).get();
        final RedimirCouponsRespuestaDTO redimirCouponsRespuestaDTO = either.get();

        assertEquals("la lista tiene 1 elemento ", 1, redimirCouponsRespuestaDTO.getItems_ids().size());
        assertEquals("El monto maximo es 210", 210, redimirCouponsRespuestaDTO.getTotal(),1);

    }

    @Test
    public void maximizarGastoTest0(){

        Item item1 = new Item("ID1", 10);
        Item item2 = new Item("ID2", 21);
        Item item3 = new Item("ID3", 26);
        Item item4 = new Item("ID4", 8);
        Item item5 = new Item("ID5", 9);

        List<Item> itemsConsultados = List.of(item1, item2, item3, item4, item5);
        final Tuple2<List<Item>, Double> respFuncion = servicioRedencionCoupons.maximizarGastoFuncion(
                itemsConsultados.toJavaList(), 50);
        List<Item> itemsEscogidos = respFuncion._1;
        Double maximoGastado = respFuncion._2;

        assertTrue("Contiene el item 1", itemsEscogidos.contains(item1));
        assertTrue("Contiene el item 2", itemsEscogidos.contains(item2));
        assertTrue("Contiene el item 4", itemsEscogidos.contains(item4));
        assertTrue("Contiene el item 5", itemsEscogidos.contains(item5));
        assertEquals("la lista tiene 4 elementos ", 4, itemsEscogidos.size());
        assertEquals("El monto maximo coincide", 48, maximoGastado, 1);

    }

    @Test
    public void maximizarGastoTest1(){

        Item item1 = new Item("ID1", 100);
        Item item2 = new Item("ID2", 210);
        Item item3 = new Item("ID3", 260);
        Item item4 = new Item("ID4", 80);
        Item item5 = new Item("ID5", 90);

        List<Item> itemsConsultados = List.of(item1, item2, item3, item4, item5);
        final Tuple2<List<Item>, Double> respFuncion = servicioRedencionCoupons.maximizarGastoFuncion(
                itemsConsultados.toJavaList(), 500);
        List<Item> itemsEscogidos = respFuncion._1;
        Double maximoGastado = respFuncion._2;

        assertTrue("Contiene el item 1", itemsEscogidos.contains(item1));
        assertTrue("Contiene el item 2", itemsEscogidos.contains(item2));
        assertTrue("Contiene el item 4", itemsEscogidos.contains(item4));
        assertTrue("Contiene el item 5", itemsEscogidos.contains(item5));
        assertEquals("la lista tiene 4 elementos ", 4, itemsEscogidos.size());
        assertEquals("El monto maximo coincide", 480, maximoGastado, 1);

    }

    @Test
    public void maximizarGastoTest2(){

        Item item1 = new Item("ID1", 100);
        Item item2 = new Item("ID2", 210);
        Item item3 = new Item("ID3", 260);
        Item item4 = new Item("ID4", 80);
        Item item5 = new Item("ID5", 90);

        List<Item> itemsConsultados = List.of(item1, item2, item3, item4, item5);
        final Tuple2<List<Item>, Double> respFuncion = servicioRedencionCoupons.maximizarGastoFuncion(
                itemsConsultados.toJavaList(), 270);
        List<Item> itemsEscogidos = respFuncion._1;
        Double maximoGastado = respFuncion._2;

        assertTrue("Contiene el item 1", itemsEscogidos.contains(item1));
        assertTrue("Contiene el item 4", itemsEscogidos.contains(item4));
        assertTrue("Contiene el item 5", itemsEscogidos.contains(item5));
        assertEquals("la lista tiene 3 elementos ", 3, itemsEscogidos.size());
        assertEquals("El monto maximo coincide", 270, maximoGastado, 1);
    }

    @Test
    public void maximizarGastoTest3(){

        Item item1 = new Item("ID1", 10);
        Item item2 = new Item("ID2", 21);
        Item item3 = new Item("ID3", 26);
        Item item4 = new Item("ID4", 8);
        Item item5 = new Item("ID5", 9);

        List<Item> itemsConsultados = List.of(item1, item2, item3, item4, item5);
        final Tuple2<List<Item>, Double> respFuncion = servicioRedencionCoupons.maximizarGastoFuncion(
                itemsConsultados.toJavaList(), 26);
        List<Item> itemsEscogidos = respFuncion._1;
        Double maximoGastado = respFuncion._2;

        assertTrue("Contiene el item 3", itemsEscogidos.contains(item3));
        assertEquals("la lista tiene 1 elementos ", 1, itemsEscogidos.size());
        assertEquals("El monto maximo coincide", 26, maximoGastado, 1);

    }

}
