package fabrica.dominio;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.item.Item;
import org.joda.time.DateTime;

public class BookmarkFabrica {

    private DateTime bookmarked_date;
    private Item item;

    public BookmarkFabrica (){
        bookmarked_date = DateTime.now();
        item = new ItemFabrica().get();
    }

    public Bookmark get(){
        return new Bookmark(bookmarked_date, item);
    }

    public BookmarkFabrica setBookmarked_date(DateTime newBookmarkedDate){
        this.bookmarked_date = newBookmarkedDate;
        return this;
    }

    public BookmarkFabrica setItem(Item newItem){
        this.item = newItem;
        return this;
    }
}