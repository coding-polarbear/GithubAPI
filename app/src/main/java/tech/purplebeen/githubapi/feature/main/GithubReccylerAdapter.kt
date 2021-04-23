package tech.purplebeen.githubapi.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.purplebeen.githubapi.databinding.ItemDefaultRepositoryBinding
import tech.purplebeen.githubapi.databinding.ItemDefaultUserBinding
import tech.purplebeen.githubapi.model.Data
import tech.purplebeen.githubapi.model.ViewType

class GithubReccylerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: ArrayList<Data> = ArrayList()

    override fun getItemViewType(position: Int): Int {
        return dataList[position].viewType.number
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ViewType.USER_TYPE.number) {
            val binding = ItemDefaultUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(binding)
        } else {
            val binding = ItemDefaultRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RepositoryViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        if(item.viewType ==  ViewType.USER_TYPE) {
            (holder as UserViewHolder).binding.item = item
        } else {
            (holder as RepositoryViewHolder).binding.item = item
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun submitList(targetList: ArrayList<Data>) {
        this.dataList = targetList
        notifyDataSetChanged()
    }
}