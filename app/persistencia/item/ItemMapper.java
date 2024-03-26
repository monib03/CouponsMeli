package persistencia.item;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemMapper implements RowMapper<ItemRecord> {

    @Override
    public ItemRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new ItemRecord(
                rs.getString("id"),
                rs.getFloat("price")
        );
    }
}

