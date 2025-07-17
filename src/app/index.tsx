import React, { useState } from 'react';
import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';

interface ButtonProps {
  text: string;
  color?: 'blue' | 'green' | 'red';
}

const SimpleButton: React.FC<ButtonProps> = ({ text, color = 'blue' }) => {
  const [count, setCount] = useState(0);

  const handleClick = () => {
    setCount(count + 1);
  };

  const getButtonColor = () => {
    switch (color) {
      case 'blue':
        return '#3B82F6';
      case 'green':
        return '#10B981';
      case 'red':
        return '#EF4444';
      default:
        return '#3B82F6';
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Contador Simples</Text>
      
      <Text style={styles.counter}>
        Você clicou {count} {count === 1 ? 'vez' : 'vezes'}
      </Text>
      
      <TouchableOpacity
        style={[styles.button, { backgroundColor: getButtonColor() }]}
        onPress={handleClick}
      >
        <Text style={styles.buttonText}>{text}</Text>
      </TouchableOpacity>
      
      <View style={styles.infoBox}>
        <Text style={styles.infoTitle}>
          Este é um componente React Native simples em TSX que demonstra:
        </Text>
        <Text style={styles.infoText}>• Uso de TypeScript com interfaces</Text>
        <Text style={styles.infoText}>• Estado local com useState</Text>
        <Text style={styles.infoText}>• Props tipadas</Text>
        <Text style={styles.infoText}>• StyleSheet para estilização</Text>
        <Text style={styles.infoText}>• TouchableOpacity para interação</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#F9FAFB',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#1F2937',
    marginBottom: 20,
  },
  counter: {
    fontSize: 18,
    color: '#6B7280',
    marginBottom: 30,
  },
  button: {
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 8,
    marginBottom: 30,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
    elevation: 5,
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: '600',
    textAlign: 'center',
  },
  infoBox: {
    backgroundColor: '#F3F4F6',
    padding: 16,
    borderRadius: 8,
    width: '100%',
  },
  infoTitle: {
    fontSize: 14,
    color: '#374151',
    marginBottom: 8,
    fontWeight: '500',
  },
  infoText: {
    fontSize: 12,
    color: '#6B7280',
    marginBottom: 4,
  },
});

export default function App() {
  return (
    <View style={{ flex: 1 }}>
      <SimpleButton text="Clique aqui!" color="blue" />
    </View>
  );
}