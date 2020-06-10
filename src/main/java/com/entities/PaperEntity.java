package com.entities;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "paper")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class PaperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_name")
    private String fileName;

    @Type(type = "list-array")
    @Column(name = "topics")
    private List<String> topics;

    @Type(type = "list-array")
    @Column(name = "keywords")
    private List<String> keywords;

    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
    private List<BidEntity> bidders;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "paper_pcmember",
            joinColumns = {@JoinColumn(name = "paper_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "email", referencedColumnName = "email")})
    @MapKeyJoinColumn(name = "evaluation_id")
    private Map<EvaluationEntity, CommitteeMemberEntity> reviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id")
    private SectionEntity section;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "paper_author",
            joinColumns = {@JoinColumn(name = "paper_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "email", referencedColumnName = "email")})
    private List<AuthorEntity> authors;

    public List<BidEntity> getBids() {

        return bidders;
    }

    public void addAuthor(AuthorEntity author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    public void addReview(EvaluationEntity evaluationEntity, CommitteeMemberEntity committeeMemberEntity) {
        if (reviews == null) {
            reviews = new HashMap<>();
        }
        reviews.put(evaluationEntity, committeeMemberEntity);
    }

    public Map<EvaluationEntity, CommitteeMemberEntity> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperEntity)) return false;
        PaperEntity that = (PaperEntity) o;
        return title.equals(that.title) &&
                description.equals(that.description) &&
                topics.equals(that.topics) &&
                keywords.equals(that.keywords)
                && fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, description, topics, keywords, fileName);
        return result;
    }
}
