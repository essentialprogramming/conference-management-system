package com.entities;

import com.model.Qualifier;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "proposal")
@TypeDefs({
        @TypeDef(
                typeClass = EnumArrayType.class,
                defaultForType = Qualifier[].class,
                parameters = {
                        @org.hibernate.annotations.Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "qualifier")
                }
        )
})
public class ProposalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "proposal", fetch = FetchType.LAZY)
    private List<RecommendationEntity> recommendations;

    @Column(name = "qualifiers", columnDefinition = "qualifiers")
    private Qualifier[] qualifiers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_proposal",
            joinColumns = @JoinColumn(name = "proposal_id"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    private List<UserEntity> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_proposal",
            joinColumns = @JoinColumn(name = "proposal_id"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    private List<UserEntity>  reviewers;


}
