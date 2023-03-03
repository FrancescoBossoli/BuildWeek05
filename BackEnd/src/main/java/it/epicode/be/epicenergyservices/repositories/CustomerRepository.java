package it.epicode.be.epicenergyservices.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.epicode.be.epicenergyservices.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM customers c INNER JOIN addresses a ON c.legal_address_id = a.id INNER JOIN municipalities m ON a.municipality_id = m.id INNER JOIN provinces p ON m.province_id = p.id ORDER BY p.name ASC"
        )
        List<Customer> findAllAndOrderByProvince();
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM customers WHERE gross_annual_pay >= :t"
            )
		List<Customer> filterByGap(@Param("t") double t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM customers WHERE subscription_date = :t"
            )
		List<Customer> filterBySubDate(@Param("t") LocalDate t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM customers WHERE last_contact_date = :t"
            )
		List<Customer> filterByLastDate(@Param("t") LocalDate t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM customers WHERE business_name LIKE CONCAT('%', :t, '%')"
            )
		List<Customer> filterByPartialName(@Param("t") String t);
}
