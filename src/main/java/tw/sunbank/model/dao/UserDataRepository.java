package tw.sunbank.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.sunbank.model.bean.UserDataBean;



@Repository
public interface UserDataRepository extends JpaRepository<UserDataBean, Integer> {
	
//	查詢電話方法
	Optional<UserDataBean> findByphonenumber(String phonenumber);
	
//	登入Query寫法
	@Query(value = "SELECT * FROM userdata WHERE phonenumber = ?1 AND password = ?2", nativeQuery = true)
	UserDataBean accountCheck(String phonenumber, String password);
	
//	登入JPA寫法
	Optional<UserDataBean> findByPhonenumberAndPassword(String phonenumber, String password);
	
}
