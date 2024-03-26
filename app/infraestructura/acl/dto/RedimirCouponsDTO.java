package infraestructura.acl.dto;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Validation;

public class RedimirCouponsDTO implements DTOValidacion<RedimirCouponsDTO> {

    private List<String> item_ids;
    private long amount;

    public RedimirCouponsDTO() {
    }

    public RedimirCouponsDTO(List<String> item_ids, long amount) {
        this.item_ids = item_ids;
        this.amount = amount;
    }

    @Override
    public Either<List<String>, RedimirCouponsDTO> validar() {
        return Validation.combine(
                        validarListaIds(item_ids),
                        validarAmount(amount)
                )
                .ap(RedimirCouponsDTO::new)
                .toEither().mapLeft(List::ofAll);
    }

    public List<String> getItem_ids() {
        return item_ids;
    }

    public float getAmount() {
        return amount;
    }

    private Validation<String, List<String>> validarListaIds(List<String> idsItem) {
        return idsItem != null ? Validation.valid(idsItem) : Validation.invalid("items_ids");
    }

    private Validation<String, Long> validarAmount(Long amount) {
        long total = amount != null ? amount : 0;
        return total > 0 ? Validation.valid(amount) :Validation.invalid("amount");
    }

}
