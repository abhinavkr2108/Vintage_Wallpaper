package com.example.vintage.fragments

//import com.example.vintage.model.ColorModel
//import com.example.vintage.model.colorToneAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vintage.R
import com.example.vintage.model.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment()
{
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = FirebaseFirestore.getInstance()

        //Getting Best Of Month Wallpapers Data from Firebase
        db.collection("BestOgMonth").addSnapshotListener { value, error ->
            val listBestOfMonth = arrayListOf<BomModel>() //Making ArrayList Of Best Of Month Wallpapers
            val data = value?.toObjects(BomModel::class.java) //Converting Firebase data to objects
            listBestOfMonth.addAll(data!!) //Adding the objects to ArrayList

            rcv_Bom.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
            rcv_Bom.adapter = BomAdapter(requireContext(), listBestOfMonth)

            /*for (i in listBestOfMonth){
                Log.e("bomFirebase", "Images: $i")
            }*/
        }

        //Getting Colour Tone Data from Firebase
        db.collection("ColourTone").addSnapshotListener { value, error ->
            val listColorTone = arrayListOf<ColorModel>() //Making ArrayList Of Best Of Month Wallpapers
            val data = value?.toObjects(ColorModel::class.java) //Converting Firebase data to objects
            listColorTone.addAll(data!!) //Adding the objects to ArrayList

            rcv_colorTone.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL ,false)
            rcv_colorTone.adapter = colorToneAdapter(requireContext(), listColorTone)
        }

        //Getting Categories Wallpapers Data from Firebase
        db.collection("Categories").addSnapshotListener { value, error ->
            val listCategories = arrayListOf<CategoryModel>() //Making ArrayList Of Best Of Month Wallpapers
            val data = value?.toObjects(CategoryModel::class.java) //Converting Firebase data to objects
            listCategories.addAll(data!!) //Adding the objects to ArrayList

            rcv_category.layoutManager = GridLayoutManager(requireContext(),2)
            rcv_category.adapter = CategoryAdapter(requireContext(), listCategories)
        }

        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}