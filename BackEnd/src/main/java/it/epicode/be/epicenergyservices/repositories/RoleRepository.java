package it.epicode.be.epicenergyservices.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.be.epicenergyservices.models.Role;
import it.epicode.be.epicenergyservices.models.RoleType;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleType r);
}