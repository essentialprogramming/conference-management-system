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

    @OneToMany(mappedBy = "paper", fetch = FetchType.LAZY)
    private List<RecommendationEntity> recommendations;

    @Column(name = "qualifiers", columnDefinition = "qualifiers")
    private Qualifier[] qualifiers;

    @Type(type = "list-array")
    @Column(name = "topics")
    private List<String> topics;

    @Type(type = "list-array")
    @Column(name = "keywords")
    private List<String> keywords;

    @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPaper> users;

    public List<UserPaper> getUsers() {
        return users;
    }

    public void addUser(UserEntity userEntity, String type) {
        UserPaper userPaper = new UserPaper(userEntity, this, type);
        users.add(userPaper);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperEntity)) return false;
        PaperEntity that = (PaperEntity) o;
        return title.equals(that.title) &&
                content.equals(that.content) &&
                recommendations.equals(that.recommendations) &&
                Arrays.equals(qualifiers, that.qualifiers) &&
                topics.equals(that.topics) &&
                keywords.equals(that.keywords);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title, content, recommendations, topics, keywords);
        result = 31 * result + Arrays.hashCode(qualifiers);
        return result;
    }
}
