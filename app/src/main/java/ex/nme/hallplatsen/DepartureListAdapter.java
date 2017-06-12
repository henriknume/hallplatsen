package ex.nme.hallplatsen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.Leg;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

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

    public DepartureListAdapter(@NonNull Context context, @NonNull List<Trip> trips) {
        super(context, R.layout.departure_row, trips);
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
        Trip trip = (Trip) getItem(position);
        Leg firstLeg = trip.getLeg().get(0);
        holder.routeName.setText(firstLeg.getName());
        holder.direction.setText(firstLeg.getDirection());
        holder.timeTo.setText(firstLeg.getOrigin().getRtTime());
        return rowView;
    }
}