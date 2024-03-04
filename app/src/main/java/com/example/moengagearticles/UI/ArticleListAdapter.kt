package com.example.moengagearticles.UI

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moengagearticles.Data.IndArticle
import com.example.moengagearticles.R
import com.example.moengagearticles.Utils.ClickListerner

class ArticleListAdapter(
    private var userList: List<IndArticle>,
    val listerner: ClickListerner
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = userList[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(users: List<IndArticle>) {
        userList += users
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(articles: IndArticle) {
            val image : ImageView = itemView.findViewById(R.id.image)
            val title: TextView = itemView.findViewById(R.id.title)
            val author_name: TextView = itemView.findViewById(R.id.author_name)
            val publish_date: TextView = itemView.findViewById(R.id.publish_date)

            Glide.with(itemView)
                .load(articles.urlToImage)
                .apply(RequestOptions.circleCropTransform())
                .into(image)
            title.text = articles.title
            author_name.text = articles.author
            publish_date.text = articles.publishedAt


            itemView.setOnClickListener {
                listerner.onItemClick(articles)
            }
        }
    }
}
