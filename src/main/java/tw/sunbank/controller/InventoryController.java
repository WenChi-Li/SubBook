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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.transaction.Transactional;
import tw.sunbank.model.bean.BookBean;
import tw.sunbank.model.bean.InventoryBean;
import tw.sunbank.model.dao.BookRepository;
import tw.sunbank.model.dto.InventoryCreateDto;
import tw.sunbank.model.service.InventoryService;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@Transactional
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private BookRepository bookRepository;

//	庫存新增
	@PostMapping("/InventoryInsert.controller")
	@ResponseBody
	public ResponseEntity<?> processInsert(@ModelAttribute InventoryCreateDto dto) {
	    try {
	        System.out.println(dto);

	        InventoryBean bean = new InventoryBean();
	        Optional<BookBean> findByBookId = bookRepository.findById(dto.getiSBN());
	        if (findByBookId.isPresent()) {
	            bean.setBookBean(findByBookId.get());
	            bean.setStoreTime(dto.getStoreTime());
	            bean.setStatus(dto.getStatus());
	        }
	        
	        inventoryService.Insert(bean);
	        System.out.println(bean);
	        
	        return new ResponseEntity<>("Y", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("N", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
//	修改庫存狀態
	@PutMapping("inventory/{id}/status")
	@ResponseStatus(HttpStatus.OK)
    public void updateInventoryStatus(@PathVariable("id") Integer inventoryId,
                                      @RequestParam("status") String status) {
        inventoryService.updateStatusByInventoryId(inventoryId, status);
    }
	
	
//	庫存刪除
	@DeleteMapping("/InventoryDelete.controller/{inventoryId}")
	@ResponseBody
	public ResponseEntity<String> processDelete(@PathVariable int inventoryId) {
		try {
			inventoryService.deleteById(inventoryId);
			return new ResponseEntity<>("Y", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
	
	
//  庫存查詢行程id
	@GetMapping("/InventorySelectId.controller/{inventoryId}")
	@ResponseBody
	public ResponseEntity<?> processSelectId(@PathVariable int inventoryId) {
		try {
			InventoryBean beanList = inventoryService.findById(inventoryId);
			System.out.println(inventoryService.findById(inventoryId));
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("查不到此庫存id", HttpStatus.OK);
		}
	}
	

//	庫存查詢全部
	@GetMapping("/InventoryFindAll.controller")
	@ResponseBody
	public ResponseEntity<?> processSelectAll() {
		try {
			List<InventoryBean> beanList = inventoryService.findAll();
			System.out.println(inventoryService.findAll());
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
}
