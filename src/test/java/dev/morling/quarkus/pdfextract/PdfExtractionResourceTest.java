package dev.morling.quarkus.pdfextract;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.internal.util.IOUtils;

@QuarkusTest
public class PdfExtractionResourceTest {

    @Test
    public void testExtractEndpoint() throws IOException {

        final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/testfile.pdf"));

        given()
                .multiPart("pdfFile", "PDF file name", bytes)
                .when().post("/rest/extract")
                .then()
                .statusCode(200)
                .body(containsString("Quarkus"), containsString("rocks!"));
    }
}
