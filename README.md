<h1 align="center">react-native-battery-monitor</h1>
<p align="center">Battery Monitor for React Native.</p>

[![npm version](https://badge.fury.io/js/react-native-battery-monitor.svg)](http://badge.fury.io/js/react-native-battery-monitor)

## 🚀 Getting Started

### Prerequisites

- react-native:
  - supported versions "<strong>&gt;= 0.60.0</strong>"

### Installation

<table>
<td>
<details style="border: 1px solid; border-radius: 5px; padding: 5px">
  <summary>with react-native "<strong>&gt;=0.60.0</strong>"</summary>

### 1. Install latest version

```
# Using npm
npm install --save react-native-battery-monitor

# Using Yarn
yarn add react-native-battery-monitor
```

### 2. Install pods

`$ cd ios && pod install && cd ..`

</details>
</td>
</table>

## 🍰 Example

```jsx
import BatteryMonitor from 'react-native-battery-monitor';

const ubsubscribe = BatteryMonitor.onStateChange((status) => {
  console.log(status.state); //  "unknown" | "unplugged" | "charging" | "full"
  console.log(status.level); // 0.80000001192092
});

const batteryState = await BatteryMonitor.getBatteryState();

// unsubscribe
ubsubscribe();
```
