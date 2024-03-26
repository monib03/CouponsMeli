package persistencia.item.bookmark;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkTopMapper  implements RowMapper<BookmarkTopRecord> {

    @Override
    public BookmarkTopRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new BookmarkTopRecord(
                rs.getString("item_id"),
                rs.getLong("quantity")
        );
    }
}
