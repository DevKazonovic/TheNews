package com.devkazonovic.projects.thenews.presentation.foryou.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.devkazonovic.projects.thenews.R
import com.devkazonovic.projects.thenews.common.util.DateTimeUtil
import com.devkazonovic.projects.thenews.data.remote.googlenewsrss.ArticleScrapper
import com.devkazonovic.projects.thenews.databinding.ItemTopstoryBinding
import com.devkazonovic.projects.thenews.domain.model.Story
import com.devkazonovic.projects.thenews.presentation.foryou.ForYouViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val KEY_STORY = "Story Object"
private const val KEY_STORY_POSITION = "Story Position In List"

@AndroidEntryPoint
class TopStoryFragment : Fragment() {

    private var _binding: ItemTopstoryBinding? = null
    private val binding get() = _binding!!

    private var story: Story? = null
    private var pos: Int? = null

    private lateinit var textViewSource: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewPublishedDate: TextView
    private lateinit var imageViewArticleImg: ImageView

    private val viewModel by
    hiltNavGraphViewModels<ForYouViewModel>(R.id.forYouPage)

    @Inject
    lateinit var articleScrapper: ArticleScrapper

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
            textViewSource.text = it.source.name
            textViewTitle.text = it.title
            textViewPublishedDate.text = DateTimeUtil.showTimePassed(context, it.howMuchAgo)
            articleScrapper.getArticleImageUrl(it.url)
                .subscribe(
                    { imgUrl ->
                        Glide.with(binding.root.context)
                            .load(imgUrl)
                            .placeholder(R.drawable.ic_grey)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                            .into(imageViewArticleImg)
                    },
                    {
                        Glide.with(binding.root.context)
                            .load("")
                            .placeholder(R.drawable.ic_grey)
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                            .into(imageViewArticleImg)
                    }
                )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.let {
            textViewSource = it.textViewSource
            textViewTitle = it.textViewTitle
            textViewPublishedDate = it.textViewPublishedDate
            imageViewArticleImg = it.imageViewArticleImg
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(story: Story, pos: Int) = TopStoryFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_STORY, story)
                putInt(KEY_STORY_POSITION, pos)
            }
        }
    }

}