package com.meghdut.news.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.meghdut.news.R
import com.meghdut.news.adapter.AdpNotificationCategory
import com.meghdut.news.adapter.CatSettingAdapter
import com.meghdut.news.databinding.ActSettingScreenBinding
import com.meghdut.news.dialog.CategoryListBottomSheetDialog
import com.meghdut.news.helper.Common
import com.meghdut.news.helper.PreferenceField
import com.meghdut.news.model.category.CategoryClass
import com.meghdut.news.model.category.CategoryData
import com.meghdut.news.model.comonclass.CommonClass
import com.meghdut.news.network.WebAPIClient
import com.pixplicity.easyprefs.library.Prefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ActSettingScreen : ActBase(), CatSettingAdapter.CatSettingClickListner,
    AdpNotificationCategory.NotificationCategoryClickListner {

    lateinit var categoryList: ArrayList<CategoryData>
    var dialogCategoryList: ArrayList<CategoryData> = arrayListOf()
    lateinit var notificationList: ArrayList<CategoryData>
    lateinit var catSettingAdapter: CatSettingAdapter
    lateinit var adpNotificationCategory: AdpNotificationCategory
    lateinit var actSettingScreenBinding: ActSettingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actSettingScreenBinding =
            DataBindingUtil.setContentView(this, R.layout.act_setting_screen)

        actSettingScreenBinding.fontSize = Common.getFontSize(activity!!)
        actSettingScreenBinding.nighDayColor = Common.GetNightDayColor(activity!!)
        actSettingScreenBinding.imgBack.setOnClickListener {
            finish()
        }

        actSettingScreenBinding.txtAppSize.setText(Common.getCacheSize(this))
        actSettingScreenBinding.imgDeleteCash.setOnClickListener({

            actSettingScreenBinding.txtAppSize.setText(Common.deleteCache(activity!!))
        })

        Log.d(
            "NIGHT_NODE",
            "NIGHT_NODE = " + Prefs.getString(PreferenceField.NIGHT_NODE, "").toString()
        )
        if (Prefs.getString(PreferenceField.NIGHT_NODE, "").equals("On")) {
            actSettingScreenBinding.redNightModOn.isChecked = true
            actSettingScreenBinding.redNightModOff.isChecked = false

        } else {
            actSettingScreenBinding.redNightModOn.isChecked = false
            actSettingScreenBinding.redNightModOff.isChecked = true
        }

        actSettingScreenBinding.linNightModOn.setOnClickListener {
            actSettingScreenBinding.redNightModOn.isChecked = true
            actSettingScreenBinding.redNightModOff.isChecked = false
            Prefs.putString(PreferenceField.NIGHT_NODE, "On")
            actSettingScreenBinding.nighDayColor = Common.GetNightDayColor(this)
            catSettingAdapter.notifyDataSetChanged()
            adpNotificationCategory.notifyDataSetChanged()

        }

        actSettingScreenBinding.linNightModOff.setOnClickListener {
            actSettingScreenBinding.redNightModOn.isChecked = false
            actSettingScreenBinding.redNightModOff.isChecked = true
            Prefs.putString(PreferenceField.NIGHT_NODE, "Off")
            actSettingScreenBinding.nighDayColor = Common.GetNightDayColor(this)
            catSettingAdapter.notifyDataSetChanged()
            adpNotificationCategory.notifyDataSetChanged()
        }

        if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Regular")) {
            actSettingScreenBinding.radReguler.isChecked = true
        } else if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Small")) {
            actSettingScreenBinding.radSmall.isChecked = true
        } else if (Prefs.getString(PreferenceField.FONT_TYPE, "Regular").equals("Big")) {
            actSettingScreenBinding.radBig.isChecked = true
        }

        actSettingScreenBinding.linSmallFont.setOnClickListener({

            if (!Prefs.getString(PreferenceField.FONT_TYPE, "").equals("Small")) {
                actSettingScreenBinding.radSmall.isChecked = true
                actSettingScreenBinding.radReguler.isChecked = false
                actSettingScreenBinding.radBig.isChecked = false

                Prefs.putString(
                    PreferenceField.FONT_TYPE, "Small"
                )

                actSettingScreenBinding.fontSize = Common.getFontSize(activity!!)
                catSettingAdapter.notifyDataSetChanged()
                adpNotificationCategory.notifyDataSetChanged()
            }
        })

        actSettingScreenBinding.linRegFont.setOnClickListener({
            if (!Prefs.getString(PreferenceField.FONT_TYPE, "").equals("Regular")) {
                actSettingScreenBinding.radSmall.isChecked = false
                actSettingScreenBinding.radReguler.isChecked = true
                actSettingScreenBinding.radBig.isChecked = false
                Prefs.putString(
                    PreferenceField.FONT_TYPE, "Regular"
                )

                actSettingScreenBinding.fontSize = Common.getFontSize(activity!!)
                catSettingAdapter.notifyDataSetChanged()
                adpNotificationCategory.notifyDataSetChanged()
            }
        })

        actSettingScreenBinding.linBigFont.setOnClickListener({
            if (!Prefs.getString(PreferenceField.FONT_TYPE, "").equals("Big")) {
                actSettingScreenBinding.radSmall.isChecked = false
                actSettingScreenBinding.radReguler.isChecked = false
                actSettingScreenBinding.radBig.isChecked = true
                Prefs.putString(
                    PreferenceField.FONT_TYPE, "Big"
                )
                actSettingScreenBinding.fontSize = Common.getFontSize(activity!!)
                catSettingAdapter.notifyDataSetChanged()
                adpNotificationCategory.notifyDataSetChanged()
            }
        })

        categoryList = ArrayList()
        catSettingAdapter =
            CatSettingAdapter(this, categoryList)
        catSettingAdapter.setCatSettingClickListner(this)
        actSettingScreenBinding.recCategorySetting.adapter = catSettingAdapter

        notificationList = ArrayList()
        adpNotificationCategory = AdpNotificationCategory(activity!!, notificationList)
        adpNotificationCategory.setNotificationCategoryClickListner(this)
        actSettingScreenBinding.recNotificationCat.adapter = adpNotificationCategory

        actSettingScreenBinding.txtViewMore.setOnClickListener {
            val categoryListBottomSheetDialog: CategoryListBottomSheetDialog =
                CategoryListBottomSheetDialog(dialogCategoryList)
            categoryListBottomSheetDialog.show(supportFragmentManager, "category dialog")
        }

        actSettingScreenBinding.txtSaveNotification.setOnClickListener {
            var selectedNotification: String = ""
            for (selNot in notificationList) {
                if (selNot.notification.equals("yes")) {
                    if (selectedNotification.equals(""))
                        selectedNotification = selNot.id
                    else
                        selectedNotification = selectedNotification + "," + selNot.id
                }
            }
            AddSelectedNotificationTime(selectedNotification, "", "")
        }

        actSettingScreenBinding.txtLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.are_you_sure_logout)
            builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, which ->
                dialogInterface.cancel()
                LogoutApplication()
            }
            //performing cancel action
            builder.setNeutralButton(R.string.no) { dialogInterface, which ->
                dialogInterface.cancel()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        actSettingScreenBinding.swiNotificationTime.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, boolean: Boolean) {
                if (boolean) {
                    actSettingScreenBinding.linNotificationTime.visibility = View.VISIBLE
                } else {
                    var selectedNotification: String = ""
                    for (selNot in notificationList) {
                        if (selNot.notification.equals("yes")) {
                            if (selectedNotification.equals(""))
                                selectedNotification = selNot.id
                            else
                                selectedNotification = selectedNotification + "," + selNot.id
                        }
                    }
                    Prefs.putString(PreferenceField.NOTIFICATION_STATUS, "no")
                    AddSelectedNotificationTime(
                        selectedNotification, "", ""
                    )
                    actSettingScreenBinding.linNotificationTime.visibility = View.GONE
                }
            }
        })

        if (Prefs.getString(PreferenceField.NOTIFICATION_STATUS, "no").equals("yes")) {
            actSettingScreenBinding.swiNotificationTime.isChecked = true
            actSettingScreenBinding.edtStartTime.setText(
                Prefs.getString(
                    PreferenceField.NOTIFICATION_START_TIME,
                    ""
                )
            )
            actSettingScreenBinding.edtEndTime.setText(
                Prefs.getString(
                    PreferenceField.NOTIFICATION_END_TIME,
                    ""
                )
            )
        }

        actSettingScreenBinding.edtStartTime.setOnClickListener {
            Common.OpenTimePicker(activity!!, actSettingScreenBinding.edtStartTime)
        }

        actSettingScreenBinding.edtEndTime.setOnClickListener {
            Common.OpenTimePicker(activity!!, actSettingScreenBinding.edtEndTime)
        }

        actSettingScreenBinding.txtTimeSubmit.setOnClickListener {
            if (actSettingScreenBinding.edtStartTime.text.toString().length == 0) {
                Common.ShowToast(activity!!, getString(R.string.please_select_start_time))
                return@setOnClickListener
            } else if (actSettingScreenBinding.edtEndTime.text.toString().length == 0) {
                Common.ShowToast(activity!!, getString(R.string.please_select_start_time))
                return@setOnClickListener
            }

            Prefs.putString(PreferenceField.NOTIFICATION_STATUS, "yes")
            Prefs.putString(
                PreferenceField.NOTIFICATION_START_TIME,
                actSettingScreenBinding.edtStartTime.text.toString()
            )
            Prefs.putString(
                PreferenceField.NOTIFICATION_END_TIME,
                actSettingScreenBinding.edtEndTime.text.toString()
            )
            var selectedNotification: String = ""
            for (selNot in notificationList) {
                if (selNot.notification.equals("yes")) {
                    if (selectedNotification.equals(""))
                        selectedNotification = selNot.id
                    else
                        selectedNotification = selectedNotification + "," + selNot.id
                }
            }

            AddSelectedNotificationTime(
                selectedNotification, actSettingScreenBinding.edtStartTime.text.toString(),
                actSettingScreenBinding.edtEndTime.text.toString()
            )
        }

        actSettingScreenBinding.txtFavoriteNews.setOnClickListener {
            val fi = Intent(activity!!, ActFavoriteNews::class.java)
            startActivity(fi)
        }

        Handler().postDelayed({
            getCategoryList()
        }, 100)

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
                                for (cat in 0..list.size - 1) {
                                    val categoryData: CategoryData = list.get(cat)
                                    if (cat < 6)
                                        categoryList.add(categoryData)

                                    if (categoryData.favorite.equals("yes")) {
                                        notificationList.add(categoryData)
                                        actSettingScreenBinding.txtSaveNotification.visibility =
                                            View.VISIBLE
                                    }
                                }
                                dialogCategoryList.addAll(list)

                                catSettingAdapter.notifyDataSetChanged()
                                adpNotificationCategory.notifyDataSetChanged()
                                actSettingScreenBinding.txtViewMore.visibility = View.VISIBLE
                            } ?: let {
                                if (code == 0) {
                                    Common.ShowToast(activity!!, message)
                                } else {
                                    UnauthoriseUser()
                                }
                            }
                        }
                    }
                })
    }

    fun AddSelectedCategory(selectedCategory: String) {
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
                                categoryList.clear()
                                notificationList.clear()
                                getCategoryList()

                            } ?: let {
                                if (code == 0) {
                                    Common.ShowToast(activity!!, message)
                                } else {
                                    UnauthoriseUser()
                                }
                            }
                        }
                    }
                })
    }

    /*Add Selected Notification*/
    private fun AddSelectedNotificationTime(
        selectedNot: String,
        startTime: String,
        endTime: String
    ) {
        Log.d("selectedCategory", "selectedCategory = " + selectedNot)
        showLoader()

        WebAPIClient.getInstance(this)
            .checkInternet()
            ?.AddSelectedNotification(selectedNot, startTime, endTime,
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
                                Common.ShowToast(activity!!, message)
                            } ?: let {
                                if (code == 0) {
                                    Prefs.putString(PreferenceField.NOTIFICATION_STATUS, "no")
                                    Prefs.putString(PreferenceField.NOTIFICATION_START_TIME, "")
                                    Prefs.putString(PreferenceField.NOTIFICATION_END_TIME, "")
                                    Common.ShowToast(activity!!, message)
                                } else {
                                    UnauthoriseUser()
                                }
                            }
                        }
                    }
                })
    }

    override fun selectFavoriteCategory(position: Int) {

    }

    override fun SelectNotification(position: Int) {
        val categoryData: CategoryData = notificationList.get(position)
        if (categoryData.notification.equals("no"))
            categoryData.notification = "yes"
        else
            categoryData.notification = "no"
        notificationList.set(position, categoryData)
        adpNotificationCategory.notifyItemChanged(position)
    }

}
