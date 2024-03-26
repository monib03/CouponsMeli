package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.respuestas.Error;
import dominio.respuestas.ErrorValidacion;
import dominio.servicio.ServicioRedencionCoupons;
import infraestructura.acl.dto.DTO;
import infraestructura.acl.dto.RedimirCouponsDTO;
import infraestructura.acl.dto.RedimirCouponsRespuestaDTO;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;
import io.vavr.control.Try;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;

public class RedimirCoupon implements Comando {

    private final Logger.ALogger logger = Logger.of(this.getClass());

    private ServicioRedencionCoupons servicioRedencionCoupons;

    @Inject
    public RedimirCoupon(ServicioRedencionCoupons servicioRedencionCoupons) {
        this.servicioRedencionCoupons = servicioRedencionCoupons;
    }

    @Override
    public Future<Either<Error, DTO>> ejecutar(JsonNode json) {
        logger.info("Se va maximixar redencion de cupon" + json);
        return transformar(json).fold(
                e -> obtenerConsecuenciaFallida("Error en el json.", e.toList()),
                this::maximizarUsoCupon
        );
    }

    private Future<Either<Error, DTO>> maximizarUsoCupon(RedimirCouponsDTO dto){
        return servicioRedencionCoupons.maximizarUsoCupon(dto);
    }

    private Either<List<String>, RedimirCouponsDTO> transformar(JsonNode json) {
        return Try.of(() -> Json.fromJson(json, RedimirCouponsDTO.class))
                .toEither(List.of("Json invalido")).flatMap(RedimirCouponsDTO::validar);
    }

    private Future<Either<Error, DTO>> obtenerConsecuenciaFallida(String mensaje, List<String> errores) {
        ErrorValidacion error = new ErrorValidacion(mensaje);
        logger.error("Ocurrió un error maximixando redencion de cupon: " + error.getMensaje() + errores, error);
        return Future.successful(Either.left(error));
    }

}
