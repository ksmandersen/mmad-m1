package dk.itu.mmad.bookshelf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ksma on 21/03/14.
 */
public class BookManager {

    private BookDatabaseHelper helper;
    private SQLiteDatabase database;

    private static BookManager SHARED_INSTANCE = null;

    public static BookManager getSharedInstance(Context context) {
        if (SHARED_INSTANCE != null) {
            return SHARED_INSTANCE;
        }

        return new BookManager(context);
    }

    private BookManager(Context context) {
        helper = new BookDatabaseHelper(context);
    }

    public Book insertBook(Book book) {
        book.setId(helper.insertBook(book));
        return book;
    }

    public void removeBook(Book book) {
        helper.deleteBook(book);
    }

    public Book getBook(long id) {
        Book book = null;
        BookCursor cursor = helper.queryBook(id);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            book = cursor.getBook();
        }

        cursor.close();
        return book;
    }

    public BookCursor searchBooks(String searchTerms) {
        return helper.queryBooks(searchTerms);
    }

    public BookCursor getAllBooks() {
        return helper.queryBooks();
    }
}
