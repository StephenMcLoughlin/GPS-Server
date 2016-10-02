package code;


import java.sql.Timestamp;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;


@Path("/userlocation")
public class UserLocationController {

	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserLocation() {
		JSONObject latLngObj = new JSONObject();
		
		//Connect to database
		Database db = new Database();
		db.getConnection();
		
		//Get coords
		latLngObj = db.getLatLng();
		
		//return result
		return Response.status(200).entity(latLngObj).build();
	}
	
	@GET
	@Path("/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData() {
		JSONObject locationData = new JSONObject();
		
		//Connect to database
		Database db = new Database();
		db.getConnection();
		
		locationData = db.getData();
		
		return Response.status(200).entity(locationData).build();
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postUserLocation(UserDevice user) {
		
		System.out.println(user.toString());
		
		String str = null;
		
		//Connect to database
		Database db = new Database();
		db.getConnection();
		/*
		boolean tableExists = db.doesTableExist(user.name);
		if(!tableExists) {
			db.createTable(user.name);
		}
		*/
		/*
		Date timeDate = new Date(); //delete
		Timestamp time = new Timestamp(timeDate.getTime());
*/
		//Get Reverse Geocode address
		GoogleGeocode address = new GoogleGeocode();
		address.sendCoordToGoogle(user.getLatitude(), user.getLongitude());
		user.setAddress(address.getAddress());
	
		//Submit to the database
		db.submitUserLocationData(user.getTime(), user.getAddress(),user.getLatitude(), user.getLongitude());

		//Return result
		return Response.status(201).entity(user.toString()).build();
	}
}
