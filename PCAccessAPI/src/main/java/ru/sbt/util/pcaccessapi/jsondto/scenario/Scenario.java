package ru.sbt.util.pcaccessapi.jsondto.scenario;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Scenario {

    @SerializedName("ID")
    private Integer id;

    @SerializedName("Name")
    private String name;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("Content")
    private Content content;

    @Data
    @NoArgsConstructor
    public static class Content {
        @SerializedName("Groups")
        List<VuserGroup> vuserGroups;
    }
}

