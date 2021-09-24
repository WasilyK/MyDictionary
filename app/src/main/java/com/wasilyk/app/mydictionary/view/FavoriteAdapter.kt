package com.wasilyk.app.mydictionary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wasilyk.app.mydictionary.databinding.FavoriteItemViewBinding
import com.wasilyk.app.mydictionary.model.datasource.room.FavoriteEntity

class FavoriteAdapter(
    private val data: List<FavoriteEntity>,
    private val onItemDeleteClickListener: OnItemDeleteClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = FavoriteItemViewBinding.bind(itemView)
        val title = viewBinding.title
        val definition = viewBinding.definition
        val image = viewBinding.image
        val deleteBtn = viewBinding.delete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            FavoriteItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
                .root
        )

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.title.text = data[pos].title
        holder.definition.text = data[pos].definition
        holder.image.load(data[pos].imageUrl)
        holder.deleteBtn.setOnClickListener { onItemDeleteClickListener.onItemDeleteClick(pos) }
    }

    override fun getItemCount() = data.size

    interface OnItemDeleteClickListener {
        fun onItemDeleteClick(pos: Int)
    }
}