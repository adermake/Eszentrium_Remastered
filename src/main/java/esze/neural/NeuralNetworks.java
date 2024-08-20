package esze.neural;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NeuralNetworks {

	public static NeuralNetwork drawNet;

	public static void loadNeuralNetworks() {
		drawNet = loadNet("SDNettext");
	}

	public static NeuralNetwork loadNet(String name) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(name + ".txt"));

			ArrayList<String> lines = new ArrayList<String>();
			String liner;

			while ((liner = file.readLine()) != null) {
				lines.add(liner);
			}
			file.close();
			
			return new NeuralNetwork(lines);
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
