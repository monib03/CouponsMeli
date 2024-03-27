package fabrica.dominio;

import dominio.modelo.item.Item;

import java.util.Random;
import java.util.UUID;

public class ItemFabrica {

    private String id;
    private float price;

    public ItemFabrica(){
        Random r = new Random();
        int low = 10000;
        int high = 1000000;
        int result = r.nextInt(high-low) + low;

        id = UUID.randomUUID().toString();
        price = (float) result;
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