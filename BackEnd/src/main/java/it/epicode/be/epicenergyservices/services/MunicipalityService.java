package it.epicode.be.epicenergyservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.epicenergyservices.models.Municipality;
import it.epicode.be.epicenergyservices.repositories.MunicipalityRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MunicipalityService {

	@Autowired
	private MunicipalityRepository municipalityRepo;
	
	public void saveMunicipality(Municipality p) {
		if(getMunicipalityByName(p.getName()).isEmpty()) {
			municipalityRepo.save(p);
			log.info("Municipality saved succesfully!");
		}
	}
	
	public Optional<Municipality> getMunicipalityById(int id){
		return municipalityRepo.findById(id);
	}
	
	public Optional<Municipality> getMunicipalityByName(String name){
		return municipalityRepo.findMunicipalityByName(name);
	}
	
	public List<Municipality> getAllMunicipality(){
		return municipalityRepo.findAll();
	}
	
	public void deleteMunicipalityById(int id) {
		municipalityRepo.deleteById(id);
	}
	
	public void printMunicipalityList(List<Municipality> municipalities) {
		for(Municipality p : municipalities) log.info(p.toString());
	}
}
