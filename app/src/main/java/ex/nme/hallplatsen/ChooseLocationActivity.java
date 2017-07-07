package ex.nme.hallplatsen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChooseLocationActivity extends AppCompatActivity {

    private static final String TAG = "ChooseLocationActivity";

    EditText searchInput = (EditText) findViewById(R.id.location_name_edittext);
    ListView locationResults = (ListView) findViewById(R.id.location_results_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coose_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        searchInput.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged()");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged()");

            }

            public void afterTextChanged(Editable s) {
                // you can call or do what you want with your EditText here
                Log.d(TAG, "afterTextChanged()");

                //request locations

                //on response: update listview

            }
        });


    }
}
