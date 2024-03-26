package consultas;

import javax.inject.Inject;
import dominio.servicio.ServicioItems;
import infraestructura.acl.dto.BookmarkTopDTO;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import play.Logger;

public class ConsultaTopItemsFavoritos {
    private final Logger.ALogger logger = Logger.of(this.getClass());

    private final ServicioItems servicioItems;

    @Inject
    public ConsultaTopItemsFavoritos(ServicioItems servicioItems) {
        this.servicioItems = servicioItems;
    }

    public Future<List<BookmarkTopDTO>> consultar(){
        logger.info("Inicia la consulta de top 5 ");

        return servicioItems.consultarTopBookmarks();
    }
}
