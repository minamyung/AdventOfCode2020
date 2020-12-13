/** References:
 *  https://howtodoinjava.com/java/io/java-filereader/
 *
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReportRepair {

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> expenseReport = FileReader();
        System.out.println(getNumber(expenseReport));
    }

    public static ArrayList<Integer> FileReader() throws IOException {
        ArrayList<Integer> expenseReport = new ArrayList<>();
        FileReader fr = new FileReader("inputs/ReportRepairInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                expenseReport.add(Integer.parseInt(line));
            }
        } finally {
            br.close();
        }
        return expenseReport;
    }

    public static int getNumber(ArrayList<Integer> expenseReport) {
        int result = 0;
        for (int i = 0; i < expenseReport.size(); i++) {
            int num1 = expenseReport.get(i);
            for (int num2 : expenseReport) {
                for (int num3 : expenseReport) {
                    if (num1 + num2 + num3 == 2020) {
                        System.out.println(num1);
                        System.out.println(num2);
                        System.out.println(num3);
                        System.out.println(num1 + num2 + num3);
                        result = num1 * num2 * num3;
                    }
                }
            }
        }
        return result;
    }
}
