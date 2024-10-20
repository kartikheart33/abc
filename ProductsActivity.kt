package com.alexios.android.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexios.android.R
import com.alexios.android.RoomDB.AlexiousDataBase
import com.alexios.android.adapters.ProductsAdapter
import com.alexios.android.databinding.ActivityWineFillterBinding
import com.alexios.android.models.Products
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

class ProductsActivity : AppCompatActivity() {
    lateinit var binding : ActivityWineFillterBinding
     lateinit var adapter : ProductsAdapter
    private val alexiousDatabase by lazy { AlexiousDataBase.getDbInstance(this).productDao() }
    private val filteralexiousDatabase by lazy { AlexiousDataBase.getDbInstance(this).filterDao() }
    var category_id: Int = 1
    var winetype = ""
    var countryfilterlist : ArrayList<String> = ArrayList()
    var verietyfilterlist : ArrayList<String> = ArrayList()
    var bytheglassfilterlist : ArrayList<String> = ArrayList()
    var pricefilterlist : ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineFillterBinding.inflate(layoutInflater)
        setContentView(binding.root)
         category_id=intent.extras!!.getInt("category_id")
        winetype = intent.getStringExtra("winetype").toString()

        binding.toolbarTitle.text = winetype.toString()+" Wines"

        observeWineProducts(category_id)
        observfilter()
        Toast.makeText(this@ProductsActivity, "category ID : $category_id", Toast.LENGTH_SHORT).show()
        if (savedInstanceState != null) {
            binding.pricespinner.setSelection(savedInstanceState.getInt("pricespinner", 0));
            binding.originspinner.setSelection(savedInstanceState.getInt("originspinner", 0));
            binding.verietyspinner.setSelection(savedInstanceState.getInt("verietyspinner", 0));
            binding.bytheglassspinner.setSelection(savedInstanceState.getInt("bytheglassspinner", 0));
         }




        binding.resetfilter.setOnClickListener {
            binding.pricespinner.setSelection(0)
            binding.originspinner.setSelection(0)
            binding.verietyspinner.setSelection(0)
            binding.bytheglassspinner.setSelection(0)
            observeWineProducts(category_id)

        }

