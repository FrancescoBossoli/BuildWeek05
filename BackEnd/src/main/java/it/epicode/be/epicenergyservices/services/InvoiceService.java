package it.epicode.be.epicenergyservices.services;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.epicenergyservices.models.Invoice;
import it.epicode.be.epicenergyservices.repositories.InvoiceRepository;

@Service
public class InvoiceService {
	@Autowired
	private InvoiceRepository ir;
	
	public List<Invoice> findAll() {
		return ir.findAll();
	}
	
	public void add(Invoice i) {
		ir.save(i);
	}
	
	public Optional<Invoice> findById(int id) {
		return ir.findById(id);
	}
	
	public void delete(int id) {
		ir.deleteById(id);
	}
	
	public List<Invoice>filterInvoiceByCustomer(String t) {
		return ir.filterInvoiceByCustomer(t);
	}
	
	public List<Invoice>filterInvoiceByState(String t) {
		return ir.filterInvoiceByState(t);
	}
	
	public List<Invoice>filterInvoiceByDate(LocalDate t) {
		return ir.filterInvoiceByDate(t);
	}
	
	public List<Invoice>filterInvoiceByYear(int t) {
		return ir.filterInvoiceByYear(t);
	}
	
	public List<Invoice>filterInvoiceByTotal(double t1, double t2) {
		return ir.filterInvoiceByTotal(t1,t2);
	}
}
