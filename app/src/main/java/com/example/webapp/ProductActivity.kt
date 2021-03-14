package com.example.webapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_product.*
import java.io.ByteArrayOutputStream

class ProductActivity: BaseActivity(), View.OnClickListener {

    lateinit var viewModel: ImageViewModel

    private var prodcutIdText = 0
    private lateinit var productNameText: String
    private lateinit var productCategoryText: String
    private lateinit var productShortDescriptionText: String
    private lateinit var productLongDescriptionText: String
    private lateinit var productAddedDateText: String
    private var productPriceText = 0.0

    val permissions = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        // Get the list of files
        viewModel.getFileList()

        if(intent.getBooleanExtra("isThisAdmin", false)) {
            productSaveNewDataButton.visibility = View.VISIBLE
            productDeleteButton.visibility = View.VISIBLE
        } else {
            productSaveNewDataButton.visibility = View.GONE
            productDeleteButton.visibility = View.GONE
        }

        setProductData()

        productSaveNewDataButton.setOnClickListener(this)
        productBackButton.setOnClickListener(this)
        productDeleteButton.setOnClickListener(this)
        productAddImageButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            productSaveNewDataButton -> {
                if (updateProduct()) {
                    goBackToMainListActivity()
                }
            }
            productBackButton -> {
                goBackToMainListActivity()
            }
            productDeleteButton -> {
                if (deleteProduct()) {
                    goBackToMainListActivity()
                }
            }
            productAddImageButton -> {
                if(allPermissionsGranted()) {
                    startCamera()
                } else {
                    ActivityCompat.requestPermissions(this, permissions, RC_PERMISSION)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_CAPTURE_IMAGE) {
            if(resultCode == Activity.RESULT_OK) {
                val bitmap = data?.extras?.get("data") as Bitmap
                viewModel.bitmap.value = bitmap
                viewModel.storeEncryptedBitmap()
            }
        }
    }

    private fun setProductData() {
        prodcutIdText = intent.getIntExtra("productId", 0)
        productNameText = intent.getStringExtra("productName").toString()
        productCategoryText = intent.getStringExtra("productCategory").toString()
        productShortDescriptionText = intent.getStringExtra("productShortDescription").toString()
        productLongDescriptionText = intent.getStringExtra("productLongDescription").toString()
        productAddedDateText = intent.getStringExtra("productAddedDate").toString()
        productPriceText = intent.getDoubleExtra("productPrice", 00.00)

        productName.setText(productNameText)
        productCategory.text = productCategoryText
        productShortDescription.setText(productShortDescriptionText)
        productLongDescription.setText(productLongDescriptionText)
        productDateAdded.text = productAddedDateText
        productPrice.setText(productPriceText.toString())

        viewModel.bitmap.observe(this, {
            productImage.setImageBitmap(it)
        })
    }

    private fun imageToBitmap(image: ImageView): ByteArrayOutputStream {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream
    }

    private fun updateProduct() : Boolean {
        val productToUpdate = Product(prodcutIdText,
            productName.text.toString(),
            productCategory.text.toString(),
            productShortDescription.text.toString(),
            productLongDescription.text.toString(),
            productPrice.text.toString().toDouble(),
            productDateAdded.text.toString(),
            imageToBitmap(productImage))
        return DatabaseOperations(this).updateProduct(productToUpdate)
    }

    private fun deleteProduct () : Boolean {
        return DatabaseOperations(this).deleteProduct(prodcutIdText)
    }

    private fun goBackToMainListActivity() {
        NavigationUtils().moveToMainListActivity(this, isThisAdmin)
    }

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == RC_PERMISSION) {
            if(allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(this.packageManager)?.also {
                startActivityForResult(intent, RC_CAPTURE_IMAGE)
            }
        }
    }
}