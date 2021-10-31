package com.example.stackoverflowclone.presentation.homeScreen

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackoverflowclone.databinding.ItemQuestionBinding
import com.example.stackoverflowclone.domain.models.Question
import com.example.stackoverflowclone.utils.getDate

/**
 * Created by Mehul Bisht on 31-10-2021
 */

class HomeFeedAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<HomeFeedAdapter.HomeFeedViewHolder>() {

    inner class HomeFeedViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageView: ImageView = binding.image
        private val title: TextView = binding.title
        private val date: TextView = binding.date
        private val name: TextView = binding.name
        private val root: ConstraintLayout = binding.root

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(item: Question) {
            title.text = item.title
            val formattedDate = getDate(item.creation_date.toLong())
            date.text = "Posted at: $formattedDate"
            name.text = item.owner.display_name

            Glide.with(binding.root)
                .load(item.owner.profile_image)
                .into(imageView)

            root.setOnClickListener {
                onClick(item.link)
            }
        }
    }

    private val callback = object : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question) =
            oldItem.question_id == newItem.question_id

        override fun areContentsTheSame(oldItem: Question, newItem: Question) =
            oldItem == newItem
    }

    private val asyncListDiffer = AsyncListDiffer(this, callback)

    fun submitList(items: List<Question>) {
        asyncListDiffer.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedViewHolder {

        val binding = ItemQuestionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return HomeFeedViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: HomeFeedViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }
}