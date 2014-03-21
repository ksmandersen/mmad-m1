package dk.itu.mmad.bookshelf;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ksma on 21/03/14.
 */
public class BookCursorAdapter extends CursorAdapter {
    public BookCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return (View)inflater.inflate(R.layout.list_view, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Book book = ((BookCursor)cursor).getBook();

        TextView titleView = (TextView)view.findViewById(R.id.textView_list_view_title);
        titleView.setText(book.title);

        TextView authorView = (TextView)view.findViewById(R.id.textView_list_view_author);
        authorView.setText(book.author);
    }
}
