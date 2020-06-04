package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "participant")
@DiscriminatorValue("participant")

public class ParticipantEntity extends UserEntity {

    public ParticipantEntity(@Email String email) {
        super(email);
    }
}