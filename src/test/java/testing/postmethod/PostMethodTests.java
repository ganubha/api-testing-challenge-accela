package testing.postmethod;

import builders.CategoryBuilder;
import builders.CreatePetRequestBuilder;
import builders.TagsBuilder;
import entities.requests.Category;
import entities.requests.CreatePetRequest;
import entities.requests.Tags;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.RequestHelper;
import utils.ResourceHelper;

public class PostMethodTests extends BaseTest {

    @Test
    public void sendAPOSTRequestAndValidateTheResponseCode() {

        // Creating the Category Object
        Category category = new CategoryBuilder()
                .withId(1)
                .withName("Cats")
                .build();

        // Creating the Tags Object
        Tags tags = new TagsBuilder()
                .withId(1)
                .withName("Tag 1")
                .build();

        // Inserting the above created Tags object in the TagsList
        Tags[] tagsList = new Tags[1];
        tagsList[0] = tags;

       
        String[] photoUrls = {"Photo Url"}; // Create an array with some URL's
        CreatePetRequest createPetRequest = new CreatePetRequestBuilder()
                .withCategory(category)
                .withTags(tagsList)
                .withPhotoUrls(photoUrls)
                .withId(get3DigitRandomInt()) // This `get3DigitRandomInt()` will generate the random 3 digit number, coming from BaseTest
                .withName("Testing + " + get3DigitRandomInt())
                .withStatus("available")
                .build();

        // Sending a Request
        String url = propertiesReader.getEndPointUrl("create_pet"); // Fetching url from Properties file
        String json = RequestHelper.getJsonString(createPetRequest); // Convert above created object into a String
        Response response = ResourceHelper.create(url, json);

       
        // Validating the Response Code
        Assert.assertEquals(response.getStatusCode(), 200);

    }
    
    @Test
    public void sendAPOSTRequestForNegativeTestAndValidateTheResponseCode() {

        // Creating the Category Object
        Category category = new CategoryBuilder()
                .withId(1)
                .withName("Cats")
                .build();

        // Creating the Tags Object
        Tags tags = new TagsBuilder()
                .withId(1)
                .withName("Tag 1")
                .build();

        // Inserting the above created Tags object in the TagsList
        Tags[] tagsList = new Tags[1];
        tagsList[0] = tags;

   
        String[] photoUrls = {"Photo Url"}; // Create an array with some URL's
        CreatePetRequest createPetRequest = new CreatePetRequestBuilder()
                .withCategory(category)
                .withTags(tagsList)
                .withPhotoUrls(photoUrls)
                .withId(get3DigitRandomInt()) 
                .withName("Testing + " + get3DigitRandomInt())
                .withStatus("notavailable") // provide wrong status
                .build();

        // Sending a Request
        String url = propertiesReader.getEndPointUrl("create_pet"); // Fetching url from Properties file
        String json = RequestHelper.getJsonString(createPetRequest); // Convert above created object into a String
        Response response = ResourceHelper.create(url, json);
       
        // Validating the Response Code
        Assert.assertEquals(response.getStatusCode(), 405);

    }
    
    @Test
    public void sendAPOSTRequestForAuthenticationTest() {
        
    	
    	 // Creating the Category Object
        Category category = new CategoryBuilder()
                .withId(1)
                .withName("Cats")
                .build();

        // Creating the Tags Object
        Tags tags = new TagsBuilder()
                .withId(1)
                .withName("Tag 1")
                .build();

        // Inserting the above created Tags object in the TagsList
        Tags[] tagsList = new Tags[1];
        tagsList[0] = tags;

       
        String[] photoUrls = {"Photo Url"}; // Create an array with some URL's
        CreatePetRequest createPetRequest = new CreatePetRequestBuilder()
                .withCategory(category)
                .withTags(tagsList)
                .withPhotoUrls(photoUrls)
                .withId(get3DigitRandomInt()) 
                .withName("<script>alert(1)</script> + " + get3DigitRandomInt())
                .withStatus("available")
                .build();

        // Sending a Request
        String url = propertiesReader.getEndPointUrl("create_pet"); // Fetching url from Properties file
        String json = RequestHelper.getJsonString(createPetRequest); // Convert above created object into a String
        Response response = ResourceHelper.create(url, json);

     // Validating the Response Code
        Assert.assertEquals(response.getStatusCode(), 401);
    	
    	       
    	    }
}
