package com.example.abab

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val context: Context, private val imageResIds: List<Int>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ImagePreferences", Context.MODE_PRIVATE)

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val getText: TextView = itemView.findViewById(R.id.getText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = imageResIds[position]
        holder.imageView.setImageResource(imageResId)

        // 状態を取得し、初期表示を設定する
        val isGet = sharedPreferences.getBoolean(imageResId.toString(), false)
        updateGetTextVisibility(holder, isGet)

        // 画像をタップした時の処理
        holder.itemView.setOnClickListener {
            // 現在の「GET」状態を取得
            val currentIsGet = sharedPreferences.getBoolean(imageResId.toString(), false)
            // 状態を反転させる
            val newIsGet = !currentIsGet
            // SharedPreferencesに保存
            sharedPreferences.edit().putBoolean(imageResId.toString(), newIsGet).apply()
            // UIを更新する
            updateGetTextVisibility(holder, newIsGet)
        }
    }

    override fun getItemCount(): Int {
        return imageResIds.size
    }

    // GETテキストの表示/非表示を更新するメソッド
    private fun updateGetTextVisibility(holder: ImageViewHolder, isGet: Boolean) {
        holder.getText.visibility = if (isGet) View.VISIBLE else View.GONE
    }
}