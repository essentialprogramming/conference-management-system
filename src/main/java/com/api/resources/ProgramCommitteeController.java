package com.api.resources;

import com.model.Recommendation;
import com.service.ProgramCommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/programCommittee")
public class ProgramCommitteeController {

    private ProgramCommitteeService pcService;

    @Autowired
    public ProgramCommitteeController(ProgramCommitteeService pcService) {
        this.pcService = pcService;
    }

    @POST
    @Path("/recommendation")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Recommendation addRecommendation(Recommendation recommendation) {
        return pcService.addRecommendation(recommendation);
    }
}
