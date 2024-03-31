package tw.sunbank.model.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
@Component
public class InventoryBean {

	
//	庫存id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventoryid")
	private int InventoryId;
	
	
	@OneToMany(mappedBy = "inventoryBean", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BorrowingRecordBean> borrowingRecordBean;
	
	
	
//	一對一外鍵book
	@OneToOne
	@JoinColumn(name = "isbn")
	private BookBean bookBean;	
	
//	書本購買時間
	@Column(name = "storetime")
	private Date storeTime;

//	書本狀態
	@Column(name = "status")
	private String status;
	
	
	public InventoryBean() {
	}


	public int getInventoryId() {
		return InventoryId;
	}


	public void setInventoryId(int inventoryId) {
		this.InventoryId = inventoryId;
	}


	public BookBean getBookBean() {
		return bookBean;
	}


	public void setBookBean(BookBean bookBean) {
		this.bookBean = bookBean;
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



}
