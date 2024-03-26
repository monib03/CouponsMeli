package persistencia.item.bookmark;

import dominio.modelo.bookmark.Bookmark;
import dominio.modelo.item.Item;
import infraestructura.acl.dto.BookmarkTopDTO;
import org.joda.time.DateTime;

import java.sql.Timestamp;

public class BookmarkDAOAdapter {

    private BookmarkDAOAdapter() {
    }

    public static Bookmark transformar (BookmarkRecord bookmarkRecord, Item item){
        return new Bookmark(new DateTime(bookmarkRecord.getBookmarked_date()), item);
    }

    public static BookmarkRecord transformar (Bookmark bookmark, String userId){
        return new BookmarkRecord(new Timestamp(bookmark.getBookmarked_date().getMillis()), bookmark.getItem().getId(), userId);
    }

    public static BookmarkTopDTO transformar (BookmarkTopRecord bookmarkTopRecord){
        return new BookmarkTopDTO(bookmarkTopRecord.getItem_id(), bookmarkTopRecord.getQuantity());
    }
}
