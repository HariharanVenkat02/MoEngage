package com.example.moengagearticles.UI

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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ArticleListAdapter(
    private var userList: List<IndArticle>,
    private val listener: ClickListerner
) : RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {

    // Create ViewHolder and inflate layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_cardview, parent, false)
        return ViewHolder(view)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    // Return item count
    override fun getItemCount(): Int = userList.size

    // Update user list and notify data set changed
    fun setUserList(users: List<IndArticle>) {
        userList += users
        notifyDataSetChanged()
    }

    // ViewHolder class to hold and bind views
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Bind data to views
        fun bind(articles: IndArticle) {
            val image: ImageView = itemView.findViewById(R.id.image)
            val title: TextView = itemView.findViewById(R.id.title)
            val authorName: TextView = itemView.findViewById(R.id.author_name)
            val publishDate: TextView = itemView.findViewById(R.id.publish_date)

            // Load image using Glide
            Glide.with(itemView)
                .load(articles.urlToImage)
                .apply(RequestOptions.circleCropTransform())
                .into(image)

            // Set text data
            title.text = articles.title
            authorName.text = articles.author
            publishDate.text = convertDateFormat(articles.publishedAt.toString())
            

            // Set click listener
            itemView.setOnClickListener {
                listener.onItemClick(articles)
            }
        }
    }

    // Function to convert date format
    fun convertDateFormat(inputDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            // Parse the input date
            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date ?: Date())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return inputDate
    }
}