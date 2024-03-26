package persistencia.user;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.customizer.BindBean;

public interface UserDAO {

    @SqlUpdate("INSERT INTO users(id)" +
            " VALUES(:id)")
    boolean registrar(@BindBean UserRecord user);

}