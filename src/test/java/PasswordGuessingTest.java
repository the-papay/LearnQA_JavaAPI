import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordGuessingTest {

    private final String SECRET_PASSWORD_URL = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
    private final String CHECK_AUTH_COOKIE_URL = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
    private final String LOGIN = "super_admin";
    private String authCookie;
    private String authStatus;
    private List<String> passwordList;

    @Test
    public void testPasswordGuessingTest() {

        preparePasswordList();

        for (int i = 0; i < passwordList.size(); i++) {

            if (!checkAuthCookie(getAuthCookie(passwordList.get(i))).equals("You are NOT authorized")) {
                System.out.println("login " + LOGIN);
                System.out.println("password " + passwordList.get(i));
                System.out.println("auth_status " + authStatus);
            }
        }
    }

    public List<String> preparePasswordList() {

        passwordList = new ArrayList<>();
//        String[] passwords = new String[] {"password" ,"123456" ,"123456789" ,"12345678" ,"12345" ,"qwerty" ,"abc123" ,"football" ,"1234567" ,"monkey" ,"111111" ,"letmein" ,"1234" ,"1234567890" ,"dragon" ,"baseball" ,"sunshine" ,"iloveyou" ,"trustno1" ,"princess" ,"adobe123" ,"123123" ,"welcome" ,"login" ,"admin" ,"qwerty123" ,"solo" ,"1q2w3e4r" ,"master" ,"666666" ,"photoshop" ,"1qaz2wsx" ,"qwertyuiop" ,"ashley" ,"mustang" ,"121212" ,"starwars" ,"654321" ,"bailey" ,"access" ,"flower" ,"555555" ,"passw0rd" ,"shadow" ,"lovely" ,"7777777" ,"michael" ,"!@#$%^&*" ,"jesus" ,"password1" ,"superman" ,"hello" ,"charlie" ,"888888" ,"696969" ,"hottie" ,"freedom" ,"aa123456" ,"qazwsx" ,"ninja" ,"azerty" ,"loveme" ,"whatever" ,"donald" ,"batman" ,"zaq1zaq1" ,"Football" ,"000000" ,"123qwe"};
        String[] passwords = new String[] {"password" ,"password" ,"123456" ,"123456" ,"123456" ,"123456" ,"123456" ,"123456" ,"123456" ,"123456" ,"123456" ,"password" ,"password" ,"password" ,"password" ,"password" ,"password" ,"123456789" ,"12345678" ,"12345678" ,"12345678" ,"12345" ,"12345678" ,"12345" ,"12345678" ,"123456789" ,"qwerty" ,"qwerty" ,"abc123" ,"qwerty" ,"12345678" ,"qwerty" ,"12345678" ,"qwerty" ,"12345678" ,"password" ,"abc123" ,"qwerty" ,"abc123" ,"qwerty" ,"12345" ,"football" ,"12345" ,"12345" ,"1234567" ,"monkey" ,"monkey" ,"123456789" ,"123456789" ,"123456789" ,"qwerty" ,"123456789" ,"111111" ,"12345678" ,"1234567" ,"letmein" ,"111111" ,"1234" ,"football" ,"1234567890" ,"letmein" ,"1234567" ,"12345" ,"letmein" ,"dragon" ,"1234567" ,"baseball" ,"1234" ,"1234567" ,"1234567" ,"sunshine" ,"iloveyou" ,"trustno1" ,"111111" ,"iloveyou" ,"dragon" ,"1234567" ,"princess" ,"football" ,"qwerty" ,"111111" ,"dragon" ,"baseball" ,"adobe123" ,"football" ,"baseball" ,"1234" ,"iloveyou" ,"iloveyou" ,"123123" ,"baseball" ,"iloveyou" ,"123123" ,"1234567" ,"welcome" ,"login" ,"admin" ,"princess" ,"abc123" ,"111111" ,"trustno1" ,"admin" ,"monkey" ,"1234567890" ,"welcome" ,"welcome" ,"admin" ,"qwerty123" ,"iloveyou" ,"1234567" ,"1234567890" ,"letmein" ,"abc123" ,"solo" ,"monkey" ,"welcome" ,"1q2w3e4r" ,"master" ,"sunshine" ,"letmein" ,"abc123" ,"111111" ,"abc123" ,"login" ,"666666" ,"admin" ,"sunshine" ,"master" ,"photoshop" ,"111111" ,"1qaz2wsx" ,"admin" ,"abc123" ,"abc123" ,"qwertyuiop" ,"ashley" ,"123123" ,"1234" ,"mustang" ,"dragon" ,"121212" ,"starwars" ,"football" ,"654321" ,"bailey" ,"welcome" ,"monkey" ,"access" ,"master" ,"flower" ,"123123" ,"123123" ,"555555" ,"passw0rd" ,"shadow" ,"shadow" ,"shadow" ,"monkey" ,"passw0rd" ,"dragon" ,"monkey" ,"lovely" ,"shadow" ,"ashley" ,"sunshine" ,"master" ,"letmein" ,"dragon" ,"passw0rd" ,"654321" ,"7777777" ,"123123" ,"football" ,"12345" ,"michael" ,"login" ,"sunshine" ,"master" ,"!@#$%^&*" ,"welcome" ,"654321" ,"jesus" ,"password1" ,"superman" ,"princess" ,"master" ,"hello" ,"charlie" ,"888888" ,"superman" ,"michael" ,"princess" ,"696969" ,"qwertyuiop" ,"hottie" ,"freedom" ,"aa123456" ,"princess" ,"qazwsx" ,"ninja" ,"azerty" ,"123123" ,"solo" ,"loveme" ,"whatever" ,"donald" ,"dragon" ,"michael" ,"mustang" ,"trustno1" ,"batman" ,"passw0rd" ,"zaq1zaq1" ,"qazwsx" ,"password1" ,"password1" ,"Football" ,"password1" ,"000000" ,"trustno1" ,"starwars" ,"password1" ,"trustno1" ,"qwerty123" ,"123qwe"};
        for (int i = 0; i < passwords.length - 1; i++) {
            passwordList.add(passwords[i]);
        }

        return passwordList;
    }

    public String checkAuthCookie(String authCookie) {

        Response response = RestAssured
                .given()
                    .header("auth_cookie", authCookie)
                .when()
                    .get(CHECK_AUTH_COOKIE_URL)
                .andReturn();

        authStatus = response.getBody().asString();
        return authStatus;

    }

    public String getAuthCookie(String password) {

        Map<String, String> payload = new HashMap<>();
        payload.put("login", LOGIN);
        payload.put("password", password);

        Response response = RestAssured
                .given()
                    .body(payload)
                .when()
                    .post(SECRET_PASSWORD_URL)
                .andReturn();

        authCookie = response.getCookie("auth_cookie");
        return authCookie;

    }

}
