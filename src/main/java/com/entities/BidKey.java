package com.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class BidKey implements Serializable {

    private static final long serialVersionUID = -2600581264572898375L;

    @Column(name = "paper_id")
    private int paperId;
    @Column(name = "bidder")
    private String email;


    public BidKey(int paperId, String email) {
        this.paperId = paperId;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BidKey)) return false;
        BidKey that = (BidKey) o;
        return paperId == that.paperId &&
                email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, email);
    }
}
