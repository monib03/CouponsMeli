package infraestructura.acl.dto;

public class BookmarkTopDTO implements DTO{
    private String id;
    private Long quantity;

    public BookmarkTopDTO(String id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }
}
