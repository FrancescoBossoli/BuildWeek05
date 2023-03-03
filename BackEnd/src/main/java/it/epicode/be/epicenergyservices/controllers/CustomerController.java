package it.epicode.be.epicenergyservices.controllers;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.be.epicenergyservices.models.Customer;
import it.epicode.be.epicenergyservices.payloads.CustomerBody;
import it.epicode.be.epicenergyservices.services.CustomerService;

@Controller
@RestController
@RequestMapping("/customer")
@CrossOrigin("http://localhost:4200/")
public class CustomerController {
	
	@Autowired
	private CustomerService serv;
	
	@PostMapping()
	public ResponseEntity<Object> add(@Valid @RequestBody CustomerBody c) {
		Customer c1 = new Customer();
		c1.setBusinessName(c.getBusinessName());
		c1.setVatNumber(c.getVatNumber());
		c1.setEmail(c.getEmail());
		c1.setSubDate(c.getSubDate());
		c1.setLastDate(c.getLastDate());
		c1.setGap(c.getGap());
		c1.setPec(c.getPec());
		c1.setPhone(c.getPhone());
		c1.setContactEmail(c.getContactEmail());
		c1.setContactName(c.getContactName());
		c1.setContactSurname(c.getContactSurname());
		c1.setContactPhone(c.getContactPhone());
		c1.setType(c.getType());
		c1.setLegalAddress(c.getLegalAddress());
		c1.setProductionAddress(c.getProductionAddress());  	
		serv.add(c1);
		return new ResponseEntity<>(c1, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAll() {
		List<Customer> list = serv.findAll();
		
		if( list.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Customer> clObj = serv.findById(id);
		if( !clObj.isPresent()) {
			return new ResponseEntity<>("CLIENTE NON TROVATO",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(clObj.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		Optional<Customer> clObj = serv.findById(id);
		
		if( !clObj.isPresent() )  {
			return new ResponseEntity<>("CLIENTE NON TROVATO",HttpStatus.NOT_FOUND);
		} else {
			Customer x = clObj.get();
			serv.delete(x.getId());
		}
		return new ResponseEntity<>(
				String.format("Cliente con id %d cancellato!", id), HttpStatus.OK);
	}
	
	@GetMapping("by_name")
	public ResponseEntity<Object> getAll_PageByName(@RequestParam(defaultValue = "5", required = false) 
    Integer pageSize, @RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "contactName", required = false) String sort) {
		Sort sortObj = Sort.by(sort);
		Pageable paging  = PageRequest.of(page, pageSize, sortObj);
		Page<Customer> customer = serv.getAll_page(paging);
		if( customer.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping("by_gap_{p}")
	public ResponseEntity<Object> getAll_PageByGap(@PathVariable String p, @RequestParam(defaultValue = "5", required = false) 
    Integer pageSize, @RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "gap", required = false) String sort) {
		Pageable paging;
		String sorting = p.toUpperCase();
		Sort sortObj = Sort.by(sort);
		if(sorting.equals("DESC")) {
			paging  = PageRequest.of(page, pageSize, sortObj.descending());
		} else if (sorting.equals("ASC")) {
			paging  = PageRequest.of(page, pageSize, sortObj);
		} else {
			return new ResponseEntity<>("Parametro non accettato", HttpStatus.BAD_REQUEST);
		}
		Page<Customer> customer = serv.getAll_page(paging);
		if( customer.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping("by_date_{p}")
	public ResponseEntity<Object> getAll_PageByDate(@PathVariable String p,@RequestParam(defaultValue = "5", required = false) 
    Integer pageSize, @RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "subDate", required = false) String sort) {
		Pageable paging;
		String sorting = p.toUpperCase();
		Sort sortObj = Sort.by(sort);
		if(sorting.equals("DESC")) {
			paging  = PageRequest.of(page, pageSize, sortObj.descending());
		} else if (sorting.equals("ASC")) {
			paging  = PageRequest.of(page, pageSize, sortObj);
		} else {
			return new ResponseEntity<>("Parametro non accettato", HttpStatus.BAD_REQUEST);
		}
		Page<Customer> customer = serv.getAll_page(paging);
		if( customer.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	

	@GetMapping("by_last_date_{p}")
	public ResponseEntity<Object> getAll_PageByLastDate(@PathVariable String p,@RequestParam(defaultValue = "5", required = false) 
    Integer pageSize, @RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "lastDate", required = false) String sort) {
		Pageable paging;
		String sorting = p.toUpperCase();
		Sort sortObj = Sort.by(sort);
		if(sorting.equals("DESC")) {
			paging  = PageRequest.of(page, pageSize, sortObj.descending());
		} else if (sorting.equals("ASC")) {
			paging  = PageRequest.of(page, pageSize, sortObj);
		} else {
			return new ResponseEntity<>("Parametro non accettato", HttpStatus.BAD_REQUEST);
		}
		Page<Customer> customer = serv.getAll_page(paging);
		if( customer.isEmpty() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	@GetMapping("order_by_province")
	public ResponseEntity<Object> getCustomerByProvince(){
		return new ResponseEntity<>(serv.findAllAndOrderByProvince(), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_gap={t}")
	public ResponseEntity<Object> filterCustomerByGap(@PathVariable double t) {
		return new ResponseEntity<>(serv.filterByGap(t), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_sub_date={t}")
	public ResponseEntity<Object> filterCustomerBySubDate(@PathVariable String t) {
		return new ResponseEntity<>(serv.filterBySubDate(LocalDate.parse(t)), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_last_date={t}")
	public ResponseEntity<Object> filterCustomerByLastDate(@PathVariable String t) {
		return new ResponseEntity<>(serv.filterByLastDate(LocalDate.parse(t)), HttpStatus.OK);
	}
	
	@GetMapping("filter_by_partial_name={t}")
	public ResponseEntity<Object> filterCustomerByPartialName(@PathVariable String t) {
		return new ResponseEntity<>(serv.filterByPartialName(t), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@Valid @RequestBody CustomerBody c) {
        Optional<Customer> customerOptional = serv.findById(id);
        if (customerOptional.isPresent()) {
        	Customer customerToUpdate = customerOptional.get();
        	customerToUpdate.setBusinessName(c.getBusinessName());
        	customerToUpdate.setVatNumber(c.getVatNumber());
        	customerToUpdate.setEmail(c.getEmail());
        	customerToUpdate.setSubDate(c.getSubDate());
        	customerToUpdate.setLastDate(c.getLastDate());
        	customerToUpdate.setGap(c.getGap());
        	customerToUpdate.setPec(c.getPec());
        	customerToUpdate.setPhone(c.getPhone());
        	customerToUpdate.setContactEmail(c.getContactEmail());
        	customerToUpdate.setContactName(c.getContactName());
        	customerToUpdate.setContactSurname(c.getContactSurname());
        	customerToUpdate.setContactPhone(c.getContactPhone());
        	customerToUpdate.setType(c.getType());
        	customerToUpdate.setLegalAddress(c.getLegalAddress());
        	customerToUpdate.setProductionAddress(c.getProductionAddress());  	
        	customerToUpdate.setId(id);
            serv.add(customerToUpdate);
            return ResponseEntity.ok("Customer succesfully updated!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with ID " + id + " wasn't found");
        }
    }
}
