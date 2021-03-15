package com.example.webapp.activities

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.example.webapp.data.model.FileEntity
import com.example.webapp.utils.EncryptionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class ImageMainListViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application
    val bitmap: MutableLiveData<Bitmap> = MutableLiveData()
    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    private val directory = File(context.filesDir, "images")

    val fileListEntity: MutableLiveData<ArrayList<FileEntity>> = MutableLiveData()
    val fileList = ArrayList<FileEntity>()
    val fileName: MutableLiveData<String> = MutableLiveData()
    val isProgress: MutableLiveData<Boolean> = MutableLiveData()

    fun storeEncryptedBitmap() {
        viewModelScope.launch {
            val scaleBitmap = bitmap.value?.let { Bitmap.createScaledBitmap(bitmap.value!!, 1080, 780, true) }
            val dateNow = Date()
            val fileName = "${simpleDateFormat.format(dateNow)}.jpg"

            if(!directory.exists()) {
                directory.mkdir()
            }

            val file = File(directory, fileName)

            launch(Dispatchers.IO) {
                try {
                    val encryptedFile = EncryptionHelper.getEncryptedFile(file, context)
                    val byteArrayout = ByteArrayOutputStream()
                    scaleBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayout)
                    encryptedFile.openFileOutput().also { out ->
                        out.write(byteArrayout.toByteArray())
                        out.flush()
                        out.close()
                    }
                }catch (e: Exception) {

                }
            }
        }
    }

    fun getEncryptedBitmap() {
        viewModelScope.launch {
            val file = File(directory, fileName.value)
            val encrypedFile = EncryptionHelper.getEncryptedFile(file, context)

            launch(Dispatchers.IO) {
                try {
                    encrypedFile.openFileInput().also { input ->
                        val byteArrayIntputString = ByteArrayInputStream(input.readBytes())
                        bitmap.postValue(BitmapFactory.decodeStream(byteArrayIntputString))
                    }
                } catch (e: Exception) {
                    //Since we are at background thread
                }
            }
        }
    }

    fun getFileList() {
        isProgress.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val directory = File(context.filesDir.path)
            val listFiles = directory.listFiles()

            listFiles?.forEach { file ->
                if(file.isDirectory) {
                    fileList.addAll(file.listFiles()?.map {
                        FileEntity(it.name, it, "${it.length() / 1024} KB")
                    }?.sortedByDescending { it.fileName } ?: emptyList())
                    fileListEntity.postValue(fileList)
                }

                if(file.isFile) {
                    fileList.add(FileEntity(file.name, file, "${file.length() / 1024} KB"))
                    fileListEntity.postValue(fileList)
                }
            }

            isProgress.postValue(false)
        }
    }
}