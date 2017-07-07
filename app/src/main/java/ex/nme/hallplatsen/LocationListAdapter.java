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
import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nume on 2017-07-07.
 */

public class LocationListAdapter extends ArrayAdapter {

    private static final int DIRECTION_LENGTH = 25;

    private Context context;

    private static class ViewHolder{
        public TextView locationName;
    }

    public LocationListAdapter(@NonNull Context context, @NonNull List<StopLocation> trips) {
        super(context, R.layout.locations_row, locations);
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
            viewHolder.locationName = (TextView) rowView.findViewById(R.id.location_name_textview);
            rowView.setTag(viewHolder);
        }

        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        StopLocation stopLocation = (StopLocation) getItem(position);
        holder.routeName.setText(firstLeg.getSname());
        holder.direction.setText(cutoff(firstLeg.getDirection(), DIRECTION_LENGTH));
        String time = Utils.timeDiff(firstLeg.getOrigin().getTime());
        holder.timeTo.setText(time);
        return rowView;
    }

    private String cutoff(String str, int len){
        String result = str;
        if(str.length() > len){
            result = str.substring(0, len).trim();
        }
        return result;
    }
}
