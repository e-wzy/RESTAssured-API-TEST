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
}
