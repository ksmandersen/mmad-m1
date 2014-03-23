package dk.itu.mmad.bookshelf;

/**
 * Created by ksma on 21/03/14.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateBookFragment extends Fragment {

    // Views
    private Button saveButton;
    private Button scanButton;
    private EditText titleField;
    private EditText authorField;
    private EditText pagesField;
    private EditText isbnField;


    public CreateBookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_book, container, false);

        titleField = (EditText)rootView.findViewById(R.id.create_book_title_field);
        authorField = (EditText)rootView.findViewById(R.id.create_book_author_field);
        pagesField = (EditText)rootView.findViewById(R.id.create_book_pages_field);
        isbnField = (EditText)rootView.findViewById(R.id.create_book_isbn_field);

        // Scan button
        scanButton = (Button)rootView.findViewById(R.id.button_new_book_scan);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
            }
        });


        // Save button
        saveButton = (Button)rootView.findViewById(R.id.button_create_book_submit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.title = titleField.getText().toString();
                book.author = authorField.getText().toString();
                book.isbn = isbnField.getText().toString();
                String pages = pagesField.getText().toString();
                if (!pages.isEmpty()) {
                    book.pages = Integer.valueOf(pages).intValue();
                } else {
                    book.pages = 0;
                }
                BookManager.getSharedInstance(getActivity()).insertBook(book);

                Activity parentActivity = getActivity();
                if (NavUtils.getParentActivityName(parentActivity) != null) {
                    parentActivity.setResult(Activity.RESULT_OK);
                    NavUtils.navigateUpFromSameTask(parentActivity);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            Log.i("SCAN", "Scan result is back!");
            fillBookDetails(scanResult.getContents());
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fillBookDetails(String isbn) {
        isbnField.setText(isbn);

        // Do web request to Google Books API to
        // search by ISBN number and fill the remaining fields.
    }
}
