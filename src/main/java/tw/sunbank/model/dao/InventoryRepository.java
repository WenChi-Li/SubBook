package tw.sunbank.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import tw.sunbank.model.bean.InventoryBean;

public interface InventoryRepository extends JpaRepository<InventoryBean, Integer> {

//    Optional<InventoryBean> findByBookBeaniSBN(int iSBN); // Use BookBean's ISBN property

	
	@Transactional
    @Modifying
    @Query("UPDATE InventoryBean inv SET inv.status = :status WHERE inv.InventoryId = :inventoryId")
    void updateStatusByInventoryId(Integer inventoryId, String status);
	
	
}