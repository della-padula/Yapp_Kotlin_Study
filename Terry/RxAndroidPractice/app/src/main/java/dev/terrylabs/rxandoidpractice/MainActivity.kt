package dev.terrylabs.rxandoidpractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dev.terrylabs.rxandoidpractice.api.GithubClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val OWNER = "della-padula"

class MainActivity : AppCompatActivity() {

    private var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = recyclerViewAdapter

        val disposable = GithubClient().getApi().getRepos(intent.extras?.get("owner").toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items -> recyclerViewAdapter.update(items) }

    }
}
