package com.alexios.android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alexios.android.R
import com.alexios.android.databinding.ActivityCategoryBinding
import com.alexios.android.databinding.ActivityWineDetailScreenBinding
import com.bumptech.glide.Glide

class WineDetailScreen : AppCompatActivity() {
    lateinit var binding: ActivityWineDetailScreenBinding
    var winename = ""
    var price = ""
    var grapetype = ""
    var winecountry = ""
    var winedescription = ""
    var image = ""
    var winery = ""
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


         winename = intent.getStringExtra("winename").toString()
         price = intent.getStringExtra("price").toString()
         grapetype = intent.getStringExtra("grapetype").toString()
         winecountry = intent.getStringExtra("winecountry").toString()
         winedescription = intent.getStringExtra("winedescription").toString()
         image = intent.getStringExtra("image").toString()
         winery = intent.getStringExtra("winery").toString()

         if (image==""){

             binding.imgcardview.visibility=View.GONE
         }else{

             binding.imgcardview.visibility=View.VISIBLE
         }

         binding.winename.text = winename
         binding.price!!.text = price+ " €"
         binding.grapetype.text = grapetype
         binding.winecountry.text = winecountry
         binding.winedescription.text = "Description: "+winedescription
         binding.winerytype.text = winery
         binding.toolbarTitle.text = winename

         Glide.with(this).load(image).error(R.drawable.bg_inside_portrait).placeholder(R.drawable.bg_inside_portrait).into(binding.wineimage)




         binding.backtext.setOnClickListener {
             finish()
         }

        binding.back.setOnClickListener {
            finish()
        }



    }
}