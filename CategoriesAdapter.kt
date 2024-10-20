package com.alexios.android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexios.android.R
import com.alexios.android.activities.ProductsActivity
import com.alexios.android.models.Wine
import com.squareup.picasso.Picasso

class CategoriesAdapter(var wines: MutableList<Wine>, var context: Context):
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val caticon: ImageView = itemView.findViewById(R.id.caticon)
        val catname: TextView = itemView.findViewById(R.id.catname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.category_list_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = wines[position]
        holder.catname.text=listItem.name
        Picasso.get().load(listItem.image).into(holder.caticon)
        holder.itemView.setOnClickListener {
            val intent  = Intent(context , ProductsActivity::class.java)
            intent.putExtra("category_id",listItem.id)
            intent.putExtra("winetype",listItem.name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return wines.size
    }
}