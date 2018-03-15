package ru.sbt.util.pcaccessapi.jsondto.script;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ScriptMetadata {

    @Expose
    @SerializedName("ID")
    private Integer ID;
    @Expose
    @SerializedName("Name")
    private String Name;
    @Expose
    @SerializedName("CreatedBy")
    private String CreatedBy;
    @Expose
    @SerializedName("TestFolderPath")
    private String TestFolderPath;
    @Expose
    @SerializedName("Protocol")
    private String Protocol;
    @Expose
    @SerializedName("WorkingMode")
    private String WorkingMode;
}
