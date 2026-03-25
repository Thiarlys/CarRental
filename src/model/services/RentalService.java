package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {
    private Double pricePerHour;
    private Double pricePerDay;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental) {
        double minutes = Duration.between(carRental.getStart(),carRental.getFinish()).toMinutes();
        double hour = minutes / 60;

        double basicPayment;
        if(hour <= 12.0) {
            basicPayment = pricePerHour * Math.ceil(hour);
        }else {
            basicPayment = pricePerDay * Math.ceil(hour/24);
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment,tax));
    }

}
