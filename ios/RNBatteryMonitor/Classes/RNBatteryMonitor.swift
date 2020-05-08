import Foundation
import HealthKit

@objc(RNBatteryMonitor)
class RNBatteryMonitor: RCTEventEmitter {
  
  override init() {
    super.init()
    UIDevice.current.isBatteryMonitoringEnabled = true
    NotificationCenter.default.addObserver(self, selector: #selector(batteryLevelDidChange), name: UIDevice.batteryLevelDidChangeNotification, object: nil)
    
    NotificationCenter.default.addObserver(self, selector: #selector(batteryStateDidChange), name: UIDevice.batteryStateDidChangeNotification, object: nil)
  }
  
  @objc func getBatteryState(_ resolve: RCTPromiseResolveBlock,
                             rejecter reject: RCTPromiseRejectBlock) {
    let batteryState = BatteryInfo.getBatteryState()
    resolve(batteryState)
  }
  
  @objc func batteryLevelDidChange(_ notification: Notification) {
    let status = BatteryInfo.getBatteryState()
    sendEvent(withName: Constants.BATTERY_CHANGE_EVENT, body: status)
  }
  
  @objc func batteryStateDidChange(_ notification: Notification) {
    let status = BatteryInfo.getBatteryState()
    sendEvent(withName: Constants.BATTERY_CHANGE_EVENT, body: status)
  }
  
  @objc override func constantsToExport() -> [AnyHashable: Any] {
    return ["BATTERY_CHANGE_EVENT": Constants.BATTERY_CHANGE_EVENT]
  }
  
  override func supportedEvents() -> [String]! {
    return [Constants.BATTERY_CHANGE_EVENT]
  }
  
  @objc override static func requiresMainQueueSetup() -> Bool {
    return true
  }
  
  deinit {
    NotificationCenter.default.removeObserver(self)
  }
}
