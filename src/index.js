import {NativeModules, NativeEventEmitter} from 'react-native';

const {RNBatteryMonitor} = NativeModules;

const batteryEventEmitter = new NativeEventEmitter(RNBatteryMonitor);

class BatteryMonitor {
  onStateChange(listener) {
    const subscription = batteryEventEmitter.addListener(
      RNBatteryMonitor.BATTERY_CHANGE_EVENT,
      listener,
    );
    return () => subscription.remove();
  }

  getBatteryState() {
    return RNBatteryMonitor.getBatteryState();
  }
}

export default new BatteryMonitor();
