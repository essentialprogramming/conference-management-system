package com.entities;


import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Entity(name = "author")
public class AuthorEntity extends UserEntity {
}
