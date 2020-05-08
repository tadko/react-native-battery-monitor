package tadko.rnbatterymonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter
import java.util.*
import javax.annotation.Nullable


internal class RNBatteryMonitor(var context: ReactApplicationContext) : LifecycleEventListener {

    private var batteryStatus: Intent? = null

    @Nullable
    private var batteryStateReceiver: BatteryStateReceiver? = null

    init {
        registerReceiver()
    }

    override fun onHostResume() {
        registerReceiver()
    }

    override fun onHostPause() {
        unregisterReceiver()
    }

    override fun onHostDestroy() {
        unregisterReceiver()
    }

    fun getBatteryState(promise: Promise) {
        batteryStatus?.also { status ->
            val state = getBatteryStateForJS(status)
            promise.resolve(state)
        } ?: run {
            promise.reject(Exception("Battery manager is not active"))
        }
    }

    fun notifyBatteryStateChanged(intent: Intent) {
        batteryStatus = intent

        if (this.context.hasActiveCatalystInstance()) {
            val params = getBatteryStateForJS(intent)
            try {
                this.context
                        .getJSModule(RCTDeviceEventEmitter::class.java)
                        .emit(Constants.EVENT_NAME, params)
            } catch (e: Exception) {
                Log.e(Constants.APP_NAME, "notifyBatteryStateChanged called before bundle loaded")
            }
        }
    }

    private fun getBatteryStateForJS(intent: Intent): WritableNativeMap {
        val batteryLevel = getBatteryLevelFromIntent(intent)
        val batteryState = getBatteryStateFromIntent(intent)
        val params = WritableNativeMap()

        params.putString(Constants.BATTERY_STATE_KEY, batteryState)
        params.putDouble(Constants.BATTERY_LEVEL_KEY, batteryLevel.toDouble())
        return params
    }

    private fun getBatteryLevelFromIntent(intent: Intent): Float {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        return level / scale.toFloat()
    }

    private fun getBatteryStateFromIntent(intent: Intent): String {
        var isPlugged = this.getIsPlugged(intent)
        var status = this.getStatus(intent)

        var batteryState = "unknown"
        if (!isPlugged) {
            batteryState = "unplugged"
        } else if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            batteryState = "charging"
        } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
            batteryState = "full"
        }

        return batteryState
    }

    private fun getStatus(intent: Intent): Int {
        return intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
    }

    private fun getIsPlugged(intent: Intent): Boolean {
        val status = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        return status != 0
    }

    private fun registerReceiver() {
        if (batteryStateReceiver != null) {
            return
        }
        batteryStateReceiver = BatteryStateReceiver()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        batteryStatus = this.context.registerReceiver(batteryStateReceiver, filter)
    }

    private fun unregisterReceiver() {
        if (batteryStateReceiver == null) {
            return
        }
        this.context.unregisterReceiver(batteryStateReceiver)
        batteryStateReceiver = null
        batteryStatus = null
    }


     inner class BatteryStateReceiver : BroadcastReceiver() {
         override fun onReceive(context: Context, intent: Intent) {
             notifyBatteryStateChanged(intent)
         }
     }
}