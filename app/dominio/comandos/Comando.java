package dominio.comandos;

import com.fasterxml.jackson.databind.JsonNode;
import dominio.respuestas.Error;
import infraestructura.acl.dto.DTO;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

public interface Comando {

    Future<Either<Error, DTO>> ejecutar(JsonNode json);

}