package com.example.vintage.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vintage.FinalActivity
import com.example.vintage.R
import com.makeramen.roundedimageview.RoundedImageView

class CategoryImagesAdapter(val requireContext: Context, val listBestOfMonth: ArrayList<BomModel>) : RecyclerView.Adapter<CategoryImagesAdapter.bomViewHolder>(){

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<RoundedImageView>(R.id.categoriesImage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(LayoutInflater.from(requireContext).inflate(R.layout.category_items, parent, false))
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        holder.imageView
        Glide.with(requireContext).load(listBestOfMonth[position].link).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, FinalActivity::class.java)
            intent.putExtra("link",listBestOfMonth[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listBestOfMonth.size
    }
}