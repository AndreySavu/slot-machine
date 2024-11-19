package utills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CsvReader {
    public static int[][] loadFromCsv(String resourcePath, int rows, int cols) throws IOException {
        int[][] data = new int[rows][cols];
        try (InputStream inputStream = CsvReader.class.getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < rows) {
                String[] values = line.split(";");
                for (int col = 0; col < cols; col++) {
                    data[row][col] = Integer.parseInt(values[col + 1]);
                }
                row++;
            }
        }
        return data;
    }
}
