package fabrica.dominio;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.user.User;
import io.vavr.collection.List;

import java.util.UUID;

public class UserFabrica {

    private String id;
    private List<Bookmark> marcadores;

    public UserFabrica (){
        id = UUID.randomUUID().toString();
        marcadores = List.of(new BookmarkFabrica().get(), new BookmarkFabrica().get());
    }

    public User get(){
        return new User(id, marcadores);
    }

    public UserFabrica setId(String newId){
        this.id = newId;
        return this;
    }

    public UserFabrica setMarcadores(List<Bookmark> nuevosMarcadores){
        this.marcadores = nuevosMarcadores;
        return this;
    }
}