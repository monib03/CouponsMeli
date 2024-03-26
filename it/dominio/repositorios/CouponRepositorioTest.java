package dominio.repositorios;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.item.Item;
import dominio.modelo.user.User;
import dominio.repositorio.CouponRepositorio;
import fabrica.dominio.BookmarkFabrica;
import fabrica.dominio.ItemFabrica;
import fabrica.dominio.UserFabrica;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;
import persistencia.PruebaConBDTest;
import persistencia.item.bookmark.BookmarkTopRecord;

import static org.junit.Assert.assertEquals;

public class CouponRepositorioTest extends PruebaConBDTest {

    private CouponRepositorio couponRepositorio;

    @Before
    public void iniciarDatos(){
        this.couponRepositorio = new CouponRepositorio(getDb());

    }

    @Test
    public void testInsertarItem() {
        Item item = new ItemFabrica().get();
        Item resp = couponRepositorio.insertarItem(item).get().get();
        assertEquals("El id del item coincide", item.getId(), resp.getId());
        assertEquals("El price del item coincide", item.getPrice(), resp.getPrice(),2);
    }

    @Test
    public void testInsertarUser() {
        User user = new UserFabrica().get();
        User resp = couponRepositorio.insertarUsuario(user).get().get();
        assertEquals("El id del item coincide", user.getId(), resp.getId());
    }

    @Test
    public void testInsertarBookmark() {
        User user = new UserFabrica().get();
        couponRepositorio.insertarUsuario(user).get().get();
        Item item = new ItemFabrica().get();
        couponRepositorio.insertarItem(item).get().get();
        Bookmark bookmark = new BookmarkFabrica().setItem(item).get();
        Bookmark resp = couponRepositorio.insertarBookmark(bookmark, user.getId()).get().get();
        assertEquals("El id del item coincide", bookmark.getItem().getId(), resp.getItem().getId());
        assertEquals("La fecha del marcador coincide", bookmark.getBookmarked_date(), resp.getBookmarked_date());
    }

