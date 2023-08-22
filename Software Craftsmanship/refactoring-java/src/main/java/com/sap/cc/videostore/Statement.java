package com.sap.cc.videostore;

import java.util.ArrayList;
import java.util.List;

class Statement {
    private String _name;
    private List<Rental> _rentals = new ArrayList<>();
    private double totalAmount;
    private int frequentRenterPoints;

    public Statement(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.add(arg);
    }

    public String getName() {
        return _name;
    }

    private void clearTotals(){
        totalAmount = 0;
        frequentRenterPoints = 0;
    }

    private String header(){
        String statementText = "Rental Record for " + getName() + "\n";
        return statementText;
    }

    /*private double determineAmount(Rental rental){
        double rentalAmount = 0;
        switch (rental.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                rentalAmount += 2;
                if (rental.getDaysRented() > 2)
                    rentalAmount += (rental.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                rentalAmount += rental.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                rentalAmount += 1.5;
                if (rental.getDaysRented() > 3)
                    rentalAmount += (rental.getDaysRented() - 3) * 1.5;
                break;
        }

        return rentalAmount;
    }

    private int determineFrequentRenterPoints(Rental rental){
        int frequentRenterPoints = 1;
        if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                && rental.getDaysRented() > 1)
            frequentRenterPoints++;

        return frequentRenterPoints;
    }*/

    private String formatRentalLine(Rental rental, double rentalAmount){
        return "\t" + rental.getMovie().getTitle() + "\t" +
                String.valueOf(rentalAmount) + "\n";
    }
    private String rentalLine(Rental rental){
        double rentalAmount = rental.determineAmount();
        frequentRenterPoints += rental.determineFrequentRenterPoints();
        totalAmount += rentalAmount;

        return formatRentalLine(rental, rentalAmount);
    }

    private String rentalLines(){
        String rentalLines = "";

        for (Rental rental : _rentals) {
            rentalLines += rentalLine(rental);
        }
        return rentalLines;
    }

    private String footer(){
        String statementText = "";

        statementText += "Amount owed is " + String.valueOf(totalAmount) +
                "\n";
        statementText += "You earned " + String.valueOf(frequentRenterPoints)
                +
                " frequent renter points";

        return statementText;
    }
    public String generate() {
        clearTotals();
        var statementText = header() + rentalLines() + footer();

        return statementText;
    }
}
