package ex.nme.hallplatsen.services;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ex.nme.hallplatsen.models.reseplaneraren.Note;

/**
 * Created by nm2 on 2017-07-26
 */

public class NoteTypeAdapter implements JsonDeserializer<List<Note>> {
    public List<Note> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
        List<Note> values = new ArrayList<>();
        if (json.isJsonArray()) {
            for (JsonElement e : json.getAsJsonArray()) {
                values.add((Note) ctx.deserialize(e, Note.class));
            }
        } else if (json.isJsonObject()) {
            values.add((Note) ctx.deserialize(json, Note.class));
        } else {
            throw new RuntimeException("Unexpected JSON type: " + json.getClass());
        }
        return values;
    }
}