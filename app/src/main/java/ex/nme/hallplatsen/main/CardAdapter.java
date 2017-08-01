package ex.nme.hallplatsen.main;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.Utils;
import ex.nme.hallplatsen.models.CardStorage;
import ex.nme.hallplatsen.models.TripCard;
import ex.nme.hallplatsen.models.reseplaneraren.Leg;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;
import ex.nme.hallplatsen.models.responses.TripResponse;
import ex.nme.hallplatsen.services.ReseplanerarenService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nume on 2017-07-16
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private static final int MAX_TRIPS_PER_CARD = 5;
    private static final String TAG = "CardAdapter";
    private Context mContext;
    private List<TripCard> cards;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fromName;
        public TextView toName;
        public ImageButton switchBtn;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            fromName = (TextView) view.findViewById(R.id.card_from_value);
            toName = (TextView) view.findViewById(R.id.card_to_value);
            switchBtn = (ImageButton) view.findViewById(R.id.card_switch_button);
            linearLayout = (LinearLayout) view.findViewById(R.id.trips_linearlayout);
            switchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "switch Button pressed");
                    int current = getAdapterPosition();
                    TripCard tripCard = cards.get(current);
                    tripCard.switchToAndFromLocations();
                    notifyDataSetChanged();
                    new DownloadSingleTripTask().execute(current);
                }
            });
        }
    }

    private List<Trip> requestTrip(TripCard card){
        String currentTime = Utils.time();
        Call<TripResponse> call = ReseplanerarenService.getService().getTrip(card.getFromId(), card.getToId(), Utils.date(), currentTime, "json");
        try {
            Response<TripResponse> response =  call.execute();
            if (response.isSuccessful()) {
                List<Trip> newList = new ArrayList<>();
                newList.addAll(response.body().getTripList().getTrip());
                return newList;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
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
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        //Clear trip list layout
        holder.linearLayout.removeAllViewsInLayout();

        //Add a departure row for each trip
        List<Trip> trips = card.getTripList();
        for (int i = 0; i < trips.size() && i < MAX_TRIPS_PER_CARD; i++) {
            View newRow = layoutInflater.inflate(R.layout.departure_row, null);
            newRow.setId(i);
            Leg leg = card.getTripList().get(i).getLeg().get(0);

            TextView routeName = (TextView) newRow.findViewById(R.id.route_name);
            routeName.setText(leg.getSname());
            if(leg.getFgColor() != null && leg.getBgColor() != null){
                routeName.setBackgroundColor(Color.parseColor(leg.getFgColor()));
                routeName.setTextColor(Color.parseColor(leg.getBgColor()));
            }

            TextView routeDirection = (TextView) newRow.findViewById(R.id.route_direction);
            routeDirection.setText(leg.getDirection());

            TextView timeToDep = (TextView) newRow.findViewById(R.id.time_to_dep);
            String time = leg.getOrigin().getRtTime();
            if (time == null) {
                time = leg.getOrigin().getTime();
            }
            timeToDep.setText(Utils.timeDiff(time));

            holder.linearLayout.addView(newRow);
        }

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    private class DownloadSingleTripTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected void onPreExecute() {
            //TODO: Show progress spinner.
        }

        @Override
        protected Void doInBackground(Integer... index) {
            Integer i = index[0];
            TripCard card = cards.get(i);
            List<Trip> list = requestTrip(card);
            card.setTripList(list);
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            notifyDataSetChanged();
            //TODO: Hide progress spinner.
        }
    }
}

