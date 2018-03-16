package ru.sbt.util.pcaccessapi.jsondto.script;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ScriptMetadata {

    @Expose
    @SerializedName("ID")
    private Integer id;
    @Expose
    @SerializedName("Name")
    private String name;
    @Expose
    @SerializedName("CreatedBy")
    private String createdBy;
    @Expose
    @SerializedName("TestFolderPath")
    private String testFolderPath;
    @Expose
    @SerializedName("Protocol")
    private String protocol;
    @Expose
    @SerializedName("WorkingMode")
    private String workingMode;
}
