package dominio.modelo.user;

import dominio.modelo.bookmark.Bookmark;
import io.vavr.collection.List;

public class User {

    private String id;
    private List<Bookmark> marcadores;

    public User(String id, List<Bookmark> marcadores) {
        this.id = id;
        this.marcadores = marcadores;
    }

    public User(String id) {
        this.id = id;
        this.marcadores = List.empty();
    }

    public String getId() {
        return id;
    }

    public List<Bookmark> getMarcadores() {
        return marcadores;
    }
}
