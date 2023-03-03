package it.epicode.be.epicenergyservices.payloads;

import java.time.LocalDate;

import it.epicode.be.epicenergyservices.models.Address;
import it.epicode.be.epicenergyservices.models.SocietyType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBody {

	private int id;
	private String businessName;
	private long vatNumber;
	private String email;
	private LocalDate subDate;
	private LocalDate lastDate;
	private double gap;
	private String pec;
	private String phone;
	private String contactEmail;
	private String contactName;
	private String contactSurname;
	private String contactPhone;
	private SocietyType type;
    private Address legalAddress; 
    private Address productionAddress; 

}
