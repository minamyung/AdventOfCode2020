import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AdapterArray {
    private ArrayList<Integer> adapters;
    private HashMap<Integer, Double> pathCounter;


    public static void main(String[] args) throws IOException {
        AdapterArray adp = new AdapterArray();
        adp.pathCounter = new HashMap<>();
        adp.fileReader();
        int result = adp.calcDifferences();
        double combinations = adp.calcCombinations();
        System.out.println(combinations);
    }

    public void fileReader() throws IOException {
        ArrayList<Integer> adapters = new ArrayList<>();
        FileReader fr = new FileReader("inputs/AdapterArrayInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                adapters.add(Integer.parseInt(line));
            }
        } finally {
            br.close();
        }
        Collections.sort(adapters); // Sort adapters from lowest to highest jolt
        adapters.add(0, 0); // Add charging outlet with jolt 0
        this.adapters = adapters;
    }

    public int calcDifferences() {
        int oneJoltCounter = 0;
        int threeJoltCounter = 1; // Device adapter always 3 jolts higher than highest adapter
        for(int i=1; i< adapters.size(); i++) {
            int diff = adapters.get(i)- adapters.get(i-1);
            if(diff == 1) {
                oneJoltCounter++;
            } else if(diff == 3) {
                threeJoltCounter++;
            }
        }
        int result = oneJoltCounter * threeJoltCounter;
        return result;
    }

    public void findPossAdapters(int index) {
        int diff = 0;
        int newIndex = index + 1;
        while (newIndex<adapters.size()) {
            int currentAdapter = adapters.get(index);
            int possAdapter = adapters.get(newIndex);
            diff = possAdapter - currentAdapter;
            if(diff<=3){
                double newCount = pathCounter.get(newIndex) + pathCounter.get(index);
                pathCounter.replace(newIndex, newCount);
                newIndex++;
            } else {
                break;
            }
        }
    }

    public double calcCombinations() {

        for(int i=0; i<adapters.size(); i++) {
            pathCounter.put(i, (double)0);
        }

        pathCounter.replace(0,(double)1);

        for(int i = 0; i<adapters.size(); i++) {
            findPossAdapters(i);
        }


        double count = pathCounter.get(adapters.size()-1);
        return count;
    }

}
