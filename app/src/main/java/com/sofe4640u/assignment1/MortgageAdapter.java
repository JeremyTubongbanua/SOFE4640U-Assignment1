package com.sofe4640u.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * MortgageAdapter facilitates display and management for the mortgage payments in the List View
 */
public class MortgageAdapter extends BaseAdapter {

    // context of the screen, so that mortgages could be managed
    private Context context;

    // the list of mortgages to display
    private List<Mortgage> mortgages;

    public MortgageAdapter(Context context, List<Mortgage> mortgages) {
        this.context = context;
        this.mortgages = mortgages;
    }

    @Override
    public int getCount() {
        return mortgages.size();
    }

    @Override
    public Object getItem(int position) {
        return mortgages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mortgage_item, parent, false);
        }

        Mortgage mortgage = mortgages.get(position);

        TextView nicknameTextView = convertView.findViewById(R.id.nicknameTextView);
        TextView detailsTextView = convertView.findViewById(R.id.detailsTextView);

        nicknameTextView.setText(mortgage.getNickname());
        detailsTextView.setText("Mortgage Amount: " + mortgage.getMortgageAmount() +
                "\nAnnual Interest Rate: " + mortgage.getAnnualInterestRate() +
                "\nAmortization: " + mortgage.getAmortizationYears() + " years, " +
                mortgage.getAmortizationMonths() + " months" +
                "\nPayment Frequency: " + mortgage.getPaymentFrequency() +
                "\nCalculated Payment: " + mortgage.getCalculatedPayment());

        return convertView;
    }

    public void updateData(List<Mortgage> newMortgages) {
        this.mortgages.clear();
        this.mortgages.addAll(newMortgages);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mortgages.clear();
        notifyDataSetChanged();
    }
}

