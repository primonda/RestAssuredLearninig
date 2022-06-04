package L2_ValidatingTheRestApiResponses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class C3_E2EFlowFromAddToUpdateToGetPlace {
    //validate if add place api is working as expected

    //RestAssured works on below three principles:
    //given: all input details
    //when: Submit the API
    //Then: validate the response

    // please check api contract: resources/GoogleMapsAddApiContract

    public static void main(String[] args) {
        File addPlaceRequestPayload = new File(System.getProperty("user.dir")+"/src/main/resources/GoogleMapApi/ApiRequests/addPlaceJsonReq.json");
        File updateRequestPayload = new File(System.getProperty("user.dir")+"/src/main/resources/GoogleMapApi/ApiRequests/updatePlaceJsonReq.json");

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //The flow is we add a place in Google Maps using addPlace api. The "place_id" from the response of of addPlace
        // is put into the "place_id" request field of of updatePlace api and we update the address of the place using
        // updateApi and then we will use the same "place_id" to retrieve/get the details of the place using getPlace api
        String addPlaceResponse = given().queryParam("key","qaclick123")
                .log().all()
                .header("Content-Type","application/json")
                .body(addPlaceRequestPayload)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .log().all().extract().response().asString();

        JsonPath addPlaceRespJson = new JsonPath(addPlaceResponse); // for parsing json
        String placeId = addPlaceRespJson.getString("place_id");

        given().log().all()
                .queryParam("key","qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"place_id\":\""+placeId+"\",\n" +
                        "  \"address\":\"70 winter walk, USA\",\n" +
                        "  \"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
        .body("msg", equalTo("Address successfully updated"));

        given().log().all().queryParams("place_id",placeId, "key", "qaclick123")
                .when().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200)
        .body("address", equalTo("70 winter walk, USA"));
    }
}

