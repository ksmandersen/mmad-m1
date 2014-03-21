package dk.itu.mmad.bookshelf;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by ksma on 21/03/14.
 */
public class BookCursor extends CursorWrapper {
    public BookCursor(Cursor cursor) {
        super(cursor);
    }

    public Book getBook() {
        if (isBeforeFirst() || isAfterLast()) {
            return null;
        }

        Book book = new Book();
        book.setId(getLong(getColumnIndex(BookDatabaseHelper.COL_ID)));
        book.title = getString(getColumnIndex(BookDatabaseHelper.COL_TITLE));
        book.isbn = getString(getColumnIndex(BookDatabaseHelper.COL_ISBN));
        book.pages = getInt(getColumnIndex(BookDatabaseHelper.COL_PAGES));
        book.author = getString(getColumnIndex(BookDatabaseHelper.COL_AUTHOR));

        return book;
    }
}
