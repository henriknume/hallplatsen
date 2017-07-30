package ex.nme.hallplatsen.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.StopLocation;
import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nume on 2017-07-16
 */

public class CardStorage {

    //TODO: Use persistent storage (sharedprefs?)
    private static final String TAG = "CardStorage";

    private static CardStorage instance;
    private List<TripCard> cards;
    private TripCard creation;

    private CardStorage() {
        cards = new ArrayList<>();
    }

    public static CardStorage getInstance() {
        if (instance == null) {
            Log.d(TAG, "--> new instance of model.");
            instance = new CardStorage();
        }
        return instance;
    }

    public void addCard(TripCard card) {
        cards.add(card);
    }

    public void removeCard(int index){
        cards.remove(index);
    }

    public List<TripCard> getCards() {
        return cards;
    }

    public TripCard getCard(int index) {
        if (index >= cards.size()) {
            return null;
        }
        return cards.get(index);
    }

    public void setTripList(int cardIndex, List<Trip> trips) {
        if (cardIndex >= cards.size()) {
            cards.get(cardIndex).setTripList(trips);
        } else {
            throw new ArrayIndexOutOfBoundsException("CardIndex is out of bounds");
        }
    }

    public TripCard getCreation() {
        return creation;
    }

    public void setCreation(TripCard card) {
        this.creation = card;
    }

    public void clearCreation() {
        creation = null;
    }

}
