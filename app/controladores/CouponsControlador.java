package controladores;

import consultas.ConsultaTopItemsFavoritos;
import dominio.comandos.RedimirCoupon;
import dominio.respuestas.ErrorValidacion;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class CouponsControlador extends Controller implements IControlador {

    private final ConsultaTopItemsFavoritos consultaTopItemsFavoritos;
    private final RedimirCoupon redimirCoupon;
    @Inject
    public CouponsControlador(ConsultaTopItemsFavoritos consultaTopItemsFavoritos,
                              RedimirCoupon redimirCoupon) {
        this.consultaTopItemsFavoritos = consultaTopItemsFavoritos;
        this.redimirCoupon = redimirCoupon;
    }

    public CompletionStage<Result> consultarTopItemsFavoritos() {
        return consultaTopItemsFavoritos.consultar()
                .map(consulta -> ok(Json.toJson(consulta)))
                .recover(this::respuestaRecover)
                .toCompletableFuture();
    }

    public CompletionStage<Result> redimirCoupon(Http.Request request) {
        return ejecutar(redimirCoupon, request.body().asJson());
    }

    private Result respuestaRecover(Throwable e) {
        logger.error("Error interno", e);
        return internalServerError(Json.toJson(new ErrorValidacion("Error interno")));
    }
}
