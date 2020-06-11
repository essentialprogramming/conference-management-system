package com.entities;

import com.model.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("pcmember")
public class CommitteeMemberEntity extends UserEntity {

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @OneToMany(mappedBy = "bidder",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BidEntity> bids;

    @ManyToMany(mappedBy = "reviews")
    @MapKeyJoinColumn(name = "evaluation_id")
    private Map<EvaluationEntity, PaperEntity> evaluations;

    @Override
    public @Email String getEmail() {
        return super.getEmail();
    }

    public CommitteeMemberEntity(@Email String email) {
        super(email);
    }

}
