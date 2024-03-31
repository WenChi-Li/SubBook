package tw.sunbank.model.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UserDataCreateDto {


//	登入電話
	private String phonenumber;

//	登入密碼
	private String password;
	
//	加鹽密碼
	private String salt;

//	書籍內容簡介
	private String userName;
	
//	註冊日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date registrationTime;
	
//	最後登入日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lastLoginTime;
	
	public UserDataCreateDto() {
	}



	public String getPhonenumber() {
		return phonenumber;
	}



	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
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

	@Override
	public String toString() {
		return "UserDataCreateDto [phonenumber=" + phonenumber + ", password=" + password + ", salt=" + salt
				+ ", userName=" + userName + ", registrationTime=" + registrationTime + ", lastLoginTime="
				+ lastLoginTime + "]";
	}


}
