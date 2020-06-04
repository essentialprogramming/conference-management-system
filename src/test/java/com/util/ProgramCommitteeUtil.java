package com.util;

import com.entities.CommitteeMemberEntity;
import com.entities.PaperEntity;
import com.entities.UserEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ProgramCommitteeUtil {

    public static PaperEntity getPaper() {
        return PaperEntity.builder()
                .id(1)
                .title("Paper1")
                .description("First paper")
                .fileName("paper1")
                .keywords(Arrays.asList("paper", "paper1"))
                .topics(Collections.singletonList("topic1"))
                .reviews(new ArrayList<>())
                .build();

    }

    public static CommitteeMemberEntity getPcMember() {
        return new CommitteeMemberEntity("pcmember@gmail.com");
    }
}
