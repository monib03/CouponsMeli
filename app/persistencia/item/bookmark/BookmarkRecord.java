package persistencia.item.bookmark;


import java.sql.Timestamp;

public class BookmarkRecord {

    private Timestamp bookmarked_date;
    private String item_id;
    private String user_id;

    public BookmarkRecord(Timestamp bookmarked_date, String item_id, String user_id) {
        this.bookmarked_date = bookmarked_date;
        this.item_id = item_id;
        this.user_id = user_id;
    }

    public Timestamp getBookmarked_date() {
        return bookmarked_date;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
