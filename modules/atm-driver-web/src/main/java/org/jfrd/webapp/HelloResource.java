package org.jfrd.webapp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jfrd.webapp.model.Greeting;

@Path("hello")
public class HelloResource 
{

	@GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting hello(@PathParam("param") String name) 
	{
        return new Greeting(name);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String helloUsingJson(Greeting greeting) 
    {
        return greeting.getMessage() + "\n";
    }
}
