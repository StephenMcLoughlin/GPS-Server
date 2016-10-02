package code;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class UserDevice {
	String name;
	String address;
	String time;
	float latitude;
	float longitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String timestampLong) {
		long time  = Long.valueOf(timestampLong);
		Date timeDate = new Date(time * 1000); //delete
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.time = format.format(timeDate);
	}
	
	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "{address:" + address + ", latitude:" + latitude + ", longitude:" + longitude + ", time: " + time +"}"; 
	}
}
