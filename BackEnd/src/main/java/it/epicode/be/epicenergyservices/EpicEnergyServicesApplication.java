package it.epicode.be.epicenergyservices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import it.epicode.be.epicenergyservices.config.Beans;
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
import it.epicode.be.epicenergyservices.services.AddressService;
import it.epicode.be.epicenergyservices.services.CustomerService;
import it.epicode.be.epicenergyservices.services.InvoiceService;
import it.epicode.be.epicenergyservices.services.MunicipalityService;
import it.epicode.be.epicenergyservices.services.ProvinceService;
import it.epicode.be.epicenergyservices.services.RoleService;
import it.epicode.be.epicenergyservices.services.UserService;
import it.epicode.be.epicenergyservices.utils.DataRetriever;

@SpringBootApplication
public class EpicEnergyServicesApplication implements CommandLineRunner {
	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Beans.class);
	
	@Autowired
	private InvoiceService inServ;
	@Autowired
	private RoleService rlServ;
	@Autowired
	private CustomerService csServ;
	@Autowired
	private DataRetriever csvParser;
	@Autowired
	private MunicipalityService ms;
	@Autowired
	private ProvinceService ps;
	@Autowired
	private AddressService adServ;
	@Autowired
	private UserService usServ;
	@Autowired
	private PasswordEncoder pswE;

	public static void main(String[] args) {
		SpringApplication.run(EpicEnergyServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if( ps.getProvinceById(1).isEmpty() ) {
			loadingCVS();
			d_b();
		}
	}

	public Province save(int provinceCode, String acronym, String name, String region) {
		Province p = (Province) ctx.getBean("newProvince", provinceCode, acronym, name, region);
		ps.saveProvince(p);
		return p;
	}

	public Municipality save(int districtCode, String name, Province province) {
		Municipality m = (Municipality) ctx.getBean("newMunicipality", districtCode, name, province);
		ms.saveMunicipality(m);
		return m;
	}

	@SuppressWarnings("unchecked")
	public void loadingCVS() {
		List<Set<?>> list = csvParser.dataParse(csvParser.lineReader("src/main/resources/csv/province-italiane.csv"),
				"src/main/resources/csv/comuni-italiani.csv");
		for (Province p : (HashSet<Province>)list.get(0)) save(p.getProvinceCode(), p.getAcronym(), p.getName(), p.getRegion());		
		List<Province> plist = ps.getAllProvince();
		for (Municipality m : (HashSet<Municipality>)list.get(1)) {
			for (Province province : plist) {
				if (province.getName().equals(m.getProvince().getName())) {
					save(m.getDistrictCode(), m.getName(), province);
				}
			}
		}
	}

	@SuppressWarnings("serial")
	public void d_b() {

		Address a1 = (Address) ctx.getBean("address", 07001, 321, "Morelli Lido", "Piazza Emidio 23", ms.getMunicipalityByName("Catanzaro").get());
		Address a2 = (Address) ctx.getBean("address", 62907, 43, "Gioacchino Calabro", "Incrocio Gabriele 891", ms.getMunicipalityByName("Biancavilla").get());
		Address a3 = (Address) ctx.getBean("address", 34892, 894, "Audenico Lido", "Via Cleopatra 533", ms.getMunicipalityByName("Potenza").get());
		Address a4 = (Address) ctx.getBean("address", 18231, 91, "Sesto Cassiopea", "Incrocio Audenico 510", ms.getMunicipalityByName("Verona").get());
		Address a5 = (Address) ctx.getBean("address", 00100, 125, "Fiumicino", "Via del Gesù 33", ms.getMunicipalityByName("Milazzo").get());
		Address a6 = (Address) ctx.getBean("address", 46458, 41, "Poggio Mirteto", "Via dei Pioppi 510", ms.getMunicipalityByName("Roma").get());
		Address a7 = (Address) ctx.getBean("address", 20121, 15, "Via dei Giardini", "Piazza della Scala 1", ms.getMunicipalityByName("Milano").get());
		Address a8 = (Address) ctx.getBean("address", 73100, 5, "Via Roma", "Piazza Garibaldi 23", ms.getMunicipalityByName("Lecce").get());
		Address a9 = (Address) ctx.getBean("address", 10122, 3, "Via Pietro Micca", "Piazza Castello 4", ms.getMunicipalityByName("Torino").get());
		Address a10 = (Address) ctx.getBean("address", 00120, 11, "Via dei Portoghesi", "Piazza Navona 1", ms.getMunicipalityByName("Roma").get());
		Address a11 = (Address) ctx.getBean("address", 95100, 25, "Via Vittorio Emanuele II", "Piazza del Duomo 2", ms.getMunicipalityByName("Catania").get());
		Address a12 = (Address) ctx.getBean("address", 40126, 7, "Via Rialto", "Via Maggiore 150", ms.getMunicipalityByName("Bologna").get());
		Address a13 = (Address) ctx.getBean("address", 37121, 10, "Via Roma", "Via Bra 123", ms.getMunicipalityByName("Verona").get());
		Address a14 = (Address) ctx.getBean("address", 16121, 1, "Via Roma", "Via De Ferrari 1", ms.getMunicipalityByName("Genova").get());
		Address a15 = (Address) ctx.getBean("address", 50125, 9, "Via dei Neri", "Piazza della Signoria 15", ms.getMunicipalityByName("Firenze").get());
		Address a16 = (Address) ctx.getBean("address", 95100, 13, "Via Etnea", "Piazza Università 12", ms.getMunicipalityByName("Catania").get());
		adServ.add(a1);
		adServ.add(a2);
		adServ.add(a3);
		adServ.add(a4);
		adServ.add(a5);
		adServ.add(a6);
		adServ.add(a7);
		adServ.add(a8);
		adServ.add(a9);
		adServ.add(a10);
		adServ.add(a11);
		adServ.add(a12);
		adServ.add(a13);
		adServ.add(a14);
		adServ.add(a15);
		adServ.add(a16);

		Customer c1 = (Customer) ctx.getBean("customer", "Palumbo", LocalDate.parse("2020-12-19"),
				LocalDate.parse("2021-03-31"), "alighiero.leone@hotmail.com", "silvano.santoro@yahoo.com", 128298.45,
				"Ivonne", 734912655, "fiorentino.gatti@yahoo.com", "Bruno, Ferretti e Leone s.r.l.",
				"+97 015 22 72 8398", "361-067-9954", SocietyType.SRL, a1, a2);
		Customer c2 = (Customer) ctx.getBean("customer", "Lombardi", LocalDate.parse("2021-01-13"),
				LocalDate.parse("2021-02-27"), "raniero.pellegrino@email.it", "leone.giuliani@gmail.com", 164279.29,
				"Ausonio", 429653308, "zelida.grassi@yahoo.com", "Pellegrino e figli", "+90 8137 41169653",
				"(077) 538-0149", SocietyType.SAS, a3, a4);
		Customer c3 = (Customer) ctx.getBean("customer", "Mancini", LocalDate.parse("2020-04-16"),
				LocalDate.parse("2021-03-22"), "raoul.marino@email.it", "marianita.esposito@yahoo.it", 71393.77,
				"Orfeo", 725021198, "lidia.greco@yahoo.com", "Marino, Rizzo e Costantin Group", "+52 94 19140014",
				"(246) 180-2608", SocietyType.SPA, a5, a6);
		Customer c4 = (Customer) ctx.getBean("customer", "Rossi", LocalDate.parse("2021-01-15"), 
				LocalDate.parse("2021-06-30"), "luca.rossi@gmail.com", "francesca.verdi@yahoo.it", 27543.22, 
				"Matteo", 3467890123L, "simona.bianchi@gmail.com", "Rossi s.r.l.", "+39 02 8736 9265", 
				"123-456-7890", SocietyType.SRL, a11, a13);
		Customer c5 = (Customer) ctx.getBean("customer", "Ricci", LocalDate.parse("2021-02-01"), 
				LocalDate.parse("2021-07-31"), "giulia.ricci@libero.it", "luca.bianchi@gmail.com", 15000.00, 
				"Marco", 3335554444L, "valentina.neri@tiscali.it", "Ricci & C. s.a.s.", "+39 051 2345678", 
				"098-765-4321", SocietyType.SAS, a14, a15);
		Customer c6 = (Customer) ctx.getBean("customer", "Rossi", LocalDate.parse("2018-11-01"), 
				LocalDate.parse("2022-12-31"), "giuseppe.roma@libero.it", "mario.verdi@tin.it", 54678.90, 
				"Giovanna", 366998755, "luigi.bianchi@virgilio.it", "Rossi s.r.l.", "+39 06 89763521", 
				"219-128-7278", SocietyType.SRL, a9, a10);

		User u1 = (User) ctx.getBean("user", "Baudo", "pippi@gmail.com", "Pippo", pswE.encode("pippoinzaghi"), "pippobaudo");
		User u2 = (User) ctx.getBean("user", "Jino", "gino@gmail.com", "Gino", pswE.encode("biricchino"),"Jinetto");
		User u3 = (User) ctx.getBean("user", "Vendemmia", "antonello@gmail.com", "Antonello", pswE.encode("antonello123"), "Anton");
		User u4 = (User) ctx.getBean("user", "De la Vega", "zorro@gmail.com", "Diego", pswE.encode("zorro123"), "Zorro");
		User u5 = (User) ctx.getBean("user", "Johnson", "johnson@gmail.com", "John", pswE.encode("password123"), "Johnny");
		User u6 = (User) ctx.getBean("user", "Gonzalez", "gonzalez@gmail.com", "Maria", pswE.encode("maria123"), "Marie");
		User u7 = (User) ctx.getBean("user", "Smith", "smith@gmail.com", "Emily", pswE.encode("emily456"), "Emmy");
		User u8 = (User) ctx.getBean("user", "Kim", "kim@gmail.com", "Haejin", pswE.encode("haejin789"), "Hae");
		
		
		Role r1 = (Role) ctx.getBean("role", RoleType.Administrator);
		Role r2 = (Role) ctx.getBean("role", RoleType.User);
		
		Invoice i1 = (Invoice) ctx.getBean("invoice", 2020, LocalDate.parse("2020-07-15"), 3642.65, c1, InvoiceState.EMITTED);
		Invoice i2 = (Invoice) ctx.getBean("invoice", 2020, LocalDate.parse("2020-11-10"), 454.95, c2, InvoiceState.PAID);
		Invoice i3 = (Invoice) ctx.getBean("invoice", 2020, LocalDate.parse("2020-08-07"), 3678.09, c2, InvoiceState.PENDING);
		Invoice i4 = (Invoice) ctx.getBean("invoice", 2023, LocalDate.parse("2023-02-28"), 4500.00, c4, InvoiceState.PENDING);
		Invoice i5 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2021-03-01"), 2156.78, c4, InvoiceState.PAID);
		Invoice i6 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2021-03-05"), 1500.50, c5, InvoiceState.PAID);
		Invoice i7 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2021-03-10"), 2789.23, c6, InvoiceState.PENDING);
		Invoice i8 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2023-03-01"), 1234.56, c6, InvoiceState.EMITTED);
		Invoice i9 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2021-03-20"), 4567.89, c3, InvoiceState.PAID);
		Invoice i10 = (Invoice) ctx.getBean("invoice", 2021, LocalDate.parse("2021-03-25"), 3750.25, c3, InvoiceState.PENDING);
		
		
		rlServ.save(r1);
		rlServ.save(r2);
		u1.setRoles(new HashSet<>() {{add(r1);}});
		u2.setRoles(new HashSet<>() {{add(r2);}});
		u3.setRoles(new HashSet<>() {{add(r2);}});
		u4.setRoles(new HashSet<>() {{add(r1);}});
		u5.setRoles(new HashSet<>() {{add(r2);}});
		u6.setRoles(new HashSet<>() {{add(r2);}});
		u7.setRoles(new HashSet<>() {{add(r2);}});
		u8.setRoles(new HashSet<>() {{add(r2);}});
		csServ.add(c1);
		csServ.add(c2);
		csServ.add(c3);
		csServ.add(c4);
		csServ.add(c5);
		csServ.add(c6);
		usServ.save(u1);
		usServ.save(u2);
		usServ.save(u3);
		usServ.save(u4);
		usServ.save(u5);
		usServ.save(u6);
		usServ.save(u7);
		usServ.save(u8);
		inServ.add(i1);
		inServ.add(i2);
		inServ.add(i3);
		inServ.add(i4);
		inServ.add(i5);
		inServ.add(i6);
		inServ.add(i7);
		inServ.add(i8);
		inServ.add(i9);
		inServ.add(i10);
		c1.setInvoices(new ArrayList<>() {{add(i1);}});
		c2.setInvoices(new ArrayList<>() {{add(i2); add(i3);}});
		c3.setInvoices(new ArrayList<>() {{add(i9); add(i10);}});
		c4.setInvoices(new ArrayList<>() {{add(i4); add(i5);}});
		c5.setInvoices(new ArrayList<>() {{add(i6);}});
		c6.setInvoices(new ArrayList<>() {{add(i7); add(i8);}});
		csServ.add(c1);
		csServ.add(c2);
		csServ.add(c3);
		csServ.add(c4);
		csServ.add(c5);
		csServ.add(c6);
	}
}
