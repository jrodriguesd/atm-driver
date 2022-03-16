package org.jpos.rest;
import org.jpos.iso.ISOMsg;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


@Path("/readIso")
public class ReadIso {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{IsoMsgFromGet}")
    //@Path("/{name}/{family}")


    public Response echoGet(
            //@PathParam("name") String name,
            @PathParam("IsoMsgFromGet") String tMsgFromGet,
            String tMsgFromBody
    ) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("success", "true");
        //resp.put("Name", name);
        resp.put("IsoMsgFromGet", tMsgFromGet);
        resp.put("IsoMsgFromBody", tMsgFromBody);
        Response.ResponseBuilder rb = Response.ok(resp,
MediaType.APPLICATION_JSON).status(Response.Status.OK);
        return rb.build();
    }
}