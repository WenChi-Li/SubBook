package tw.sunbank.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.sunbank.model.bean.BookBean;



@Repository
public interface BookRepository extends JpaRepository<BookBean, Integer> {

//	前台 - 查詢名稱
	Optional<BookBean> findByName(String Name);

	
	
}
