package ru.sbt.util.pcaccessapi.jsondto;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Scenario {

    @SerializedName("ID")
    private Integer id;

    @SerializedName("Name")
    private String name;

    @SerializedName("CreatedBy")
    private String createdBy;

    @SerializedName("Content")
    private Content content;

    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Content {

        @SerializedName("Groups")
        List<VuserGroup> vuserGroups;

    }

}

