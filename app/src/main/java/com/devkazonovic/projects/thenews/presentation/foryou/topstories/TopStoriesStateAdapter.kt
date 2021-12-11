package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devkazonovic.projects.thenews.R

class TopStoriesStateAdapter(private var data: List<Pair<Int, Boolean>>) :
    RecyclerView.Adapter<TopStoriesStateAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textView = itemView.findViewById<View>(R.id.circle)

        fun bind(pair: Pair<Int, Boolean>) {
            val isSelected = pair.second
            if (isSelected) {
                textView.setBackgroundResource(R.drawable.circle_solid)
            } else {
                textView.setBackgroundResource(R.drawable.circle_empty)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(currentSelectedPos: Int) {
        data = data.map { item ->
            if (item.first == currentSelectedPos) Pair(
                item.first,
                true
            ) else Pair(item.first, false)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_circle, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    companion object {

        @JvmStatic
        fun initStateList(size: Int): List<Pair<Int, Boolean>> {
            val list = mutableListOf(
                Pair(0, true)
            )
            for (item in 1 until size) {
                list.add(Pair(item, false))
            }

            return list
        }
    }
}