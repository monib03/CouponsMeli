package consultas;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.item.Item;
import dominio.modelo.user.User;
import dominio.repositorio.CouponRepositorio;
import dominio.servicio.ServicioItems;
import fabrica.dominio.BookmarkFabrica;
import fabrica.dominio.ItemFabrica;
import fabrica.dominio.UserFabrica;
import infraestructura.acl.dto.BookmarkTopDTO;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;
import persistencia.PruebaConBDTest;

import static org.junit.Assert.assertEquals;

public class ConsultaTopItemsFavoritosTest extends PruebaConBDTest {
    private CouponRepositorio couponRepositorio;
    private ConsultaTopItemsFavoritos consultaTopItemsFavoritos;

    @Before
    public void iniciarDatos(){
        this.couponRepositorio = new CouponRepositorio(getDb());
        ServicioItems servicioItems = new ServicioItems(couponRepositorio);
        this.consultaTopItemsFavoritos = new ConsultaTopItemsFavoritos(servicioItems);
    }

    @Test
    public void consultaItemsFavoritosTest(){
        User user1 = new UserFabrica().get();
        User user2 = new UserFabrica().get();
        User user3 = new UserFabrica().get();
        couponRepositorio.insertarUsuario(user1).get().get();
        couponRepositorio.insertarUsuario(user2).get().get();
        couponRepositorio.insertarUsuario(user3).get().get();

        Item item1 = new ItemFabrica().get();
        Item item2 = new ItemFabrica().get();
        Item item3 = new ItemFabrica().get();

        couponRepositorio.insertarItem(item1).get().get();
        couponRepositorio.insertarItem(item2).get().get();
        couponRepositorio.insertarItem(item3).get().get();

        Bookmark bookmark1 = new BookmarkFabrica().setItem(item1).get();
        Bookmark bookmark2 = new BookmarkFabrica().setItem(item2).get();
        Bookmark bookmark3 = new BookmarkFabrica().setItem(item3).get();

        couponRepositorio.insertarBookmark(bookmark1, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark2, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark3, user1.getId()).get().get();//1

        couponRepositorio.insertarBookmark(bookmark2, user2.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark3, user2.getId()).get().get();//2

        couponRepositorio.insertarBookmark(bookmark3, user3.getId()).get().get();//3

        List<BookmarkTopDTO> resp = consultaTopItemsFavoritos.consultar().get();
        assertEquals("La lista de respuesta tiene size 3", 3, resp.size());
        assertEquals("El id del item 0 coincide", item3.getId(), resp.get(0).getId());
        assertEquals("La cantidad del item 0 coincide", 3, resp.get(0).getQuantity().longValue());
        assertEquals("El id del item 1 coincide", item2.getId(), resp.get(1).getId());
        assertEquals("La cantidad del item 1 coincide", 2, resp.get(1).getQuantity().longValue());
        assertEquals("El id del item 2 coincide", item1.getId(), resp.get(2).getId());
        assertEquals("La cantidad del item 2 coincide", 1, resp.get(2).getQuantity().longValue());

    }
}
