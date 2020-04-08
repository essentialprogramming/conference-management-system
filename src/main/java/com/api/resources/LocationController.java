package com.api.resources;


import com.model.Location;
import com.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/location")
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @POST
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Location addLocation(Location location) {
        return locationService.addLocation(location);
    }

    @DELETE
    @Path("/{id}")
    public void deleteLocation(@PathParam("id") int id) {
        locationService.deleteLocation(id);
    }
}
