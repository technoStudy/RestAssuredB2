package basqar;

import basqar.model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {

    private Cookies cookies;
    private String id;
    private String randomName;

    @BeforeClass
    public void init() {

        baseURI = "https://test.basqar.techno.study";

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "daulet2030@gmail.com");
        credentials.put("password", "TechnoStudy123@");

        cookies = given()
                .contentType(ContentType.JSON)
                .body(credentials)
//                .body("{\"username\":\"daulet2030@gmail.com\",\"password\":\"TechnoStudy123@\",\"rememberMe\":false}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().detailedCookies();
    }

    @Test
    public void createTest() {
        Country body = new Country();
        randomName = RandomStringUtils.randomAlphabetic(8);
        body.setName(randomName);
        body.setCode(RandomStringUtils.randomAlphabetic(4));

        id = given()
                .cookies(cookies)
                .body(body)
                .contentType(ContentType.JSON)
                .when()
                .post("/school-service/api/countries")
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("id");

        System.out.println(id);
        System.out.println(randomName);
    }
}
