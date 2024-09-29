package com.sofe4640u.assignment1;

/**
 * Mortgage Data Model
 * Represents what data that a Mortgage Payment consists of
 */
public class Mortgage {
    private String nickname; // this nickname of the mortgage calculation payment, can be something like "houseA"
    private double mortgageAmount; // represents the total mortgage payment cost
    private double annualInterestRate; // interest rate in annual percentage (e.g. 1.50 implies 1.5% interest rate)
    private int amortizationYears; // the payment period in years (e.g. if the arortization period is 14 months, then this value should be `1` year)
    private int amortizationMonths; // the excess period in months (e.g. if the arortization period is 14 months, then this value should be `2` months)
    private String paymentFrequency; // the payment frequency, this should either be "Yearly", "Weekly" or "Bi-Weekly" for it to be valid
    private double calculatedPayment; // the resulting calculatedPayment of the mortgage. This value is derived from all other values in this data model.

    public Mortgage(String nickname, double mortgageAmount, double annualInterestRate,
                    int amortizationYears, int amortizationMonths, String paymentFrequency, double calculatedPayment) {
        this.nickname = nickname;
        this.mortgageAmount = mortgageAmount;
        this.annualInterestRate = annualInterestRate;
        this.amortizationYears = amortizationYears;
        this.amortizationMonths = amortizationMonths;
        this.paymentFrequency = paymentFrequency;
        this.calculatedPayment = calculatedPayment;
    }

    // getters of every value
    public String getNickname() {
        return nickname;
    }

    public double getMortgageAmount() {
        return mortgageAmount;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public int getAmortizationYears() {
        return amortizationYears;
    }

    public int getAmortizationMonths() {
        return amortizationMonths;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public double getCalculatedPayment() {
        return calculatedPayment;
    }

    // setters of the calculatedPayment
    public void setCalculatedPayment(double calculatedPayment) {
        this.calculatedPayment = calculatedPayment;
    }
}
