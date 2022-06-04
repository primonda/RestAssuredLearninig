package L2_ValidatingTheRestApiResponses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class C2_ParsingJsonResponseUsingJsonPath {
    //validate if add place api is working as expected

    //RestAssured works on below three principles:
    //given: all input details
    //when: Submit the API
    //Then: validate the response

    // please check api contract: resources/GoogleMapsAddApiContract

    public static void main(String[] args) {

        // let's this time instead of writing the long request payload here, we create a json file in
        // resources/ApiRequests and use it here while doing a request
        File requestPayload = new File(System.getProperty("user.dir")+"/src/main/resources/GoogleMapApi/ApiRequests/addPlaceJsonReq.json");

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //prior to this we were only working with one api and so validating in the body itself as restAssured provides
        // us that facility.
        //Now suppose we need to use the placeId from the response of addPlace api in another api. In that case, we will
        // have to extract the value for "placeId" from the response and store it in some variable.
        // So we first need to extract the response as String and then using JsonPath we need to extract the value of "placeId"
        String response = given().queryParam("key","qaclick123")
                .log().all()
                .header("Content-Tytpe","JSON")
                .body(requestPayload)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .log().all().extract().response().asString();

        JsonPath json = new JsonPath(response); // for parsing json
        String placeId = json.getString("place_id");

        System.out.println("placeId from addPlace api is: " + placeId);
    }
}

