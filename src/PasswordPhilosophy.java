import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PasswordPhilosophy {
    private ArrayList<String> rules;
    private ArrayList<String> passwords;

    public PasswordPhilosophy() {
        this.rules = new ArrayList<>();
        this.passwords = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        PasswordPhilosophy pwd = new PasswordPhilosophy();
        ArrayList<String> lines = pwd.FileReader();
        pwd.parseLines(lines);
        System.out.println(pwd.countValidPasswords());

    }

    public ArrayList<String> FileReader() throws IOException {
        ArrayList<String> passwordsRules = new ArrayList<>();
        FileReader fr = new FileReader("inputs/PasswordPhilosophyInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                passwordsRules.add(line);
            }
        } finally {
            br.close();
        }
        return passwordsRules;
    }

    public void parseLines(ArrayList<String> passwordRules) {
        ArrayList<String> rules = new ArrayList<>();
        for (String s : passwordRules) {
            String[] line = s.split(":");
            this.rules.add(line[0]);
            this.passwords.add(line[1]);
        }
    }

    public boolean validPasswordChecker(String rule, String password) {
        char[] ruleChars = rule.toCharArray();
        char letter = rule.toCharArray()[ruleChars.length-1];
        String[] ruleSubstrings = rule.split("-");
        int min = Integer.parseInt(ruleSubstrings[0]);
        int max = Integer.parseInt(ruleSubstrings[1].split(" ")[0]);

        char[] passwordChars = password.toCharArray();
        int letterCounter = 0;

        for (char c : passwordChars) {
            if (c == letter) letterCounter = letterCounter + 1;
        }

        boolean validPassword = true;
        if (letterCounter < min || letterCounter > max) validPassword = false;

        return validPassword;
    }

    public boolean newValidPasswordChecker(String rule, String password) {
        char[] ruleChars = rule.toCharArray();
        char letter = rule.toCharArray()[ruleChars.length-1];
        String[] ruleSubstrings = rule.split("-");
        int index1 = Integer.parseInt(ruleSubstrings[0]);
        int index2 = Integer.parseInt(ruleSubstrings[1].split(" ")[0]);

        char[] passwordChars = password.toCharArray();
        int letterCounter = 0;

        if (passwordChars[index1] == letter) letterCounter = letterCounter + 1;
        if (passwordChars[index2] == letter) letterCounter = letterCounter + 1;

        boolean validPassword = true;

        if (letterCounter != 1) validPassword = false;

        return validPassword;
    }

    public int countValidPasswords() {
        int counter = 0;
        for (int i = 0; i<this.passwords.size(); i++) {
            if (newValidPasswordChecker(this.rules.get(i), this.passwords.get(i))) {
                counter = counter + 1;
            }
        }
        return counter;
    }
}
