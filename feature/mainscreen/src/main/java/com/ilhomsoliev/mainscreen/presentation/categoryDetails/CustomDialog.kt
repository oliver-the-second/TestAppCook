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
        onClose:()->Unit,
        onAddToBasket:()->Unit,
    ) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.setContentView(R.layout.dish_information_dialog)

        val dishNameTextView = dialog.findViewById<View>(R.id.dishNameDishInformation) as TextView
        val dishPicImageView = dialog.findViewById<View>(R.id.dishItemImageView) as ImageView
        val dishPriceTextView = dialog.findViewById<View>(R.id.dishPriceDishInformation) as TextView
        val dishMassTextView = dialog.findViewById<View>(R.id.dishMassDishInformation) as TextView
        val dishDescriptionTextView = dialog.findViewById<View>(R.id.dishDescriptionDishInformation) as TextView
        val addToBasketButton =  dialog.findViewById<View>(R.id.addToBaskerButtonDishInformation) as Button
        val icFavorite =  dialog.findViewById<View>(R.id.icFavoriteDishInformation) as View
        val icClose =  dialog.findViewById<View>(R.id.icCloseDishInformation) as View
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
        }
        icFavorite.setOnClickListener {

        }
        icClose.setOnClickListener{
            dialog.hide()
            //onClose()
        }
        /*val dialogButton = dialog.findViewById<View>(R.id.btn_dialog) as Button
        dialogButton.setOnClickListener { dialog.dismiss() }*/
        dialog.show()
    }
}