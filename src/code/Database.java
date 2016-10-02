package code;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.mysql.jdbc.PreparedStatement;

public class Database {
	private Connection conn = null;
	
	public Database() {
		
	}
	
	public void getConnection() {
		String DB_URL = "jdbc:mysql://localhost:3306/userlocationdatabase";
		String username = "root";
		String password = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
		    conn = (Connection) DriverManager.getConnection(DB_URL,username,password);
		   
			conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/*
	public boolean doesTableExist(String tableName) {
		DatabaseMetaData md;
		try {
			md = (DatabaseMetaData) conn.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			if(rs.next()) {
				return true;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	*/
	/*
	public void createTable(String name) {
		try {
			
			String sql = "CREATE TABLE " + name +
					" (id INTEGER NOT NULL AUTO_INCREMENT,"
					+ "time DATETIME(6),"
					+ "address VARCHAR(50),"
					+ "latitude FLOAT,"
					+ "longitude FLOAT,"
					+ "PRIMARY KEY(id))";
			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	*/
	public JSONObject getLatLng() {
		String sql = "SELECT * from userlocationdatabase WHERE time = " +
					"(SELECT MAX(time) FROM userlocationdatabase) LIMIT 1";
		PreparedStatement stmt = null;
		JSONObject jsonObj = new JSONObject();
		
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet result =stmt.executeQuery();
			
			while(result.next()) {
				try {
					jsonObj.put("address", result.getString("address"));
					jsonObj.put("latitude", result.getFloat("latitude"));
					jsonObj.put("longitude", result.getFloat("longitude"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	public void submitUserLocationData(String timeDate, 
			String address, float latitude, float longitude) {
		
		String sql = "INSERT INTO userlocationdatabase (time, address, latitude, longitude) " +
					"VALUES(?,?,?,?)";

		PreparedStatement stmt = null;
		
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			
			stmt.setString(1, timeDate);
			stmt.setString(2, address);
			stmt.setFloat(3, latitude);
			stmt.setFloat(4, longitude);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject getData() {
		
		String sql = "SELECT * from userlocationdatabase";
		PreparedStatement stmt = null;
		JSONArray jArray = new JSONArray();
		JSONObject obj = new JSONObject();
		
		try {
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet result =stmt.executeQuery();
			
			while(result.next()) {
				try {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("time", result.getString("time"));
					jsonObj.put("address", result.getString("address"));
					jsonObj.put("latitude", result.getFloat("latitude"));
					jsonObj.put("longitude", result.getFloat("longitude"));
					jArray.put(jsonObj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			try {
				obj.put("location_data", jArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
	
}
