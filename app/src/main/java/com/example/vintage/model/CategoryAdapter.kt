package com.example.vintage.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vintage.CategoryActivity
import com.example.vintage.FinalActivity
import com.example.vintage.R

class CategoryAdapter(val requireContext: Context, val listCategories: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoryAdapter.bomViewHolder>(){

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.categoryImage)
        val textView = itemView.findViewById<TextView>(R.id.categoryName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(LayoutInflater.from(requireContext).inflate(R.layout.categories, parent, false))
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        holder.textView.text = listCategories[position].name
        Glide.with(requireContext).load(listCategories[position].link).into(holder.imageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext, CategoryActivity::class.java)
            intent.putExtra("uid",listCategories[position].id)
            intent.putExtra("name",listCategories[position].name)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listCategories.size
    }
}