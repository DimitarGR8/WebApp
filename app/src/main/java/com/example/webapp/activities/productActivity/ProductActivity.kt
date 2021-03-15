package com.example.webapp.activities.productActivity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.webapp.utils.BitmapConverter
import com.example.webapp.data.database.DatabaseOperations
import com.example.webapp.activities.ImageMainListViewModel
import com.example.webapp.R
import com.example.webapp.activities.baseActivity.BaseActivity
import com.example.webapp.data.model.Product
import com.example.webapp.utils.NavigationUtils
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity: BaseActivity(), View.OnClickListener {

    lateinit var viewModel: ImageMainListViewModel

    private var productIdText = 0
    private lateinit var productNameText: String
    private lateinit var productCategoryText: String
    private lateinit var productShortDescriptionText: String
    private lateinit var productLongDescriptionText: String
    private lateinit var productAddedDateText: String
    private lateinit var productImageText: String
    private lateinit var productPriceText: String

    val permissions = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        viewModel = ViewModelProvider(this).get(ImageMainListViewModel::class.java)

        // Get the list of files from directory
        viewModel.getFileList()

        if(intent.getBooleanExtra("isThisAdmin", false)) {
           setAdminViewsProperties()
        } else {
           setUserViewsProperties()
        }

        setProductData()

        productSaveNewDataButton.setOnClickListener(this)
        productBackButton.setOnClickListener(this)
        productDeleteButton.setOnClickListener(this)
        productAddImageCameraButton.setOnClickListener(this)
        productAddImageGalleryButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            productSaveNewDataButton -> {
                if (updateProduct()) {
                    goBackToMainListActivity()
                }
            }
            productBackButton -> {
                //goBackToMainListActivity()
                super.onBackPressed()
            }
            productDeleteButton -> {
                if (deleteProduct()) {
                    goBackToMainListActivity()
                }
            }
            productAddImageCameraButton -> {
                if (allPermissionsGranted()) {
                    startCamera()
                } else {
                    ActivityCompat.requestPermissions(this, permissions, RC_PERMISSION)
                }
            }
            productAddImageGalleryButton -> {

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

    private fun setProductData() {
        productIdText = intent.getIntExtra("productId", 0)
        productNameText = intent.getStringExtra("productName").toString()
        productCategoryText = intent.getStringExtra("productCategory").toString()
        productShortDescriptionText = intent.getStringExtra("productShortDescription").toString()
        productLongDescriptionText = intent.getStringExtra("productLongDescription").toString()
        productAddedDateText = intent.getStringExtra("productAddedDate").toString()
        productPriceText = intent.getStringExtra("productPrice").toString()
        productImageText = intent.getStringExtra("productPicture").toString()

        productName.setText(productNameText)
        productCategory.text = productCategoryText
        productShortDescription.setText(productShortDescriptionText)
        productLongDescription.setText(productLongDescriptionText)
        productDateAdded.text = productAddedDateText
        productPrice.setText(productPriceText)
        productImage.setImageBitmap(BitmapConverter.convertFromString(productImageText))

        viewModel.bitmap.observe(this, {
            productImage.setImageBitmap(it)
        })
    }

    private fun updateProduct() : Boolean {
        val bitmapString: MutableLiveData<Bitmap> = MutableLiveData()
        viewModel.bitmap.observe(this, {
            bitmapString.value = it
        })

        if(bitmapString.value == null) {
            bitmapString.value = BitmapConverter.convertFromString(productImageText)
        }

        val productToUpdate = Product(productIdText,
                productName.text.toString(),
                productCategory.text.toString(),
                productShortDescription.text.toString(),
                productLongDescription.text.toString(),
                productPrice.text.toString(),
                productDateAdded.text.toString(),
                BitmapConverter.convertFromBitmap(bitmapString.value)
        )

        return DatabaseOperations(this).updateProduct(productToUpdate)
    }

    private fun deleteProduct () : Boolean {
        return DatabaseOperations(this).deleteProduct(productIdText)
    }

    private fun goBackToMainListActivity() {
        NavigationUtils().moveToMainListActivityWithNoHistory(this, isThisAdmin)
    }

    private fun allPermissionsGranted() = permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(this.packageManager)?.also {
                startActivityForResult(intent, RC_CAPTURE_IMAGE)
            }
        }
    }

    private fun setAdminViewsProperties() {
        productAddImageCameraButton.isEnabled = true
//        productAddImageCameraButton.visibility = View.VISIBLE
//        productSaveNewDataButton.visibility = View.VISIBLE
//        productAddImageGalleryButton.visibility = View.VISIBLE
//        productDeleteButton.visibility = View.VISIBLE
//
        setVisibilityEnabled(arrayListOf(productAddImageCameraButton, productSaveNewDataButton, productAddImageGalleryButton, productDeleteButton))
        setFocusabilityEnabled(arrayListOf(productName, productShortDescription, productLongDescription, productPrice))

//        productName.isFocusable = true
//        productShortDescription.isFocusable = true
//        productLongDescription.isFocusable = true
//        productPrice.isFocusable = true
    }

    private fun setUserViewsProperties() {
        productAddImageCameraButton.isEnabled = false
//        productAddImageCameraButton.visibility = View.GONE
//        productAddImageGalleryButton.visibility = View.GONE
//        productSaveNewDataButton.visibility = View.GONE
//        productDeleteButton.visibility = View.GONE
        setVisibilityGone(arrayListOf(productAddImageCameraButton, productSaveNewDataButton, productAddImageGalleryButton, productDeleteButton))
        setFocusabilityDisabled(arrayListOf(productName, productShortDescription, productLongDescription, productPrice))
//        productName.isFocusable = false
//        productShortDescription.isFocusable = false
//        productLongDescription.isFocusable = false
//        productPrice.isFocusable = false

    }

    private fun setFocusabilityEnabled(arrayList: ArrayList<View>) {
        for(view in arrayList) {
            view.isFocusable = true
        }
    }

    private fun setFocusabilityDisabled(arrayList: ArrayList<View>) {
        for(view in arrayList) {
            view.isFocusable = false
        }
    }

    private fun setVisibilityEnabled(arrayList: ArrayList<View>) {
        for(view in arrayList) {
            view.visibility = View.VISIBLE
        }
    }

    private fun setVisibilityGone(arrayList: ArrayList<View>) {
        for(view in arrayList) {
            view.visibility = View.GONE
        }
    }
}