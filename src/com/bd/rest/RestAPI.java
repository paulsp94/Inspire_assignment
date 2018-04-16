package com.bd.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import query.RunQueryLatest;

@Path("/api")
public class RestAPI {

	  @GET
	  @Produces("application/json")
	  public Response noInput() throws JSONException {

		String result = "Error: no input";
		return Response.status(200).entity(result).build();
	  }

	  @Path("{s}")
	  @GET
	  @Produces("application/json")
	  public Response inputGiven(@PathParam("s") String s) throws JSONException {
		  
		  /*
		  try {
			query.RunQueryLatest.main(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		JSONObject jsonObject = new JSONObject();
 
		jsonObject.put("Input: ", s); 


		String result = ""+jsonObject;
		return Response.status(200).entity(result).build();
	  }
}