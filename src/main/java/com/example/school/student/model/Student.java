package com.example.school.student.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Document(collection = "student")
public class Student {

    @Id
    @Field("_id")
    private UUID uuid;

    @Field("name")
    @NotNull(message = "name is required")
    private String name;

    @Field("surname")
    @NotNull(message = "surname is required")
    private String surname;

    @Field("adress")
    @NotNull(message = "adress is required")
    private String adress;

    @Field("city")
    @NotNull(message = "city is required")
    private String city;

    @Field("postalCode")
    @NotNull(message = "postalCode is required")
    private String postalCode;

    @Field("email")
    @Email(message = "email is not valid")
    private String email;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
