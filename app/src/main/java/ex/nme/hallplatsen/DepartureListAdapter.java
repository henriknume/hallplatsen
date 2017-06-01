package ex.nme.hallplatsen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ex.nme.hallplatsen.models.Departure;

/**
 * Created by nm2 on 2017-06-01
 */

public class DepartureListAdapter extends ArrayAdapter {
    private Context context;

    private static class ViewHolder{
        public TextView routeName;
        public TextView direction;
        public TextView timeTo;
    }

    public DepartureListAdapter(@NonNull Context context, @NonNull ArrayList<Departure> departures) {
        super(context, R.layout.departure_row, departures);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;

        // Reuse old view for better performance.
        if(rowView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.departure_row, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.routeName = (TextView) rowView.findViewById(R.id.route_name);
            viewHolder.direction = (TextView) rowView.findViewById(R.id.route_direction);
            viewHolder.timeTo = (TextView) rowView.findViewById(R.id.time_to_dep);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Departure d = (Departure) getItem(position);
        holder.routeName.setText(d.getRouteName());
        holder.direction.setText(d.getRouteDirection());
        holder.timeTo.setText(d.getTimeToDeparture());

        return rowView;
    }
}