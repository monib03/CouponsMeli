package persistencia.item;

import dominio.modelo.item.Item;

public final class ItemDAOAdapter {

    private ItemDAOAdapter() {
        throw new IllegalStateException("Esta clase no debe ser instanciada");
    }

    public static Item transformar (ItemRecord itemRecord){
        return new Item(itemRecord.getId(), itemRecord.getPrice());
    }

    public static ItemRecord transformar (Item item){
        return new ItemRecord(item.getId(), item.getPrice());
    }
}
