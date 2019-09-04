package org.androidtown.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import androidx.databinding.DataBindingUtil
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: org.androidtown.todolist.databinding.ActivityMainBinding
    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        setSupportActionBar(toolbar)

        val realmResult = realm.where<Todo>().findAll().sort("date", Sort.DESCENDING)
        val adapter = TodoAdapter(realmResult)

        ListView.adapter = adapter
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }
        ListView.setOnItemClickListener { parent, view, position, ID ->
            startActivity<EditAct>("ID" to ID)
        }

    }

    fun onFABClick(view: View) {
        Add.setOnClickListener {
            startActivity<EditAct>()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
