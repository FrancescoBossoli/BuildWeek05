package it.epicode.be.epicenergyservices.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.epicode.be.epicenergyservices.models.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM invoices i INNER JOIN customers c ON i.customer_id = c.id WHERE c.business_name LIKE CONCAT('%', :t, '%')"
        )
        List<Invoice> filterInvoiceByCustomer(@Param("t") String t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM invoices WHERE state LIKE CONCAT('%', :t, '%')"
        )
        List<Invoice> filterInvoiceByState(@Param("t") String t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM invoices WHERE date = :t"
        )
        List<Invoice> filterInvoiceByDate(@Param("t") LocalDate t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM invoices WHERE year = :t"
        )
        List<Invoice> filterInvoiceByYear(@Param("t") int t);
	
	@Query(
            nativeQuery = true,
            value = "SELECT * FROM invoices WHERE total  >= :t1 AND total <= :t2" 
        )
        List<Invoice> filterInvoiceByTotal(@Param("t1") double t1, @Param("t2") double t2);
}
