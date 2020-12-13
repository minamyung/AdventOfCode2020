import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

public class HandheldHalting {
    private ArrayList<String> instructions;
    private final String INST_PATTERN = "(\\w{1,})\\s([+-])([0-9]{1,})";

    public static void main(String[] args) throws IOException {
        HandheldHalting hnhd = new HandheldHalting();
        hnhd.fileReader();
        int accValue = hnhd.changeInst();
        System.out.println(accValue);
    }

    public void fileReader() throws IOException {
        ArrayList<String> instructions = new ArrayList<>();
        FileReader fr = new FileReader("inputs/HandheldHaltingInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                instructions.add(line);
            }
        } finally {
            br.close();
        }
        this.instructions = instructions;
    }

    public ArrayList<Integer> getNextIndex(String line, int currentIndex, int accCount) {
        int nextIndex = currentIndex;

        Pattern p = Pattern.compile(INST_PATTERN);
        Matcher m = p.matcher(line);

        if(m.find()) {
            String inst = m.group(1);
            String op = m.group(2);
            int num = Integer.parseInt(m.group(3));

            switch(inst) {
                case("acc"):
                    if(op.equals("+")) {
                        accCount = accCount + num;
                    } else {
                        accCount = accCount - num;
                    }
                    nextIndex += 1;
                    break;

                case("jmp"):
                    if(op.equals("+")) {
                        nextIndex = nextIndex + num;
                    } else {
                        nextIndex = nextIndex - num;
                    }
                    break;

                case("nop"):
                    nextIndex += 1;
                    break;
            }
        }

        ArrayList<Integer> result = new ArrayList<>();

        result.add(nextIndex);
        result.add(accCount);

        return result;
    }

    public ArrayList<Integer> calcAccValue(ArrayList<String> instructions) {
        ArrayList<Integer> ranInst = new ArrayList<>();
        ArrayList<Integer> results = new ArrayList<>();

        int accCount = 0;
        int i = 0;
        boolean quit = false;

        while(!quit) {
            String line = instructions.get(i);
            ranInst.add(i);
            ArrayList<Integer> result = getNextIndex(line, i, accCount);
            int nextIndex = result.get(0);
            if (ranInst.contains(nextIndex)) {
                results.add(accCount);
                results.add(nextIndex);
                quit = true;
            } else if (nextIndex==instructions.size()-1) {
                accCount = result.get(1);
                results.add(accCount);
                results.add(nextIndex);
                quit = true;
            } else {
                accCount = result.get(1);
                i = nextIndex;
            }
        }
        return results;
    }

    public int changeInst() {
        int accCount = 0;

        for(int i = 0; i < this.instructions.size(); i++) {
            ArrayList<String> changedInst = new ArrayList<>();
            changedInst.addAll(instructions);

            Pattern p = Pattern.compile(INST_PATTERN);
            Matcher m = p.matcher(this.instructions.get(i));

            if(m.find()) {
                String inst = m.group(1);
                if(inst.equals("jmp")) {
                    String newInst = "nop " + m.group(2) + m.group(3);
                    changedInst.set(i, newInst);
                } else if(inst.equals("nop")) {
                    String newInst = "jmp " + m.group(2) + m.group(3);
                    changedInst.set(i, newInst);
                }
            }

            ArrayList<Integer> results = calcAccValue(changedInst);

            int lastIndex = results.get(1);

            if(lastIndex==changedInst.size()-1) {
                accCount = results.get(0);
                break;
            }
        }
        return accCount;
    }
}