package com.example.moengagearticles.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moengagearticles.Data.IndArticle
import com.example.moengagearticles.Utils.ClickListerner
import com.example.moengagearticles.ViewModel.ArticleListViewModel
import com.example.moengagearticles.databinding.BottomSheetDialogBinding
import com.example.moengagearticles.databinding.FragmentArticleListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ArticleListFragment : Fragment(), ClickListerner {
    private lateinit var binding: FragmentArticleListBinding
    private lateinit var articleListAdapter: ArticleListAdapter
    private lateinit var viewModel: ArticleListViewModel
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize views and set up RecyclerView
        initViews()
        binding.listRecycler.adapter = articleListAdapter

        // Observe LiveData for article list changes
        viewModel.articleList.observe(viewLifecycleOwner, Observer { response ->
            binding.listRecycler.also {
                // Update the adapter with the new data
                articleListAdapter.setUserList(response.articles)
                isLoading = false
            }
        })

        // Fetch articles when the view is created
        fetchData()
    }

    // Fetches articles from the ViewModel
    private fun fetchData() {
        viewModel.fetchArticles()
    }

    // Initializes views, RecyclerView, ViewModel, and adapter
    private fun initViews() {
        binding.listRecycler.layoutManager = LinearLayoutManager(requireContext())
        articleListAdapter = ArticleListAdapter(listOf(), this)
        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
    }

    // Handles item click from the adapter
    override fun onItemClick(response: IndArticle) {
        // Create and show a bottom sheet dialog with article details
        val dialog = BottomSheetDialog(requireActivity())
        val binding = BottomSheetDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.show()
        with(binding) {
            // Load article image using Glide
            Glide.with(requireActivity()).load(response.urlToImage).into(articleImage)
            // Set article details in the bottom sheet
            articleTitle.text = response.title
            articleAuthor.text = "By ${response.author}"
            articleContent.text = response.content
        }
        // Set click listeners for the close button and "Read More" button
        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        binding.readMoreButton.setOnClickListener {
            // Open the article link in a browser
            val url = response.url
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}