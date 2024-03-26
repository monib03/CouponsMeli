package infraestructura.acl.dto;

public class ItemMeliDTO implements DTO {

    private String id;
    private float price;

    public ItemMeliDTO() {
    }

    public ItemMeliDTO(String id, float price) {
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
