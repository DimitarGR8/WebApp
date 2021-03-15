package com.example.webapp.data.model

import java.io.File

data class FileEntity(
    val fileName: String,
    val file: File,
    val fileSize: String
)