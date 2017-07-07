package ex.nme.hallplatsen.services;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.Leg;

/**
 * Adapter used for converting json-objects to java-arrays when they appear.
 */

public class LegTypeAdapter implements JsonDeserializer<List<Leg>> {
    public List<Leg> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
        List<Leg> values = new ArrayList<>();
        if (json.isJsonArray()) {
            for (JsonElement e : json.getAsJsonArray()) {
                values.add((Leg) ctx.deserialize(e, Leg.class));
            }
        } else if (json.isJsonObject()) {
            values.add((Leg) ctx.deserialize(json, Leg.class));
        } else {
            throw new RuntimeException("Unexpected JSON type: " + json.getClass());
        }
        return values;
    }
}
