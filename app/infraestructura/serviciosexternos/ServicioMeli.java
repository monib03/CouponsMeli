package infraestructura.serviciosexternos;

import com.typesafe.config.Config;
import infraestructura.acl.dto.ItemMeliDTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import play.Logger;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class ServicioMeli {
    private static final Logger.ALogger logger = Logger.of(ServicioMeli.class);
    private WSClient wsClient;
    private Config config;

    @Inject
    public ServicioMeli(WSClient wsClient, Config config) {
        this.wsClient = wsClient;
        this.config = config;
    }

    public Future<Either<String, Option<ItemMeliDTO>>> obtenerInfoItem(String idItem, ExecutorService executorService) {
        return Future.fromCompletableFuture(executorService,
                        CompletableFuture.supplyAsync(() -> wsClient.url(
                                generarUrlConsultaItemMeli(idItem)).get().toCompletableFuture(), executorService)
                ).flatMap(wsResponses -> Future.fromCompletableFuture(executorService, wsResponses))
                .map(this::validarRespuestaConsultaItem);
    }

    private Either<String, Option<ItemMeliDTO>> validarRespuestaConsultaItem(WSResponse respuesta) {
        return Match(respuesta.getStatus()).of(
                Case($(Http.Status.OK), s -> parsearRespuestaItemMeli(respuesta).map(Option::of)),
                Case($(Http.Status.NOT_FOUND), s -> Either.right(Option.none())),
                Case($(), s -> {
                    logger.error("Error al transformar la respuesta de la consulta item, HTTP status: " + s +
                            " body: " + respuesta.getBody());
                    return Either.left("No se pudo obtener la información del item");
                })
        );
    }

    private Either<String, ItemMeliDTO> parsearRespuestaItemMeli(WSResponse respuesta) {
        return Try.of(respuesta::asJson)
                .flatMap(json -> Try.of(() -> Json.fromJson(json, ItemMeliDTO.class)))
                .onFailure(t -> logger.error("Error en parsing de dto item: ", t))
                .toEither("No se pudo procesar la información del item");
    }

    private String generarUrlConsultaItemMeli(String idItem) {
        return String.format(config.getString("servicios.externos.consultaItemMeli"), idItem);
    }
}