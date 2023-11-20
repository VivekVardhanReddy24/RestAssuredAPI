package APITesting;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/*It follows BDD Approach, use of Gherkin Language
given() - content type, set cookies, add path, add params, set headers info
when() - get, post, put, delete requests
then() - Validate status codes, extract response, extract headers, cookies and response body
 */
public class HttpRequests {
    int id;
    @Test(priority = 1)
    void getUser() {
        given()
                .when().get("https://reqres.in/api/users?page=2")// getting list of users from page 2
                .then().statusCode(200).body("page", equalTo(2))// status code 200, if it fetches list of users from page 2
                .log().all();
    }


    /*For Post Request we need to specify the content type when sending the body part
    Also it accepts JSon type. Here we will be adding data in form of key value using
    HAshmap concept.
     */
    @Test(priority = 2)
    void createUser() {
        HashMap hashMap = new HashMap();
        hashMap.put("name", "vivek");
        hashMap.put("job", "QA");



        id=given()
                .contentType("application/json")
                .body(hashMap)

                .when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");

                /*.then()
                .statusCode(201)
                .log().all();*/
    }

    @Test(priority=3, dependsOnMethods = {"createUser"})
    void updateUser()
    {
        HashMap hashmap = new HashMap();
        hashmap.put("name","Ram");
        hashmap.put("job", "Developer");

        given()
                .contentType("application/json")
                .body(hashmap)
                .when()
                .put("https://reqres.in/api/users/" +id)
                .then()
                .statusCode(200).log().all();
    }

    @Test(priority=4)
    void deleteUser()
    {
        given()
                .when()
                .delete("https://reqres.in/api/users/" +id)
        .then()
            .statusCode(204)
            .log().all();

    }
}
