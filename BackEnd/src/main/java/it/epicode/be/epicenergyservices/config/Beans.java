package it.epicode.be.epicenergyservices.config;

import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import it.epicode.be.epicenergyservices.models.Address;
import it.epicode.be.epicenergyservices.models.Customer;
import it.epicode.be.epicenergyservices.models.Invoice;
import it.epicode.be.epicenergyservices.models.InvoiceState;
import it.epicode.be.epicenergyservices.models.Municipality;
import it.epicode.be.epicenergyservices.models.Province;
import it.epicode.be.epicenergyservices.models.Role;
import it.epicode.be.epicenergyservices.models.RoleType;
import it.epicode.be.epicenergyservices.models.SocietyType;
import it.epicode.be.epicenergyservices.models.User;

@Configuration
public class Beans {

	@Bean
	@Scope("prototype")
	public Customer customer(String cs, LocalDate sd, LocalDate ld, String e, String ce, double gap, String cn,
			long vat, String pec, String bn, String p, String cp, SocietyType t, Address la, Address pa) {
		return Customer.builder().contactSurname(cs).subDate(sd).lastDate(ld).email(e).contactEmail(ce).gap(gap)
				.contactName(cn).vatNumber(vat).pec(pec).businessName(bn).phone(p).contactPhone(cp).type(t)
				.legalAddress(la).productionAddress(pa).build();
	}

	@Bean
	@Scope("prototype")
	public Address address(int ps, int n, String cy, String s, Municipality m) {
		return Address.builder().postCode(ps).number(n).city(cy).street(s).municipality(m).build();
	}

	@Bean
	@Scope("prototype")
	public Invoice invoice(int y, LocalDate d, double t, Customer c, InvoiceState s) {
		return Invoice.builder().year(y).date(d).total(t).customer(c).state(s).build();
	}

	@Bean
	@Scope("prototype")
	public User user(String s, String e, String n, String pw, String us) {
		return User.builder().surname(s).email(e).name(n).password(pw).username(us).build();
	}

	@Bean
	@Scope("prototype")
	public Role role(RoleType r) {
		return Role.builder().name(r).build();
	}

	@Bean
	@Scope("prototype")
	public Province newProvince(int provinceCode, String acronym, String name, String region) {
		return Province.builder().provinceCode(provinceCode).acronym(acronym).name(name).region(region).build();
	}

	@Bean
	@Scope("prototype")
	public Municipality newMunicipality(int districtCode, String name, Province province) {
		return Municipality.builder().districtCode(districtCode).name(name).province(province).build();
	}

}
