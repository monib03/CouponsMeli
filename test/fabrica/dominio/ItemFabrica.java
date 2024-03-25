package fabrica.dominio;


import com.github.javafaker.Faker;
import dominio.modelo.item.Item;

import java.util.UUID;

public class ItemFabrica {

    private Faker faker = new Faker();
    private String id;
    private float price;

    public ItemFabrica(){
        id = UUID.randomUUID().toString();
        price = (float) faker.number().numberBetween(10000, 1000000);
    }

    public Item get(){
        return new Item(id, price);
    }

    public ItemFabrica setId(String newId){
        this.id = newId;
        return this;
    }

    public ItemFabrica setPrice(float newPrice){
        this.price = newPrice;
        return this;
    }
}