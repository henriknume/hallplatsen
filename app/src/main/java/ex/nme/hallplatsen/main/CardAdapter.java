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
        public TextView fromName;
        public TextView toName;

        public MyViewHolder(View view) {
            super(view);

            fromName = (TextView) view.findViewById(R.id.card_from_value);
            toName = (TextView) view.findViewById(R.id.card_to_value);
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

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void updateList(){
        notifyDataSetChanged();
    }
}
