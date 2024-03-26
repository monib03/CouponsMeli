package controladores;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorValidacion;
import infraestructura.acl.dto.DTO;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.internalServerError;
import static play.mvc.Results.ok;

public interface IControlador {

    Logger.ALogger logger = Logger.of(IControlador.class);

    default CompletionStage<Result> ejecutar(Comando comando, JsonNode json) {
        return comando.ejecutar(json)
                .map(consecuencia -> consecuencia.fold(
                        this::obtenerRespuestaConsecuenciaError,
                        this::obtenerRespuestaConsecuenciaExitosa
                    ))
                .recover(recuperarEjecucion())
                .toCompletableFuture();
    }


    default Result obtenerRespuestaConsecuenciaError(Error error) {
        logger.error(error.getMensaje());
        return Match(error).of(
                Case($(instanceOf(ErrorValidacion.class)), e -> badRequest(Json.toJson(e))),
                Case($(), e -> internalServerError(Json.toJson(e))));
    }

    default Result obtenerRespuestaConsecuenciaExitosa(DTO dto) {
        logger.info("Consecuencia exitosa: " + dto.getClass());
        return ok(Json.toJson(dto));
    }

    default Function<Throwable, Result> recuperarEjecucion() {
        return this::obtenerErrorInterno;
    }

    default Result obtenerErrorInterno(Throwable t) {
        logger.error("Error interno", t);
        return internalServerError(Json.toJson("Error interno"));
    }
}