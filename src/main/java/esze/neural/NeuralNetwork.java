package esze.neural;

import java.util.ArrayList;

public class NeuralNetwork {

    ArrayList<float[][]> weights = new ArrayList<float[][]>();
    ArrayList<float[]> bias = new ArrayList<float[]>();

    public NeuralNetwork(ArrayList<String> lines) {
        int lineIndex = 0;

        // Row Count
        while (lineIndex < lines.size()) {
            String rline = lines.get(lineIndex);
            int rowCount = Integer.parseInt(rline);
            lineIndex++;

            int rowIndex = 0;

            float[][] weight = new float[rowCount][];
            // ---------
            while (true) {
                String line = lines.get(lineIndex);
                if (line.equals("")) {
                    lineIndex++;
                    break;
                }
                String[] doubleString = line.split(" ");
                float[] doubles = new float[doubleString.length];
                int index = 0;
                for (String dString : doubleString) {
                    doubles[index] = Float.parseFloat(dString);
                    index++;
                }
                weight[rowIndex] = doubles;
                lineIndex++;
                rowIndex++;
            }

            String line = lines.get(lineIndex);
            String[] doubleString = line.split(" ");
            float[] doubles = new float[doubleString.length];
            int index = 0;
            for (String dString : doubleString) {
                doubles[index] = Float.parseFloat(dString);
                index++;
            }
            bias.add(doubles);
            lineIndex++;

        }
    }

}
