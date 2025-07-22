import { createNativeStackNavigator } from "@react-navigation/native-stack";

// const HomeScreen = ({ navigation }) => {
//   return (
//     <Button
//       title="Go to Jane's profile"
//       onPress={() =>
//         navigation.navigate('Profile', { name: 'Jane' })
//       }
//     />
//   );
// };
// const ProfileScreen = ({ navigation, route }) => {
//   return <Text>This is {route.params.name}'s profile</Text>;
// }
// export default function App() {
//   return (
//     <NavigationContainer>
//       <Stack.Navigator initialRouteName='Home'>
//         <Stack.Screen name="Home" component={HomeScreen}/>
//         <Stack.Screen name="DetailsScreen" component={DetailsScreen} />
//       </Stack.Navigator>
//     </NavigationContainer>
//   )
// }
//
// const styles = StyleSheet.create({
//   container: {
//     flex: 1,
//     justifyContent: 'center',
//     alignItems: 'center',
//     padding: 20,
//     backgroundColor: '#F9FAFB',
//   },
//   title: {
//     fontSize: 24,
//     fontWeight: 'bold',
//     color: '#1F2937',
//     marginBottom: 20,
//   },
//   counter: {
//     fontSize: 18,
//     color: '#6B7280',
//     marginBottom: 30,
//   },
//   button: {
//     paddingHorizontal: 24,
//     paddingVertical: 12,
//     borderRadius: 8,
//     marginBottom: 30,
//     shadowColor: '#000',
//     shadowOffset: {
//       width: 0,
//       height: 2,
//     },
//     shadowOpacity: 0.25,
//     shadowRadius: 3.84,
//     elevation: 5,
//   },
//   buttonText: {
//     color: 'white',
//     fontSize: 16,
//     fontWeight: '600',
//     textAlign: 'center',
//   },
//   infoBox: {
//     backgroundColor: '#F3F4F6',
//     padding: 16,
//     borderRadius: 8,
//     width: '100%',
//   },
//   infoTitle: {
//     fontSize: 14,
//     color: '#374151',
//     marginBottom: 8,
//     fontWeight: '500',
//   },
//   infoText: {
//     fontSize: 12,
//     color: '#6B7280',
//     marginBottom: 4,
//   },
// })
