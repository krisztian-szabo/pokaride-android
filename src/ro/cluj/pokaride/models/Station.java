package ro.cluj.pokaride.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Station {
	private static final String STATION_ID = "station_id";
	private int station_id;
	private static final String OPPOSITE_STATION_ID = "opposite_station_id";
	private int opposite_station_id;
	private static final String STATION_NAME = "station_name";
	private String station_name;
	private static final String LAT = "lat";
	private double lat;
	private static final String LON = "lon";
	private double lon;
	private static final String ROUTES = "routes";
	private List<Integer> routes;

	public Station() {
		this.routes = new ArrayList<Integer>();
	}

	public int getStation_id() {
		return station_id;
	}

	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	public int getOpposite_station_id() {
		return opposite_station_id;
	}

	public void setOpposite_station_id(int opposite_station_id) {
		this.opposite_station_id = opposite_station_id;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public List<Integer> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Integer> routes) {
		this.routes = routes;
	}

	public static Station fromJsonString(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			return Station.fromJsonObject(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Station fromJsonObject(JSONObject jsonObject) {
		Station station = new Station();
		try {
			if (jsonObject.has(STATION_ID)) {
				station.setStation_id(jsonObject.getInt(STATION_ID));
			}
			if (jsonObject.has(OPPOSITE_STATION_ID)) {
				station.setOpposite_station_id(jsonObject
						.getInt(OPPOSITE_STATION_ID));
			}
			if (jsonObject.has(STATION_NAME)) {
				station.setStation_name(jsonObject.getString(STATION_NAME));
			}
			if (jsonObject.has(LAT)) {
				station.setLat(jsonObject.getDouble(LAT));
			}
			if (jsonObject.has(LON)) {
				station.setLon(jsonObject.getDouble(LON));
			}
			if (jsonObject.has(ROUTES)) {
				JSONArray routes = jsonObject.getJSONArray(ROUTES);
				for (int i = 0; i < routes.length(); i++) {
					station.getRoutes().add(routes.getInt(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			station = null;
		}
		return station;
	}

	public String toJson() {
		String result = null;
		try {
			JSONObject json = new JSONObject();
			json.put("station_id", this.station_id);
			json.put("opposite_station_id", this.opposite_station_id);
			json.put("station_name", this.station_name);
			json.put("lat", this.lat);
			json.put("lon", this.lon);
			JSONArray arr = new JSONArray();
			for (Integer i : this.routes) {
				arr.put(i);
			}
			json.put("routes", arr);
			result = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
