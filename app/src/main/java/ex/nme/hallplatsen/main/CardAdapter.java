package ex.nme.hallplatsen.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.Utils;
import ex.nme.hallplatsen.models.TripCard;
import ex.nme.hallplatsen.models.reseplaneraren.Leg;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nume on 2017-07-16
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private static final String TAG = "CardAdapter";
    private Context mContext;
    private List<TripCard> cards;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fromName;
        public TextView toName;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            fromName = (TextView) view.findViewById(R.id.card_from_value);
            toName = (TextView) view.findViewById(R.id.card_to_value);
            linearLayout = (LinearLayout) view.findViewById(R.id.trips_linearlayout);
        }
    }

    public CardAdapter(Context mContext, List<TripCard> cards) {
        this.mContext = mContext;
        this.cards = cards;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TripCard card = cards.get(position);
        holder.fromName.setText(card.getFromName());
        holder.toName.setText(card.getToName());

        //inflate
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        Log.d(TAG, "onBindViewHolder()");

        List<Trip> trips = card.getTripList();
        for (int i = 0; i < trips.size(); i++) {
                View inflatedView = layoutInflater.inflate(R.layout.departure_row, null);
                inflatedView.setId(i);
                TextView routeName = (TextView) inflatedView.findViewById(R.id.route_name);
                TextView routeDirection = (TextView) inflatedView.findViewById(R.id.route_direction);
                TextView timeToDep = (TextView) inflatedView.findViewById(R.id.time_to_dep);

                Leg leg = card.getTripList().get(i).getLeg().get(0);
                routeName.setText(leg.getSname());
                routeDirection.setText(leg.getDirection());
                String time = leg.getOrigin().getRtTime();
                if (time == null) {
                    time = leg.getOrigin().getTime();
                }
                timeToDep.setText(Utils.timeDiff(time));
                holder.linearLayout.addView(inflatedView);
        }

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void updateList(){

        notifyDataSetChanged();
    }
}
