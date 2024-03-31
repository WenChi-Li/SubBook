package tw.sunbank.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.sunbank.model.bean.BorrowingRecordBean;



@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecordBean, Integer> {


	
}
