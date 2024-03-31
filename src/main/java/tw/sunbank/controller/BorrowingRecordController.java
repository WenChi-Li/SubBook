package tw.sunbank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.transaction.Transactional;
import tw.sunbank.model.bean.BorrowingRecordBean;
import tw.sunbank.model.bean.InventoryBean;
import tw.sunbank.model.bean.UserDataBean;
import tw.sunbank.model.dao.InventoryRepository;
import tw.sunbank.model.dao.UserDataRepository;
import tw.sunbank.model.dto.BorrowingRecordCreateDto;
import tw.sunbank.model.service.BorrowingRecordService;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@Transactional
public class BorrowingRecordController {

	@Autowired
	private BorrowingRecordService borrowingRecordService;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private UserDataRepository userDataRepository;
	

//	後台新增
	@PostMapping("/BorrowingRecordInsert.controller")
	@ResponseBody
	public ResponseEntity<?> processInsert(@ModelAttribute BorrowingRecordCreateDto dto) {
	    try {
	        System.out.println(dto);
	        
	        BorrowingRecordBean bean = new BorrowingRecordBean();
	        Optional<InventoryBean> findByInventoryId = inventoryRepository.findById(dto.getInventoryId());
	        Optional<UserDataBean> findByUserId = userDataRepository.findById(dto.getUserId());
	        
	        if (findByUserId.isPresent()) {
	            bean.setInventoryBean(findByInventoryId.get());
	            bean.setUserDataBean(findByUserId.get());
	            bean.setBorrowingTime(dto.getBorrowingTime());
	            bean.setReturnTime(dto.getReturnTime());
	        }
	        
	        borrowingRecordService.Insert(bean);
	        System.out.println(bean);
	        
	        return new ResponseEntity<>("Y", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("N", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
//	後台刪除
	@DeleteMapping("/BorrowingRecordDelete.controller/{borrowingRecordId}")
	@ResponseBody
	public ResponseEntity<String> processDelete(@PathVariable int borrowingRecordId) {
		try {
			borrowingRecordService.deleteById(borrowingRecordId);
			return new ResponseEntity<>("Y", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
	
	
//  後台查詢借閱紀錄id
	@GetMapping("/BorrowingRecordSelectId.controller/{borrowingRecordId}")
	@ResponseBody
	public ResponseEntity<?> processSelectId(@PathVariable int borrowingRecordId) {
		try {
			BorrowingRecordBean beanList = borrowingRecordService.findById(borrowingRecordId);
			System.out.println(borrowingRecordService.findById(borrowingRecordId));
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("查不到此借閱紀錄id", HttpStatus.OK);
		}
	}

//	查詢全部
	@GetMapping("/BorrowingRecordFindAll.controller")
	@ResponseBody
	public ResponseEntity<?> processSelectAll() {
		try {
			List<BorrowingRecordBean> beanList = borrowingRecordService.findAll();
			System.out.println(borrowingRecordService.findAll());
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
}
