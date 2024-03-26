package dominio.servicio;

import dominio.repositorio.CouponRepositorio;
import infraestructura.acl.dto.BookmarkTopDTO;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import persistencia.item.bookmark.BookmarkDAOAdapter;
import persistencia.item.bookmark.BookmarkTopRecord;

import javax.inject.Inject;

public class ServicioItems {

    private CouponRepositorio couponRepositorio;

    @Inject
    public ServicioItems(CouponRepositorio couponRepositorio) {
        this.couponRepositorio = couponRepositorio;
    }


    public Future<List<BookmarkTopRecord>> insertarItem() {
        return couponRepositorio.consultarTopBookmarks();
    }

    public Future<List<BookmarkTopDTO>> consultarTopBookmarks() {
        return couponRepositorio.consultarTopBookmarks()
                .map(lista -> lista.map(BookmarkDAOAdapter::transformar));
    }
}
