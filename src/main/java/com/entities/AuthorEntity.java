package com.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("author")

public class AuthorEntity extends UserEntity {

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    public List<PaperEntity> papers;


    public AuthorEntity(@Email String email) {
        super(email);
    }
}
