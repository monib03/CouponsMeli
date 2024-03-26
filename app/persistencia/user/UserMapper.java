package persistencia.user;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserRecord> {

    @Override
    public UserRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new UserRecord(
                rs.getString("id")
        );
    }
}
