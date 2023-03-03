package it.epicode.be.epicenergyservices.payloads;

import java.time.LocalDate;

import it.epicode.be.epicenergyservices.models.InvoiceState;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvoiceBody {
	private int year;
	private LocalDate date;
	private double total;
	private CustomerBody customer;
	private InvoiceState state;
	
	
}
