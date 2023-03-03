package it.epicode.be.epicenergyservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.epicenergyservices.models.Address;
import it.epicode.be.epicenergyservices.repositories.AddressRepository;

@Service
public class AddressService {
			
	@Autowired
	private AddressRepository ar;
	
	public List<Address> findAll() {
		return ar.findAll();
	}
	
	public void add(Address a) {
		ar.save(a);
	}
	
	public Optional<Address> findById(int id) {
		return ar.findById(id);
	}
	
	public void delete(int id) {
		ar.deleteById(id);
	}
	

}
