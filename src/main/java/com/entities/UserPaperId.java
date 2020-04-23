package com.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserPaperId implements Serializable {

    private static final long serialVersionUID = -2600581264572898375L;
    @Column(name = "paper_id")
    private int paperId;

    @Column(name = "email")
    private String userEmail;

    public UserPaperId() {
    }

    public UserPaperId(int paperId, String userEmail) {
        this.paperId = paperId;
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPaperId)) return false;
        UserPaperId that = (UserPaperId) o;
        return paperId == that.paperId &&
                userEmail.equals(that.userEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, paperId);
    }
}
