package com.example.a1116homework_1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val listView = findViewById<ListView>(R.id.listView)
        val gridView = findViewById<GridView>(R.id.gridView)

        val count = ArrayList<String>()
        val item = ArrayList<Item>()
        val priceRange = IntRange(10, 100)
        val array = resources.obtainTypedArray(R.array.image_list)
        for(index in 0 until array.length()) {
            val photo = array.getResourceId(index,0)
            val name = "水果${index+1}"
            val price = priceRange.random()
            count.add("${index+1}個")
            item.add(Item(photo, name, price))
        }
        array.recycle()

        spinner.adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, count)

        gridView.numColumns = 3
        gridView.adapter = MyAdapter(this, item, R.layout.adapter_vertical)
        listView.adapter = MyAdapter(this, item, R.layout.adapter_horizontal)
    }
}

class MyAdapter(
    context: Context,
    data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: View.inflate(parent.context, layout, null)
        val item = getItem(position) ?: return view
        val imgPhoto = view.findViewById<ImageView>(R.id.imgPhoto)
        imgPhoto.setImageResource(item.photo)
        val tvMsg = view.findViewById<TextView>(R.id.tvMsg)
        tvMsg.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}: ${item.price}元"
        }
        return view
    }
}

data class Item(
    val photo: Int,
    val name: String,
    val price: Int
)