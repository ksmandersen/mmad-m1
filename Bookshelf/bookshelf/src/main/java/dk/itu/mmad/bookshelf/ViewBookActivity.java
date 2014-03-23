package dk.itu.mmad.bookshelf;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class ViewBookActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        long bookId = intent.getLongExtra(BookListFragment.INTENT_EXTRA_BOOK_ID, -1);
        return ViewBookFragment.newInstance(bookId);
    }
}
