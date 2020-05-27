package com.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;



@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "account")
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.STRING,
        name = "type",
        columnDefinition = "text"
)
public class UserEntity {

    @Id
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    private SectionEntity section;


    public UserEntity(@Email String email) {
        this.email = email;
    }
}
