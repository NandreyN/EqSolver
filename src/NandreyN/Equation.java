package NandreyN;

import java.util.ArrayList;
import java.util.Collections;

public class Equation {
    private ArrayList<ArrayList<Double>> matrix;
    private int activeRow;
    private int activeColumn;
    private int n;

    public Equation(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;
        activeColumn = 0;
        activeRow = 0;
        n = matrix.size() - 1;
    }

    public double[] solve() throws EqSetException {
        int i = 0;
        while (matrix.get(i).get(0) == 0 && i < n) {
            swapLines(i, i + 1);
            i++;
        }
        i = (i == 0) ? 0 : i--;
        if (matrix.get(i).get(0) == 0) {
            throw new EqSetException("Equation set has no answers");
        }

        forward();
        if (!hasAnswer())
            throw new EqSetException("Equation set has no answers");

        if (!isAnswerUnique())
            throw new EqSetException("Equation set has more than one answer");

        reverse();

        diagFix();

        return extractAnswerFromDiagMatrix();
    }

    private double[] extractAnswerFromDiagMatrix() {
        double[] ans = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            ans[i] = matrix.get(i).get(n + 1);
        }
        return ans;
    }

    private void forwardStep() {
        for (int j = activeRow + 1; j <= n; j++) {
            double k = -matrix.get(j).get(activeColumn) / matrix.get(activeRow).get(activeColumn);
            for (int i = 0; i <= n + 1; i++) {
                double newRatio = matrix.get(activeRow).get(i) * k + matrix.get(j).get(i);
                if (Math.abs(newRatio) <= 1e-5)
                    newRatio = 0.;
                matrix.get(j).set(i, newRatio);
            }
        }
        activeRow++;
        activeColumn++;
    }

    private void diagFix() {
        for (int i = 0; i <= n; i++) {
            double dg = matrix.get(i).get(i);
            matrix.get(i).set(i, (double) 1);
            matrix.get(i).set(n + 1, matrix.get(i).get(n + 1) / dg);
        }
    }

    private boolean hasAnswer() {
        double last = matrix.get(n).get(n);
        double b = matrix.get(n).get(n + 1);

        return (last == 0 && b != 0 || last == 0 && b == 0) ? false : true;
    }

    private boolean isAnswerUnique() {
        return (matrix.get(n).get(n + 1) != 0.) ? true : false;
    }

    private void reverseStep() {
        for (int j = activeRow - 1; j >= 0; j--) { // applyto
            double k = -matrix.get(j).get(activeColumn) / matrix.get(activeRow).get(activeColumn);
            double newRatio = matrix.get(activeRow).get(activeColumn) * k + matrix.get(j).get(activeColumn);
            if (Math.abs(newRatio) <= 1e-5)
                newRatio = 0.;
            matrix.get(j).set(activeColumn, newRatio);

            newRatio = matrix.get(activeRow).get(n + 1) * k + matrix.get(j).get(n + 1);
            if (Math.abs(newRatio) <= 1e-5)
                newRatio = 0.;
            matrix.get(j).set(n + 1, newRatio);
        }
        activeRow--;
        activeColumn--;
    }

    private void swapLines(int i, int j) {
        Collections.swap(matrix, i, j);
    }

    private void reverse() {
        while (activeRow > 0) {
            if (matrix.get(activeRow).get(activeColumn) != 0) {
                reverseStep();
            } else {

                int i = activeRow, j = activeColumn;
                while (matrix.get(i).get(j) == 0 && i > 0 && j > 0) {
                    swapLines(i, i - 1);
                    --i;
                    --j;
                }
            }
        }
    }

    public void print() {
        System.out.println();
        for (ArrayList<Double> aMatrix : matrix) {
            System.out.println(aMatrix);
        }
        System.out.println();
    }

    private void forward() {
        while (activeRow < n) {
            if (matrix.get(activeRow).get(activeColumn) != 0) {
                forwardStep();
            } else {

                int i = activeRow, j = activeColumn;
                while (matrix.get(i).get(j) == 0 && i < matrix.size() - 1 && j < matrix.size() - 1) {
                    swapLines(i, i + 1);
                    i++;
                    j++;
                }
                activeRow = i;
            }
        }
    }
}
