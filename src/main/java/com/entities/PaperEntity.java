package com.entities;

import com.model.Qualifier;
import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "paper")
@TypeDefs({
        @TypeDef(
                typeClass = EnumArrayType.class,
                defaultForType = Qualifier[].class,
                parameters = {
                        @org.hibernate.annotations.Parameter(name = EnumArrayType.SQL_ARRAY_TYPE, value = "qualifier")
                }
        ),
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        )
})
public class PaperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

//    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
//    private List<EvaluationEntity> reviews;

    @Column(name = "qualifiers", columnDefinition = "qualifiers")
    private Qualifier[] qualifiers;

    @Type(type = "list-array")
    @Column(name = "topics")
    private List<String> topics;

    @Type(type = "list-array")
    @Column(name = "keywords")
    private List<String> keywords;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "paper_pcmember",
            joinColumns = {@JoinColumn(name = "paper_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "email", referencedColumnName = "email")})
    @MapKeyJoinColumn(name = "bid_id")
    private Map<BidEntity, CommitteeMemberEntity> bidders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    public Map<BidEntity, CommitteeMemberEntity> getBids() {
        return bidders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperEntity)) return false;
        PaperEntity that = (PaperEntity) o;
        return title.equals(that.title) &&
                content.equals(that.content) &&
                topics.equals(that.topics) &&
                keywords.equals(that.keywords);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, content, topics, keywords);
        return result;
    }
}
