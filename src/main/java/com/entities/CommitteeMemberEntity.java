package com.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pc_member")
@DiscriminatorValue("1")
public class CommitteeMemberEntity extends UserEntity {

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @ManyToMany(mappedBy = "bidders")
    @MapKeyJoinColumn(name = "bid_id")
    private Map<BidEntity, PaperEntity> papers;

//    @OneToMany(mappedBy = "reviewer")
//    private List<EvaluationEntity> evaluation;

    @Override
    public @Email String getEmail() {
        return super.getEmail();
    }

    public CommitteeMemberEntity(@Email String email) {
        super(email);
    }
}
