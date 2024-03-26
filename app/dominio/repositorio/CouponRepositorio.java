package dominio.repositorio;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.item.Item;
import dominio.modelo.user.User;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import persistencia.item.ItemDAO;
import persistencia.item.ItemDAOAdapter;
import persistencia.item.bookmark.BookmarkDAO;
import persistencia.item.bookmark.BookmarkDAOAdapter;
import persistencia.item.bookmark.BookmarkTopRecord;
import persistencia.user.UserDAO;
import persistencia.user.UserDAOAdapter;
import play.Logger;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigInteger;

@Singleton
public class CouponRepositorio {

    private final Database db;
    private final Logger.ALogger logger = Logger.of(this.getClass());

    @Inject
    public CouponRepositorio(Database db) {
        this.db = db;
    }

    public Future<Option<Item>> insertarItem(Item item) {
        logger.info(String.format("Se inserta nuevo item: %s - %s", item.getId(), item.getPrice()));
        return Future.of(() -> Option.of(Jdbi.create(db.getDataSource())
                        .installPlugin(new SqlObjectPlugin())
                        .onDemand(ItemDAO.class).registrar(ItemDAOAdapter.transformar(item)))
                .flatMap(respuesta -> Boolean.TRUE.equals(respuesta)
                        ? Option.of(item)
                        : Option.none()));
    }

    public Future<Option<User>> insertarUsuario(User user) {
        logger.info(String.format("Se inserta nuevo usuario: %s ", user.getId()));
        return Future.of(() -> Option.of(Jdbi.create(db.getDataSource())
                        .installPlugin(new SqlObjectPlugin())
                        .onDemand(UserDAO.class).registrar(UserDAOAdapter.transformar(user)))
                .flatMap(respuesta -> Boolean.TRUE.equals(respuesta)
                        ? Option.of(user)
                        : Option.none()));
    }

    public Future<Option<Bookmark>> insertarBookmark(Bookmark bookmark, String idUser) {
        logger.info(String.format("Se inserta nuevo favorito: item: %s - User: %s", bookmark.getItem().getId(), idUser));
        return Future.of(() -> Option.of(Jdbi.create(db.getDataSource())
                        .installPlugin(new SqlObjectPlugin())
                        .onDemand(BookmarkDAO.class).registrar(BookmarkDAOAdapter.transformar(bookmark, idUser)))
                .flatMap(respuesta -> Boolean.TRUE.equals(respuesta)
                        ? Option.of(bookmark)
                        : Option.none()));
    }

    public Future<List<BookmarkTopRecord>> consultarTopBookmarks() {
        logger.info("Se consulta top 5 favoritos");
        return Future.of(() -> List.ofAll(Jdbi.create(db.getDataSource())
                        .installPlugin(new SqlObjectPlugin())
                        .onDemand(BookmarkDAO.class).buscarTopFavoritos()));
    }

}
