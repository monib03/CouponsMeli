package persistencia.user;

import dominio.modelo.user.User;
public final class UserDAOAdapter {

    private UserDAOAdapter() {
        throw new IllegalStateException("Esta clase no debe ser instanciada");
    }

    public static User transformar (UserRecord userRecord){
        return new User(userRecord.getId());
    }

    public static UserRecord transformar (User user){
        return new UserRecord(user.getId());
    }
}
