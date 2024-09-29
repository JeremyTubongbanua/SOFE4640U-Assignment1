package com.sofe4640u.assignment1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MortgagePaymentsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mortgagePayments.db";

    public MortgagePaymentsDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Run when this instance is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // this executes a query to create the table
        db.execSQL("CREATE TABLE mortgages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nickname TEXT," +
                "mortgage_amount REAL," +
                "annual_interest_rate REAL," +
                "amortization_years INTEGER," +
                "amortization_months INTEGER," +
                "payment_frequency TEXT," +
                "calculated_payment REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Get all Mortgages that are currently in the database
     * @return List of Mortgages that the user has previously created
     */
    public List<Mortgage> getAllMortgages() {
        List<Mortgage> mortgages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mortgages", null);
        while (cursor.moveToNext()) {
            Mortgage mortgage = new Mortgage(
                    cursor.getString(cursor.getColumnIndexOrThrow("nickname")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("mortgage_amount")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("annual_interest_rate")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("amortization_years")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("amortization_months")),
                    cursor.getString(cursor.getColumnIndexOrThrow("payment_frequency")),
                    cursor.getDouble(cursor.getColumnIndex("calculated_payment"))
            );
            mortgages.add(mortgage);
        }
        cursor.close();
        return mortgages;
    }
}
