package it.epicode.be.epicenergyservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.epicode.be.epicenergyservices.models.Province;
import it.epicode.be.epicenergyservices.repositories.ProvinceRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepo;
    
    public void saveProvince(Province p) {
        if(getProvinceByName(p.getName()).isEmpty()) {
        provinceRepo.save(p);
        log.info("Province saved succesfully!");
        }
    }
    
    public Optional<Province> getProvinceById(int id){
        return provinceRepo.findById(id);
    }
    
    public Optional<Province> getProvinceByName(String name){
        return provinceRepo.findProvinceByName(name);
    }
    
    public List<Province> getAllProvince(){
        return provinceRepo.findAll();
    }
    
    public void deleteProvinceById(int id) {
        provinceRepo.deleteById(id);
    }
    
    public void printProvinceList(List<Province> provinces) {
        for(Province p : provinces) log.info(p.toString());
    }
}