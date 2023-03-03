package it.epicode.be.epicenergyservices.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.epicode.be.epicenergyservices.models.Customer;
import it.epicode.be.epicenergyservices.repositories.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository cr;
	
	public List<Customer> findAll() {
		return cr.findAll();
	}
	
	public Customer add(Customer c) {
		return cr.save(c);
	}
	
	public Optional<Customer> findById(int id) {
		return cr.findById(id);
	}
	
	public void delete(int id) {
		cr.deleteById(id);
	}
	
	public Page<Customer> getAll_page(Pageable pageable) {
		return cr.findAll(pageable);
	}
	
	public List<Customer> findAllAndOrderByProvince() {
		return cr.findAllAndOrderByProvince();
	}
	
	public List<Customer>filterByGap(double t) {
		return cr.filterByGap(t);
	}
	
	public List<Customer>filterBySubDate(LocalDate t) {
		return cr.filterBySubDate(t);
	}
	
	public List<Customer>filterByLastDate(LocalDate t) {
		return cr.filterByLastDate(t);
	}
	
	public List<Customer>filterByPartialName(String t) {
		return cr.filterByPartialName(t);
	}
}
