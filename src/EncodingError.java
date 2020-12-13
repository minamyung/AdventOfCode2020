import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EncodingError {
private ArrayList<Double> numbers;
private final int PREAMBLE_LENGTH = 25;
private double intruderNumber;

    public static void main(String[] args) throws IOException {
        EncodingError enc = new EncodingError();
        enc.fileReader();
        enc.intruderNumber = enc.findIntruder();
        ArrayList<Double> weaknessSequence = enc.findWeaknessSequence();
        double result = enc.findResult(weaknessSequence);
        System.out.printf("%.0f",result);

    }

    public void fileReader() throws IOException {
        ArrayList<Double> numbers = new ArrayList<>();
        FileReader fr = new FileReader("inputs/EncodingErrorInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                numbers.add(Double.parseDouble(line));
            }
        } finally {
            br.close();
        }
        this.numbers = numbers;
    }

    public ArrayList<Double> calcPossibleSums(int index) {
        ArrayList<Double> possibleSums = new ArrayList<>();

            for(int j = 0; j<PREAMBLE_LENGTH; j++) {
                for(int k = 0; k<PREAMBLE_LENGTH; k++) {
                    double x = this.numbers.get(j+index-PREAMBLE_LENGTH);
                    double y = this.numbers.get(k+index-PREAMBLE_LENGTH);
                    if (x!=y && !possibleSums.contains(x+y)) {
                        possibleSums.add(x+y);

                    }
                }
            }

        return possibleSums;
    }

    public ArrayList<Double> newCalcPossibleSums(int index) {
        ArrayList<Double> weaknessSequence = new ArrayList<>();

        for(int j = 0; j<PREAMBLE_LENGTH+1; j++) {
            double x = this.numbers.get(j+index);
            double y = this.numbers.get(j+1+index);
            if (x!=y && x+y == this.intruderNumber) {
                weaknessSequence.add(x);
                System.out.println(x);
            }

        }

        return weaknessSequence;
    }

    public double findIntruder() {
        double intruder = 0;
        for(int i=this.PREAMBLE_LENGTH; i<this.numbers.size(); i++) {
            ArrayList<Double> possibleSums = calcPossibleSums(i);
            double currentNumber = this.numbers.get(i);
            if(!possibleSums.contains(currentNumber)) {
                intruder = currentNumber;
                break;
            }
        }

        return intruder;
    }

    public double findMin(ArrayList<Double> weaknessSequence) {
        double min = weaknessSequence.get(0);

        for(double d : weaknessSequence) {
            if (d < min) min = d;
        }

        return min;
    }

    public double findMax(ArrayList<Double> weaknessSequence) {
        double max = weaknessSequence.get(0);

        for(double d : weaknessSequence) {
            if (d > max) max = d;
        }

        return max;
    }

    public ArrayList<Double> findWeaknessSequence() {
        double weakness = 0;

        for(int i=0; i<this.numbers.size(); i++) {
            ArrayList<Double> weaknessSequence = new ArrayList<>();
            weaknessSequence.add(this.numbers.get(i));
            double sum = this.numbers.get(i);
            int inc = 1;
            while(sum<this.intruderNumber){
                sum += this.numbers.get(i+inc);
                weaknessSequence.add(this.numbers.get(i+inc));
                inc++;
                if(sum==this.intruderNumber && weaknessSequence.size()>=2) {
                    return weaknessSequence;
                }
            }
            /*double x = this.numbers.get(i-1);
            System.out.println(x);
            double y = this.numbers.get(i);
            System.out.println(y);
            if (x!=y && x+y == this.intruderNumber) {
                weaknessSequence.add(x);
                System.out.println(x);
            }

            if(weaknessSequence.size()>=2) {
                double min = findMin(weaknessSequence);
                double max = findMax(weaknessSequence);
                weakness = min + max;
            }*/
        }

        return new ArrayList<>();
    }

    public double findResult(ArrayList<Double> weaknessSequence){
        double result = 0;

        double min = findMin(weaknessSequence);
        double max = findMax(weaknessSequence);
        result = min + max;

        return result;
    }
}
