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

    private static final int MAX_TEXT_LENGTH = 25;

    private Context context;

    private static class ViewHolder{
        public TextView locationName;
    }

    public LocationListAdapter(@NonNull Context context, @NonNull List<StopLocation> locations) {
        super(context, R.layout.location_row, locations);
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
        holder.locationName.setText(Utils.cutoff(stopLocation.getName(), MAX_TEXT_LENGTH));
        return rowView;
    }


}
