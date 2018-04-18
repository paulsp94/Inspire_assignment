package com.bd.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import unit.SpatialObject;
import unit.query.QueryType;



@Path("/api")
public class RestAPI {

	  @GET
	  @Produces("application/json")
	  public Response noInput() throws JSONException {

		String result = "Error: no input";
		return Response.status(200).entity(result).build();
	  }

	  @Path("id={id}&lat={lat}&lon={lon}&query={q}")
	  @GET
	  @Produces("application/json")
	  public Response inputGiven(	@PathParam("id") String id,
			  						@PathParam("lat") String lat,
			  						@PathParam("lon") String lon,
			  						@PathParam("q") String q) throws JSONException {
		  
		  String results = null;	  
		  try {
			results = query.RunQueryLatest.main(id+"\t"+lat+"\t"+lon+"\t"+q);
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  
		  
		  
		  /*
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id: ", id);
		jsonObject.put("latitude: ", lat);
		jsonObject.put("longitude: ", lon);
		jsonObject.put("query: ", q); 
*/ 			
		  

		String result = "";//+jsonObject;
		return Response.status(200).entity(results).build();
	  }
}