import Foundation
import UIKit

public class BatteryInfo {
  
  private static var batteryState: String {
    get {
      switch UIDevice.current.batteryState {
      case .charging:
        return "charging"
      case .full:
        return "full"
      case .unknown:
        return "unknown"
      case .unplugged:
        return "unplugged"
      default:
        return "unknown"
      }
    }
  }
  
  
  static func getBatteryState()->[AnyHashable : Any] {
    var payload = [AnyHashable : Any](minimumCapacity: 2)
    
    payload[Constants.BATTERY_STATE_KEY] = self.batteryState
    payload[Constants.BATTERY_LEVEL_KEY] = UIDevice.current.batteryLevel
    
    return payload
  }
  
}
