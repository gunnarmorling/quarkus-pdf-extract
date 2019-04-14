package dev.morling.quarkus.pdfextract;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.internal.util.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class PdfExtractionResourceTest {

    public PdfExtractionResourceTest() {
    }


    @Test
    public void testExtractEndpoint() throws IOException {

        final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testfile.pdf"));

        given()
                .multiPart("uploadedFile", "PDF file name", bytes)
                .when().post("/extract")
                .then()
                .statusCode(200)
                .body(containsString("Quarkus"), containsString("rocks!"));
    }
}
