package com.example.codegama

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codegama.adapter.ItemAdapter
import com.example.codegama.adapter.CategoryAdapter
import com.example.codegama.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var viewModel: ApiViewModel? = null
    var adapterCategotyItem : ItemAdapter? = null
    private lateinit var originalItemList: List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_CodeGama)
        setContentView(binding.root)
        window.setFlags( WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding.addNightMode.setOnCheckedChangeListener { compound, isChecked ->
            if (isChecked) {
                toggleDayNightMode()
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Day mode
                toggleDayNightMode()
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
           // Apply the new mode without restarting the app
//            recreate()
//            delegate.applyDayNight()
        }


        viewModel = ViewModelProvider(this)[ApiViewModel::class.java]
        viewModel?.categories?.observe(this) { apiData ->
            Log.d("#DATAAPI","$apiData")
            binding.audioListRecyclear.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
            val adapterAudio = CategoryAdapter(apiData, this,::onSelectedCategory)
            binding.audioListRecyclear.adapter = adapterAudio

            viewModel?.sendDataToApi(apiData[0].name)
            binding.titleItems.text = apiData[0].name

        }
        viewModel?.response?.observe(this) { response ->
            Log.d("#DATAAPI", "${response}")
            originalItemList = response.products
            binding.audioListRecyclear2.layoutManager = LinearLayoutManager(this)

            if (adapterCategotyItem == null) {
                adapterCategotyItem = ItemAdapter(originalItemList, this, ::onRemoveItems)
                binding.audioListRecyclear2.adapter = adapterCategotyItem
            } else {
                adapterCategotyItem?.updateList(originalItemList)
            }

        }
        viewModel?.product?.observe(this){remRes ->
            binding.audioListRecyclear2.layoutManager = LinearLayoutManager(this)
            adapterCategotyItem = ItemAdapter(remRes.products, this,::onRemoveItems)
            binding.audioListRecyclear2.adapter = adapterCategotyItem
        }

        binding.searchView.setOnQueryTextFocusChangeListener(View.OnFocusChangeListener { view, b ->
            if (view.hasFocus() || binding.searchView.query.isNotEmpty()) {
                binding.titleItems?.visibility = View.GONE

            } else {
                binding.titleItems?.visibility  = View.VISIBLE
            }
        })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.orEmpty())
                return true
            }
        })

    }

    fun toggleDayNightMode() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val newNightMode = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
            Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(newNightMode)
        delegate.applyDayNight() // Apply the new theme without recreating the activity
        val rootView = window.decorView.rootView
        rootView.setBackgroundColor(
            if (newNightMode == AppCompatDelegate.MODE_NIGHT_YES)
                ContextCompat.getColor(this, R.color.white)
            else
                ContextCompat.getColor(this, R.color.black)
        )
    }
    fun filterList(query: String) {
        val filteredList = originalItemList.filter { item ->
            item.title.contains(query, ignoreCase = true)
        }
        adapterCategotyItem?.updateList(filteredList)
    }

    private fun onSelectedCategory(name : String){
        viewModel?.sendDataToApi(name)
        binding.titleItems.text = name

    }

    private fun onRemoveItems(item : Product){
        viewModel?.removeProduct(item)
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle the configuration change if needed
    }
}