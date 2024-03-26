package infraestructura.acl.dto;

public class BookmarkTopDTO implements DTO{
    private String item_id;
    private Long quantity;

    public BookmarkTopDTO(String item_id, Long quantity) {
        this.item_id = item_id;
        this.quantity = quantity;
    }
}
