package dev.terrylabs.gitdoit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.terrylabs.gitdoit.api.ApiClient
import dev.terrylabs.gitdoit.api.GitHubApi
import dev.terrylabs.gitdoit.model.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var gitHubService: GitHubApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gitHubService = ApiClient.instance.create(GitHubApi::class.java)
        loadRepositoryList(BaseApplication.userData.login)
    }

    private fun loadRepositoryList(loginName: String?) {
        loginName?.let {
            this.gitHubService.listRepos(loginName).enqueue(object : Callback<List<Repo>> {
                override fun onFailure(call: Call<List<Repo>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                    response.body()?.let {
                        Log.e("MAIN ::", "Load RepositoryList")
                        initRecyclerView(response.body()!!)
                    }
                }
            })
        }
    }

    private fun initRecyclerView(list: List<Repo>) {
        // TEMP
        this.gitHubService.getRepoCommitPerHourEachDay(BaseApplication.userData.login!!, list[0].name!!)
            .enqueue(object : Callback<List<List<Int>>> {
                override fun onFailure(call: Call<List<List<Int>>>, t: Throwable) {
                    Log.e("MAIN ::", "Load RepositoryList - Failure")
                }

                override fun onResponse(call: Call<List<List<Int>>>, response: Response<List<List<Int>>>) {
                    response.body()?.let {
                        printNestedArray(response.body())
                    }
                }
            })
    }

    private fun printNestedArray(commitList: List<List<Int>>?) {
        for (list in commitList!!) {
            Log.e("MAIN ::", "${list[0]}/${list[1]}/${list[2]}")
        }
    }
}
