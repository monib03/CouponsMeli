package infraestructura.acl.dto;

import io.vavr.collection.List;
import io.vavr.control.Either;

public interface DTOValidacion<T> extends DTO {

    Either<List<String>, T> validar();

}