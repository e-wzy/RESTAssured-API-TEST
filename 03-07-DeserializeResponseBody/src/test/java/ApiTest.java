import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
//import static org.hamcrest.core.Every.everyItem;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.hamcrest.number.OrderingComparison.greaterThan;

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
                    then().                        //response
                            assertThat().
                                statusCode(200).   //assert status code
                                body("id", equalTo("2")).    //assert all of fields.
                                body("name", equalTo("Cross-Back Training Tank")).
                                body("description", equalTo("The most awesome phone of 2013!")).
                                body("price", equalTo("299.00")).
                                body("category_id", equalTo("2")).
                                body("category_name", equalTo("Active Wear - Women"));

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
                "id": 21,
                "name": "Water Bottle",
                "description": "Blue water bottle. Holds 64 ounces",
                "price": 15,
                "category_id": 3
                }
                """;
        var response = given().body(body).when().put(endpoint).then();
        response.log().body();
    }
    @Test
    public void deleteProduct(){
        String endpoint = "http://127.0.0.1/api_testing/product/delete.php";
        String body = """
                {
                "id": 20
                }
                """;
        var response = given().body(body).when().delete(endpoint).then();
        response.log().body();
    }

    @Test
    //Serialize request body into Java object.
    public void createSerializedProduct(){
        String endpoint = "http://127.0.0.1/api_testing/product/create.php";
        Product product = new Product(
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                12,
                3
        );
        var response = given().body(product).when().post(endpoint).then();
        response.log().body();
    }
    @Test
    public void updateSerializedProduct(){
        String endpoint = "http://127.0.0.1/api_testing/product/create.php";
        Product product = new Product(
                19,
                "Water Bottle",
                "Blue water bottle. Holds 64 ounces",
                15,
                3,
                "Active Wear - Women"
        );
        var response = given().body(product).when().put(endpoint).then();
        response.log().body();
    }

    @Test
    //Get all products, verify the complex body.
    public void getProducts(){
        String endpoint = "http://127.0.0.1/api_testing/product/read.php";

        var response =
                given().
                when().
                        get(endpoint).         //action
                then().                        //response
                      log().                   //not a must
                       headers().              //not a must
                       assertThat().
                            statusCode(200).     //assert status code
                            header("Content-Type", equalTo("application/json; charset=UTF-8")).
                            body("records.size()", greaterThan(0)).   //lenient test
                            body("records.id", everyItem(notNullValue())).
                            body("records.id[0]", equalTo("25"));
        //response.log().body();   // to print out response results on the screen.
        //response.log().all();
    }
    @Test
    public void getDeserializedProduct() {
        String endpoint = "http://127.0.0.1/api_testing/product/read_one.php";
        //"id":"2","name":"Cross-Back Training Tank","description":"The most awesome phone of 2013!","price":"299.00","category_id":"2","category_name":"Active Wear - Women"}
        Product expectedProduct = new Product(
                2,
                "Cross-Back Training Tank",
                "The most awesome phone of 2013!",
                299.00,
                2,
                "Active Wear - Women"
        );

        //We want to get the response returned as a Java object.
        Product actualProduct =
                given().
                        queryParam("id", "2").
                        when().
                        get(endpoint).
                        as(Product.class);  //deserialize response body into Java object.

        assertThat(actualProduct, samePropertyValuesAs(expectedProduct));

    }
}