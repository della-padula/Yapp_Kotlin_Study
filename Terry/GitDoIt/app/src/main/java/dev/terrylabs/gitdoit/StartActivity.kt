package dev.terrylabs.gitdoit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import dev.terrylabs.gitdoit.api.ApiClient
import dev.terrylabs.gitdoit.api.GitHubApi
import dev.terrylabs.gitdoit.databinding.ActivityStartBinding
import dev.terrylabs.gitdoit.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private lateinit var githubService: GitHubApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        binding.activity = this
        Util.setIconTinkDark(this)

    }

    fun onStartClick(view: View) {
        Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()

        this.githubService = ApiClient.instance.create(GitHubApi::class.java)
        this.githubService.userCheck(binding.etNickname.text.toString()).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {

            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    Log.e("START ::", "UserId : " + it.id)
                    Log.e("START ::", "UserName : " + it.login)
                    Log.e("START ::", "UserName : " + it.name)
                    Log.e("START ::", "UserName : " + it.location)

                    BaseApplication.userData = it
                    goToMain()
                }
            }
        })
    }

    private fun goToMain() {
        startActivity(Intent(this@StartActivity, MainActivity::class.java))
        finish()
    }
}
