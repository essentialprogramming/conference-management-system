package com.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bid")
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paper_id")
    private PaperEntity paper;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pc_member")
    private PCMemberEntity pcMember;

    public BidEntity(PCMemberEntity pcMember) {
        this.pcMember = pcMember;
    }

    public BidEntity(PaperEntity paper, PCMemberEntity pcMember) {
        this.paper = paper;
        this.pcMember = pcMember;
    }
}
