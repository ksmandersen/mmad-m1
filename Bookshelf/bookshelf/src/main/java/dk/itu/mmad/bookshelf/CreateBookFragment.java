package dk.itu.mmad.bookshelf;

/**
 * Created by ksma on 21/03/14.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateBookFragment extends Fragment {

    // Views
    private Button saveButton;
    private EditText titleField;
    private EditText authorField;
    private EditText pagesField;

    public CreateBookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        View rootView = inflater.inflate(R.layout.fragment_create_book, container, false);

        titleField = (EditText)rootView.findViewById(R.id.create_book_title_field);
        authorField = (EditText)rootView.findViewById(R.id.create_book_author_field);
        pagesField = (EditText)rootView.findViewById(R.id.create_book_pages_field);


        // Save button
        saveButton = (Button)rootView.findViewById(R.id.button_create_book_submit);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.title = titleField.getText().toString();
                book.author = authorField.getText().toString();
                String pages = pagesField.getText().toString();
                if (!pages.isEmpty()) {
                    book.pages = Integer.valueOf(pages).intValue();
                } else {
                    book.pages = 0;
                }
                BookManager.getSharedInstance(getActivity()).insert(book);

                Activity parentActivity = getActivity();
                if (NavUtils.getParentActivityName(parentActivity) != null) {
                    parentActivity.setResult(Activity.RESULT_OK);
                    NavUtils.navigateUpFromSameTask(parentActivity);
                }
            }
        });

        return rootView;
    }
}
