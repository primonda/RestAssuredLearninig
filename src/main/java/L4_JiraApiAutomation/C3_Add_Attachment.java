package L4_JiraApiAutomation;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.Test;

import java.io.File;

public class C3_Add_Attachment {

    // adding an attachemnt on issue using addAttachmentApi. please refer the api contract for add attachment
    // In this api the request is not application/json and rather multipart/form-data and se we need to modify the
    // content-type.
    // we need attach file using the multipart method of restAssured
    @Test
    void attachAFileOnAnIssue(){
        RestAssured.baseURI = "http://localhost:8080";
        SessionFilter session = new SessionFilter();
        RestAssured.given()
                .log().all()
                .header("content-type","application/json")
                .body("{ \"username\": \"pritam.mondalmails\", \"password\": \"_Wx48Wf8Cb8jB$s\" }")
                .filter(session)
                .when().post("rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        RestAssured.given().log().all()
                .header("content-type","multipart/form-data")
                .header("X-Atlassian-Token", "no-check") // we need to provide this header as well as mentioned in api contract
                .pathParam("issueIdOrKey","10002")
                .multiPart(new File(System.getProperty("user.dir")+ "\\src\\main\\resources\\JiraApi/addAttachmentApiContract.txt"))
                .filter(session)
                .when().post("/rest/api/2/issue/{issueIdOrKey}/attachments")
                .then().log().all().assertThat().statusCode(200);

        //sometimes restAssured might not able to recognize certificate from a website.. in that case we can use
        // releaxedHttpsValidation() method from RestAssured and RestAssured will not validate the Https certificate


    }





}
