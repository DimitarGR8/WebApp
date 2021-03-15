package com.example.webapp.data.model

import java.io.File


//This is connected to the unfinished functionality with the encryption. See ImageMainListViewModel.class
data class FileEntity(
    val fileName: String,
    val file: File,
    val fileSize: String
)