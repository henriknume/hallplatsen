package ex.nme.hallplatsen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

public class ChooseLocationActivity extends AppCompatActivity {

    private static final String TAG = "ChooseLocationActivity";

    private EditText searchInput;
    private ListView locationResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchInput = (EditText) findViewById(R.id.location_name_edittext);
        locationResults = (ListView) findViewById(R.id.location_results_list);

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
            }
        });
    }
}
