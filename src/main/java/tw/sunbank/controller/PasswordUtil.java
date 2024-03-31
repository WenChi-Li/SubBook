package tw.sunbank.controller;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.util.DigestUtils;

import io.micrometer.common.util.StringUtils;

/**
 *  Hash 加密
 *  1. 把字串轉換成亂碼，亂碼稱為「雜湊值 hash value」
 *  2. 同一個字串，每次轉換出來的雜湊值，都是一樣的 (理論上有可能發生「雜湊碰撞」，不同字串卻轉換出同一個雜湊值，但發生機率很低)
 *  3. Hash 加密運算是單向的 -> 雜湊值不能被解密。只能把字串轉成雜湊值，不能把雜湊值轉回字串
 *
 *  Hash 加密，運用於會員資料表的 password 密碼欄位，註冊、登入邏輯
 *  1. 註冊：
 *      使用 Hash 加密的 MD5 公式，把字串 password 轉成「雜湊值 hash value」，
 *      把雜湊值儲存到資料表的密碼欄位，這樣密碼在資料庫中就不會以明文顯示
 *
 *  2. 登入：
 *      錯誤作法，由於 Hash 加密是單向的，雜湊值不能被解密回字串
 *      -> 所以「不能」把會員資料表，密碼欄位的雜湊值轉回字串，再和會員登入時輸入的密碼比較是否相同
 *
 *      正確做法，把會員登入時輸入的密碼，利用 MD5 公式轉成雜湊值，並和資料庫該會員密碼欄位儲存的雜湊值，兩者比較是否相同
 *      -> 因為同一個字串，每次轉換出來的雜湊值都會一樣，所以雜湊值相同，就代表註冊和登入時，輸入的密碼都一樣
 */

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

public class PasswordUtil {

    public static void main(String[] args) {
        // 模擬註冊
        // 產生會員的 salt 加鹽值，並將 salt 加鹽值，儲存到會員資料表的 salt 欄位
        String salt = generateSalt();
        // 「註冊密碼 + salt 鹽值」，進行 MD5 Hash 加密，產生雜湊值
        // 並將雜湊值 hashedRegisterPassword，儲存到會員資料表的 password 密碼欄位
        String hashedRegisterPassword = hash("123", salt);

        System.out.println("salt:" + salt);
        System.out.println("hashedRegisterPassword:" + hashedRegisterPassword);

        System.out.println("=========================");

        // 模擬登入
        // 取出會員資料表的 salt 加鹽值，以及註冊時的密碼雜湊值 hashedRegisterPassword (上面產生的)
        String userSalt = salt;

        // 對「登入密碼 + salt 鹽值」，進行 MD5 Hash 加密，產生雜湊值
        // 驗證「註冊密碼的雜湊值」和「登入密碼的雜湊值」，兩者是否相同，如果相同就代表登入密碼輸入正確
        // 模擬登入成功，密碼正確
        String hashedLoginPassword1 = hash("123", userSalt);
        System.out.println("hashedLoginPassword1:" + hashedLoginPassword1);
        System.out.println("登入是否成功:" +  hashedRegisterPassword.equals(hashedLoginPassword1));

        System.out.println();

        // 模擬登入失敗，密碼錯誤
        String hashedLoginPassword2 = hash("456", userSalt);
        System.out.println("hashedLoginPassword2:" + hashedLoginPassword2);
        System.out.println("登入是否成功:" +  hashedRegisterPassword.equals(hashedLoginPassword2));

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
//            saltedPassword = StringUtils.reverse(salt) + '|' + password + '$' + salt;
        } else {
            saltedPassword = password;
        }

        // 「註冊密碼 + salt 鹽值」，進行 MD5 Hash 加密，產生雜湊值回傳
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }

}