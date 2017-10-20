package NandreyN;

import java.util.ArrayList;

import java.io.*;

public class MatrixReader {


    public static  ArrayList<ArrayList<Double>> read(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || file.isDirectory())
        {
            throw new FileNotFoundException();
        }

        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int j = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(" ");
            data.add(new ArrayList<>());
            for (int i = 0; i< values.length;i++)
            {
                data.get(j).add(Double.parseDouble(values[i]));
            }
            j++;
        }

        return data;
    }

}
