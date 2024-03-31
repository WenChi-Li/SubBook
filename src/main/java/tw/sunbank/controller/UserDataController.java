package tw.sunbank.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.transaction.Transactional;
import tw.sunbank.model.bean.UserDataBean;
import tw.sunbank.model.dto.UserDataCreateDto;
import tw.sunbank.model.service.UserDataService;

@Controller
@CrossOrigin(origins = "http://localhost:8080")
@Transactional
public class UserDataController {

	@Autowired
	private UserDataService userDataService;

//  後台使用者帳戶新增
	@PostMapping("/UserDataInsert.controller")
	@ResponseBody
	public RedirectView processInsert(@ModelAttribute UserDataCreateDto dto) {
		try {
			System.out.println(dto);
			UserDataBean bean = new UserDataBean();
			// 在設置密碼前先加密
            String salt = UserDataService.generateSalt();
            String hashedPassword = UserDataService.hash(dto.getPassword(), salt);
            bean.setPhoneNumber(dto.getPhonenumber());
            bean.setSalt(salt);
			bean.setPassword(hashedPassword);
			bean.setUserName(dto.getUserName());
			bean.setRegistrationTime(dto.getRegistrationTime());
			bean.setLastLoginTime(dto.getLastLoginTime());

			try {
				userDataService.Insert(bean);
				System.out.println(bean);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			return new ResponseEntity<>("N", HttpStatus.OK);
		}
		return new RedirectView("http://localhost:8080/BookIndex.html");
	}

//	登入功能
	@PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody String body) throws JSONException {
        JSONObject responseJson = new JSONObject();
        JSONObject requestBodyJson = new JSONObject(body);

        String phonenumber = requestBodyJson.getString("phonenumber");
        String password = requestBodyJson.getString("password");

        if (phonenumber == null || phonenumber.isEmpty() || password == null || password.isEmpty()) {
            responseJson.put("messages", "請輸入手機號碼和密碼!");
            responseJson.put("loginStatus", false);
            return new ResponseEntity<>(responseJson.toString(), HttpStatus.BAD_REQUEST);
        } else {
            UserDataBean userDataBean = userDataService.login(phonenumber, password);
            if (userDataBean == null) {
                responseJson.put("messages", "手機號碼或密碼錯誤!!");
                responseJson.put("loginStatus", false);
                return new ResponseEntity<>(responseJson.toString(), HttpStatus.UNAUTHORIZED);
            } else {
                responseJson.put("messages", "歡迎光臨!!");
                responseJson.put("loginStatus", userDataBean);
                
                // 更新登入時間
                userDataService.updateLastLoginTime(phonenumber);
            
                return new ResponseEntity<>(responseJson.toString(), HttpStatus.OK);
            }
        }
    }

	
//  後台帳號刪除
	@DeleteMapping("/UserDataDelete.controller/{userId}")
	@ResponseBody
	public ResponseEntity<String> processDelete(@PathVariable int userId) {
		try {
			userDataService.deleteById(userId);
			return new ResponseEntity<>("Y", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}

//  後台查詢使用者id
	@GetMapping("/UserDataSelectId.controller/{userId}")
	@ResponseBody
	public ResponseEntity<?> processSelectId(@PathVariable int userId) {
		try {
			UserDataBean beanList = userDataService.findById(userId);
			System.out.println(userDataService.findById(userId));
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("查不到此使用者id", HttpStatus.OK);
		}
	}

//  查詢全部
	@GetMapping("/UserDataFindAll.controller")
	@ResponseBody
	public ResponseEntity<?> processSelectAll() {
		try {
			List<UserDataBean> beanList = userDataService.findAll();
			System.out.println(userDataService.findAll());
			return new ResponseEntity<>(beanList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("N", HttpStatus.OK);
		}
	}
}


