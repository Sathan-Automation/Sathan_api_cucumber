package StepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import com.jayway.jsonpath.JsonPath;

public class StepDefinition_library {

    private static String Token;
    private static  String Bookid;

    private  static String UserId= "6809d9fd-f159-451d-a87d-976af77cc6fd";


    @Given("As a Registered user I should be able to Generate Token")
    public static void generating_token()
    {
        Response res = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).
                body("{\n" +
                        "  \"userName\": \"simbu\",\n" +
                        "  \"password\": \"Thalapathy01#\"\n" +
                        "}").when().post("/Account/v1/GenerateToken");

        res.then().statusCode(200).log().all();

        Token = JsonPath.read(res.asString(), "$.token");

        System.out.println("Token is  "+Token);


    }
    @And("I should be able to retrieve all the books in the library")
    public static void  get_all_books()
    {
        Response response = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).when().
                get("/BookStore/v1/Books");
        response.then().statusCode(200).log().all();

        List<Map<String,String>> books = JsonPath.read(response.asString(), "$.books");
        Bookid = books.get(0).get("isbn");
        System.out.println("Book id is   "+Bookid);

    }
    @And("I Should assign a book to myself")
    public static  void add_book_to_list_with_auth()
    {
        Response response = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).
                header("Authorization","Bearer " +Token).
                body("{\n" +
                        "  \"userId\": \""+UserId+"\" ,\n" +
                        "  \"collectionOfIsbns\": [\n" +
                        "    {\n" +
                        "      \"isbn\": \""+Bookid+"\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .when().post("/BookStore/v1/Books");

        response.then().statusCode(201).log().all();
    }

    @When("I should delete the book that has been assigned to me")
    public static void delete_book_with_auth()
    {
        Response response = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).
                header("Authorization","Bearer " +Token).
                body("{\n" +
                        "  \"isbn\": \""+Bookid+"\",\n" +
                        "  \"userId\": \""+UserId+"\"\n" +
                        "}").
                when().
                delete("/BookStore/v1/Book");

        response.then().log().all();
    }
    @Then("I should view my information to check any books are available in my name")
    public static void get_user()
    {
        Response response = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).
                header("Authorization","Bearer " +Token).
                when().get("/Account/v1/User/"+UserId);
        response.then().statusCode(200).log().all();
    }

    @And("I Should validate authorization")
    public static  void authorization()
    {
        Response res = given().
                baseUri("https://bookstore.toolsqa.com").
                contentType(ContentType.JSON).
                body("{\n" +
                        "  \"userName\": \"simbu\",\n" +
                        "  \"password\": \"Thalapathy01#\"\n" +
                        "}").when().post("/Account/v1/Authorized");

        res.then().statusCode(200).log().all();

    }
}


