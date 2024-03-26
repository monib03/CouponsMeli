package dominio.servicios;

import dominio.repositorio.CouponRepositorio;
import dominio.servicio.ServicioItems;
import infraestructura.acl.dto.BookmarkTopDTO;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import persistencia.item.bookmark.BookmarkTopRecord;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ServicioItemsTest {

    private ServicioItems servicioItems;

    @Mock
    CouponRepositorio couponRepositorio;

    @Before
    public void setUp(){
        this.servicioItems = new ServicioItems(couponRepositorio);
    }

    @Test
    public void consultaItemsFavoritosTest(){

        when(couponRepositorio.consultarTopBookmarks())
                .thenReturn(Future.successful(List.of(new BookmarkTopRecord("ID1", 10L))));

        final List<BookmarkTopDTO> bookmarksTopDTO = servicioItems.consultarTopBookmarks().get();
        final BookmarkTopDTO bookmarkTopDTO = bookmarksTopDTO.get(0);

        assertEquals("El id del item coincide","ID1", bookmarkTopDTO.getId());
        assertEquals("La cantidad del item coincide", 10L, bookmarkTopDTO.getQuantity().longValue());

    }
}
