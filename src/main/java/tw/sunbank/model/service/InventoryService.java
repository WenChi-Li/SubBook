package tw.sunbank.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sunbank.model.bean.InventoryBean;
import tw.sunbank.model.dao.InventoryRepository;

@Service
@Transactional
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
//	庫存新增資料
	public InventoryBean Insert(InventoryBean I ) throws IOException {
//		Optional<InventoryBean> BookName = 
//		if(!BookName.isPresent()) {
			return inventoryRepository.save(I);			
//		}else {
//			throw new RuntimeException("此書本已存在，無法新增");
		}
//	}
	 
	
//	修改庫存狀態
	 public void updateStatusByInventoryId(Integer inventoryId, String status) {
	        inventoryRepository.updateStatusByInventoryId(inventoryId, status);
	    }
	
	
	
//	庫存刪除資料(Id)
	public void  deleteById(Integer id) {
		Optional<InventoryBean> InventoryId = inventoryRepository.findById(id);	
		if (InventoryId.isPresent()) {
			inventoryRepository.deleteById(id);	
		}else {
			throw new RuntimeException("此庫存不存在，無法刪除");
		}
	}
	
	
// 後台查詢單一庫存id
	public InventoryBean findById(Integer id) {
		Optional<InventoryBean> InventoryId = inventoryRepository.findById(id);
		return InventoryId.orElse(null);
	}
	
	
// 查詢全部庫存
	public List<InventoryBean> findAll(){
		return inventoryRepository.findAll();
	}
	
////	查詢有沒有重複的 ISBN
//	 public Optional<InventoryBean> findByISBN(int ISBN) {
//	        return inventoryRepository.findByBookBeanSBN(ISBN);
//	    }
}
