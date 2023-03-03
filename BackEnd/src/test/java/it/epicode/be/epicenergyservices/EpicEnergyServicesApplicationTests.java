package it.epicode.be.epicenergyservices;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import it.epicode.be.epicenergyservices.controllers.AuthController;
import it.epicode.be.epicenergyservices.controllers.CustomerController;
import it.epicode.be.epicenergyservices.models.Address;
import it.epicode.be.epicenergyservices.models.Customer;
import it.epicode.be.epicenergyservices.models.Municipality;
import it.epicode.be.epicenergyservices.models.Province;
import it.epicode.be.epicenergyservices.payloads.LoginRequest;
import it.epicode.be.epicenergyservices.services.AddressService;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@AutoConfiguration
class EpicEnergyServicesApplicationTests {
	
	@Resource
	CustomerController cc;
	@Resource
	AuthController ac;
	@Resource
	PasswordEncoder pe;
	@Resource
	AddressService aS;	
	private static String token = "";
	
	Province p1;
	Municipality m1;
	Address a1;
	Address a2;
	Customer c1;
	
	
	@BeforeEach
	public void getEntities() {
		p1 = Province.builder().acronym("RC")
				.name("Reggio Calabria")
				.region("Calabria")
				.build();
		m1 = Municipality.builder()
				.districtCode(63)
				.province(p1)
				.name("Reggio di Calabria")
				.build();
		a1 = Address.builder()
				.postCode(89122)
				.number(4).city("Reggio Calabria").street("via Vito Superiore")
				.municipality(m1)
				.build();
		a2 = Address.builder()
				.postCode(89100)
				.number(6).city("Reggio Calabria").street("via Dei Pazzi")
				.municipality(m1)
				.build();
		c1 = Customer.builder().contactSurname("Palumbo")
				.subDate(LocalDate.parse("2020-12-19"))
				.lastDate(LocalDate.parse("2021-03-31"))
				.email("alighiero.leone@hotmail.com")
				.contactEmail("silvano.santoro@yahoo.com")
				.legalAddress(a1)
				.productionAddress(a1)
				.build();
	}
	
	@Test
	@DisplayName("Data CheckUp")
	void testData() {
		assertEquals(c1.getLegalAddress(), c1.getProductionAddress());
		c1.setProductionAddress(a2);
		assertTrue(c1.getLegalAddress()!= c1.getProductionAddress());
	}
	
	@Test
	@DisplayName("Database Data Correctly Inserted")
	void testDbData () {
		assertEquals(aS.findById(5).get().getCity(), "Fiumicino");
	}
	
	@Test
	@DisplayName("Http Call CheckUp")
	void testGetHttpCall () {
		cc.getById(1).getBody().toString().contains("silvano");
	}
	
	@Test
	@DisplayName("Http Endpoint CheckUp under Http Call")
	void testRealCall () {
		String body = "{\"username\" : \"pippobaudo\",  \"password\" : \"pippoinzaghi\"}";
		String response = this.connect("http://localhost:9090/auth/login", body);
		assertTrue(response.contains("token"));
	}
	
	@Test
	@DisplayName("Check Login")
	void testLogin () {
		LoginRequest lr = new LoginRequest();
		lr.setUsername("pippobaudo");
		lr.setPassword("pippoinzaghi");
		ac.authenticateUser(lr).getBody().toString().contains("token");
	}
	
	@Test
	@DisplayName("Check Route")
	void testRoute() {
		List<Customer> list = cc.getAll().getBody();
		assertTrue(list.size() == 6);
		assertEquals(list.get(0).getBusinessName(), "Bruno, Ferretti e Leone s.r.l.");
	}
	
	String connect(String url, String body) {
		String response = null;
		try {
			URL x = new URL(url);
			HttpURLConnection httpCon = (HttpURLConnection) x.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("POST");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Accept", "application/json");
			if (token != "")
				httpCon.setRequestProperty("Authorization", "Bearer " + token);
			OutputStream os = httpCon.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();
			os.close();
			httpCon.connect();
			BufferedInputStream bis = new BufferedInputStream(httpCon.getInputStream());
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			int result2 = bis.read();
			while (result2 != -1) {
				buf.write((byte) result2);
				result2 = bis.read();
			}
			response = buf.toString();
		} catch (MalformedURLException e) {
			log.info("L'indirizzo immesso non sembra essere corretto.");
		} catch (ProtocolException e) {
			log.info("Il protocollo utilizzato non viene accettato.");
		} catch (UnsupportedEncodingException e) {
			log.info("Il parametro di codifica impostato non è supportato");
		} catch (IOException e) {
			log.info("Non è stato possibile portare a termine la chiamata http.");
		} catch (IllegalStateException e) {
			log.info("Non è stato possibile avviare la chiamata.");
		} catch (NullPointerException e) {
			log.info("Non è stato possibile leggere qualche dato fornito.");
		}
		return response;
	}
}
