package com.todos.communicatetogithubapi

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadAvatar( imageUrl: String) = Glide.with(context)
        .load(imageUrl)
        .into(this)

fun ImageView.loadAvatar( imageUrl: String, fallBackImageUrl: String) = Glide.with(context)
        .load(imageUrl)
        .error(Glide.with(context).load(fallBackImageUrl))
        .into(this)