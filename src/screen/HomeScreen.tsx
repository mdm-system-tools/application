import type { NativeStackScreenProps } from "@react-navigation/native-stack";
import React from "react";
import { Button, RootTag, Text, View } from "react-native";
import type { RootStackParamList } from "./types";

type Props = NativeStackScreenProps<RootStackParamList, "Home">;

export default function HomeScreen(props: Props) {
	const { navigation } = props;

	return (
		<View style={{ flex: 1, alignItems: "center", justifyContent: "center" }}>
			<Text>Home Screen</Text>
			<Button
				title="Ir para Detalhes"
				onPress={() => navigation.navigate("Details")}
			/>
		</View>
	);
}
