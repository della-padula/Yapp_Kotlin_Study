package dev.terrylabs.gitdoit.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import dev.terrylabs.gitdoit.BaseApplication
import dev.terrylabs.gitdoit.R
import dev.terrylabs.gitdoit.api.ApiClient
import dev.terrylabs.gitdoit.api.GitHubApi
import dev.terrylabs.gitdoit.databinding.ActivityStatisticBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatisticActivity : AppCompatActivity() {

    private lateinit var repoName: String
    private lateinit var repoFullName: String
    private lateinit var gitHubService: GitHubApi
    private var dayCommitCount = arrayOf(0, 0, 0, 0, 0, 0, 0)
    private var hourCommitCount = arrayOf(0, 0, 0, 0)

    // 012 345 678  3으로 나눈 몫이 index

    private lateinit var binding: ActivityStatisticBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistic)
        binding.activity = this
        gitHubService = ApiClient.instance.create(GitHubApi::class.java)

        repoName = intent.getStringExtra("name")!!
        repoFullName = intent.getStringExtra("full-name")!!

        binding.tvTitle.text = repoName
        binding.tvSubtitle.text = repoFullName

        loadCommitCountEachDay()
    }

    private fun loadCommitCountEachDay() {
        this.gitHubService.getRepoCommitPerHourEachDay(BaseApplication.userData.login!!, repoName)
            .enqueue(object : Callback<List<List<Int>>> {
                override fun onFailure(call: Call<List<List<Int>>>, t: Throwable) {
                    Log.e("MAIN ::", "Load RepositoryList - Failure")
                }

                override fun onResponse(call: Call<List<List<Int>>>, response: Response<List<List<Int>>>) {
                    response.body()?.let {
                        addCommitCountEachDayToArray(response.body()!!)
                        addCommitCountEachHourToArray(response.body()!!)
                    }
                }
            })
    }

    private fun addCommitCountEachDayToArray(param: List<List<Int>>) {
        var index = 0
        for (list in param) {
            dayCommitCount[list[0]] += list[2]
        }

        binding.tvDayValSun.text = "${dayCommitCount[0]}"
        binding.tvDayValMon.text = "${dayCommitCount[1]}"
        binding.tvDayValTue.text = "${dayCommitCount[2]}"
        binding.tvDayValWed.text = "${dayCommitCount[3]}"
        binding.tvDayValThu.text = "${dayCommitCount[4]}"
        binding.tvDayValFri.text = "${dayCommitCount[5]}"
        binding.tvDayValSat.text = "${dayCommitCount[6]}"
    }

    private fun addCommitCountEachHourToArray(param: List<List<Int>>) {
        for(list in param) {
            hourCommitCount[list[1] / 6] += list[2]
        }

        binding.tvHourVal1.text = "${hourCommitCount[0]}"
        binding.tvHourVal2.text = "${hourCommitCount[1]}"
        binding.tvHourVal3.text = "${hourCommitCount[2]}"
        binding.tvHourVal4.text = "${hourCommitCount[3]}"

        Log.e("MAIN ::", "${hourCommitCount[0]}/${hourCommitCount[1]}/${hourCommitCount[2]}/${hourCommitCount[3]}")
    }

    fun onBackClick(view: View) {
        onBackPressed()
    }
}
