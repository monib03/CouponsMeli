package infraestructura.acl.dto;


import io.vavr.collection.List;

public class RedimirCouponsRespuestaDTO implements DTO{
    private final List<String> items_ids;
    private final Double total;

    public RedimirCouponsRespuestaDTO(List<String> items_ids, Double total) {
        this.items_ids = items_ids;
        this.total = total;
    }

    public List<String> getItems_ids() {
        return items_ids;
    }

    public Double getTotal() {
        return total;
    }
}
