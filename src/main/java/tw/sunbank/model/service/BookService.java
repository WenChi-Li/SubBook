package tw.sunbank.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sunbank.model.bean.BookBean;
import tw.sunbank.model.dao.BookRepository;

@Service
@Transactional
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
//	後台新增資料
	public BookBean Insert(BookBean B ) throws IOException {
		Optional<BookBean> BookName = 
				bookRepository.findByName(B.getName());
		if(!BookName.isPresent()) {
			return bookRepository.save(B);			
		}else {
			throw new RuntimeException("此書本已存在，無法新增");
		}
	}
	 
	
//	後台刪除資料(Id)
	public void  deleteById(Integer id) {
		Optional<BookBean> BookId = bookRepository.findById(id);	
		if (BookId.isPresent()) {
			bookRepository.deleteById(id);	
		}else {
			throw new RuntimeException("此書本不存在，無法刪除");
		}
	}
	
	
// 後台查詢單一書本id
	public BookBean findById(Integer id) {
		Optional<BookBean> BookId = bookRepository.findById(id);
		return BookId.orElse(null);
	}
	
	
// 查詢全部書本
	public List<BookBean> findAll(){
		return bookRepository.findAll();
	}
}
