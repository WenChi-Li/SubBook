package tw.sunbank.model.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.sunbank.model.bean.BookBean;
import tw.sunbank.model.bean.BorrowingRecordBean;
import tw.sunbank.model.bean.UserDataBean;
import tw.sunbank.model.dao.BookRepository;
import tw.sunbank.model.dao.BorrowingRecordRepository;

@Service
@Transactional
public class BorrowingRecordService {

	@Autowired
	private BorrowingRecordRepository borrowingRecordRepository;

//	新增紀錄表方法
	public BorrowingRecordBean Insert(BorrowingRecordBean B) throws IOException {
//		Optional<BorrowingRecordBean> BookName = 
//				borrowingRecordRepository.findByName(B.getName());
//		if(!BookName.isPresent()) {
		return borrowingRecordRepository.save(B);
//		}else {
//			throw new RuntimeException("此書本已存在，無法新增");
	}
//	}

//	借出時間
	public BorrowingRecordBean updateLastLoginTime(int borrowingRecordId) {

		BorrowingRecordBean BorrowingRecordBean = borrowingRecordRepository.findById(borrowingRecordId).orElse(null);
//	如果這個借閱紀錄id有值
		if (BorrowingRecordBean != null) {
			Date now = new Date();
			System.err.println(now);
			BorrowingRecordBean.setReturnTime(now);
			borrowingRecordRepository.save(BorrowingRecordBean);
		}
		return BorrowingRecordBean;
	}

//	後台刪除紀錄表方法(Id)
	public void deleteById(Integer id) {
		Optional<BorrowingRecordBean> BorrowingRecordId = borrowingRecordRepository.findById(id);
		if (BorrowingRecordId.isPresent()) {
			borrowingRecordRepository.deleteById(id);
		} else {
			throw new RuntimeException("此紀錄表id不存在，無法刪除");
		}
	}

// 後台查詢單一紀錄表id
	public BorrowingRecordBean findById(Integer id) {
		Optional<BorrowingRecordBean> BorrowingRecordId = borrowingRecordRepository.findById(id);
		return BorrowingRecordId.orElse(null);
	}

// 查詢全部書本
	public List<BorrowingRecordBean> findAll() {
		return borrowingRecordRepository.findAll();
	}
}
