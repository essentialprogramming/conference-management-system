package com.entities;


import com.model.Qualifier;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.Objects;


@Builder
@Getter
@Setter
@Entity(name = "evaluation")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationEntity {

    //@EmbeddedId
    //private EvaluationKey id;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    //@MapsId("reviewer")
    private CommitteeMemberEntity reviewer;

    //    @MapsId("paperId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_id")
    private PaperEntity paper;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "qualifier")
    private Qualifier qualifier;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "recommendation_id")
    private RecommendationEntity recommendation;

    public EvaluationEntity(Qualifier qualifier, CommitteeMemberEntity reviewer, RecommendationEntity recommendation, PaperEntity paper) {
        this.qualifier = qualifier;
        this.reviewer = reviewer;
        this.recommendation = recommendation;
        this.paper = paper;
        //this.id = new EvaluationKey(paper.getId(), reviewer.getEmail());
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
