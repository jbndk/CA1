package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.MemberFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("groupmembers/all")
public class MemberResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final MemberFacade FACADE =  MemberFacade.getMemberFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMembers() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllMembers())).build();
    }
}
