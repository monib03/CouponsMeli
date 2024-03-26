package persistencia.item;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.customizer.BindBean;
public interface ItemDAO {

    @SqlUpdate("INSERT INTO items(id, price)" +
            " VALUES(:id, :price)")
    boolean registrar(@BindBean ItemRecord item);

}
