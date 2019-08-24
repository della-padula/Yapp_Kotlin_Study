package dev.terrylabs.rxandoidpractice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.terrylabs.rxandoidpractice.api.GithubRepo
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.mViewHolder>() {

    private val githubRepos: ArrayList<GithubRepo> = ArrayList()

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.mID
        var name: TextView = itemView.mName
        var url: TextView = itemView.mUrl
        var date: TextView = itemView.mDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder = mViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_row, parent, false))

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.id.text = githubRepos[position].id
        holder.name.text = githubRepos[position].name
        holder.url.text = githubRepos[position].url
        holder.date.text = githubRepos[position].date
    }

    override fun getItemCount(): Int = githubRepos.size

    fun update(githubRepos: ArrayList<GithubRepo>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        notifyDataSetChanged()
    }

}
