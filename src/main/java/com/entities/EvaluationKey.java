package com.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PaperPcMemberId implements Serializable {

    private static final long serialVersionUID = -2600581264572898375L;

    private int paperId;

    private String email;

    public PaperPcMemberId() {
    }

    public PaperPcMemberId(int paperId, String email) {
        this.paperId = paperId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaperPcMemberId)) return false;
        PaperPcMemberId that = (PaperPcMemberId) o;
        return paperId == that.paperId &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, email);
    }
}
