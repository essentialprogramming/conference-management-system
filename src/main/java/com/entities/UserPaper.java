package com.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "user_paper")
public class UserPaper {

    @EmbeddedId
    private UserPaperId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("paperId")
    private PaperEntity paper;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userEmail")
    private UserEntity user;

    @Column(name = "type")
    private String type;

    public UserPaper() {
    }

    public UserPaper(UserEntity user, PaperEntity paper, String type) {
        this.user = user;
        this.paper = paper;
        this.type = type;
        this.id = new UserPaperId(paper.getId(), user.getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPaper)) return false;
        UserPaper that = (UserPaper) o;
        return user.equals(that.user) &&
                paper.equals(that.paper) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, paper, type);
    }

    @Override
    public String toString() {
        return "UserPaper{" +
                "id=" + id +
                ", paper=" + paper +
                ", user=" + user +
                ", type='" + type + '\'' +
                '}';
    }
}
