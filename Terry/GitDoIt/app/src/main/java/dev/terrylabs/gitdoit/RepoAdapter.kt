package dev.terrylabs.gitdoit

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.terrylabs.gitdoit.model.Repo
import kotlinx.android.synthetic.main.list_repo_item.view.*

class RepoAdapter(private var activity: Activity, private var list: List<Repo>, private val itemClick: (Repo) -> Unit) :
    RecyclerView.Adapter<RepoAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            tvTitle.text = list[position].name
            tvSubTitle.text = list[position].fullName

            itemView.setOnClickListener { itemClick(list[position]) }
        }
    }

    inner class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_repo_item, parent, false)
    ) {
        val tvTitle: TextView = itemView.tv_repo_title
        val tvSubTitle: TextView = itemView.tv_repo_subtitle
    }

}