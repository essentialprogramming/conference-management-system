package com.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.util.json.LocalDateDeserializer;
import com.util.json.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "program")
public class ProgramEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "interval")
    private String interval;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "abstract_deadline")
    private LocalDateTime abstractDeadline;

    @Column(name = "proposal_deadline")
    private LocalDateTime proposalDeadline;

    @Column(name = "bidding_deadline")
    private LocalDateTime biddingDeadline;

    @OneToOne(mappedBy = "program")
    private EventEntity eventEntity;

}
