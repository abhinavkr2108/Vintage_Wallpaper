package com.example.vintage

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_final.*
import kotlinx.coroutines.*
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.net.URL
import java.util.*


class FinalActivity : AppCompatActivity() {
    val random1 = Random().nextInt(520985)
    val random2 = Random().nextInt(520985)

    val name = "VINTAGE-${random1 + random2}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)
        val url = intent.getStringExtra("link")
        val urlImage = URL(url)
        val urlString = urlImage.toString()
        finalWallPaper
        Glide.with(this).load(url).into(finalWallPaper)

        //Downloading wallpaper
        btnDownload.setOnClickListener {

            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
                addImageToGallery(name, result.await(),this@FinalActivity)
            //downloadImageNew(result.await())
            }
        }

        //Setting Wallpaper
        btnSetWallpaper.setOnClickListener {
            Toast.makeText(this, "Wallpaper Applied", Toast.LENGTH_SHORT).show()
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())
            }
        }
    }

    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }
    }

    fun saveImage(image: Bitmap?) {
        val random1 = Random().nextInt(520985)
        val random2 = Random().nextInt(520985)

        val name = "VINTAGE-${random1 + random2}"

        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "Vintage Wallpaper"
            )
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream?>(data)
            Toast.makeText(this, "Image Save", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error Occurred While Downloading", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addImageToGallery(fileName: String, bitmap: Bitmap?, ctx: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val fos: OutputStream
                val resolver = contentResolver
                val values = ContentValues()

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                values.put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "EduImages"
                )

                val imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                fos = resolver.openOutputStream(imageUri!!) as OutputStream
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
                Toast.makeText(
                    ctx, "Saved in gallery",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName)
                values.put(MediaStore.Images.ImageColumns.TITLE, fileName)

                val uri: Uri? = ctx.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                uri?.let {
                    ctx.contentResolver.openOutputStream(uri)?.let { stream ->
                        val oStream =
                            BufferedOutputStream(stream)
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, oStream)
                        oStream.close()
                        Toast.makeText(ctx, "Saved in gallery", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(ctx, e.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }



}

