package com.devkazonovic.projects.thenews.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.util.DateTimeUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.ItemStoryBinding
import com.devkazonovic.projects.thenews.domain.model.Story

class StoriesListAdapter(
    private val articleScrapper: ArticleScrapper,
    private val onClick: (story: Story) -> Unit,
    private val onMenuClick : (story: Story) -> Unit
) : ListAdapter<Story, StoriesListAdapter.StoryViewHolder>(Story.DIFF_UTIl) {

    class StoryViewHolder(
        private val binding: ItemStoryBinding,
        private val articleScrapper: ArticleScrapper,
        private val onClick: (story: Story) -> Unit,
        private val onMenuClick : (story: Story) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.let {
                val context = it.root.context
                it.root.setOnClickListener {
                    onClick(story)
                }
                it.icStoryMenu.setOnClickListener {
                    onMenuClick(story)
                }
                it.imageViewArticleImg.setImageDrawable(null)
                it.textViewArticleSource.text = story.source.name
                it.textViewArticleTitle.text = story.title
                it.textViewArticlePublishDate.text =
                    DateTimeUtil.showTimePassed(context, story.publishDateFormat)
                articleScrapper.getArticleImageUrl(story.url).subscribe(
                    { imgUrl ->
                        Glide.with(context)
                            .load(imgUrl)
                            .placeholder(R.drawable.ic_grey)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                            .into(it.imageViewArticleImg)
                    }, { error ->
                        Glide.with(context).clear(it.imageViewArticleImg)
                        it.imageViewArticleImg.setImageResource(R.drawable.ic_grey)
                    }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding, articleScrapper, onClick ,onMenuClick)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun clear() {
        submitList(listOf())
    }

}