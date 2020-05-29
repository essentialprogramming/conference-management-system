package com.entities;

import com.model.Qualifier;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pc_member")
@DiscriminatorValue("pcmember")
public class CommitteeMemberEntity extends UserEntity {

    @OneToOne(mappedBy = "supervisor")
    private SectionEntity section;

    @ManyToMany(mappedBy = "bidders")
    @MapKeyJoinColumn(name = "bid_id")
    private Map<BidEntity, PaperEntity> papers;

    @OneToMany(
            mappedBy = "reviewer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EvaluationEntity> evaluations;

    @Override
    public @Email String getEmail() {
        return super.getEmail();
    }

    public CommitteeMemberEntity(@Email String email) {
        super(email);
    }

    public EvaluationEntity addReview(PaperEntity paper) {
        EvaluationEntity review = new EvaluationEntity(this,  paper);
        evaluations.add(review);
        paper.getReviews().add(review);
        return review;
    }
}
