import {EmitterSubscription} from 'react-native';

export interface BatteryState {
  state: 'unknown' | 'unplugged' | 'charging' | 'full';
  level: number;
}

declare class BatteryMonitor {
  onStateChange(listener: (status: BatteryState) => void): EmitterSubscription;
  getBatteryState(): Promise<BatteryState>;
}

export const batteryMonitor: BatteryMonitor;
export default batteryMonitor;
