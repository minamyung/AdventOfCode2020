import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TobogganTrajectory {

    public static void main(String[] args) throws IOException {
        TobogganTrajectory tbg = new TobogganTrajectory();
        ArrayList<String> lines = tbg.FileReader();
        //int trees = tbg.treeCounter(lines);
        int[] slope1 = {1, 1};
        int trees1 = tbg.newTreeCounter(lines, slope1[0], slope1[1]);
        int[] slope2 = {3, 1};
        int trees2 = tbg.newTreeCounter(lines, slope2[0], slope2[1]);
        int[] slope3 = {5, 1};
        int trees3 = tbg.newTreeCounter(lines, slope3[0], slope3[1]);
        int[] slope4 = {7, 1};
        int trees4 = tbg.newTreeCounter(lines, slope4[0], slope4[1]);
        int[] slope5 = {1, 2};
        int trees5 = tbg.newTreeCounter(lines, slope5[0], slope5[1]);
        int answer = trees1 * trees2 * trees3 * trees4 * trees5;
        System.out.println(answer);

    }

    public ArrayList<String> FileReader() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        FileReader fr = new FileReader("inputs/TobogganTrajectoryInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            br.close();
        }
        return lines;
    }

    public int treeCounter(ArrayList<String> lines) {
        int xPosition = 0;
        int trees = 0;
        for (String line : lines) {
            if (isTree(line.toCharArray()[xPosition])) trees = trees + 1;
            xPosition = xPosition + 3;
            if (xPosition > 30) {
                xPosition = xPosition - 31;
            }
        }
        return trees;
    }

    public int newTreeCounter(ArrayList<String> lines, int xIncrement, int yIncrement) {
        int xPosition = 0;
        int trees = 0;
        for (int i = 0; i<lines.size(); i = i + yIncrement) {
            if (isTree(lines.get(i).toCharArray()[xPosition])) trees = trees + 1;
            xPosition = xPosition + xIncrement;
            if (xPosition > 30) {
                xPosition = xPosition - 31;
            }
        }
        return trees;
    }

    public boolean isTree(char c) {
        boolean tree = false;
        if (c=='#') tree = true;
        return tree;
    }
}
