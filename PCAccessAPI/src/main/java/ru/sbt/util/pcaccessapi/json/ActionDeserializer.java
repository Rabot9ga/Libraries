package ru.sbt.util.pcaccessapi.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ru.sbt.util.pcaccessapi.jsondto.scenario.Scheduler;

import java.lang.reflect.Type;
import java.util.Map;

public class ActionDeserializer implements JsonDeserializer<Scheduler.Action> {
    @Override
    public Scheduler.Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Map.Entry<String, JsonElement> entry = json.getAsJsonObject().entrySet().iterator().next();
        switch (entry.getKey()) {
            case "StartGroup":
                return context.deserialize(entry.getValue(), Scheduler.StartGroup.class);
            case "Initialize":
                return context.deserialize(entry.getValue(), Scheduler.Initialize.class);
            case "StartVusers":
                return context.deserialize(entry.getValue(), Scheduler.StartVusers.class);
            case "Duration":
                return context.deserialize(entry.getValue(), Scheduler.Duration.class);
            case "StopVusers":
                return context.deserialize(entry.getValue(), Scheduler.StopVusers.class);
            default:
                throw new JsonParseException("Can not deserialize JSON object: " + json);
        }
    }
}
