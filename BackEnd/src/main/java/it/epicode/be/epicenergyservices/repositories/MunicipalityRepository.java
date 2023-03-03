package it.epicode.be.epicenergyservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.be.epicenergyservices.models.Municipality;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Integer>{

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM municipalities WHERE name = :name"
            )
    Optional<Municipality> findMunicipalityByName(@Param("name") String name);
}
