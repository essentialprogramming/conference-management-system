package com.entities;

import com.model.Status;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "bid")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "status")
    @Type(type = "pgsql_enum")
    private Status status;
}
