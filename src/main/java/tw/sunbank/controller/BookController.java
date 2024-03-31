package tw.sunbank.controller;

import java.io.IOException;
import java.util.List;

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
import tw.sunbank.model.bean.BookBean;
import tw.sunbank.model.bean.UserDataBean;
import tw.sunbank.model.dto.BookCreateDto;
import tw.sunbank.model.dto.UserDataCreateDto;
import tw.sunbank.model.service.BookService;
import tw.sunbank.model.service.UserDataService;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@Transactional
public class BookController {

	@Autowired
	private BookService bookService;

//	後台新增
	@PostMapping("/BookInsert.controller")
	@ResponseBody
	public ResponseEntity<?> processInsert(@ModelAttribute BookCreateDto dto) {
		try {
			System.out.println(dto);
			BookBean bean = new BookBean();
			bean.setName(dto.getName());
			bean.setAuthor(dto.getAuthor());
			bean.setIntroduction(dto.getIntroduction());


			try {
				bookService.Insert(bean);
				System.out.println(bean);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("Y", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
//		return new RedirectView("http://localhost:8080/html/grouptour/ShowGroupTour.html");
	}
	
	
	
//	後台刪除
	@DeleteMapping("/BookDelete.controller/{iSBN}")
	@ResponseBody
	public ResponseEntity<String> processDelete(@PathVariable int iSBN) {
		try {
			bookService.deleteById(iSBN);
			return new ResponseEntity<>("Y", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
	
	
//  後台查詢行程id
	@GetMapping("/BookSelectId.controller/{iSBN}")
	@ResponseBody
	public ResponseEntity<?> processSelectId(@PathVariable int iSBN) {
		try {
			BookBean beanList = bookService.findById(iSBN);
			System.out.println(bookService.findById(iSBN));
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("查不到此書本id", HttpStatus.OK);
		}
	}
	

//	查詢全部
	@GetMapping("/BookFindAll.controller")
	@ResponseBody
	public ResponseEntity<?> processSelectAll() {
		try {
			List<BookBean> beanList = bookService.findAll();
			System.out.println(bookService.findAll());
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
}
