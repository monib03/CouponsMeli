package infraestructura.acl.adapters;

import dominio.modelo.item.Item;
import infraestructura.acl.dto.ItemMeliDTO;

public class ItemAdapter {

    private ItemAdapter() {
        throw new IllegalStateException("Esta clase no debe ser instanciada");
    }
    public static Item transformar (ItemMeliDTO itemMeliDTO){
        return new Item(itemMeliDTO.getId(), itemMeliDTO.getPrice());
    }
}
