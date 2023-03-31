package org.daemio.merch.controller;

import java.math.BigDecimal;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import org.daemio.merch.MerchServiceApplication;
import org.daemio.merch.domain.Image;
import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.repository.MerchRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesRegex;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = MerchServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integ-test")
public class MerchAPITest {

    @LocalServerPort
    private int port;

    @Autowired
    private MerchRepository merchRepository;

    private Merch merch;

    @BeforeEach
    public void setup() {
        Merch merch = new Merch();
        merch.setTitle("Amazing band shirt");
        merch.setStatus(MerchStatus.SOLD_OUT);
        merch.setPrice(BigDecimal.valueOf(5));

        this.merch = merchRepository.save(merch);
    }

    @AfterEach
    public void tearDown() {
        merchRepository.deleteAll();
    }

    @Test
    public void whenCalling_thenReturn() {
        given().port(port).when().get("/merch").then().statusCode(HttpStatus.OK.value()).contentType(ContentType.JSON)
                .body("merch", hasSize(1)).body("page", is(0)).body("size", is(25)).body("totalPages", is(1));
    }

    @Test
    public void whenCalling_thenReturnNoResults() {
        var page = 2;

        given().port(port).queryParam("page", page).when().get("/merch").then().statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON).body("merch", hasSize(0)).body("page", is(page)).body("size", is(25))
                .body("totalPages", is(1));
    }

    @Test
    public void whenCallingForMerch_thenGetMerchItem() {
        given().port(port).pathParam("merchId", merch.getId()).when().get("/merch/{merchId}").then()
                .statusCode(HttpStatus.OK.value()).contentType(ContentType.JSON).body("title", is(merch.getTitle()))
                .body("createdTime", is(notNullValue())).body("modifiedTime", is(notNullValue()));
    }

    @Test
    public void givenMerchNotThere_whenCallingForMerch_thenGetNotFound() {
        var merchId = 17;

        given().port(port).pathParam("merchId", merchId).when().get("/merch/{merchId}").then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @WithMockUser(roles = {"VENDOR"})
    public void whenPostMerch_thenMerchIsSaved() {
        Merch merch = new Merch();
        merch.setTitle("Another amazing band shirt");
        merch.setStatus(MerchStatus.LOADED);
        merch.setPrice(BigDecimal.valueOf(17));

        var thumb = new Image();
        thumb.setTitle("jdshfksdhf");
        thumb.setUri("dfksdfgsdjghf");
        merch.getImages().add(thumb);

        given().port(port).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).auth().preemptive()
                .basic("test", "pass").body(merch).when().post("/merch").then().statusCode(HttpStatus.CREATED.value())
                .header(HttpHeaders.LOCATION, is(notNullValue()))
                .header(HttpHeaders.LOCATION, matchesRegex("^/merch/\\d+$"));
    }
}
