package hello.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SecurityStockRepository extends CrudRepository<security_stock, String> {
    @Query(value = "select p from security_stock p where p.User_id = ?1")
    List<security_stock> getStockByUserId(String User_id);
}