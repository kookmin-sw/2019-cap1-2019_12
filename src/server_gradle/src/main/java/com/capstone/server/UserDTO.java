package com.capstone.server;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection= "capstone")
public class UserDTO {
    @Id
    public String id;

    public String Name;
    public String Company;

    public UserDTO() {}

    public UserDTO(String firstName, String lastName) {
        this.Name = Name;
        this.Company = Company ;
    }

    @Override
    public String toString() {
        return Name;
    }

}