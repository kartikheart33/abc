package com.alexios.android.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexios.android.R
import com.alexios.android.activities.WineDetailScreen
import com.alexios.android.models.Products

class ProductsAdapter(var products: MutableList<Products>, var context: Context):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val winename: TextView = itemView.findViewById(R.id.winename)
        val price: TextView = itemView.findViewById(R.id.price)
        val grapetype: TextView = itemView.findViewById(R.id.grapetype)
         val winecountry: TextView = itemView.findViewById(R.id.winecountry)
         val winedescription: TextView = itemView.findViewById(R.id.winedescription)
        val winery: TextView = itemView.findViewById(R.id.winerytype)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.product_list_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = products[position]
        holder.winename.text=listItem.name
        holder.price.text=listItem.price.toString()+" €"
        holder.grapetype.text=listItem.grapetype
        holder.winecountry.text=listItem.country
        holder.winedescription.text="Description: "+listItem.description
        holder.winery.text = listItem.winery.toString()


        holder.itemView.setOnClickListener {
            val intent  = Intent(context ,WineDetailScreen::class.java)
            intent.putExtra("winename",listItem.name)
            intent.putExtra("price",listItem.price.toString())
            intent.putExtra("grapetype",listItem.grapetype)
            intent.putExtra("winecountry",listItem.country)
            intent.putExtra("winedescription",listItem.description)
            intent.putExtra("image",listItem.image.toString())
            intent.putExtra("winery",listItem.winery.toString())
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return products.size
    }
}