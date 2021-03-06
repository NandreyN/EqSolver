package NandreyN;


import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try {
            Equation eq = new Equation(MatrixReader.read("Set.txt"));
            double[] result = eq.solve();
            System.out.println("X = " + Arrays.toString(result));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (EqSetException e) {
            System.out.println(e.getMessage());
        }
    }
}
