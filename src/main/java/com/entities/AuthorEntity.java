package com.entities;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "author")
@DiscriminatorValue("author")
public class AuthorEntity extends UserEntity {
}
