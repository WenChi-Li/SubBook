package tw.sunbank.model.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import io.micrometer.common.util.StringUtils;
import tw.sunbank.model.bean.UserDataBean;
import tw.sunbank.model.dao.UserDataRepository;

@Service
@Transactional
public class UserDataService {
	
	/**
	 * Hash 加密，目前已經被「彩虹表」破解
	 * 彩虹表的概念，有一張超大的表格資料，會記錄字串所對應的雜湊值 (嚴格來說並不算破解，因為不是把雜湊值反推回字串)
	 *
	 * 解決方案：為了反制破解，研究 Hash 加密過程 「加鹽 salt」，
	 * 加鹽的概念，是在密碼的任意位置，插入系統隨機生成的字串，再把「密碼+加鹽」的字串，透過 Hash 加密產生雜湊值，使得破解難度大幅提升
	 *
	 * 「加鹽 salt」，如何在註冊、登入的邏輯運用
	 *  1. 註冊：
	 *      在每位會員註冊時，就會產生一個系統生成的「加鹽字串」，儲存到會員資料表的 salt 欄位
	 *      「註冊密碼 + 加鹽字串」，透過 Hash 加密產生的雜湊值，儲存到會員資料表的 password 密碼欄位
	 *
	 *  2. 登入：
	 *      -> 取出該會員的 salt 欄位值，是之前註冊產生的加鹽字串
	 *      -> 「登入輸入的密碼 + 加鹽字串」，利用 Hash MD5 公式轉成雜湊值
	 *      -> 取出會員資料表的 password 欄位值，比較兩個雜湊值是否相同
	 *      同一個會員，加鹽字串會固定，所以「密碼 + 加鹽」 產生的 hash 雜湊值，當註冊和登入時相同，代表密碼輸入正確
	 */
	
	@Autowired
	private UserDataRepository userDataRepository;
	
//	後台新增使用者
	public UserDataBean Insert(UserDataBean U ) throws IOException {
		
//		String salt = generateSalt();
//        String hashedPassword = hash(U.getPassword(), salt);
//        U.setPassword(hashedPassword);
		
		Optional<UserDataBean> phoneNumber = 
				userDataRepository.findByphonenumber(U.getPhoneNumber());
				
		if(!phoneNumber.isPresent()) {
			return userDataRepository.save(U);			
		}else {
			throw new RuntimeException("此電話已註冊過，無法新增");
		}
	}
	
// 隨機產生加鹽字串
	 public static String generateSalt() {
	        SecureRandom random = new SecureRandom();
	        byte[] salt = new byte[16];
	        random.nextBytes(salt);

	        // 使用 Base64 編碼，將 byte[] 轉換為字串
	        return Base64.getEncoder().encodeToString(salt);
	    }
	
	// 將「密碼 + 加鹽字串」，進行 MD5 Hash 加密，產生雜湊值
	    public static String hash(String password, String salt) {
	        String saltedPassword;

	        // 把密碼和加鹽值結合
	        if (StringUtils.isNotBlank(salt)) {
	            saltedPassword = password + salt;
//	            saltedPassword = StringUtils.reverse(salt) + '|' + password + '$' + salt;
	        } else {
	            saltedPassword = password;
	        }

	        // 「註冊密碼 + salt 鹽值」，進行 MD5 Hash 加密，產生雜湊值回傳
	        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
	    }
	

	// 登入
    public UserDataBean login(String phonenumber, String loginPassword) {
    	System.err.println("phonenumber" + phonenumber);
    	System.err.println("loginPassword" + loginPassword);
//        return userDataRepository.accountCheck(phonenumber, password);
   
    	UserDataBean userDataBean = 
    			userDataRepository.findByphonenumber(phonenumber)
    			.orElse(null);
    	
    	// 沒有這個電話(使用者)
    	if (userDataBean == null) {
    		return userDataBean;
    	}
    	
    	// 有這個電話
    	String salt = userDataBean.getSalt();
    	String password = userDataBean.getPassword();
    	
    	System.err.println("salt: " + salt);
    	System.err.println("password: " + password);
    	System.err.println("hashLoginPassword: " + hash(loginPassword, salt));
    	
    	if (password.equals(hash(loginPassword, salt))) {
    		return userDataBean;
    	}
    	
//    	UserDataBean userDataBean = 
//    			userDataRepository.findByPhonenumberAndPassword(phonenumber, hash(password, phonenumber))
//    			.orElse(null);
    	
    	return null;
    }
    
//	登入時間
	public UserDataBean updateLastLoginTime(String phonenumber) {
		
		UserDataBean userDataBean = userDataRepository.findByphonenumber(phonenumber)
				.orElse(null);
		
		if (userDataBean != null) {
			Date now = new Date();
			System.err.println(now);
			userDataBean.setLastLoginTime(now);
			userDataRepository.save(userDataBean);	
		}
		
		return userDataBean;
	}
    
//	加密
	private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	
	
	
	
//	後台刪除資料(Id)
	public void  deleteById(Integer id) {
		Optional<UserDataBean> userId = userDataRepository.findById(id);	
		if (userId.isPresent()) {
			userDataRepository.deleteById(id);	
		}else {
			throw new RuntimeException("此註冊電話不存在，無法刪除");
		}
	}
	

	
// 後台查詢單一userid
	public UserDataBean findById(Integer id) {
		Optional<UserDataBean> userId = userDataRepository.findById(id);
		return userId.orElse(null);
	}
	
	
// 查詢user
	public List<UserDataBean> findAll(){
		return userDataRepository.findAll();
	}
	
	
}
