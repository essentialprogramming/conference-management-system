package com.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EvaluationKey implements Serializable {

    private static final long serialVersionUID = -2600581264572898375L;

    private int paperId;

    private String email;

    public EvaluationKey() {
    }

    public EvaluationKey(int paperId, String email) {
        this.paperId = paperId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvaluationKey)) return false;
        EvaluationKey that = (EvaluationKey) o;
        return paperId == that.paperId &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, email);
    }
}
