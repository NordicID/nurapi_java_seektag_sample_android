package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nordicid.nurapi.NurApi;
import com.nordicid.nurapi.NurApiSocketAutoConnect;

public class MainActivity extends AppCompatActivity {

    /**
     * It's a good practice to have only one instance of NurApi.
     */
    private static final NurApi nurApi = new NurApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // NUR API RELATED BELOW:

        // Step 1: Creating an event listener to listen for NurApi events:
        nurApi.setListener(new NurApiEventListener(nurApi));
        // Step 2: Connect to a reader. For simplicity, connecting to integrated reader (this app is expected to run on HH83, or HH85).
        NurApiSocketAutoConnect auto = new NurApiSocketAutoConnect(this, nurApi);
        auto.setAddress("integrated_reader");
    }

    @Override
    protected void onDestroy() {
        Log.w("nur-test-app", "MainActivity was DESTROYED");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.w("nur-test-app", "MainActivity was STOPPED");
        super.onStop();
    }

    @Override
    protected void onStart() {
        Log.w("nur-test-app", "MainActivity was STARTED");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.w("nur-test-app", "MainActivity was RESUMED");
        super.onResume();
    }


}