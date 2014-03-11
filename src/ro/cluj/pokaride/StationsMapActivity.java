package ro.cluj.pokaride;

import java.util.HashMap;
import java.util.List;

import ro.cluj.pokaride.models.Station;
import ro.cluj.pokaride.persistence.PersistenceManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StationsMapActivity extends FragmentActivity {
	private GoogleMap mGoogleMap;
	private HashMap<Marker, Station> mStationMarkerMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations_map);

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mGoogleMap.setOnMarkerClickListener(mOnMarkerClickListener);
		setupMap();
	}

	private void setupMap() {
		mGoogleMap.setMyLocationEnabled(true);
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = manager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null) {
			LatLng myLocation = new LatLng(location.getLatitude(),
					location.getLongitude());
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
					17));
		}
		List<Station> stations = PersistenceManager.getInstance(this)
				.getAllStations();
		mStationMarkerMap = new HashMap<Marker, Station>();
		for (Station s : stations) {
			Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(
					new LatLng(s.getLat(), s.getLon())).icon(
					BitmapDescriptorFactory.fromResource(R.drawable.icn_bus)));
			mStationMarkerMap.put(marker, s);
		}
	}

	private OnMarkerClickListener mOnMarkerClickListener = new OnMarkerClickListener() {
		@Override
		public boolean onMarkerClick(Marker arg0) {
			Station station = mStationMarkerMap.get(arg0);
			Intent intent = new Intent(StationsMapActivity.this,
					StationActivity.class);
			intent.putExtra("station", station.toJson());
			startActivity(intent);
			return false;
		}
	};

}
