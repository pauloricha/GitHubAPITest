package br.com.example.githubapitest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.example.githubapitest.R
import br.com.example.githubapitest.data.network.ConnectionState
import br.com.example.githubapitest.model.Repository
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_adapter_repository.view.*

class RepositoryAdapter(private val callback: OnListener) :
    PagedListAdapter<Repository, RepositoryAdapter.MyViewHolder>(diffCallback) {

    private var connectionState: ConnectionState? = null

    var onItemClick: ((Int) -> Unit)? = null

    interface OnListener {
        fun listUpdated()
        fun setEmptyListUI(connectionState: ConnectionState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_adapter_repository, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bindPost(it)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(position)
            }
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val repositoryName = itemView.tvRepositoryName
        val stars = itemView.tvStars
        val forks = itemView.tvForks
        val autorName = itemView.tvAuthorName

        fun bindPost(repository : Repository){
            loadImage(repository.owner.avatar_url.toString(), itemView.imgAuthor)
            repositoryName.text = repository.name
            stars.text = repository.stargazersCount.toString()
            forks.text = repository.forksCount.toString()
            autorName.text = repository.owner.login
        }

        private fun loadImage(url: String, imageView: ImageView) {
            Glide.with(itemView.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
    }

    override fun getItemCount(): Int {
        if(super.getItemCount() > 0) {
            this.callback.listUpdated()
        } else if(super.getItemCount() == 0 && connectionState == ConnectionState.SUCCESS){
            this.callback.setEmptyListUI(connectionState)
        }
        return super.getItemCount()
    }

    fun updateConnectionState(connectionState: ConnectionState) {
        this.connectionState = connectionState
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository):
                    Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: Repository, newItem: Repository):
                    Boolean = oldItem == newItem
        }
    }
}