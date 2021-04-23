package tech.purplebeen.githubapi

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("profileUrl")
fun bindProfileUrl(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView).load(url).into(imageView)
    }
}