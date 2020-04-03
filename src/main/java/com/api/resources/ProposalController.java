package com.api.resources;

import com.model.Proposal;
import com.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/proposal")
public class ProposalController {

    private ProposalService proposalService;

    @Autowired
    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Proposal addProposal(Proposal proposal) {

        return proposalService.addProposal(proposal);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Proposal findById(@PathParam("id") int id){
        return proposalService.findById(id);
    }
}
