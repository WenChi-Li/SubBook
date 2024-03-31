package tw.sunbank.model.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
@Component
public class BookBean {

	
//	國際標準書號 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "isbn")
	private int iSBN;

//	書名
	@Column(name = "name")
	private String name;

//	作者
	@Column(name = "author")
	private String author;

//	書籍內容簡介
	@Column(name = "introduction")
	private String introduction;
	
	@OneToOne(mappedBy = "bookBean")
	@JsonIgnore
    private InventoryBean inventoryBean;
	
	public BookBean() {
	}

	public int getiSBN() {
		return iSBN;
	}

	public void setiSBN(int iSBN) {
		this.iSBN = iSBN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public InventoryBean getInventoryBean() {
		return inventoryBean;
	}

	public void setInventoryBean(InventoryBean inventoryBean) {
		this.inventoryBean = inventoryBean;
	}



}
