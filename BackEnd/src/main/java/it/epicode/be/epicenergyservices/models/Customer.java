package it.epicode.be.epicenergyservices.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="business_name")
	private String businessName;
	@Column(name="vat_number")
	private long vatNumber;
	private String email;
	@Column(name="subscription_date")
	private LocalDate subDate;
	@Column(name="last_contact_date")
	private LocalDate lastDate;
	@Column(name="gross_annual_pay")
	private double gap;
	private String pec;
	private String phone;
	@Column(name="contact_email")
	private String contactEmail;
	@Column(name="contact_name")
	private String contactName;
	@Column(name="contact_surname")
	private String contactSurname;
	@Column(name="contact_phone")
	private String contactPhone;
	@Enumerated(EnumType.STRING)
	private SocietyType type;
	@ManyToOne
    @JsonBackReference
    private Address legalAddress; 
    @ManyToOne
    @JsonBackReference
    private Address productionAddress; 
    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

}
