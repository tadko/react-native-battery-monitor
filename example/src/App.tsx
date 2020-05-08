import React from 'react';
import {StyleSheet, SafeAreaView, StatusBar} from 'react-native';
import {Example} from './components/Example';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

const App: React.FC = () => {
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView style={styles.container}>
        <Example />
      </SafeAreaView>
    </>
  );
};

export default App;
