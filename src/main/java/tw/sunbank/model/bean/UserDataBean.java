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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "userdata")
@Component
public class UserDataBean {

	
//	使用者id 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userId;
	
	
	@OneToMany(mappedBy = "userDataBean", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<BorrowingRecordBean> borrowingRecordBean;
	
	

//	登入電話
	@Column(name = "phonenumber")
	private String phonenumber;

//	登入密碼
	@Column(name = "password")
	private String password;
	
//	加鹽密碼
	@Column(name = "salt")
	private String salt;
	

//	使用者姓名
	@Column(name = "username")
	private String userName;
	
//	註冊日期
	@Column(name = "registrationtime")
	private Date registrationTime;
	
//	最後登入日期
	@Column(name = "lastlogintime")
	private Date lastLoginTime;
	
// PrePersist 回調方法，在持久化之前自動設置註冊時間和最後登入時間
	@PrePersist
    protected void onCreate() {
        registrationTime = new Date();
        lastLoginTime = new Date();
    }
	
	// PreUpdate 回調方法，在更新之前自動設置最後登入時間
    @PreUpdate
    protected void onUpdate() {
        lastLoginTime = new Date();
    }
	
	
	public UserDataBean() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phonenumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phonenumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}


}
