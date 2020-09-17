package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.CarFacade;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("cars")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final CarFacade FACADE =  CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
  
    
     @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCars() {
       return Response.ok().entity(GSON.toJson(FACADE.getAllCars())).build();
    }
    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCarById(@PathParam("id") int id) {
        return Response.ok().entity(GSON.toJson(FACADE.getCarById(id))).build();
    }
}
