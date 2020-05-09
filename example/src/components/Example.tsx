import React, {useEffect, useState} from 'react';
import {Text} from 'react-native';
import BatteryMonitor, {BatteryState} from 'react-native-battery-monitor';

export const Example: React.FC = () => {
  const [state, setState] = useState<BatteryState>();

  useEffect(() => {
    const initialize = async () => {
      const initialState = await BatteryMonitor.getBatteryState();
      setState(initialState);
    };
    initialize();
  }, []);

  useEffect(() => {
    const unsubscribe = BatteryMonitor.onStateChange((status: BatteryState) => {
      setState(status);
    });
    return () => unsubscribe();
  }, []);

  return (
    <>
      <Text>[Battery status]</Text>
      <Text>state: {state?.state}</Text>
      <Text>level: {state && `${(state.level * 100).toFixed(2)}%`}</Text>
    </>
  );
};
