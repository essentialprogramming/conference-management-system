package com.api.resources;


import com.model.Recommendation;
import com.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/recommendation")
public class RecommendationController {

    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Recommendation addRecommendation(Recommendation recommendation) {
        return recommendationService.addRecommendation(recommendation);
    }
}
