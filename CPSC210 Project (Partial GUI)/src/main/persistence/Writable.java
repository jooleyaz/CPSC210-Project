package persistence;

import org.json.JSONObject;

// modelled from code in JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}