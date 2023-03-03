package it.epicode.be.epicenergyservices.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.epicode.be.epicenergyservices.models.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer>{

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM provinces WHERE name = :name"
            )
    Optional<Province> findProvinceByName(@Param("name") String name);
}
