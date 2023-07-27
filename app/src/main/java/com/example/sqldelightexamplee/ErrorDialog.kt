package com.example.sqldelightexamplee

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView

object ErrorDialog {
    fun showErrorDialog(context: Context, first: String?, last: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = (context as Activity).layoutInflater
        val dialogView = inflater.inflate(R.layout.details_dialog, null)
        dialogBuilder.setView(dialogView)
        val title: AppCompatTextView = dialogView.findViewById(R.id.title)
        title.text = first
        val btnNag: AppCompatTextView = dialogView.findViewById(R.id.btnNag)
        btnNag.text = last
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}