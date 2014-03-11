package ro.cluj.pokaride;

import ro.cluj.pokaride.adapter.RouteListAdapter;
import ro.cluj.pokaride.models.Route;
import ro.cluj.pokaride.models.Station;
import ro.cluj.pokaride.persistence.PersistenceManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RouteActivity extends Activity {
	private TextView mStationName;
	private Button mSwitchButton;
	private ListView mRouteList;
	private RouteListAdapter mRouteAdapter;
	private Station mStation;
	private Route mRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);

		mStation = Station.fromJsonString(getIntent().getExtras().getString(
				"station"));
		mRoute = PersistenceManager.getInstance(this).getRouteWithId(
				getIntent().getExtras().getInt("routeId"));

		findIds();
	}

	private void findIds() {
		mStationName = (TextView) findViewById(R.id.station_name);
		mStationName.setText(mStation.getStation_name());
		mSwitchButton = (Button) findViewById(R.id.station_switch);
		mSwitchButton.setVisibility(View.INVISIBLE);
		mRouteList = (ListView) findViewById(R.id.station_buslist);
		mRouteAdapter = new RouteListAdapter(this, mRoute);
		mRouteList.setAdapter(mRouteAdapter);
	}

}
