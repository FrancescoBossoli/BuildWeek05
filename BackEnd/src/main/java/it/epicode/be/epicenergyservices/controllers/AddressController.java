package it.epicode.be.epicenergyservices.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.epicenergyservices.models.Address;
import it.epicode.be.epicenergyservices.services.AddressService;

@RestController
@RequestMapping("/address")
@CrossOrigin("http://localhost:4200/")
public class AddressController {
	
	@Autowired
	private AddressService aServ;

	@PostMapping()
	public ResponseEntity<Object> add(@Valid @RequestBody Address a) { 	
		aServ.add(a);
		return new ResponseEntity<>(a, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Address>> getAll() {
		List<Address> list = aServ.findAll();		
		if( list.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		Optional<Address> clObj = aServ.findById(id);
		if( !clObj.isPresent())  return new ResponseEntity<>("INDIRIZZO NON TROVATO",HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(clObj.get(), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		Optional<Address> clObj = aServ.findById(id);		
		if( !clObj.isPresent() ) return new ResponseEntity<>("INDIRIZZO NON TROVATO",HttpStatus.NOT_FOUND);
		else {
			Address x = clObj.get();
			aServ.delete(x.getId());
		}
		return new ResponseEntity<>(String.format("Indirizzo con id %d cancellato!", id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@Valid @RequestBody Address a) {
        Optional<Address> addressOptional = aServ.findById(id);
        if(addressOptional.isPresent()) {
        	Address address = addressOptional.get();
        	address.setStreet(a.getStreet());
        	address.setNumber(a.getNumber());
        	address.setPostCode(a.getPostCode());
        	address.setCity(a.getCity());
        	address.setMunicipality(a.getMunicipality());
            aServ.add(address);
            return ResponseEntity.ok("Address succesfully updated!");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address with ID " + id + " wasn't found");        
    }
}
