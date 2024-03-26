package persistencia.item.bookmark;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkMapper implements RowMapper<BookmarkRecord> {

    @Override
    public BookmarkRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new BookmarkRecord(
                rs.getTimestamp("bookmarked_date"),
                rs.getString("item_id"),
                rs.getString("user_id")
        );
    }
}
