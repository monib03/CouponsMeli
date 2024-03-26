package persistencia.item.bookmark;

public class BookmarkTopRecord {
    private String item_id;
    private Long quantity;

    public BookmarkTopRecord(String item_id, Long quantity) {
        this.item_id = item_id;
        this.quantity = quantity;
    }

    public String getItem_id() {
        return item_id;
    }

    public Long getQuantity() {
        return quantity;
    }
}
