package dominio.modelo.bookmark;

import dominio.modelo.item.Item;
import org.joda.time.DateTime;

public class Bookmark {


    private DateTime bookmarked_date;
    private Item item;

    public Bookmark(DateTime bookmarked_date, Item item) {
        this.bookmarked_date = bookmarked_date;
        this.item = item;
    }

    public DateTime getBookmarked_date() {
        return bookmarked_date;
    }

    public Item getItem() {
        return item;
    }
}
