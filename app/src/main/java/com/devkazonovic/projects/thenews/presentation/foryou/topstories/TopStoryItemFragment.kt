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
import com.devkazonovic.projects.thenews.databinding.ItemTopstoryBinding
import com.devkazonovic.projects.thenews.model.Story


private const val KEY_STORY = "Story Object"
private const val KEY_STORY_POSITION = "Story Position In List"

class TopStoryFragment : Fragment() {

    private var _binding: ItemTopstoryBinding? = null
    private val binding get() = _binding!!

    private var story: Story? = null
    private var pos: Int? = null

    private lateinit var textViewSource: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewPublishedDate: TextView
    private lateinit var imageViewArticleImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            story = it.getSerializable(KEY_STORY) as Story
            pos = it.getInt(KEY_STORY_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ItemTopstoryBinding.inflate(inflater, container, false)
        val root = binding.root
        binding.let {
            textViewSource = it.textViewSource
            textViewTitle = it.textViewTitle
            textViewPublishedDate = it.textViewPublishedDate
            imageViewArticleImg = it.imageViewArticleImg
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        story?.let {
            textViewSource.text = it.source
            textViewTitle.text = it.title
            textViewPublishedDate.text = it.publishDate
            Glide.with(this)
                .load(it.imgUrl)
                .placeholder(R.drawable.ic_placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(18)))
                .into(imageViewArticleImg)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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