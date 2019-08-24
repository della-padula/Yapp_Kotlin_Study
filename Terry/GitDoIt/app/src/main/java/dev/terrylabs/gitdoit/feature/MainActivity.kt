package dev.terrylabs.gitdoit.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dev.terrylabs.gitdoit.BaseApplication
import dev.terrylabs.gitdoit.R
import dev.terrylabs.gitdoit.RepoAdapter
import dev.terrylabs.gitdoit.api.ApiClient
import dev.terrylabs.gitdoit.api.GitHubApi
import dev.terrylabs.gitdoit.databinding.ActivityMainBinding
import dev.terrylabs.gitdoit.model.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var gitHubService: GitHubApi
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        gitHubService = ApiClient.instance.create(GitHubApi::class.java)
        loadRepositoryList(BaseApplication.userData.login)
    }

    private fun loadRepositoryList(loginName: String?) {
        loginName?.let {
            this.gitHubService.listRepos(loginName, "all", "full_name", "desc").enqueue(object : Callback<List<Repo>> {
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

        binding.rvRepoList.adapter = RepoAdapter(this, list) { repo ->
            Toast.makeText(this, "Repository Name : ${repo.name}", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, StatisticActivity::class.java)
            intent.putExtra("name", repo.name)
            intent.putExtra("full-name", repo.fullName)

            startActivity(intent)
        }
        binding.rvRepoList.setHasFixedSize(true)
        binding.rvRepoList.layoutManager = LinearLayoutManager(this)


        // TEMP
        /*

            */
    }

    private fun printNestedArray(commitList: List<List<Int>>?) {
        for (list in commitList!!) {
            Log.e("MAIN ::", "${list[0]}/${list[1]}/${list[2]}")
        }
    }
}
