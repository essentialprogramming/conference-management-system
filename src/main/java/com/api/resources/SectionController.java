package com.api.resources;


import com.model.Section;
import com.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/section")
public class SectionController {

    private SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Section addSection(Section section) {
        return sectionService.addSection(section);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSection(@PathParam("id") int id) {
        sectionService.deleteSection(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Section findById(@PathParam("id") int id) {
        return sectionService.findById(id);
    }

}
