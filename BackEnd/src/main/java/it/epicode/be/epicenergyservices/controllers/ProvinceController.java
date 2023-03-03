package it.epicode.be.epicenergyservices.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.epicenergyservices.models.Province;
import it.epicode.be.epicenergyservices.services.ProvinceService;

@RestController
@RequestMapping("/province")
@CrossOrigin("http://localhost:4200/")
public class ProvinceController {
	
	@Autowired
	private ProvinceService pSrv;

	@GetMapping
	public ResponseEntity<List<Province>> getAll() {
		List<Province> list = pSrv.getAllProvince();		
		if( list.isEmpty() ) return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable int id) {
		Optional<Province> clObj = pSrv.getProvinceById(id);
		if( !clObj.isPresent()) {
			return new ResponseEntity<>("Provincia non trovata",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(clObj.get(), HttpStatus.OK);
	}
}
