package dominio.modelo.item;

public class Item implements Comparable<Item> {

    private String id;
    private float price;

    public Item(String id, float price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item otro) {
        return Float.compare(this.price, otro.price);
    }
}
