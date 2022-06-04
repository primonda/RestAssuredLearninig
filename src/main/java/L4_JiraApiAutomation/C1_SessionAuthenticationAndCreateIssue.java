package L4_JiraApiAutomation;

import  io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class C1_SessionAuthenticationAndCreateIssue {

    // In Cookie based authentication we need to generate one session which will have a session name and value,
    //using the api provided to generate cookie based session. Now this session information(name and value)
    // needs to be shared with the createIssue API to proceed further after authentication
    //There are two ways to handle this in RestAssured:
    // - one is to pass the session name and value as Header
    // - second is by using SessionFilter class of RestAssured

    //using Header
    @Test
    void createIssueByPassingCookieSessionDetailsAsHeader(){
        RestAssured.baseURI = "http://localhost:8080";
        String authenticationResp = RestAssured.given()
                .log().all()
                .header("content-type","application/json")
                .body("{ \"username\": \"pritam.mondalmails\", \"password\": \"_Wx48Wf8Cb8jB$s\" }")
                .when().post("rest/auth/1/session")
        .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath authenticationRespJson = new JsonPath(authenticationResp);
        String sessionName = authenticationRespJson.getString("session.name");
        String sessionValue = authenticationRespJson.getString("session.value");

        RestAssured.given().log().all()
                .header("content-type","application/json")
                .header("Cookie", sessionName+ "="+sessionValue)
                .body("{\n" +
                        "\t\"fields\":{\n" +
                        "\t\t\"project\":{\n" +
                        "\t\t\t\"key\": \"REST\"\n" +
                        "\t\t},\n" +
                        "\t\t\"summary\": \"Learninig Rest Api Testing using RestAssured\",\n" +
                        "\t\t\"description\": \"creating issue\",\n" +
                        "\t\t\"issuetype\": {\n" +
                        "\t\t\t\"name\": \"Bug\"\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "}")
                .when().post("/rest/api/2/issue")
        .then().log().all().assertThat().statusCode(201);
    }

    //using SessionFilter
    @Test
    void createIssueByPassingCookieSessionDetailsUsingSessionFilter(){
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
                .body("{\n" +
                        "\t\"fields\":{\n" +
                        "\t\t\"project\":{\n" +
                        "\t\t\t\"key\": \"REST\"\n" +
                        "\t\t},\n" +
                        "\t\t\"summary\": \"Learninig Rest Api Testing using RestAssured\",\n" +
                        "\t\t\"description\": \"creating issue\",\n" +
                        "\t\t\"issuetype\": {\n" +
                        "\t\t\t\"name\": \"Bug\"\n" +
                        "\t\t}\n" +
                        "\t}\n" +
                        "}")
                .filter(session)
                .when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201);


    }





}
