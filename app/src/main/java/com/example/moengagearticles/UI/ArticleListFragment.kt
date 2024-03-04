package com.example.moengagearticles.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moengagearticles.Data.IndArticle
import com.example.moengagearticles.Utils.ClickListerner
import com.example.moengagearticles.ViewModel.ArticleListViewModel
import com.example.moengagearticles.databinding.BottomSheetDialogBinding
import com.example.moengagearticles.databinding.FragmentArticleListBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class ArticleListFragment : Fragment(), ClickListerner {
    lateinit var binding : FragmentArticleListBinding
    lateinit var articleListAdapter : ArticleListAdapter
    lateinit var viewModel : ArticleListViewModel
    private var isloading :Boolean =false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.listRecycler.adapter = articleListAdapter

        viewModel.articleList.observe(viewLifecycleOwner, Observer { Response ->
            binding.listRecycler.also {
                articleListAdapter.setUserList(Response.articles)
                isloading=false
            }
        })

        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchArticles()
    }

    private fun initView() {
        binding.listRecycler.layoutManager = LinearLayoutManager(requireContext())
        articleListAdapter = ArticleListAdapter(listOf(), this)
        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
    }

    override fun onItemClick(response: IndArticle) {
        val dialog = BottomSheetDialog(requireActivity())
        val binding = BottomSheetDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.show()
        with(binding){
            Glide.with(requireActivity()).load(response.urlToImage).into(articleImage)
            articleTitle.text = response.title
            articleAuthor.text = "By " + response.author
            articleContent.text =response.content
        }
        binding.btnClose.setOnClickListener {
            dialog.dismiss()
        }
        binding.readMoreButton.setOnClickListener {
            val url = response.url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }


}