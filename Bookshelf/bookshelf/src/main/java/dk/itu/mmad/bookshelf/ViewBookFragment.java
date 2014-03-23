package dk.itu.mmad.bookshelf;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ksma on 23/03/14.
 */
public class ViewBookFragment extends Fragment implements View.OnClickListener {
    private static final String BOOK_ID_KEY = "dk.itu.mmad.bookshelt.BOOK_ID_KEY";

    private TextView mtitleTextView;
    private TextView mAuthorTextView;
    private TextView mPagesTextView;
    private TextView mIsbnTextView;
    private Button mDeleteButton;

    private long mBookId;
    private Book book;

    public ViewBookFragment() {}
    public ViewBookFragment(long bookId) {
        mBookId = bookId;
    }

    public static ViewBookFragment newInstance(long bookId){
        return new ViewBookFragment(bookId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_book, container, false);

        long bookId;
        if (mBookId != -1 && mBookId != 0) {
            bookId = mBookId;
        } else {
            bookId = savedInstanceState.getLong(BOOK_ID_KEY, -1);
        }

        if (bookId != -1) {
            book = BookManager.getSharedInstance(getActivity()).getBook(bookId);

            mtitleTextView = (TextView)rootView.findViewById(R.id.view_book_title);
            mtitleTextView.setText(book.title);

            mAuthorTextView = (TextView)rootView.findViewById(R.id.view_book_author);
            mAuthorTextView.setText(book.author);

            mPagesTextView = (TextView)rootView.findViewById(R.id.view_book_pages);
            mPagesTextView.setText(String.valueOf(book.pages) + " pages");

            mIsbnTextView = (TextView)rootView.findViewById(R.id.view_book_isbn);
            mIsbnTextView.setText(book.isbn);

            mDeleteButton = (Button)rootView.findViewById(R.id.view_book_delete_button);
            mDeleteButton.setOnClickListener(this);
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        BookManager.getSharedInstance(getActivity()).removeBook(book);

        Activity parentActivity = getActivity();
        if (NavUtils.getParentActivityName(parentActivity) != null) {
            parentActivity.setResult(Activity.RESULT_OK);
            NavUtils.navigateUpFromSameTask(parentActivity);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(BOOK_ID_KEY, mBookId);
    }


}
