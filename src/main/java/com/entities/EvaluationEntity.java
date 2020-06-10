package com.entities;


import com.model.Qualifier;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;


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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    @Column(name = "qualifier")
    private Qualifier qualifier;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "recommendation_id")
    private RecommendationEntity recommendation;

    @Column(name = "reviewer")
    private String reviewer;

    @Column(name = "paper_id")
    private int paper;

    public EvaluationEntity(CommitteeMemberEntity reviewer, PaperEntity paper) {
        this.reviewer = reviewer.getEmail();
        this.paper = paper.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationEntity)) return false;
        EvaluationEntity that = (EvaluationEntity) o;
        return paper == that.paper &&
                reviewer.equals(that.reviewer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewer, paper);
    }
}
