package com.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pc_member")
public class PCMemberEntity extends UserEntity {

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @OneToOne(mappedBy = "reviewer")
    private EvaluationEntity evaluation;

    @Override
    public @Email String getEmail() {
        return super.getEmail();
    }

    public PCMemberEntity(@Email String email) {
        super(email);
    }
}
