package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.util.DateTimeUtil
import com.devkazonovic.projects.thenews.common.util.IntentUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.ItemTopstoryBinding
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.common.storymenu.StoryMenuFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

private const val KEY_STORY = "Story Object"
private const val KEY_STORY_POSITION = "Story Position In List"

@AndroidEntryPoint
class TopStoryItemFragment : Fragment() {

    private var story: Story? = null
    private var pos: Int? = null

    private var _binding: ItemTopstoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var textViewSource: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewPublishedDate: TextView
    private lateinit var imageViewArticleImg: ImageView
    private lateinit var icStoryMenu: ImageView

    @Inject
    lateinit var articleScrapper: ArticleScrapper

    private val dispose = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            story = it.getSerializable(KEY_STORY) as Story
            pos = it.getInt(KEY_STORY_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ItemTopstoryBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        story?.let {
            val context = requireContext()
            binding.root.setOnClickListener { view ->
                onStoryClick(it)
            }
            textViewSource.text = it.source.name
            textViewTitle.text = it.title
            textViewPublishedDate.text = DateTimeUtil.showTimePassed(context, it.publishDateFormat)
            icStoryMenu.setOnClickListener { view ->
                onStoryMenuClick(it)
            }
            articleScrapper.getArticleImageUrl(it.url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { imgUrl ->
                    Glide.with(this)
                        .load(imgUrl)
                        .placeholder(R.drawable.ic_placeholder)
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                        .into(imageViewArticleImg)
                }.addTo(dispose)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dispose.clear()
        _binding = null
    }

    private fun initViews() {
        binding.let {
            textViewSource = it.textViewSource
            textViewTitle = it.textViewTitle
            textViewPublishedDate = it.textViewPublishedDate
            imageViewArticleImg = it.imageViewArticleImg
            icStoryMenu = it.icStoryMenu
        }
    }

    private fun onStoryMenuClick(story: Story) {
        StoryMenuFragment.newInstance(story).show(childFragmentManager, StoryMenuFragment.TAG)
    }

    private fun onStoryClick(it: Story) {
        IntentUtil.openUrl(requireContext(), it.url)
    }

    companion object {
        @JvmStatic
        fun newInstance(story: Story, pos: Int) = TopStoryItemFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_STORY, story)
                putInt(KEY_STORY_POSITION, pos)
            }
        }
    }

}