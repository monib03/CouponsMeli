package controladores;

import consultas.ConsultaTopItemsFavoritos;
import dominio.respuestas.ErrorValidacion;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class CouponsControlador extends Controller implements IControlador {

    private final ConsultaTopItemsFavoritos consultaTopItemsFavoritos;
    @Inject
    public CouponsControlador(ConsultaTopItemsFavoritos consultaTopItemsFavoritos) {
        this.consultaTopItemsFavoritos = consultaTopItemsFavoritos;
    }

    public CompletionStage<Result> consultarTopItemsFavoritos() {
        return consultaTopItemsFavoritos.consultar()
                .map(consulta -> ok(Json.toJson(consulta)))
                .recover(this::respuestaRecover)
                .toCompletableFuture();
    }

    private Result respuestaRecover(Throwable e) {
        logger.error("Error interno", e);
        return internalServerError(Json.toJson(new ErrorValidacion("Error interno")));
    }
}
