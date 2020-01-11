package hello.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockAccountUserRepository extends CrudRepository<StockAccountUser, Integer> {
    @Query(value = "select p from StockAccountUser p where p.account_id = ?1")
    List<StockAccountUser> getUserById(Long id);
}
