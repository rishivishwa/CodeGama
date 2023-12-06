package com.example.codegama.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codegama.ProductCategory
import com.example.codegama.databinding.CatTypeBinding



class RecruiterOfferAdapter (
    private val items: List<ProductCategory>?,
    private val context: Context?,
    private val clickListenerCallback:(name : String) -> Unit
) :
    RecyclerView.Adapter<RecruiterOfferAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CatTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items,position)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ViewHolder(private val binding: CatTypeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: List<ProductCategory>?, position: Int) {
            binding.textViewItem.text = item?.get(position)?.name
//            binding.tvJob.text = item?.get(position)?.job.toString()
//            binding.tvName1.text = item?.get(position)?.name1
//            binding.tvJob1.text = item?.get(position)?.job1.toString()
            binding.root.setOnClickListener {
                if (item != null) {
                    clickListenerCallback(item?.get(position)?.name!!)
                }
            }
        }
    }
}
