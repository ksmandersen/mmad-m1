package dk.itu.mmad.bookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ksma on 21/03/14.
 */
public class BookDatabaseHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "books";
    private final static int DB_VERSION = 1;

    public final static String TABLE_NAME = "books";

    public final static String COL_ID = "_id";
    public final static String COL_TITLE = "title";
    public final static String COL_PAGES = "pages";
    public final static String COL_ISBN = "isbn";
    public final static String COL_AUTHOR = "author";


    public BookDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_NAME +
                " (_id integer primary key autoincrement, title text, author text, pages integer, isbn text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public long insertBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, book.title);
        values.put(COL_PAGES, book.pages);
        values.put(COL_ISBN, book.isbn);
        values.put(COL_AUTHOR, book.author);

        return getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public int deleteBook(Book book) {
        return getWritableDatabase().delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public BookCursor queryBook(long id) {
        Cursor cursor = getReadableDatabase()
                .query(TABLE_NAME,
                        null, // All columns
                        COL_ID + " = ? ", // select book with id
                        new String[] { String.valueOf(id) },
                        null, // group by
                        null, // having
                        null, // order by
                        "1" // limit
                );

        return new BookCursor(cursor);
    }

    public BookCursor queryBooks(String searchTerm) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "select * from " + TABLE_NAME + " " +
                "where " + COL_TITLE + " like '%" + searchTerm+ "%' " +
                "or " + COL_ISBN + " like '%" + searchTerm+ "%' " +
                "or " + COL_AUTHOR + " like '%" + searchTerm+ "%'",
                new String[]{}
        );

        return new BookCursor(cursor);
    }

    public BookCursor queryBooks() {
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " order by " + COL_AUTHOR + " ASC", new String[] {});

        return new BookCursor(cursor);
    }
}
