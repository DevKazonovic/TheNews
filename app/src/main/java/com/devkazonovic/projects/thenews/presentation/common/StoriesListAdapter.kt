package com.devkazonovic.projects.thenews.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.util.DateTimeUtil.showTimePassed
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.ItemStoryBinding
import com.devkazonovic.projects.thenews.domain.model.Story
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo

class StoriesListAdapter(
    private val context: Context,
    private val articleScrapper: ArticleScrapper,
    private val onClick: (story: Story) -> Unit,
    private val onMenuClick: (story: Story) -> Unit
) : ListAdapter<Story, StoriesListAdapter.StoryViewHolder>(Story.DIFF_UTIl) {


    class StoryViewHolder(
        private val context: Context,
        private val binding: ItemStoryBinding,
        private val articleScrapper: ArticleScrapper,
        private val onClick: (story: Story) -> Unit,
        private val onMenuClick: (story: Story) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dispose = CompositeDisposable()
        fun bind(story: Story) {
            binding.let {
                it.root.setOnClickListener { onClick(story) }
                it.icStoryMenu.setOnClickListener { onMenuClick(story) }
                it.textViewArticleSource.text = story.source.name
                it.textViewArticleTitle.text = story.title
                it.textViewArticlePublishDate.text =
                    showTimePassed(context, story.publishDateFormat)
                articleScrapper.getArticleImageUrl(story.url)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { imgUrl ->
                        Glide.with(binding.root.context)
                            .load(imgUrl)
                            .placeholder(R.drawable.ic_placeholder)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                            .into(it.imageViewArticleImg)
                    }.addTo(dispose)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(context, binding, articleScrapper, onClick, onMenuClick)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}