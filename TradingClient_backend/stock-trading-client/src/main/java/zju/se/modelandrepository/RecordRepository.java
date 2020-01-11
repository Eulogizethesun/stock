package zju.se.modelandrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import zju.se.modelandrepository.Record;

public interface RecordRepository extends JpaRepository<Record,Integer>{
    public List <Record> findAllByAmount(int amount);
    public List <Record> findAllByFundId(String fundId);
}
