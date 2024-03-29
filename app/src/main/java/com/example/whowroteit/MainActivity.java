package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText bookInput;
    private TextView authorTextView, titleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookInput = (EditText)findViewById(R.id.bookInput);
        authorTextView = (TextView)findViewById(R.id.authorText);
        titleTextView = (TextView)findViewById(R.id.titleText);

    }

    public void searchBooks(View view) {
        String queryString = bookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length()!=0)
        {
            new FetchBook(authorTextView, titleTextView).execute(queryString);
            titleTextView.setText("Loading...");
            authorTextView.setText("");
        }else{
            if(queryString.length() == 0) {
                titleTextView.setText("Please enter a search term");
                authorTextView.setText("");
            }else{
                titleTextView.setText("Please check your network connection and try again");
                authorTextView.setText("");
            }
        }

    }
}
