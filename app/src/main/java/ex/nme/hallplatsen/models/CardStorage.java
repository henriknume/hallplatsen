package ex.nme.hallplatsen.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.Trip;

/**
 * Created by nume on 2017-07-16
 */

public class CardStorage {

    //TODO: Use persistent storage (sharedprefs?)
    private static final String TAG = "CardStorage";

    private static CardStorage instance;
    private List<TripCardOld> cards;
    private TripCardOld creation;

    private CardStorage() {
        cards = new ArrayList<>();
        addExampleCards();
    }

    public static CardStorage getInstance() {
        if (instance == null) {
            Log.d(TAG, "--> new instance of model.");
            instance = new CardStorage();
        }
        return instance;
    }

    public void addCard(TripCardOld card) {
        cards.add(card);
    }

    public void removeCard(int index){
        cards.remove(index);
    }

    public List<TripCardOld> getCards() {
        return cards;
    }

    public TripCardOld getCard(int index) {
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

    public TripCardOld getCreation() {
        return creation;
    }

    public void setCreation(TripCardOld card) {
        this.creation = card;
    }

    public void clearCreation() {
        creation = null;
    }

    private void addExampleCards() {
        /*

        "9021014005862000","SKF"
        "9021014004490000","Lindholmen"
        "9021014004140000","Kviberg"
        "9021014001950000","Centralstationen"
        "9021014001760000","Brunnsparken"
        "9021014001960000","Chalmers"

        */
        TripCardOld card = new TripCardOld(new Station("9021014005862000","SKF"), new Station("9021014004140000","Kviberg"));
        addCard(card);
        card = new TripCardOld(new Station("9021014004490000","Lindholmen"), new Station("9021014004140000","Kviberg"));
        addCard(card);
        card = new TripCardOld(new Station("9021014004140000","Kviberg"), new Station("9021014001950000","Centralstationen"));
        addCard(card);
        card = new TripCardOld(new Station("9021014001760000","Brunnsparken"), new Station("9021014001960000","Chalmers"));
        addCard(card);
        card = new TripCardOld(new Station("9021014001960000","Chalmers"), new Station("9021014004490000","Lindholmen"));
        addCard(card);
        card = new TripCardOld(new Station("9021014005862000","SKF"), new Station("9021014001960000","Chalmers"));
        addCard(card);
        card = new TripCardOld(new Station("9021014004140000","Kviberg"), new Station("9021014001960000","Chalmers"));
        addCard(card);
        card = new TripCardOld(new Station("9021014001950000","Centralstationen"), new Station("9021014001960000","Chalmers"));
        addCard(card);
    }

}
