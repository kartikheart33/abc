package com.alexios.android.activities

import android.app.Activity
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexios.android.RoomDB.AlexiousDataBase
import com.alexios.android.adapters.CategoriesAdapter
import com.alexios.android.databinding.ActivityCategoryBinding
import kotlinx.coroutines.launch

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var adapter : CategoriesAdapter
    private val alexiousDatabase by lazy { AlexiousDataBase.getDbInstance(this).wineDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeWines()
//        binding.whitewine.setOnClickListener {
//            val intent = Intent(this, WineFillterActivity::class.java)
//            startActivity(intent)
//
//        }
//        binding.redwine.setOnClickListener {
//            Toast.makeText(this, "redwine", Toast.LENGTH_SHORT).show()
//        }
//        binding.rosewine.setOnClickListener {
//            Toast.makeText(this, "rosewine", Toast.LENGTH_SHORT).show()
//        }
//
//        binding.sparklingwine.setOnClickListener {
//            Toast.makeText(this, "sparklingwine", Toast.LENGTH_SHORT).show()
//        }
//        binding.champagnewine.setOnClickListener {
//            Toast.makeText(this, "champagnewine", Toast.LENGTH_SHORT).show()
//        }
//        binding.dessertwine.setOnClickListener {
//            Toast.makeText(this, "dessertwine", Toast.LENGTH_SHORT).show()
//
//        }
    }
    private fun observeWines() {
        lifecycleScope.launch {
            alexiousDatabase.wines.collect { winelist ->
                if (winelist.isNotEmpty()) {
                    Toast.makeText(this@CategoryActivity,"Total wines : "+winelist.size.toString(),Toast.LENGTH_LONG).show()
                    adapter = CategoriesAdapter(winelist,this@CategoryActivity )
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        binding.rvcategories!!.layoutManager = GridLayoutManager(this@CategoryActivity ,2)
                    }else{
                        binding.rvcategories!!.layoutManager = GridLayoutManager(this@CategoryActivity ,3)
                    }

                    binding.rvcategories!!.adapter = adapter
                }
            }
        }
    }

}