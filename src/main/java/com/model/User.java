package com.model;


import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String email;
    private int participantsSectionId;
    private Role role;
}
