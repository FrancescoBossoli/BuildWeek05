package it.epicode.be.epicenergyservices.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.epicenergyservices.models.Customer;
import it.epicode.be.epicenergyservices.models.Invoice;
import it.epicode.be.epicenergyservices.payloads.CustomerBody;
import it.epicode.be.epicenergyservices.payloads.InvoiceBody;
import it.epicode.be.epicenergyservices.services.CustomerService;
import it.epicode.be.epicenergyservices.services.InvoiceService;

@Controller
@RestController
@RequestMapping("/invoice")
@CrossOrigin("http://localhost:4200/")
public class InvoiceController {
	
	@Autowired
	private InvoiceService inServ;
	@Autowired
	private CustomerService cServ;
	
	
	@GetMapping("filter_by_customer={t}")
	public ResponseEntity<Object> filterByCustomer(@PathVariable String t) {
		return new ResponseEntity<>(inServ.filterInvoiceByCustomer(t), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_state={t}")
	public ResponseEntity<Object> filterByState(@PathVariable String t) {
		return new ResponseEntity<>(inServ.filterInvoiceByState(t.toUpperCase()), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_date={t}")
	public ResponseEntity<Object> filterByDate(@PathVariable String t) {
		return new ResponseEntity<>(inServ.filterInvoiceByDate(LocalDate.parse(t)), HttpStatus.OK);
	}

	@GetMapping("filter_by_year={t}")
	public ResponseEntity<Object> filterByYear(@PathVariable int t) {
		return new ResponseEntity<>(inServ.filterInvoiceByYear(t), HttpStatus.OK);
	}
	
	@GetMapping("where_total_more_than={s}")
	public ResponseEntity<Object> filterByTotal(@PathVariable String s) {	
		double a = Double.parseDouble(s.substring(0, s.indexOf('&')));
		double b = Double.parseDouble(s.substring((s.indexOf("less_than")) + 10));
		return new ResponseEntity<>(inServ.filterInvoiceByTotal(a, b), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Object> add(@Valid @RequestBody InvoiceBody i) {
		Invoice i1 = new Invoice();
		i1.setYear(i.getYear());
		i1.setDate(i.getDate());
		i1.setTotal(i.getTotal());
		if(i.getCustomer() != null) {
			if(i.getCustomer().getId() != 0) {
				Optional<Customer> cOpt = cServ.findById(i.getCustomer().getId());
				if(cOpt.isEmpty()) {
					Customer c = createCustomer(i.getCustomer());
					i1.setCustomer(c);
				}else {
					i1.setCustomer(cOpt.get());
				}
			}else {
				Customer c = createCustomer(i.getCustomer());
				i1.setCustomer(c);
			}
		}
		i1.setState(i.getState());  	
		inServ.add(i1);
		return new ResponseEntity<>(i1, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Invoice>> getAll() {
		List<Invoice> list = inServ.findAll();
		
		if( list.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Invoice> clObj = inServ.findById(id);
		if( !clObj.isPresent()) {
			return new ResponseEntity<>("FATTURA NON TROVATA",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(clObj.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		Optional<Invoice> clObj = inServ.findById(id);
		
		if( !clObj.isPresent() )  {
			return new ResponseEntity<>("FATTURA NON TROVATA",HttpStatus.NOT_FOUND);
		} else {
			Invoice x = clObj.get();
			inServ.delete(x.getId());
		}
		return new ResponseEntity<>(
				String.format("Fattura con id %d cancellata!", id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable int id, @Valid @RequestBody InvoiceBody i) {
		Optional<Invoice> i1 = inServ.findById(id);
	    if (i1.isPresent()) {
	        Invoice invoiceToUpdate = i1.get();
	        invoiceToUpdate.setYear(i.getYear());
	        invoiceToUpdate.setDate(i.getDate());
	        invoiceToUpdate.setTotal(i.getTotal());
	        if(i.getCustomer() != null) {
				if(i.getCustomer().getId() != 0) {
					Optional<Customer> cOpt = cServ.findById(i.getCustomer().getId());
					if(cOpt.isEmpty()) {
						Customer c = createCustomer(i.getCustomer());
						invoiceToUpdate.setCustomer(c);
					}else {
						invoiceToUpdate.setCustomer(cOpt.get());
					}
				}else {
					Customer c = createCustomer(i.getCustomer());
					invoiceToUpdate.setCustomer(c);
				}
			}
	        invoiceToUpdate.setState(i.getState()); 
	        invoiceToUpdate.setId(id);
			inServ.add(invoiceToUpdate);
            return ResponseEntity.ok("Invoice succesfully updated!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice with ID " + id + " wasn't found");
        }
	}
	
	public Customer createCustomer(CustomerBody i) {
		return Customer.builder()
				.businessName(i.getBusinessName())
				.contactEmail(i.getContactEmail())
				.contactName(i.getContactName())
				.contactPhone(i.getContactPhone())
				.contactSurname(i.getContactSurname())
				.email(i.getEmail())
				.gap(i.getGap())
				.lastDate(i.getLastDate())
				.legalAddress(i.getLegalAddress())
				.pec(i.getPec())
				.phone(i.getPhone())
				.productionAddress(i.getProductionAddress())
				.subDate(i.getSubDate())
				.type(i.getType())
				.vatNumber(i.getVatNumber())
				.build();
	}
}
