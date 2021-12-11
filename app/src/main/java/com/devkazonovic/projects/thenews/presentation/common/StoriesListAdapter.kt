package com.devkazonovic.projects.thenews.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.devkazonovic.projects.thenews.databinding.ItemStoryBinding
import com.devkazonovic.projects.thenews.model.Story

class StoriesListAdapter(onClick: (story: Story) -> Unit) :
    ListAdapter<Story, StoriesListAdapter.StoryViewHolder>(Story.DIFF_UTIl) {

    class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.let {
                it.textViewArticleSource.text = story.source
                it.textViewArticleTitle.text = story.title
                it.textViewArticlePublishDate.text = story.publishDate
                Glide.with(binding.root.context)
                    .load(story.imgUrl)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                    .into(it.imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding =
            ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}