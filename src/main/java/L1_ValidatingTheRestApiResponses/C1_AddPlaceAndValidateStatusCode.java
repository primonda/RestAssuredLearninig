package L1_ValidatingTheRestApiResponses;

import io.restassured.RestAssured;

import java.util.*;

import static io.restassured.RestAssured.given;

public class C1_AddPlaceAndValidateStatusCode {

//    public static void main(String[] args) {
//        //validate if add place api is working as expected
//
//        //RestAssured works on below three principles:
//        //given: all input details
//        //when: Submit the API
//        //Then: validate the response
//
//        // please check api contract: resources/GoogleMapsAddApiContract
//
//        RestAssured.baseURI = "https://rahulshettyacademy.com";
//        given().queryParam("key","qaclick123")
//                .log().all()
//                .header("Content-Tytpe","JSON")
//                .body("{\n" +
//                        "  \"location\": {\n" +
//                        "    \"lat\": -38.383494,\n" +
//                        "    \"lng\": 33.427362\n" +
//                        "  },\n" +
//                        "  \"accuracy\": 50,\n" +
//                        "  \"name\": \"Frontline house\",\n" +
//                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
//                        "  \"address\": \"29, side layout, cohen 09\",\n" +
//                        "  \"types\": [\n" +
//                        "    \"shoe park\",\n" +
//                        "    \"shop\"\n" +
//                        "  ],\n" +
//                        "  \"website\": \"http://google.com\",\n" +
//                        "  \"language\": \"French-IN\"\n" +
//                        "}")
//                .when().post("/maps/api/place/add/json")
//                .then().assertThat().statusCode(200)
//                .log().all();
//    }


    public static void main(String[] args) {
        //'auctioned', 'actors', 'altered', 'streaming', 'related',
        //        'education', 'aspired', 'costar', 'despair', 'mastering', 'UNAICOTED'

        List<String> words = new ArrayList<String>();
        words.add("auctioned");
        words.add("education");
        words.add("actors");
        words.add("altered");
        words.add("streaming");
        words.add("related");
        words.add("UNAICOTED");
        words.add("aspired");
        words.add("costar");
        words.add("despair");
        words.add("mastering");
        findAnagrams(words);
    }

    static void findAnagrams(List<String> words){
        Set<String> anagramMap = new HashSet<>();
        for(int i=0; i<words.size(); i++){
            for(int j=i+1; j<words.size(); j++){
                char [] a = words.get(i).toCharArray();
                char[] b = words.get(j).toCharArray();
                if(a.length == b.length){
                    int c =0 ;
                    for(int k=0; k<a.length; k++){

                        for(int l=0; l<a.length; l++){
                            String d = "" +b[l];
                            if(d.equalsIgnoreCase(""+a[k])){
                                c++;
                                break;
                            }
                        }
                    }
                    if(c == a.length){

                        anagramMap.add(words.get(i), words.get(j));
                        System.out.print(words.get(i) + " : " + words.get(j));
                        System.out.println();
                    }

                }
            }
        }


    }
}

