package com.example.codegama.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codegama.Product
import com.example.codegama.ProductCategory
import com.example.codegama.ProductListResponse
import com.example.codegama.databinding.CatTypeBinding
import com.example.codegama.databinding.ItemCatBinding


class ItemAdapter(
    private var items: List<Product>,
    private val context: Context?,
    private val clickListenerCallback:(name : Product) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items,position)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: List<Product>?, position: Int) {
            binding.textView1.text = item?.get(position)?.brand
            binding.textView2.text = item?.get(position)?.title
            binding.textView3.text = item?.get(position)?.price.toString()+"$"
            binding.ratingStatus.text = "Rating "+item?.get(position)?.rating.toString()+"   *"
            if (context != null) {
                Glide.with(context)
                    .load(item?.get(position)?.thumbnail)
                    .into(binding.imageView1)
            }
//            binding.tvJob.text = item?.get(position)?.job.toString()
//            binding.tvName1.text = item?.get(position)?.name1
//            binding.tvJob1.text = item?.get(position)?.job1.toString()
            binding.deleteIcon.setOnClickListener {
                if (item != null) {
                    clickListenerCallback(item?.get(position)!!)
                }
            }
        }
    }
    fun updateList(newList: List<Product>) {
        this.items = newList
        notifyDataSetChanged()
    }
}
