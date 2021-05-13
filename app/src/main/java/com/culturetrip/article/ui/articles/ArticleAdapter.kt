package com.culturetrip.article.ui.articles

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.culturetrip.article.R
import com.culturetrip.article.data.local.entity.Article
import com.culturetrip.article.databinding.ArticleItemBinding
import com.culturetrip.article.util.formatDisplayDate
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "ArticleAdapter"

class ArticleAdapter(): RecyclerView.Adapter<ArticleViewHolder>() {

    private val items = ArrayList<Article>()

    fun setItems(items: List<Article>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding: ArticleItemBinding = ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(items[position])
}

class ArticleViewHolder(private val itemBinding: ArticleItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var article: Article

    @SuppressLint("SetTextI18n")
    fun bind(item: Article) {
        this.article = item
        itemBinding.articleTitleTv.text = item.title
        itemBinding.articleCatagoryTv.text = item.category
        itemBinding.authorNameTv.text = item.author.authorName
        itemBinding.dateCreationTv.text =  Date().formatDisplayDate(item.metaData.creationTime)
        itemBinding.likeCountTv.text = item.likesCount.toString()

        if(item.isSaved) {
            itemBinding.saveImageV.setImageResource(R.drawable.saved)
        }

        if(item.isLiked) {
            itemBinding.likeImageV.setImageResource(R.drawable.liked)
        }

        Glide.with(itemView)
            .load(item.imageUrl)
            .into(itemBinding.articleImageV)

        Glide.with(itemView)
            .load(item.author.authorAvatar.imageAuthorUrl)
            .transform(CircleCrop())
            .into(itemBinding.authorImageV)
    }

}

