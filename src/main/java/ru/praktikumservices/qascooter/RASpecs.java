package ru.praktikumservices.qascooter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RASpecs {
    public RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("http://qa-scooter.praktikum-services.ru/")
                .setContentType(ContentType.JSON)
                .build();
    }
}