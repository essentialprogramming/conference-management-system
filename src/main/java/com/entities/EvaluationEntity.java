package com.entities;


import com.model.Qualifier;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;


//@Builder
@Getter
@Setter
@Entity(name = "evaluation")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@NoArgsConstructor
public class EvaluationEntity {

    @EmbeddedId
    private PaperPcMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewer")
    private PCMemberEntity reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("paperId")
    private PaperEntity paper;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "qualifier")
    private Qualifier qualifier;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "recommendation_id")
    private RecommendationEntity recommendation;

    public EvaluationEntity(Qualifier qualifier, PCMemberEntity reviewer, RecommendationEntity recommendation, PaperEntity paper) {
        this.qualifier = qualifier;
        this.reviewer = reviewer;
        this.recommendation = recommendation;
        this.paper = paper;
        this.id = new PaperPcMemberId(paper.getId(), reviewer.getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationEntity)) return false;
        EvaluationEntity entity = (EvaluationEntity) o;
        return
                qualifier == entity.qualifier &&
                reviewer.equals(entity.reviewer) &&
                recommendation.equals(entity.recommendation) &&
                paper.equals(entity.paper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifier, reviewer, recommendation, paper);
    }
}
