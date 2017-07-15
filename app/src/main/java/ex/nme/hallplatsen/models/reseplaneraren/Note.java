
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

    /**
     * No args constructor for use in serialization
     * 
     */
    public Note() {
    }

    /**
     * 
     * @param $
     * @param priority
     * @param severity
     * @param key
     */
    public Note(String key, String severity, String priority, String $) {
        super();
        this.key = key;
        this.severity = severity;
        this.priority = priority;
        this.$ = $;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Note withKey(String key) {
        this.key = key;
        return this;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Note withSeverity(String severity) {
        this.severity = severity;
        return this;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Note withPriority(String priority) {
        this.priority = priority;
        return this;
    }

    public String get$() {
        return $;
    }

    public void set$(String $) {
        this.$ = $;
    }

    public Note with$(String $) {
        this.$ = $;
        return this;
    }

}
