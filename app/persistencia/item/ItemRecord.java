package persistencia.item;

public class ItemRecord {

    private String id;
    private float price;

    public ItemRecord(String id, float price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }
}