    @Test
    public void testConsultarTopBookmarks() {
        User user1 = new UserFabrica().get();
        User user2 = new UserFabrica().get();
        User user3 = new UserFabrica().get();
        User user4 = new UserFabrica().get();
        User user5 = new UserFabrica().get();
        User user6 = new UserFabrica().get();
        User user7 = new UserFabrica().get();
        couponRepositorio.insertarUsuario(user1).get().get();
        couponRepositorio.insertarUsuario(user2).get().get();
        couponRepositorio.insertarUsuario(user3).get().get();
        couponRepositorio.insertarUsuario(user4).get().get();
        couponRepositorio.insertarUsuario(user5).get().get();
        couponRepositorio.insertarUsuario(user6).get().get();
        couponRepositorio.insertarUsuario(user7).get().get();

        Item item1 = new ItemFabrica().get();
        Item item2 = new ItemFabrica().get();
        Item item3 = new ItemFabrica().get();
        Item item4 = new ItemFabrica().get();
        Item item5 = new ItemFabrica().get();
        Item item6 = new ItemFabrica().get();
        Item item7 = new ItemFabrica().get();
        Item item8 = new ItemFabrica().get();
        Item item9 = new ItemFabrica().get();
        Item item10 = new ItemFabrica().get();
        couponRepositorio.insertarItem(item1).get().get();
        couponRepositorio.insertarItem(item2).get().get();
        couponRepositorio.insertarItem(item3).get().get();
        couponRepositorio.insertarItem(item4).get().get();
        couponRepositorio.insertarItem(item5).get().get();
        couponRepositorio.insertarItem(item6).get().get();
        couponRepositorio.insertarItem(item7).get().get();
        couponRepositorio.insertarItem(item8).get().get();
        couponRepositorio.insertarItem(item9).get().get();
        couponRepositorio.insertarItem(item10).get().get();

        Bookmark bookmark1 = new BookmarkFabrica().setItem(item1).get();
        Bookmark bookmark2 = new BookmarkFabrica().setItem(item2).get();
        Bookmark bookmark3 = new BookmarkFabrica().setItem(item3).get();
        Bookmark bookmark4 = new BookmarkFabrica().setItem(item4).get();
        Bookmark bookmark5 = new BookmarkFabrica().setItem(item5).get();
        Bookmark bookmark6 = new BookmarkFabrica().setItem(item6).get();
        Bookmark bookmark7 = new BookmarkFabrica().setItem(item7).get();
        Bookmark bookmark8 = new BookmarkFabrica().setItem(item8).get();
        Bookmark bookmark9 = new BookmarkFabrica().setItem(item9).get();
        Bookmark bookmark10 = new BookmarkFabrica().setItem(item10).get();

        couponRepositorio.insertarBookmark(bookmark1, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark2, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark3, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark4, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark5, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark6, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark7, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark8, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark9, user1.getId()).get().get();//1
        couponRepositorio.insertarBookmark(bookmark10, user1.getId()).get().get();//1

        couponRepositorio.insertarBookmark(bookmark2, user2.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark4, user2.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark5, user2.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark8, user2.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark9, user2.getId()).get().get();//2

        couponRepositorio.insertarBookmark(bookmark1, user3.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark2, user3.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark3, user3.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark4, user3.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark5, user3.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark6, user3.getId()).get().get();//2
        couponRepositorio.insertarBookmark(bookmark8, user3.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark10, user3.getId()).get().get();//2

        couponRepositorio.insertarBookmark(bookmark1, user4.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark4, user4.getId()).get().get();//4
        couponRepositorio.insertarBookmark(bookmark5, user3.getId()).get().get();//4
        couponRepositorio.insertarBookmark(bookmark6, user4.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark8, user4.getId()).get().get();//4

        couponRepositorio.insertarBookmark(bookmark1, user5.getId()).get().get();//4
        couponRepositorio.insertarBookmark(bookmark2, user5.getId()).get().get();//4
        couponRepositorio.insertarBookmark(bookmark3, user5.getId()).get().get();//3
        couponRepositorio.insertarBookmark(bookmark4, user5.getId()).get().get();//5
        couponRepositorio.insertarBookmark(bookmark8, user5.getId()).get().get();//5

        couponRepositorio.insertarBookmark(bookmark1, user6.getId()).get().get();//5
        couponRepositorio.insertarBookmark(bookmark2, user6.getId()).get().get();//5
        couponRepositorio.insertarBookmark(bookmark3, user6.getId()).get().get();//4
        couponRepositorio.insertarBookmark(bookmark4, user6.getId()).get().get();//6
        couponRepositorio.insertarBookmark(bookmark8, user6.getId()).get().get();//6

        couponRepositorio.insertarBookmark(bookmark8, user7.getId()).get().get();//7

        List<BookmarkTopRecord> resp = couponRepositorio.consultarTopBookmarks().get();
        assertEquals("La lista de respuesta tiene size 5", 5, resp.size());
        assertEquals("El id del item 0 coincide", item8.getId(), resp.get(0).getItem_id());
        assertEquals("La cantidad del item 0 coincide", 7, resp.get(0).getQuantity().longValue());
        assertEquals("El id del item 1 coincide", item4.getId(), resp.get(1).getItem_id());
        assertEquals("La cantidad del item 1 coincide", 6, resp.get(1).getQuantity().longValue());
        assertEquals("El id del item 2 coincide", item2.getId(), resp.get(2).getItem_id());
        assertEquals("La cantidad del item 2 coincide", 5, resp.get(2).getQuantity().longValue());
        assertEquals("El id del item 3 coincide", item1.getId(), resp.get(3).getItem_id());
        assertEquals("La cantidad del item 3 coincide", 5, resp.get(3).getQuantity().longValue());
        assertEquals("El id del item 4 coincide", item3.getId(), resp.get(4).getItem_id());
        assertEquals("La cantidad del item 4 coincide", 4, resp.get(4).getQuantity().longValue());
    }

}