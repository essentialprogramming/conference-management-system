//package com.service;
//
//import com.entities.CommitteeMemberEntity;
//import com.entities.EvaluationEntity;
//import com.entities.PaperEntity;
//import com.entities.RecommendationEntity;
//import com.model.Qualifier;
//import com.output.PaperJSON;
//import com.repository.PCMemberRepository;
//import com.repository.PaperRepository;
//import com.repository.RecommendationRepository;
//import com.util.ProgramCommitteeUtil;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import javax.ws.rs.QueryParam;
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//public class ProgramCommitteeServiceTest {
//
//    @Mock
//    private RecommendationRepository recommendationRepository;
//    @Mock
//    private PaperRepository paperRepository;
//    @Mock
//    private PCMemberRepository pcMemberRepository;
//
//    @InjectMocks
//    private ProgramCommitteeService pcService;
//
//    @InjectMocks
//    private PaperService paperService;
//
//    @Before
//    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void reviewPaper() {
//
//        PaperEntity paper = ProgramCommitteeUtil.getPaper();
//        CommitteeMemberEntity pcMember = ProgramCommitteeUtil.getPcMember();
//        pcMember.setEvaluations(new ArrayList<>());
//
//        when(paperRepository.findById(1)).thenReturn(Optional.of(paper));
//        when(pcMemberRepository.findById(anyString())).thenReturn(Optional.of(pcMember));
//
//        Optional<PaperEntity> paperEntity = paperRepository.findById(1);
//
//        if (paperEntity.get().getReviews().size() < 4) {
//            pcMember.addReview(paper);
//        }
//
//        when(recommendationRepository.save(any())).then(i -> i.getArgument(0, RecommendationEntity.class));
//        RecommendationEntity recommendationEntity = recommendationRepository.save(new RecommendationEntity("Recommendation"));
//
//        EvaluationEntity evaluationEntity = pcMember.getEvaluations().get(0);
//        evaluationEntity.setRecommendation(recommendationEntity);
//
//        assertEquals(pcMember.getEvaluations().size(),1);
//        assertEquals(paperEntity.get().getReviews().size(),1);
//        assertEquals(paperEntity.get().getReviews().get(0).getReviewer().getEmail(),pcMember.getEmail());
//        assertEquals(paperEntity.get().getReviews().get(0).getRecommendation().getText(),"Recommendation");
//
//    }
//
//    @Test
//    public void assignPaper(){
//        PaperEntity paper = ProgramCommitteeUtil.getPaper();
//        CommitteeMemberEntity pcMember = ProgramCommitteeUtil.getPcMember();
//        pcMember.setEvaluations(new ArrayList<>());
//
//        when(paperRepository.findById(1)).thenReturn(Optional.of(paper));
//        when(pcMemberRepository.findById(anyString())).thenReturn(Optional.of(pcMember));
//
//        pcMember.addReview(paper);
//
//        assertEquals(pcMember.getEvaluations().size(),1);
//        assertEquals(paper.getReviews().size(),1);
//        assertEquals(paper.getReviews().get(0).getReviewer().getEmail(),pcMember.getEmail());
//    }
//
//
//}
