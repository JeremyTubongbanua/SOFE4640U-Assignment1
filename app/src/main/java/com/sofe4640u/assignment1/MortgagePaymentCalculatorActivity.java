package com.sofe4640u.assignment1;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;

public class MortgagePaymentCalculatorActivity extends AppCompatActivity {

    // text inputs of the form
    private TextInputEditText nicknameInput;
    private TextInputEditText mortgageInput;
    private TextInputEditText interestRateInput;
    private TextInputEditText amortizationYearsInput;
    private TextInputEditText amortizationMonthsInput;

    // select what the payment frequency is "Yearly"/"Monthly"/"Bi-Weekly"
    private Spinner paymentFrequencySpinner;

    // access to the database. When they press "Done" it will save it here
    private MortgagePaymentsDatabase mortgagePaymentsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_payment_calculator);

        mortgagePaymentsDatabase = new MortgagePaymentsDatabase(this);

        // Set up the input
        nicknameInput = findViewById(R.id.nicknameInput);
        mortgageInput = findViewById(R.id.mortgageInput);
        interestRateInput = findViewById(R.id.annualInterestRateInput);
        amortizationYearsInput = findViewById(R.id.amortizationPeriodYearsInput);
        amortizationMonthsInput = findViewById(R.id.amortizationPeriodMonthInput);
        paymentFrequencySpinner = findViewById(R.id.paymentFrequencySpinner);

        // Set up the Drop down for Payment Frequency
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.payment_frequency_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentFrequencySpinner.setAdapter(adapter);

        // Set up "Add To Comparison" button (this is the save button)
        Button addToComparisonButton = findViewById(R.id.button);
        addToComparisonButton.setOnClickListener(v -> addMortgageToDatabase());

        // Set up the "Back" button (they want to discard the payment calculation that they're doing right now)
        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> finish());
    }

    /**
     * // Calculate Mortgage Payment function
     * @param principal principal amount in dollars
     * @param annualInterestRate interest rate (0.0-1.0)
     * @param totalPayments n number of payments
     * @return the payment in dollars/yr
     */
    private double calculateMortgagePayment(double principal, double annualInterestRate, int totalPayments) {
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        return (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments)) /
                (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);
    }

    /**
     * After they press "Add To Comparison"
     * this function should be called
     * to add the inputs to the database appropriately
     */
    private void addMortgageToDatabase() {
        if (nicknameInput.getText().toString().isEmpty() || mortgageInput.getText().toString().isEmpty() ||
                interestRateInput.getText().toString().isEmpty() || amortizationYearsInput.getText().toString().isEmpty() ||
                amortizationMonthsInput.getText().toString().isEmpty()) {
            return;
        }

        String nickname = nicknameInput.getText().toString();
        double mortgageAmount = Double.parseDouble(mortgageInput.getText().toString());
        double annualInterestRate = Double.parseDouble(interestRateInput.getText().toString());
        int amortizationYears = Integer.parseInt(amortizationYearsInput.getText().toString());
        int amortizationMonths = Integer.parseInt(amortizationMonthsInput.getText().toString());
        String paymentFrequency = paymentFrequencySpinner.getSelectedItem().toString();

        int totalPayments;
        switch (paymentFrequency) {
            case "Yearly":
                totalPayments = amortizationYears;
                break;
            case "Bi-Weekly":
                totalPayments = (amortizationYears * 12 + amortizationMonths) * 2;
                break;
            default: // Monthly is the default
                totalPayments = amortizationYears * 12 + amortizationMonths;
                break;
        }

        double monthlyPayment = calculateMortgagePayment(mortgageAmount, annualInterestRate, totalPayments);

        // prepare a ContentValues object so that we can pass it to SQLiteOpenHelper.getWriteableDatabase().insert(..., values);
        ContentValues values = new ContentValues();
        values.put("nickname", nickname);
        values.put("mortgage_amount", mortgageAmount);
        values.put("annual_interest_rate", annualInterestRate);
        values.put("amortization_years", amortizationYears);
        values.put("amortization_months", amortizationMonths);
        values.put("payment_frequency", paymentFrequency);
        values.put("calculated_payment", monthlyPayment);

        // write to database
        mortgagePaymentsDatabase.getWritableDatabase().insert("mortgages", null, values);

        // intent that sends them back to the Main Screen
        Intent intent = new Intent(MortgagePaymentCalculatorActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
