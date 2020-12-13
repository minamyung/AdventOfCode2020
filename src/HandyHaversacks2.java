import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandyHaversacks2 {
    private static ArrayList<String> rules;
    private final String ROOT_BAG_FORMAT = "(\\w+\\s\\w+)";
    private final String NESTED_BAG_FORMAT = "(\\d+)\\s(\\w+\\s\\w+)";

    public static void main(String[] args) throws IOException {
        HandyHaversacks2 hvsk = new HandyHaversacks2();
        hvsk.fileReader();
        int result = hvsk.countNestedBags("shiny gold", 1);

        System.out.println(result);
    }

    public void fileReader() throws IOException {
        ArrayList<String> rules = new ArrayList<>();
        FileReader fr = new FileReader("inputs/HandyHaversacksInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                rules.add(line);
            }
        } finally {
            br.close();
        }
        this.rules = rules;
    }

    public HashMap<String, Integer> findNestedBags(String rootBagType) {
        HashMap<String, Integer> nestedBags = new HashMap<>();

        Pattern pRoot = Pattern.compile(ROOT_BAG_FORMAT);
        Pattern pNested = Pattern.compile(NESTED_BAG_FORMAT);

        for(String rule : rules) {
            if(!rule.isBlank()) {
                Matcher mRoot = pRoot.matcher(rule);
                Matcher mNested = pNested.matcher(rule);

                if (mRoot.find() && mRoot.group(1).equals(rootBagType)) {
                    //String bagType = mRoot.group(1);

                    while(mNested.find()) {
                        int nestedNum = Integer.parseInt(mNested.group(1));
                        String nestedType = mNested.group(2);

                        nestedBags.put(nestedType, nestedNum);
                    }
                }
            }
        }

        return nestedBags;
    }

    public int countNestedBags(String bagType, int number) {
        int count = 0;
        HashMap<String, Integer> nestedBags = findNestedBags(bagType);

        if (nestedBags.isEmpty()) {
            count = 0;
        } else {
            for (Map.Entry<String, Integer> bag : nestedBags.entrySet()) {
                count = count + bag.getValue() * number;
                count = count + countNestedBags(bag.getKey(), bag.getValue() * number);
            }
        }
        return count;
    }

}