        binding.back.setOnClickListener {
            finish()
        }
        binding.backtext.setOnClickListener {
            finish()
        }
//        adapter = ProductsAdapter(winelist,this )
//        binding.recyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
//        binding.recyclerView!!.adapter = adapter

    }



    private fun priceSpinnerSet() {
        val items= pricefilterlist

        val spinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.pricespinner.adapter = spinnerAdapter

        binding.pricespinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (view!=null){
                    if(value == items[0]){
                        (view as TextView).setTextColor(Color.GRAY)
                    }else{
                        (view as TextView).setTextColor(Color.WHITE)

                        if (value==items[1]) {
                            lifecycleScope.launch {
                                alexiousDatabase.getAllProductsByCategory(category_id)
                                    .collect { products ->
                                        if (products.isNotEmpty()) {
                                            Toast.makeText(
                                                this@ProductsActivity,
                                                "Total products : " + products.size.toString(),
                                                Toast.LENGTH_LONG
                                            ).show()
                                            var priceproducts: MutableList<Products> = ArrayList()
                                            priceproducts =
                                                products.sortedBy { it.price.inc() } as MutableList<Products>
                                            Log.d(
                                                "TAG",
                                                "onItemSelected: " + priceproducts.size.toString()
                                            )
                                            Log.d(
                                                "TAG",
                                                "onItemSelected: " + items[position].toString()
                                            )
                                            adapter = ProductsAdapter(
                                                priceproducts,
                                                this@ProductsActivity
                                            )
                                            binding.recyclerView.layoutManager =
                                                LinearLayoutManager(
                                                    this@ProductsActivity,
                                                    LinearLayoutManager.VERTICAL,
                                                    false
                                                )
                                            binding.recyclerView.adapter = adapter
                                        }
                                    }
                            }
                        }
                        if (value==items[2]){
                            lifecycleScope.launch {
                                alexiousDatabase.getAllProductsByCategory(category_id)
                                    .collect { products ->
                                        if (products.isNotEmpty()) {
                                            Toast.makeText(
                                                this@ProductsActivity,
                                                "Total products : " + products.size.toString(),
                                                Toast.LENGTH_LONG
                                            ).show()
                                            var priceproducts: MutableList<Products> = ArrayList()
                                            priceproducts =
                                                products.sortedBy { it.price.inc() } as MutableList<Products>
                                            Log.d(
                                                "TAG",
                                                "onItemSelected: " + priceproducts.size.toString()
                                            )
                                            Log.d(
                                                "TAG",
                                                "onItemSelected: " + items[position].toString()
                                            )
                                            priceproducts.reverse()
                                            adapter = ProductsAdapter(
                                                priceproducts,
                                                this@ProductsActivity
                                            )
                                            binding.recyclerView.layoutManager =
                                                LinearLayoutManager(
                                                    this@ProductsActivity,
                                                    LinearLayoutManager.VERTICAL,
                                                    false
                                                )
                                            binding.recyclerView.adapter = adapter
                                        }
                                    }
                            }
                        }

//                        if (value==items[1]){
//                            lifecycleScope.launch {
//                                alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
//                                    if (products.isNotEmpty()) {
//                                        Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
//                                        var priceproducts: MutableList<Products> = ArrayList()
//                                        for (i in 0 until products.size){
//                                            if (products[i].price<=500){
//                                                priceproducts.add(products[i])
//                                            }
//                                        }
//                                        adapter = ProductsAdapter(priceproducts,this@ProductsActivity )
//                                        binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
//                                        binding.recyclerView.adapter = adapter
//                                    }
//                                }
//                            }
//
//                        }
//                        if (value==items[2]){
//                            lifecycleScope.launch {
//                                alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
//                                    if (products.isNotEmpty()) {
//                                        Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
//                                        var priceproducts: MutableList<Products> = ArrayList()
//                                        for (i in 0 until products.size){
//                                            if (products[i].price<=1000){
//                                                priceproducts.add(products[i])
//                                            }
//                                        }
//                                        adapter = ProductsAdapter(priceproducts,this@ProductsActivity )
//                                        binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
//                                        binding.recyclerView.adapter = adapter
//                                    }
//                                }
//                            }
//                        }
//                        if (value==items[3]){
//                            lifecycleScope.launch {
//                                alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
//                                    if (products.isNotEmpty()) {
//                                        Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
//                                        var priceproducts: MutableList<Products> = ArrayList()
////                                        for (i in 0 until products.size){
////                                            if (products[i].price<=1500){
////                                                priceproducts.add(products[i])
////                                            }
////                                        }
//                                        priceproducts = products!!.filter { s -> s.price <= 1500 }as MutableList<Products>
//                                        Log.d("TAG", "onItemSelected: "+priceproducts.size.toString())
//                                        Log.d("TAG", "onItemSelected: "+items[position].toString())
//
//
//                                        adapter = ProductsAdapter(priceproducts,this@ProductsActivity )
//                                        binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
//                                        binding.recyclerView.adapter = adapter
//                                    }
//                                }
//                            }
//                        }
                    }
                }
            }

        }
    }

    private fun bytheglassSpinnerSet() {
        val items= bytheglassfilterlist

        val spinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bytheglassspinner.adapter = spinnerAdapter

        binding.bytheglassspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (view!=null){
                    if(value == items[0]){
                        (view as TextView).setTextColor(Color.GRAY)
                    }else{
                        (view as TextView).setTextColor(Color.WHITE)
                        lifecycleScope.launch {
                            alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
                                if (products.isNotEmpty()) {
                                    Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
                                    var bytheglass: MutableList<Products> = ArrayList()
                                    bytheglass = products!!.filter { s -> s.glass== items[position] }as MutableList<Products>
                                    Log.d("TAG", "onItemSelected: "+bytheglass.size.toString())
                                    Log.d("TAG", "onItemSelected: "+items[position].toString())
                                    adapter = ProductsAdapter(bytheglass,this@ProductsActivity )
                                    binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
                                    binding.recyclerView.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private fun virietySpinnerSet() {
        val items= verietyfilterlist

        val spinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.verietyspinner.adapter = spinnerAdapter

        binding.verietyspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (view!=null){
                    if(value == items[0]){
                        (view as TextView).setTextColor(Color.GRAY)
                    }else{
                        (view as TextView).setTextColor(Color.WHITE)
                        lifecycleScope.launch {
                            alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
                                if (products.isNotEmpty()) {
                                    Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
                                    var veriety: MutableList<Products> = ArrayList()
                                    veriety = products!!.filter { s -> s.veriety!!.contains(items[position]) }as MutableList<Products>
                                    Log.d("TAG", "onItemSelected: "+veriety.size.toString())
                                    Log.d("TAG", "onItemSelected: "+items[position].toString())
                                    adapter = ProductsAdapter(veriety,this@ProductsActivity )
                                    binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
                                    binding.recyclerView.adapter = adapter
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private fun wineoriginSpinnerSet() {
        val items= countryfilterlist

        val spinnerAdapter= object : ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                // Disable the first item from Spinner
                // First item will be used for hint
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                //set the color of first item in the drop down list to gray
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                } else {
                    //here it is possible to define color for other items by
                    //view.setTextColor(Color.RED)
                }
                return view
            }

        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.originspinner.adapter = spinnerAdapter

        binding.originspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (view!=null){
                    if(value == items[0]){
                        (view as TextView).setTextColor(Color.GRAY)
                    }else{
                        (view as TextView).setTextColor(Color.WHITE)

                        lifecycleScope.launch {
                            alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
                                if (products.isNotEmpty()) {
                                    Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
                                    var countryproducts: MutableList<Products> = ArrayList()
                                    countryproducts = products!!.filter { s -> s.country== items[position] }as MutableList<Products>
                                    Log.d("TAG", "onItemSelected: "+countryproducts.size.toString())
                                    Log.d("TAG", "onItemSelected: "+items[position].toString())
                                    adapter = ProductsAdapter(countryproducts,this@ProductsActivity )
                                    binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
                                    binding.recyclerView.adapter = adapter
                                }
                            }
                        }
                    }
                }

            }

        }
    }


    private fun observeWineProducts(category_id: Int) {
        lifecycleScope.launch {
            alexiousDatabase.getAllProductsByCategory(category_id).collect { products ->
                if (products.isNotEmpty()) {
                    Toast.makeText(this@ProductsActivity,"Total products : "+products.size.toString(),Toast.LENGTH_LONG).show()
                    adapter = ProductsAdapter(products,this@ProductsActivity )
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL ,false)
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }

    private fun observfilter() {
        lifecycleScope.launch {
            filteralexiousDatabase.filter.collect{ filter->
                if (filter.isNotEmpty()){
                    Toast.makeText(applicationContext, ""+filter.get(0).country!!.size.toString(), Toast.LENGTH_SHORT).show()
                    countryfilterlist.addAll(filter.get(0).country!!)
                    verietyfilterlist.addAll(filter.get(0).veriety!!)
                    bytheglassfilterlist.addAll(filter.get(0).bytheglass!!)
                    pricefilterlist.addAll(filter.get(0).price!!)
                    wineoriginSpinnerSet()
                    virietySpinnerSet()
                    bytheglassSpinnerSet()
                    priceSpinnerSet()

                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("originspinner",binding.originspinner.selectedItemPosition)
        outState.putInt("pricespinner",binding.pricespinner.selectedItemPosition)
        outState.putInt("bytheglassspinner",binding.bytheglassspinner.selectedItemPosition)
        outState.putInt("verietyspinner",binding.verietyspinner.selectedItemPosition)
    }
}