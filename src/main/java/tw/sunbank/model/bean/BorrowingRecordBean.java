package tw.sunbank.model.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrowingrecord")
@Component
public class BorrowingRecordBean {

	
//	借閱紀錄表id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "borrowingrecordid")
	private int borrowingRecordId;
	
//	庫存外鍵
	@ManyToOne(targetEntity = InventoryBean.class)
	@JoinColumn(name = "inventoryid")
	private InventoryBean inventoryBean;
	
//	使用者外鍵	
	@ManyToOne(targetEntity = UserDataBean.class)
	@JoinColumn(name = "userid")
	private UserDataBean userDataBean;
	
	
//	借出日期
	@Column(name = "borrowingtime")
	private Date borrowingTime;

//	歸還日期
	@Column(name = "returntime")
	private Date returnTime;
	
	@PrePersist
    protected void onCreate() {
		borrowingTime = new Date();
		returnTime = new Date();
    }
	
	public BorrowingRecordBean() {
	}


	public int getBorrowingRecordId() {
		return borrowingRecordId;
	}


	public void setBorrowingRecordId(int borrowingRecordId) {
		this.borrowingRecordId = borrowingRecordId;
	}


	public InventoryBean getInventoryBean() {
		return inventoryBean;
	}


	public void setInventoryBean(InventoryBean inventoryBean) {
		this.inventoryBean = inventoryBean;
	}


	public UserDataBean getUserDataBean() {
		return userDataBean;
	}


	public void setUserDataBean(UserDataBean userDataBean) {
		this.userDataBean = userDataBean;
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



}
