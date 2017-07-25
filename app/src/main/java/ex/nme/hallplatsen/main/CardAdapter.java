package ex.nme.hallplatsen.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ex.nme.hallplatsen.R;
import ex.nme.hallplatsen.models.TripCard;

/**
 * Created by nume on 2017-07-16
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context mContext;
    private List<TripCard> cards;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView count;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
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
        holder.title.setText(card.getFromName());
        holder.count.setText(card.getFromId());

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void updateList(List<TripCard> newCards){
        notifyDataSetChanged();
    }
}
