package si.fri.rso.project.vehicle.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.project.vehicle.config.RestProperties;
import si.fri.rso.project.vehicle.lib.Vehicle;
import si.fri.rso.project.vehicle.services.beans.VehicleBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/vehicles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VehicleResource {

    private Logger log = Logger.getLogger(VehicleResource.class.getName());

    @Inject
    private VehicleBean vehicleBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all image metadata.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of image metadata",
                    content = @Content(schema = @Schema(implementation = Vehicle.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getVehicles() {

        List<Vehicle> vehicle = vehicleBean.getVehicle();

        return Response.status(Response.Status.OK).entity(vehicle).build();
    }


    @Operation(description = "Get all vehicles for user.", summary = "Get all vehicles for user")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of vehicles for user",
                    content = @Content(schema = @Schema(implementation = Vehicle.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    @Path("/user/{userId}")
    public Response getVehiclesForUser(@Parameter(description = "User ID.", required = true)
                                           @PathParam("userId") Integer userId)  {

        List<Vehicle> vehicle = vehicleBean.getVehiclesForUser(userId);

        return Response.status(Response.Status.OK).entity(vehicle).build();
    }

    @Operation(description = "Get data for vehicle.", summary = "Get data for vehicle")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Vehicle data",
                    content = @Content(
                            schema = @Schema(implementation = Vehicle.class))
            )})
    @GET
    @Path("/{vehicleId}")
    public Response getVehicle(@Parameter(description = "Vehicle ID.", required = true)
                                     @PathParam("vehicleId") Integer vehicleId) {

        Vehicle vehicle = vehicleBean.getVehicle(vehicleId);

        if (vehicle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(vehicle).build();
    }

    @Operation(description = "Add vehicle data.", summary = "Add vehicle data")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Vehicle successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createVehicle(@RequestBody(
            description = "DTO object with vehicle data.",
            required = true, content = @Content(
            schema = @Schema(implementation = Vehicle.class))) Vehicle vehicle) {

        if ((vehicle.getLstCharge() == null || vehicle.getUserId() == null || vehicle.getModel() == null || vehicle.getNumOfKilometers() == null || vehicle.getNumOfChargesLstMonth() == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            vehicle = vehicleBean.createVehicle(vehicle);
        }

        return Response.status(Response.Status.CREATED).entity(vehicle).build();

    }


    @Operation(description = "Update data for a vehicle.", summary = "Update vehicle")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Vehicle successfully updated."
            )
    })
    @PUT
    @Path("{vehicleId}")
    public Response putImageMetadata(@Parameter(description = "Vehicle ID.", required = true)
                                     @PathParam("vehicleId") Integer vehicleId,
                                     @RequestBody(
                                             description = "DTO object with vehicle data.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Vehicle.class)))
                                             Vehicle vehicle){

        vehicle = vehicleBean.putVehicle(vehicleId, vehicle);

        if (vehicle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();

    }

    @Operation(description = "Delete vehicle data.", summary = "Delete vehicle data")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Vehicle successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{vehicleId}")
    public Response deleteVehicle(@Parameter(description = "Vehicle ID.", required = true)
                                        @PathParam("vehicleId") Integer vehicleId){

        boolean deleted = vehicleBean.deleteImageMetadata(vehicleId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
