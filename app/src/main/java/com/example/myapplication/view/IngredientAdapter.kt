package com.example.myapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.IngredientData

class IngredientAdapter(val c: Context, val ingredientList:ArrayList<IngredientData>):RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    inner class IngredientViewHolder(val v: View): RecyclerView.ViewHolder(v){
        val name = v.findViewById<TextView>(R.id.ingredientTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.ingredent_list_item, parent, false)
        return IngredientViewHolder(v)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val newList = ingredientList[position]
        holder.name.text = newList.ingredientName
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }
}