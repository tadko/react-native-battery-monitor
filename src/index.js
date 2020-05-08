import {NativeModules, NativeEventEmitter} from 'react-native';

const {RNBatteryMonitor} = NativeModules;

const batteryEventEmitter = new NativeEventEmitter(RNBatteryMonitor);

class BatteryMonitor {
  onStateChange(listener) {
    return batteryEventEmitter.addListener(RNBatteryMonitor.BATTERY_CHANGE_EVENT, listener);
  }

  getBatteryState() {
    return RNBatteryMonitor.getBatteryState();
  }
}

export default new BatteryMonitor();
