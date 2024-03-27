package dominio.servicios;

import dominio.respuestas.Error;
import dominio.servicio.ServicioRedencionCoupons;
import infraestructura.acl.dto.ItemMeliDTO;
import infraestructura.acl.dto.RedimirCouponsDTO;
import infraestructura.acl.dto.RedimirCouponsRespuestaDTO;
import infraestructura.serviciosexternos.ServicioMeli;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioRedencionCouponsITTest {
    private ServicioRedencionCoupons servicioRedencionCoupons;
    private ServicioMeli servicioMeli;

    @Before
    public void iniciarDatos(){
        this.servicioMeli =  mock(ServicioMeli.class);
        this.servicioRedencionCoupons = new ServicioRedencionCoupons(servicioMeli);
    }

    @Test
    public void maximizarUsoCuponConsultaCompletaTest(){

        when(servicioMeli.obtenerInfoItem(anyString(), any()))
                .thenReturn(Future.successful(Either.right(Option.of(new ItemMeliDTO("ID1", 100)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID2", 210)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID3", 260)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID4", 80)))),
                        Future.successful(Either.right(Option.of(new ItemMeliDTO("ID5", 90)))));

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

}
