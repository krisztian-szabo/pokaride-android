package ro.cluj.pokaride.adapter;

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

public class RouteListAdapter extends BaseAdapter {
	private LayoutInflater mLayoutInflater;
	private Route mRoute;
	private Context mContext;

	public RouteListAdapter(Context context, Route route) {
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mRoute = route;
	}

	@Override
	public int getCount() {
		return mRoute.getStations().size();
	}

	@Override
	public Object getItem(int arg0) {
		return PersistenceManager.getInstance(mContext).getStationWithId(
				mRoute.getStations().get(arg0));
	}

	@Override
	public long getItemId(int arg0) {
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

		Station station = (Station) getItem(position);
		holder.name.setText(station.getStation_name());
		Random r = new Random();
		int i1 = r.nextInt(20 - 6) + 6;
		int i2 = r.nextInt(20 - 6) + 6;
		holder.arrive_time.setText("in" + i1 + " mins");
		holder.next_arrive_time.setText("and in " + (i1 + i2) + " min, "
				+ (i1 + i2 + i2) + " min");
		holder.next_station.setText(station.getRoutes().toString());

		return convertView;
	}

	class ViewHolder {
		TextView name, arrive_time, next_station, next_arrive_time;
	}

}
