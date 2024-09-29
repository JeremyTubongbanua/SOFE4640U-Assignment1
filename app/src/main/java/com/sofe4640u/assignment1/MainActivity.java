package com.sofe4640u.assignment1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Represents the Home Screen
 * This home screen holds a list of the Mortgage Payments that were created by the user for cost comparison analysis.
 * It also contains a Reset button that resets the database of all the mortgage payments that the user has created for cost comparison analysis
 * And a "Add Mortgage" Button for adding a Mortgage payment to their comparison list
 */
public class MainActivity extends AppCompatActivity {

    // instance that holds access to the database
    private MortgagePaymentsDatabase mortgagePaymentsDatabase;

    // holds adapter so that the list view can be managed by the mortgages state
    private MortgageAdapter mortgageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate Database
        mortgagePaymentsDatabase = new MortgagePaymentsDatabase(this);

        // Set up the List of mortgage payment calculations
        ListView mortgageListView = findViewById(R.id.mortgagePaymentsListView);
        mortgageAdapter = new MortgageAdapter(this, mortgagePaymentsDatabase.getAllMortgages());
        mortgageListView.setAdapter(mortgageAdapter);

        // Set up the "Add Mortgage" BUtton
        Button addMortgageButton = findViewById(R.id.addMortgageButton);
        addMortgageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MortgagePaymentCalculatorActivity.class);
            startActivity(intent);
        });

        // Set up the "Reset" button
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            SQLiteDatabase db = mortgagePaymentsDatabase.getWritableDatabase();
            db.delete("mortgages", null, null);
            mortgageAdapter.clear();
        });
    }

    /**
     * when we go back to the Home Screen (from the original "add mortgage" screen),
     * we want the list of mortgages to be updated)
     */
    @Override
    protected void onResume() {
        super.onResume();
        mortgageAdapter.updateData(mortgagePaymentsDatabase.getAllMortgages());
    }
}
