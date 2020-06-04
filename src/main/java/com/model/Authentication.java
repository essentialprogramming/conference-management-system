package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    private String username;
    private String password;
    private Role role;
    private String firstName;
    private String lastName;

    public String getUserName(){
        return username;
    }
}
