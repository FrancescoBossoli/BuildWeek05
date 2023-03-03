package it.epicode.be.epicenergyservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.epicode.be.epicenergyservices.models.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
