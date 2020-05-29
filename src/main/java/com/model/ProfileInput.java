package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInput {

    private String username;
    private String password;
    private String roles;
    private String firstName;
    private String lastName;
}
