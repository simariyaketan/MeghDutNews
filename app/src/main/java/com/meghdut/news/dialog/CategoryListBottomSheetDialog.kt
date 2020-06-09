package com.meghdut.news.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meghdut.news.R
import com.meghdut.news.adapter.CatSettingAdapter
import com.meghdut.news.databinding.LayoutCategoryDialogBinding
import com.meghdut.news.helper.Common
import com.meghdut.news.model.category.CategoryData
import com.meghdut.news.view.ActSettingScreen

class CategoryListBottomSheetDialog(var categoryList: ArrayList<CategoryData>) :
    BottomSheetDialogFragment(), CatSettingAdapter.CatSettingClickListner {

    lateinit var catSettingAdapter: CatSettingAdapter

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val layoutCategoryDialogBinding: LayoutCategoryDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(dialog.context), R.layout.layout_category_dialog, null, false
        )

        layoutCategoryDialogBinding.fontSize = Common.getFontSize(activity!!)
        layoutCategoryDialogBinding.nighDayColor = Common.GetNightDayColor(activity!!)

        layoutCategoryDialogBinding.txtSaveFavCat.setOnClickListener {
            dialog.cancel()
            var selectedCategory: String = ""
            for (cat in categoryList) {
                if (cat.favorite.equals("yes")) {
                    if (selectedCategory.equals(""))
                        selectedCategory = cat.id
                    else
                        selectedCategory = selectedCategory + "," + cat.id
                }
            }
            if (!selectedCategory.equals("")) {
                (activity!! as ActSettingScreen).AddSelectedCategory(selectedCategory)
            } else {
                Common.ShowToast(activity!!, "Please select any one.")
            }
        }

        catSettingAdapter = CatSettingAdapter(activity!!, categoryList)
        catSettingAdapter.setCatSettingClickListner(this)
        layoutCategoryDialogBinding.recCategoryList.adapter = catSettingAdapter
        catSettingAdapter.notifyDataSetChanged()

        dialog.setCancelable(false)
        dialog.setContentView(layoutCategoryDialogBinding.root)
    }

    override fun selectFavoriteCategory(position: Int) {
        val categoryData: CategoryData = categoryList.get(position)

        if (categoryData.favorite.equals("yes"))
            categoryData.favorite = "no"
        else
            categoryData.favorite = "yes"
        categoryList.set(position, categoryData)
        catSettingAdapter.notifyItemChanged(position)

    }
}