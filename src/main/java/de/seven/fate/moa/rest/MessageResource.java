package de.seven.fate.moa.rest;

import de.seven.fate.moa.facade.MessageFacade;
import de.seven.fate.moa.rest.interceptor.RestErrorInterceptor;
import de.seven.fate.moa.vo.MessageVO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Interceptors(RestErrorInterceptor.class)
public class MessageResource {


    @Inject
    private MessageFacade facade;


    @GET
    public Response findAllOrderedByTitle() {

        return Response.ok(facade.findAllOrderedByTitle()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long messageId) {

        return Response.ok(facade.findById(messageId)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMessage(MessageVO message) {

        return Response.ok(facade.updateMessage(message)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveMessage(MessageVO message) {

        return Response.ok(facade.saveMessage(message)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessage(@PathParam("id") Long messageId) {

        return Response.ok(facade.deleteMessage(messageId)).build();
    }

}