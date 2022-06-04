package L4_JiraApiAutomation;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class C2_CommentOnIssue {

    // adding an comment on issue using addCommentApi. please refer the api contract for add comment
    //in addComment api we will have to send the existing issue id as path parameter
    // we can either send it it directly with the resource or we can use method pathParam for same
    @Test
    void commentOnAnIssue(){
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
                .header("content-type","application/json")
                .pathParam("issueIdOrKey","10002") // sending issueId as pathParam
                .body("{\n" +
                        "    \"body\": \"adding first comment\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}")
                .filter(session)
                .when().post("/rest/api/2/issue/{issueIdOrKey}/comment") // need to provide these curly braces for system to understand
                .then().log().all().assertThat().statusCode(201);


    }





}
