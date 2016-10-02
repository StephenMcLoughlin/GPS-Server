package code;

import javax.management.RuntimeErrorException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class GoogleGeocode {

	private String address;
	
	public GoogleGeocode() {
		
	}
	
	public void sendCoordToGoogle(double latitude, double longitude) {
		try{
			//Create client
			Client client = new Client();
			
			//Web url
			WebResource webResource = client
					.resource("http://maps.googleapis.com/maps/api/geocode/json?latlng=" 
							+ latitude + "," + longitude + "&sensor=true");
			
			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);
			
			if(response.getStatus() != 200) {
				throw new RuntimeException("Failed: HTTP Error code"
						+ response.getStatus());
			}
			
			String outputResponse = response.getEntity(String.class);
			parseResponse(outputResponse);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseResponse(String response) {
		
		try {
			//Parse JSON
			JSONObject jsonObj = new JSONObject(response);
			JSONObject jsonAddress = jsonObj.getJSONArray("results").getJSONObject(0);
			
			//Set address
			this.address = jsonAddress.get("formatted_address").toString();
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
	}
	
	public String getAddress() {
		return address;
	}
}
