package ro.cluj.pokaride;

import ro.cluj.pokaride.adapter.BusListAdapter;
import ro.cluj.pokaride.models.Station;
import ro.cluj.pokaride.persistence.PersistenceManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StationActivity extends Activity {
	private TextView mStationName;
	private Button mSwitchButton;
	private ListView mBusList;
	private BusListAdapter mBusAdapter;
	private Station mStation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station);

		mStation = Station.fromJsonString(getIntent().getExtras().getString(
				"station"));

		findIds();
	}

	private void findIds() {
		mStationName = (TextView) findViewById(R.id.station_name);
		mStationName.setText(mStation.getStation_name());
		mSwitchButton = (Button) findViewById(R.id.station_switch);
		mSwitchButton.setOnClickListener(mSwitchClickListener);
		mBusList = (ListView) findViewById(R.id.station_buslist);
		mBusList.setOnItemClickListener(mBusSelectListener);
		mBusAdapter = new BusListAdapter(this, PersistenceManager.getInstance(
				this).getAllRoutes(), mStation);
		mBusList.setAdapter(mBusAdapter);
	}

	private OnClickListener mSwitchClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mStation = PersistenceManager.getInstance(StationActivity.this)
					.getStationWithId(mStation.getOpposite_station_id());
			if (mStation != null) {
				findIds();
			} else {
				Toast.makeText(StationActivity.this, "Station not found!",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private OnItemClickListener mBusSelectListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(StationActivity.this,
					RouteActivity.class);
			intent.putExtra("station", mStation.toJson());
			intent.putExtra("routeId",
					PersistenceManager.getInstance(StationActivity.this)
							.getAllRoutes().get(arg2).getRoute_id());
			startActivity(intent);

		}
	};
}
