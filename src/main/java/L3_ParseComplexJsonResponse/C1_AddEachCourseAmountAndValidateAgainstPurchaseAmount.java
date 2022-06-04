package L3_ParseComplexJsonResponse;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

public class C1_AddEachCourseAmountAndValidateAgainstPurchaseAmount {

    public static void main(String[] args) {

        //fetch complex json file which we will use a mock response json
        //In Agile, we start developing automation testing before actual development starts,
        // So in those cases we will use these mock files which will resemble the response, we should be
        //getting after hitting actual API. We can get the mock response from API contract
        File coursesJsonFile = new File(System.getProperty("user.dir")+"/src/main/resources/AutomationCourses/automationCourses.json");
        JsonPath jsonResponse = new JsonPath(coursesJsonFile); //parsing file

        //All the courses are written in an array in the json file and are dynamically stored. so we will get
        // the size of the array and iterate through them using loop and get the amount.

        int courseArraySize = jsonResponse.getInt("courses.size()");
        int totalAmount=0;
        for(int i=0; i<courseArraySize; i++){
            totalAmount += (jsonResponse.getInt("courses["+i+"].price")*jsonResponse.getInt("courses["+i+"].copies"));
        }
        Assert.assertEquals(totalAmount, jsonResponse.getInt("dashboard.purchaseAmount"));

    }
}
