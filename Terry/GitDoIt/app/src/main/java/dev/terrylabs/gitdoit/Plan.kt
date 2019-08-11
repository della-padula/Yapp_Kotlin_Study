package dev.terrylabs.gitdoit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Plan {

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("space")
    @Expose
    var space: Int? = null

    @SerializedName("collaborators")
    @Expose
    var collaborators: Int? = null

    @SerializedName("private_repos")
    @Expose
    var privateRepos: Int? = null

}