package com.restful.booker.curdTest;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class BookingCURDTest  extends TestBase {

    static String firstname = "Brooks" + TestUtils.getRandomValue();
    static String lastname = "William" + TestUtils.getRandomValue();
    static int totalprice = 999;
    static boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static String username = "admin";
    static String password = "password123";

    @Test
    public void Test1() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername(username);
        bookingPojo.setPassword(password);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .body(bookingPojo)
                .post("/auth");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void Test2() {
        BookingPojo.BookingDates date = new BookingPojo.BookingDates();
        date.setCheckin("2023-10-05");
        date.setCheckout("2023-10-06");
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setAdditionalneeds(additionalneeds);
        bookingPojo.setBookingdates(date);
        Response response = given()
//                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .auth().preemptive().basic("username", "password")
                .when()
                .body(bookingPojo)
                .post("/booking");
        response.then().log().all().statusCode(200);
    }

    @Test
    public void Test3() {
//        String s1 = "booking.findAll{it.firstname == '";
//        String s2 = "'}.get(0)";
        //HashMap<String, Object> studentMap = given()
        Response response = given()
                .when()
                .header("Connection", "keep-alive")
                .get("/booking/139");
        response.then().statusCode(200);
        //   .extract()
        //    .path(s1 + firstname + s2);
        // Assert.assertThat(studentMap, hasValue(firstname));
    }

    @Test
    public void Test4() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setTotalprice(113);
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token=abc123")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .body(bookingPojo)
                .patch("/booking/7");
        response.prettyPrint();
        response.then().statusCode(200);
    }

    @Test
    public void Test5() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .header("Cookie", "token=abc123")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("/booking/7");
        response.then().statusCode(201);
    }
}
