package com.wasilyk.app.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wasilyk.app.room_api.history.HistoryEntity
import com.wasilyk.app.history.databinding.HistoryItemViewBinding

class HistoryAdapter(
    private val histories: List<HistoryEntity>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = HistoryItemViewBinding.bind(itemView)
        val numb = viewBinding.numb
        val date = viewBinding.date
        val word = viewBinding.word
        val definition = viewBinding.definition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(HistoryItemViewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).root)

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) {
        holder.apply {
            numb.text = pos.toString()
            date.text = histories[pos].date
            word.text = histories[pos].word
            definition.text = histories[pos].definition
            itemView.setOnClickListener { onItemClickListener.itemClick(pos) }
        }
    }

    override fun getItemCount() = histories.size

    interface OnItemClickListener {
        fun itemClick(pos: Int)
    }
}