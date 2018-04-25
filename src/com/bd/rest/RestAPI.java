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

	  @Path("id={id}&lat={lat}&lon={lon}&query={q}&radius={rad}")
	  @GET
	  @Produces("application/json")
	  public Response inputGiven(	@PathParam("id") String id,
			  						@PathParam("lat") String lat,
			  						@PathParam("lon") String lon,
			  						@PathParam("q") String q,
			  						@PathParam("rad") String rad) throws JSONException {
		 double min_lat = -37.84973559;
		 double min_lng = 144.9;
		 double latRegion = Double.parseDouble(lat) - min_lat;
		 double lonRegion = Double.parseDouble(lon) - min_lng;
		 
		 System.out.println(latRegion);
		  String results = null;	  
		  try {
			results = "[\n"+query.RunQueryLatest.main(id+"\t"+latRegion+"\t"+lonRegion+"\t"+q, rad)+"]";
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }

		return Response.status(200).entity(results).build();
	  }
}