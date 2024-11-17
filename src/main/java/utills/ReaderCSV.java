package utills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderCSV {
    public static int[][] loadFromCsv(String resourcePath, int rows, int cols) throws IOException {
        int[][] data = new int[rows][cols];
        try (InputStream inputStream = ReaderCSV.class.getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Resource file not found: " + resourcePath);
            }
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < rows) {
                String[] values = line.split(";");
                for (int col = 0; col < cols; col++) {
                    data[row][col] = Integer.parseInt(values[col+1]);
                }
                row++;
            }
        }
        //for(int i =0;i<11;i++)
        //    System.out.println(data[i][0] + " " + data[i][1] + " " + data[i][2]);
        return data;
    }
}
