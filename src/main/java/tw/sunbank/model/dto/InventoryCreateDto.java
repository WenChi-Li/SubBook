package tw.sunbank.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class InventoryCreateDto {

	
//	外鍵書本id
	private int iSBN;

//	書本購買時間
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date storeTime;

//	書本狀態
	private String status;


	public InventoryCreateDto() {
	}


	public int getiSBN() {
		return iSBN;
	}


	public void setiSBN(int iSBN) {
		this.iSBN = iSBN;
	}


	public Date getStoreTime() {
		return storeTime;
	}


	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "InventoryCreateDto [iSBN=" + iSBN + ", storeTime=" + storeTime + ", status=" + status + "]";
	}



}
