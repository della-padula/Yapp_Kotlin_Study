package dev.terrylabs.gitdoit

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat

class Util {
    companion object {
        @JvmStatic
        fun setIconTinkDark(activity: Activity) {
            val window: Window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(activity, R.color.colorWhite)

                val decor = activity.window.decorView
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 마시멜로우 버전보다 낮으면 상단바 색상을 어두운 색상으로 변경하여 아이콘이 보이도록 조정
                window.statusBarColor = ContextCompat.getColor(activity, R.color.colorBlack)
            }
        }
    }
}