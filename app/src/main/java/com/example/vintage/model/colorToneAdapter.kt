package com.example.vintage.model

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.vintage.FinalActivity
import com.example.vintage.R

class colorToneAdapter(private val requireContext: Context, private val listColorTone: ArrayList<ColorModel>) : RecyclerView.Adapter<colorToneAdapter.bomViewHolder>(){

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardview: CardView = itemView.findViewById<CardView>(R.id.cardItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(LayoutInflater.from(requireContext).inflate(R.layout.color_tone, parent, false))
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {
        val color = listColorTone[position].colour
        holder.itemView.setOnClickListener {
            val intent = Intent(requireContext,FinalActivity::class.java)
            intent.putExtra("link",listColorTone[position].link)
            requireContext.startActivity(intent)
        }
        holder.cardview.setCardBackgroundColor(Color.parseColor(color!!))

    }

    override fun getItemCount(): Int {
        return listColorTone.size
    }
}