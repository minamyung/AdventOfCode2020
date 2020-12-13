import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CustomCustoms {

    public static void main(String[] args) throws IOException {
        CustomCustoms cc = new CustomCustoms();
        ArrayList<String> questionsList = cc.fileReader();
        ArrayList<Integer> numList = cc.newCountQuestions(questionsList);
        double result = cc.calcSum(numList);
        System.out.println(result);
    }

    public ArrayList<String> fileReader() throws IOException {
        ArrayList<String> questionsList = new ArrayList<>();
        String questions = null;
        File f = new File("inputs/CustomCustomsInput.txt");
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String newLine = sc.nextLine();
            if (!newLine.isBlank() && questions == null) {
                questions = newLine;
            } else if (!newLine.isBlank() && questions != null) {
                questions = questions + "/" + newLine;
            } else {
                questionsList.add(questions);
                questions = null;
            }
        }
        return questionsList;
    }

    public ArrayList<Integer> countQuestions(ArrayList<String> questionsList) {
        ArrayList<Integer> numList = new ArrayList<>();
        for(String questions : questionsList) {
            char[] questionsChars = questions.toCharArray();
            ArrayList<Character> presentChars = new ArrayList<>();
            String newQuestions = null;
            for(char c : questionsChars) {
                if (!presentChars.contains(c) && c!='/') {
                    presentChars.add(c);
                    if(newQuestions==null) {
                        newQuestions = String.valueOf(c);
                    } else {
                        newQuestions = newQuestions + c;
                    }
                }
            }
            numList.add(newQuestions.length());
        }
        return numList;
    }

    public double calcSum(ArrayList<Integer> numList) {
        double sum = 0;
        for(int num : numList) {
            sum = sum + num;
        }
        return sum;
    }

    public ArrayList<Integer> newCountQuestions(ArrayList<String> questionsList){
        ArrayList<Integer> numList = new ArrayList<>();

        for (String questions : questionsList) {
            String[] questionsSubstrings = questions.split("/");
            int groupSize = questionsSubstrings.length;

            char[] questionsChars = questions.toCharArray();
            HashMap<Character, Integer> occurrenceHashMap = new HashMap<>();

            for(char c : questionsChars) {
                if (!occurrenceHashMap.containsKey(c) && c!='/') {
                    occurrenceHashMap.put(c, 1);
                } else if (occurrenceHashMap.containsKey(c) && c!='/') {
                    int previousCount = occurrenceHashMap.get(c);
                    occurrenceHashMap.replace(c, previousCount + 1);
                }
            }

            int questionsCount = 0;

            for (int i : occurrenceHashMap.values()) {
                if (i == groupSize) {
                    questionsCount += 1;
                }
            }
            numList.add(questionsCount);
        }
        return numList;
    }
}
