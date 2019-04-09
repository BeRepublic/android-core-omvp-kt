package com.omvp.app.interceptor.operation

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.omvp.app.BuildConfig
import com.raxdenstudios.commons.util.StringUtils
import com.raxdenstudios.square.interceptor.ActivitySimpleInterceptor
import java.util.*

/**
 * Created by Angel on 10/08/2017.
 */

class OperationBroadcastActivityInterceptor(activity: FragmentActivity) : ActivitySimpleInterceptor(activity), OperationBroadcastInterceptor {

    private val mOperationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(OPERATION, -1)) {
                OPERATION_ACTIVITY_FINISH -> if (intent.hasExtra(ACTIVITY_NAME)) {
                    var activitiesToFinish: MutableList<String> = ArrayList()
                    val activityName = intent.getStringExtra(ACTIVITY_NAME)
                    if (activityName.contains(",")) {
                        activitiesToFinish = Arrays.asList(*activityName.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                    } else {
                        activitiesToFinish.add(activityName)
                    }
                    for (activityToFinish in activitiesToFinish) {
                        if (activityToFinish == activity.javaClass.name) {
                            activity.finish()
                        }
                    }
                }
                OPERATION_ACTIVITY_FINISH_ALL -> activity.finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerOperationReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterOperationReceiver()
    }

    private fun registerOperationReceiver() {
        val intentFilter = IntentFilter(OPERATION_ACTION)
        activity.registerReceiver(mOperationReceiver, intentFilter)
    }

    private fun unregisterOperationReceiver() {
        activity.unregisterReceiver(mOperationReceiver)
    }

    companion object {

        private const val OPERATION_ACTION = BuildConfig.APPLICATION_ID + ".OPERATION_ACTION"
        private const val OPERATION = "OPERATION"
        private const val ACTIVITY_NAME = "ACTIVITY_NAME"

        private const val OPERATION_ACTIVITY_FINISH = 1
        private const val OPERATION_ACTIVITY_FINISH_ALL = 2

        fun finishActivity(activity: Activity, className: Class<Activity>) {
            val intent = Intent(OPERATION_ACTION)
            intent.putExtra(OPERATION, OPERATION_ACTIVITY_FINISH)
            intent.putExtra(ACTIVITY_NAME, className.simpleName)
            activity.sendBroadcast(intent)
        }

        fun finishActivity(activity: Activity, classNameList: List<Class<Activity>>) {
            val intent = Intent(OPERATION_ACTION)
            intent.putExtra(OPERATION, OPERATION_ACTIVITY_FINISH)
            intent.putExtra(ACTIVITY_NAME, StringUtils.join(classNameList, ","))
            activity.sendBroadcast(intent)
        }

        fun finishAllActivities(activity: Activity) {
            val intent = Intent(OPERATION_ACTION)
            intent.putExtra(OPERATION, OPERATION_ACTIVITY_FINISH_ALL)
            activity.sendBroadcast(intent)
        }
    }

}