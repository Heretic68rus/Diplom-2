package send;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static config.ApiConfig.BASE_URL;
import static io.restassured.RestAssured.given;

public class BaseSend {
    protected RequestSpecification getDefaultRequestSpecification() {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }
}
