package com.devkazonovic.projects.thenews.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private val fragment: Fragment,
    private val articleScrapper: ArticleScrapper,
    private val onClick: (story: Story) -> Unit,
    private val onMenuClick: (story: Story) -> Unit
) : ListAdapter<Story, StoriesListAdapter.StoryViewHolder>(Story.DIFF_UTIl) {

    val dispose = CompositeDisposable()

    inner class StoryViewHolder(
        private val binding: ItemStoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.let {
                it.root.setOnClickListener {
                    onClick(story)
                }
                it.icStoryMenu.setOnClickListener { onMenuClick(story) }
                it.textViewArticleSource.text = story.source.name
                it.textViewArticleTitle.text = story.title
                it.textViewArticlePublishDate.text =
                    showTimePassed(binding.root.context, story.publishDateFormat)
                articleScrapper.getArticleImageUrl(story.url)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { imgUrl ->
                        Glide.with(fragment)
                            .load(imgUrl)
                            .placeholder(R.drawable.ic_placeholder)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                            .into(it.imageViewArticleImg)

                    }.addTo(dispose)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoriesListAdapter.StoryViewHolder {
        val binding = ItemStoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

}