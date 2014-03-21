package dk.itu.mmad.bookshelf;

import android.content.Context;

/**
 * Created by ksma on 21/03/14.
 */
public class Book {
    public String title;
    public String isbn;
    public String author;
    public int pages;
    private long _id;

    public static final int NOT_INSERTED_ID = -1;

    public static Book create(Context context) {
        return BookManager.getSharedInstance(context).insert(new Book());
    }

    public Book() {
        _id = NOT_INSERTED_ID;
    }

    public void setId(long newId) {
        _id = newId;
    }
}
