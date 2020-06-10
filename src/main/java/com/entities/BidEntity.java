package com.entities;

import com.model.Status;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "bid")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
@AllArgsConstructor
public class BidEntity {

    @EmbeddedId
    private BidKey id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status")
    @Type(type = "pgsql_enum")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("paperId")
    @JoinColumn(name = "paper_id")
    private PaperEntity paper;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bidder")
    @JoinColumn(name = "bidder")
    private CommitteeMemberEntity bidder;

    public BidEntity(CommitteeMemberEntity bidder, PaperEntity paper) {
        this.bidder = bidder;
        this.paper = paper;
        this.id = new BidKey(paper.getId(), bidder.getEmail());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BidEntity)) return false;
        BidEntity bidEntity = (BidEntity) o;
        return status == bidEntity.status &&
                Objects.equals(paper, bidEntity.paper) &&
                Objects.equals(bidder, bidEntity.bidder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, paper, bidder);
    }
}
