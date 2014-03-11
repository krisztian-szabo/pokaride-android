package ro.cluj.pokaride.persistence;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import ro.cluj.pokaride.PokarideApplication;
import ro.cluj.pokaride.models.Route;
import ro.cluj.pokaride.models.Station;
import android.content.Context;

public class PersistenceManager {
	private static PersistenceManager _instance = null;
	private List<Route> mRoutes;
	private List<Station> mStations;
	private Context context;

	private PersistenceManager(Context context) {
		this.context = context;
	}

	public static PersistenceManager getInstance(Context context) {
		if (_instance == null) {
			_instance = new PersistenceManager(context);
		}
		return _instance;
	}

	public List<Route> getAllRoutes() {
		if (mRoutes != null)
			return mRoutes;
		mRoutes = new ArrayList<Route>();
		String json = null;
		try {
			InputStream is = PokarideApplication.getContext().getAssets()
					.open("routes.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Route station = Route
						.fromJsonObject(jsonArray.getJSONObject(i));
				mRoutes.add(station);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mRoutes;
	}

	public List<Station> getAllStations() {
		if (mStations != null)
			return mStations;
		mStations = new ArrayList<Station>();
		String[] list = null;
		String json = null;
		try {
			list = context.getAssets().list("stations");
			for (String s : list) {
				InputStream is = PokarideApplication.getContext().getAssets()
						.open("stations/" + s);
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				json = new String(buffer, "UTF-8");
				JSONArray jsonArray = new JSONArray(json);
				for (int i = 0; i < jsonArray.length(); i++) {
					Station station = Station.fromJsonObject(jsonArray
							.getJSONObject(i));
					mStations.add(station);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mStations;
	}

	public Route getRouteWithId(int id) {
		List<Route> routes = getAllRoutes();
		for (Route route : routes) {
			if (route.getRoute_id() == id) {
				return route;
			}
		}
		return null;
	}

	public Station getStationWithId(int id) {
		List<Station> stations = getAllStations();
		for (Station station : stations) {
			if (station.getStation_id() == id) {
				return station;
			}
		}
		return null;
	}

	public Station getNextStation(Route route, Station station) {
		boolean found = false;
		for (int ids : route.getStations()) {
			if (ids == station.getStation_id()) {
				found = true;
				continue;
			}
			if (found) {
				return getStationWithId(ids);
			}
		}
		return null;
	}
}
