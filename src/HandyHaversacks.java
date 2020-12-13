import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HandyHaversacks {
    public ArrayList<String> rules;

    public static void main(String[] args) throws IOException {
        HandyHaversacks hvsk = new HandyHaversacks();
        hvsk.fileReader();
        int result = hvsk.countNestedBags("shiny gold", 1);
        System.out.println(hvsk.rules.size());
        System.out.println(result);
    }

    public void fileReader() throws IOException {
        ArrayList<String> rules = new ArrayList<>();
        FileReader fr = new FileReader("inputs/testinput3.txt");
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

    public int findGoldBagContainers(ArrayList<String> rules) {
        ArrayList<String> containers = new ArrayList<>();

        int i = 0;
        while(i<10) {
            for(String rule : rules) {
                if (!rule.isEmpty()) {
                    String[] ruleSubstrings = rule.split(" contain");
                    String contains = ruleSubstrings[1];
                    // add all bags that directly contain shiny gold bags
                    String bagType = ruleSubstrings[0].split(" bags")[0];

                    if (contains.contains("shiny gold") && !containers.contains(bagType)) {
                        containers.add(bagType);
                    } else {
                        ArrayList<String> toAdd = new ArrayList<>();
                        for(String container : containers) {
                            if (contains.contains(container) && !containers.contains(bagType)) toAdd.add(bagType);
                        }
                        for(String item : toAdd) {
                            if(!containers.contains(item)) containers.add(item);
                        }
                    }
                }
            }
            i +=1;


        }
        int num = containers.size();
        return num;
    }

/*    public int findNestedBags(String bagType, int nOfBags) {
        for(String rule : this.rules) {
            if (!rule.isEmpty()) {
                String[] ruleSubstrings = rule.split(" contain ");
                String container = ruleSubstrings[0];
                String contained = ruleSubstrings[1];

                if (container.contains(bagType) && !contained.contains("no other bags")) {
                    String[] containedBags = contained.split(", ");
                    int subNumber = 0;
                    for (String bag : containedBags) {
                        String[] bagSubString = bag.split(" ", 2);
                        subNumber = Integer.parseInt(bagSubString[0]);
                        String containedBagType = bagSubString[1].split(" bags")[0];

                        int nested = findNestedBags(containedBagType, subNumber);
                        nOfBags = nested * subNumber;
                    }
                }
            }
        }
        return nOfBags;
    }*/

    public HashMap<String, Integer> findNestedBags(String bagType) {
        HashMap<String, Integer> nestedBags = new HashMap<>();

        for(String rule : rules) {
            if (!rule.isEmpty()) {
                String[] ruleSubstrings = rule.split(" contain ");
                String container = ruleSubstrings[0];
                String contained = ruleSubstrings[1];
                if(container.contains(bagType) && !contained.contains("no other bags")) {
                    String[] containedBags = contained.split(", ");
                    for (String bag : containedBags) {
                        String[] bagSubString = bag.split(" ", 2);
                        String nestedBagType = bagSubString[1].split(" bags")[0];
                        int subNumber = Integer.parseInt(bagSubString[0]);
                        nestedBags.put(nestedBagType, subNumber);
                    }
                }
            }
        }
        return nestedBags;
    }

    public int countNestedBags(String bagType, Integer number) {
        int count = 0;

        HashMap<String, Integer> nestedBags = findNestedBags(bagType);

        if (nestedBags.isEmpty()) {
            return 0;
        } else {
            for (Map.Entry<String, Integer> bag : nestedBags.entrySet()) {
                count += bag.getValue() * number + countNestedBags(bag.getKey(), bag.getValue());
            }
        }
        return count;
    }
}
