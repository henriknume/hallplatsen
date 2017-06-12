package ex.nme.hallplatsen.models.reseplaneraren;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("severity")
    @Expose
    private String severity;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("$")
    @Expose
    private String $;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String get$() {
        return $;
    }

    public void set$(String $) {
        this.$ = $;
    }

}
