package com.example.vintage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.vintage.model.BomModel
import com.example.vintage.model.CategoryAdapter
import com.example.vintage.model.CategoryImagesAdapter
import com.example.vintage.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("name")

        db.collection("Categories").document(uid!!).collection("wallpaper").addSnapshotListener { value, error ->
            val listCategoriesWallaper = arrayListOf<BomModel>() //Making ArrayList Of Best Of Month Wallpapers
            val data = value?.toObjects(BomModel::class.java) //Converting Firebase data to objects
            listCategoriesWallaper.addAll(data!!) //Adding the objects to ArrayList

            categoryTitle.text = name.toString()
            categorySubtitle.text = "${listCategoriesWallaper.size} Wallpapers Available"

            rcv_categoryList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            rcv_categoryList.adapter = CategoryImagesAdapter(this,listCategoriesWallaper)
        }
    }
}