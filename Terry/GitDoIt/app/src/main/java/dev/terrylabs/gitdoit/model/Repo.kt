package dev.terrylabs.gitdoit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Repo {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("node_id")
    @Expose
    var nodeId: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null
 }