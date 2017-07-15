
package ex.nme.hallplatsen.models.reseplaneraren;


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notes {

    @SerializedName("Note")
    @Expose
    private List<Note> note = new ArrayList<Note>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Notes() {
    }

    /**
     * 
     * @param note
     */
    public Notes(List<Note> note) {
        super();
        this.note = note;
    }

    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    public Notes withNote(List<Note> note) {
        this.note = note;
        return this;
    }

}
