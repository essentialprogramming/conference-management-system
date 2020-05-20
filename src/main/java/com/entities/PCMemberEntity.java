package com.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pc_member")
public class PCMemberEntity extends UserEntity {

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @OneToMany(mappedBy = "reviewer")
    private List<EvaluationEntity> evaluation;

    @OneToOne(mappedBy = "pcMember")
    private BidEntity bidEntity;

    @Override
    public @Email String getEmail() {
        return super.getEmail();
    }

    public PCMemberEntity(@Email String email) {
        super(email);
    }
}
