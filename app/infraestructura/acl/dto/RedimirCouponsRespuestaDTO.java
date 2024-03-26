package infraestructura.acl.dto;

import java.util.List;

public class RedimirCouponsRespuestaDTO implements DTO{
    private final List<String> items_ids;
    private final Long total;

    public RedimirCouponsRespuestaDTO(List<String> items_ids, Long total) {
        this.items_ids = items_ids;
        this.total = total;
    }

    public List<String> getItems_ids() {
        return items_ids;
    }

    public Long getTotal() {
        return total;
    }
}
