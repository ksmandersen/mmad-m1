package dk.itu.mmad.bookshelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by ksma on 21/03/14.
 */
public class BookListFragment extends Fragment {
    public static final int REQUEST_CODE_RELOAD_BOOKS = 1;
    public static final String INTENT_EXTRA_BOOK_ID = "dk.itu.mmad.bookshelf.extra_book_id";

    private static final String SEARCH_TERM_KEY = "dk.itu.mmad.bookshelf.search_term_key";

    private BookCursorAdapter bookCursorAdapter;
    private String searchTerm;

    // Views
    private ListView bookListView;
    private EditText searchTermField;
    private Button newBookButton;
    private Button searchButton;

    public BookListFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        if (savedInstanceState != null) {
            String term = savedInstanceState.getString(SEARCH_TERM_KEY);
            if (term != null) {
                searchTerm = term;
            }
        }



        // List View
        bookListView = (ListView)rootView.findViewById(R.id.book_list);
        BookCursor cursor;
        if (searchTerm != null) {
            cursor = BookManager.getSharedInstance(getActivity()).searchBooks(searchTerm);
        } else {
            cursor = BookManager.getSharedInstance(getActivity()).getAllBooks();
        }
        bookCursorAdapter = new BookCursorAdapter(getActivity(), cursor);
        bookListView.setAdapter(bookCursorAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long _id) {
                Intent intent = new Intent(getActivity(), ViewBookActivity.class);
                intent.putExtra(INTENT_EXTRA_BOOK_ID, _id);
                startActivityForResult(intent, REQUEST_CODE_RELOAD_BOOKS);
            }
        });

        // New book button
        newBookButton = (Button)rootView.findViewById(R.id.button_new_book);
        newBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateBookActivity.class);
                startActivityForResult(intent, REQUEST_CODE_RELOAD_BOOKS);
            }
        });

        // Search button
        searchButton = (Button)rootView.findViewById(R.id.button_search_books);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookManager manager = BookManager.getSharedInstance(getActivity());
                BookCursor searchCursor;
                if (searchTerm == null || searchTerm.isEmpty()) {
                    searchCursor = manager.getAllBooks();
                } else {
                    searchCursor = manager.searchBooks(searchTerm);
                }

                ((BookCursorAdapter)bookListView.getAdapter()).changeCursor(searchCursor);
            }
        });

        // Search field
        searchTermField = (EditText)rootView.findViewById(R.id.field_search_books);
        searchTermField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                searchTerm = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (searchTerm != null) {
            searchTermField.setText(searchTerm);
        }


        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SEARCH_TERM_KEY, searchTerm);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RELOAD_BOOKS) {
            if (resultCode == Activity.RESULT_OK) {
                BookCursor cursor = BookManager.getSharedInstance(getActivity()).getAllBooks();
                ((BookCursorAdapter)bookListView.getAdapter()).changeCursor(cursor);
                ((BaseAdapter)bookListView.getAdapter()).notifyDataSetChanged();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
