package ro.cluj.pokaride.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;

public class Route {
	private static final String ROUTE_ID = "route_id";
	private int route_id;
	private static final String ROUTE_NAME = "route_name";
	private String route_name;
	private static final String COLOR = "color";
	private int color;
	private static final String SCHEDULES = "schedules";
	private static final String START_TIME = "start_time";
	private List<Date> start_time;
	private static final String END_TIME = "end_time";
	private List<Date> end_time;
	private static final String STATIONS = "stations";
	private List<Integer> stations;

	public Route() {
		this.start_time = new ArrayList<Date>();
		this.end_time = new ArrayList<Date>();
		this.stations = new ArrayList<Integer>();
	}

	public int getRoute_id() {
		return route_id;
	}

	public void setRoute_id(int route_id) {
		this.route_id = route_id;
	}

	public String getRoute_name() {
		return route_name;
	}

	public void setRoute_name(String route_name) {
		this.route_name = route_name;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public List<Date> getStart_time() {
		return start_time;
	}

	public void setStart_time(List<Date> start_time) {
		this.start_time = start_time;
	}

	public List<Date> getEnd_time() {
		return end_time;
	}

	public void setEnd_time(List<Date> end_time) {
		this.end_time = end_time;
	}

	public List<Integer> getStations() {
		return stations;
	}

	public void setStations(List<Integer> stations) {
		this.stations = stations;
	}

	public static Route fromJsonObject(JSONObject jsonObject) {
		Route route = new Route();
		try {
			if (jsonObject.has(ROUTE_ID)) {
				route.route_id = jsonObject.getInt(ROUTE_ID);
			}
			if (jsonObject.has(ROUTE_NAME)) {
				route.route_name = jsonObject.getString(ROUTE_NAME);
			}
			if (jsonObject.has(COLOR)) {
				route.color = Color.argb(255, jsonObject.getJSONObject(COLOR)
						.getInt("red"), jsonObject.getJSONObject("color")
						.getInt("green"), jsonObject.getJSONObject("color")
						.getInt("blue"));
			}
			if (jsonObject.has(STATIONS)) {
				JSONArray stations = jsonObject.getJSONArray(STATIONS);
				for (int i = 0; i < stations.length(); i++) {
					route.stations.add(stations.getInt(i));
				}
			}
			if (jsonObject.has(SCHEDULES)) {
				JSONArray schedules = jsonObject.getJSONArray(SCHEDULES);
				for (int i = 0; i < schedules.length(); i++) {
					JSONObject sch = schedules.getJSONObject(i);
					String startTime = sch.getString(START_TIME);
					String endTime = sch.getString(END_TIME);
					SimpleDateFormat sdf = new SimpleDateFormat("hh:mm",
							Locale.getDefault());
					route.start_time.add(sdf.parse(startTime));
					route.end_time.add(sdf.parse(endTime));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return route;
	}
}
