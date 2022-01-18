import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiTest {

    @Test
    //Get all categories.
    public void getCategories(){
       // String endpoint = "http://localhost/api_testing/category/read.php"; // can not print out results
        String endpoint = "http://127.0.0.1/api_testing/category/read.php";

        //given().when().get(endpoint) - make a request.
        //then() - to retrieve response.
        var response = given().when().get(endpoint).then();

        //to print out response results on the screen.
        response.log().body(); // to print out response results on the screen.

        //response.log().all();
    }
    @Test
    //Get one specific product, parameter is required.
   public void getProduct(){
        String endpoint = "http://127.0.0.1/api_testing/product/read_one.php";

       // queryParam("id", 2) - required parameters ( key value pair) by given.
       // id value can be got by querying DB - select * from products.
        var response =
                    given().
                            queryParam("id", 2).   //parameter
                    when().
                            get(endpoint).         //action
                    then();                        //response

        response.log().body();   // to print out response results on the screen.

        //response.log().all();
    }

    @Test
    public void createProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/create.php";

        //The body in JSON format is used as POST request body or payload.
        //The text block below requires JAVA 15 or higher, and no comments inside the block is allowed.
        //The "category id" is got from DB.
        String strBody = """
                {
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 12,
                "category_id": 3
                }
                """;
        var response = given().body(strBody).when().post(endpoint).then();
        response.log().body();

        //response.log().all();
    }
    @Test
    public void updateProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/update.php";
        String body = """
                {
                "id": 22,
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 25,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }

}
