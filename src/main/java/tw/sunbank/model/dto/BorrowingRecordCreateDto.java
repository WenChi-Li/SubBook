package tw.sunbank.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BorrowingRecordCreateDto {


//	外鍵庫存id
	private int InventoryId;
	
	
//	外鍵使用者id
	private int userId;

//	借出日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date borrowingTime;

//	歸還日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date returnTime;
	
	

	public BorrowingRecordCreateDto() {
	}

	public int getInventoryId() {
		return InventoryId;
	}

	public void setInventoryId(int inventoryId) {
		InventoryId = inventoryId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getBorrowingTime() {
		return borrowingTime;
	}

	public void setBorrowingTime(Date borrowingTime) {
		this.borrowingTime = borrowingTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@Override
	public String toString() {
		return "BorrowingRepositoryCreateDto [InventoryId=" + InventoryId + ", userId=" + userId + ", borrowingTime="
				+ borrowingTime + ", returnTime=" + returnTime + "]";
	}


}
