package com.ravikant.randomapiwithretrofithiltexample.retrofit

data class AppInfoItem(
    val app_author: String,
    val app_major_version: String,
    val app_minor_version: String,
    val app_name: String,
    val app_patch_version: String,
    val app_semantic_version: String,
    val app_version: String,
    val id: Int,
    val uid: String
)