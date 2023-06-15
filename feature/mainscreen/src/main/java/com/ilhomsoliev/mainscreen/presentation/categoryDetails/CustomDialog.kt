package com.ilhomsoliev.mainscreen.presentation.categoryDetails

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ilhomsoliev.testappcook.feature.mainscreen.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class ViewDialog {
    fun showDialog(
        activity: Context?,
        dishName: String?,
        imageUrl: String,
        dishPrice: String,
        dishMass: String,
        dishDescription: String,
        onAddToBasket:()->Unit,
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.setContentView(R.layout.dish_information_dialog)

        val dishNameTextView = dialog.findViewById(R.id.dishNameDishInformation) as TextView
        val dishPicImageView = dialog.findViewById(R.id.dishItemImageView) as ImageView
        val dishPriceTextView = dialog.findViewById(R.id.dishPriceDishInformation) as TextView
        val dishMassTextView = dialog.findViewById(R.id.dishMassDishInformation) as TextView
        val dishDescriptionTextView = dialog.findViewById(R.id.dishDescriptionDishInformation) as TextView
        val addToBasketButton =  dialog.findViewById(R.id.addToBaskerButtonDishInformation) as Button
        val icFavorite =  dialog.findViewById(R.id.icFavoriteDishInformation) as View
        val icClose =  dialog.findViewById(R.id.icCloseDishInformation) as View
        dishPriceTextView.text = dishPrice
        dishMassTextView.text = dishMass
        dishDescriptionTextView.text = dishDescription
        dishNameTextView.text = dishName
        val url = URL(imageUrl)
        GlobalScope.launch(Dispatchers.IO) {
            val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                dishPicImageView.setImageBitmap(bmp)
            }
        }
        addToBasketButton.setOnClickListener{
            onAddToBasket()
            dialog.hide()
        }
        icFavorite.setOnClickListener {}
        icClose.setOnClickListener{
            dialog.hide()
        }
        dialog.show()
    }
}