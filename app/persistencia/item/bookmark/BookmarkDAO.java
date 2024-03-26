package persistencia.item.bookmark;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface BookmarkDAO {

    @SqlUpdate("INSERT INTO bookmarks(item_id, user_id, bookmarked_date)" +
            " VALUES(:item_id, :user_id, :bookmarked_date)")
    boolean registrar(@BindBean BookmarkRecord bookmark);

    @SqlQuery("SELECT item_id, count(*) as quantity" +
            " FROM bookmarks" +
            " GROUP BY item_id" +
            " ORDER BY quantity desc" +
            " LIMIT 5")
    @RegisterRowMapper(BookmarkTopMapper.class)
    List<BookmarkTopRecord> buscarTopFavoritos();

}
