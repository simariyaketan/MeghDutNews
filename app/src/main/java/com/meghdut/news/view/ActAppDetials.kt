package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.meghdut.news.MyApplication
import com.meghdut.news.R
import com.meghdut.news.adapter.AppDetailAdapter
import com.meghdut.news.fragment.screensevenfragment
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.category.CategoryClass
import com.meghdut.news.model.category.CategoryData
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.network.WebAPIClient
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.act_app_detials.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActAppDetials : ActBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_app_detials)

        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        val appDetailAdapter: AppDetailAdapter = AppDetailAdapter(supportFragmentManager)
        viewPagerAppDetail.adapter = appDetailAdapter

        txtSkip.setOnClickListener {
            GoToNextScreen()
        }

        txtNext.setOnClickListener {
            GoToNextScreen()
        }


        getCategoryList()
    }

    private fun GoToNextScreen() {
        Log.d("viewPagerAppDetail", "viewPagerAppDetail = " + viewPagerAppDetail.currentItem)
        if (viewPagerAppDetail.currentItem == 6) {

            val page: Fragment? =
                supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPagerAppDetail + ":" + viewPagerAppDetail.getCurrentItem())
            var categoryArrayList: ArrayList<CategoryData> =
                (page as screensevenfragment).categoryArrayList

            var selectedCategory: String = ""
            for (cat in categoryArrayList) {
                if (cat.isSelected) {
                    if (selectedCategory.equals(""))
                        selectedCategory = cat.id
                    else
                        selectedCategory = selectedCategory + "," + cat.id
                }
            }
            if (!selectedCategory.equals("")) {
                AddSelectedCategory(selectedCategory)
            } else {
                Common.ShowToast(activity!!, "Please select any one.")
            }

        } else {
            val nextScreen: Int = viewPagerAppDetail.currentItem + 1
            viewPagerAppDetail.currentItem = nextScreen
        }
    }

    private fun AddSelectedCategory(selectedCategory: String) {
        Log.d("selectedCategory", "selectedCategory = " + selectedCategory)
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.AddSelectedCategory(selectedCategory,
                object : Callback<CommonClass> {
                    override fun onFailure(call: Call<CommonClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<CommonClass>,
                        response: Response<CommonClass>
                    ) {
                        closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {
                                Prefs.putString(
                                    PreferenceField.LOGIN_SUCCESS, "Yes"
                                )

                                Prefs.putString(
                                    PreferenceField.FONT_TYPE, "Regular"
                                )

                                val di = Intent(activity, ActDeshboardScreen::class.java)
                                di.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(di)
                                finish()
                            } ?: Common.ShowToast(activity!!, message)
                        }
                    }
                })
    }


    private fun getCategoryList() {
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.GetCategoryList("",
                object : Callback<CategoryClass> {
                    override fun onFailure(call: Call<CategoryClass>, t: Throwable) {
                        Common.ShowErrorMessage(activity!!, "", null, 0)
                        closeLoader()
                    }

                    override fun onResponse(
                        call: Call<CategoryClass>,
                        response: Response<CategoryClass>
                    ) {
                        closeLoader()
                        response.body()?.apply {
                            takeIf { status }?.let {
                                Log.d("list", "list = " + list.size)
                                MyApplication.getInstance()?.categoryArrayList?.addAll(list)
                            }
                        }
                    }
                })
    }


}
