package ro.cluj.pokaride.adapter;

import java.util.List;
import java.util.Random;

import ro.cluj.pokaride.R;
import ro.cluj.pokaride.models.Route;
import ro.cluj.pokaride.models.Station;
import ro.cluj.pokaride.persistence.PersistenceManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private List<Route> routes;
	private Station mStation;
	private Context mContext;

	public BusListAdapter(Context context, List<Route> routes, Station station) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.routes = routes;
		this.mStation = station;
	}

	@Override
	public int getCount() {
		return this.routes.size();
	}

	@Override
	public Object getItem(int position) {
		return this.routes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.row_bus, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.bus_name);
			holder.arrive_time = (TextView) convertView
					.findViewById(R.id.bus_arrive_time);
			holder.next_station = (TextView) convertView
					.findViewById(R.id.bus_next_station);
			holder.next_arrive_time = (TextView) convertView
					.findViewById(R.id.bus_arrive_next);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Route route = this.routes.get(position);
		holder.name.setText(route.getRoute_name());
		holder.name.setTextColor(route.getColor());
		Random r = new Random();
		int i1 = r.nextInt(20 - 6) + 6;
		int i2 = r.nextInt(20 - 6) + 6;
		holder.arrive_time.setText(i1 + " mins");
		holder.next_arrive_time.setText((i1 + i2) + " min, " + (i1 + i2 + i2)
				+ " min");
		Station nextStation = PersistenceManager.getInstance(mContext)
				.getNextStation(route, mStation);
		if (nextStation != null) {
			holder.next_station.setText(nextStation.getStation_name());
		}

		return convertView;
	}

	class ViewHolder {
		TextView name, arrive_time, next_station, next_arrive_time;
	}
}
