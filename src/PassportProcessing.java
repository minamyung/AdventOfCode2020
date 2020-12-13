import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PassportProcessing {

    public static void main(String[] args) throws IOException {
        PassportProcessing pprt = new PassportProcessing();
        ArrayList<String> passports = pprt.fileReader();
        System.out.println(passports.size());
        System.out.println(pprt.calcValidPassports(passports));
    }

    public ArrayList<String> fileReader() throws IOException {
        ArrayList<String> passports = new ArrayList<>();
        String passport = null;
        File f = new File("inputs/PassportProcessingInput.txt");
        Scanner sc = new Scanner(f);
        while (sc.hasNextLine()) {
            String newLine = sc.nextLine();
            if (!newLine.isBlank() && passport==null) {
                passport = newLine;
            } else if(!newLine.isBlank() && passport!=null) {
                passport = passport + " " + newLine;
            } else {
                passports.add(passport);
                passport = null;
            }
        }

        return passports;
    }

    public boolean checkValidPassport(String passport) {

        ArrayList<String> required = new ArrayList<>();
        required.add("byr");
        required.add("iyr");
        required.add("eyr");
        required.add("hgt");
        required.add("hcl");
        required.add("ecl");
        required.add("pid");

        String[] fields = passport.split(" ");
        ArrayList<String> fieldNames = new ArrayList<>();
        ArrayList<String> fieldValues = new ArrayList<>();

        for(String f : fields) {
            String[] fieldSubstring = f.split(":");
            fieldNames.add(fieldSubstring[0]);
            fieldValues.add(fieldSubstring[1]);
        }
        boolean byrValid = false;
        boolean iyrValid = false;
        boolean eyrValid = false;
        boolean hgtValid = false;
        boolean hclValid = false;
        boolean eclValid = false;
        boolean pidValid = false;

        if(fieldNames.containsAll(required)) {
            for (int i=0; i<fields.length; i++) {
                switch (fieldNames.get(i)) {
                    case("byr"):
                        byrValid = checkByr(fieldValues.get(i));
                        break;
                    case("iyr"):
                        iyrValid = checkIyr(fieldValues.get(i));
                        break;
                    case("eyr"):
                        eyrValid = checkEyr(fieldValues.get(i));
                        break;
                    case("hgt"):
                        hgtValid = checkHgt(fieldValues.get(i));
                        break;
                    case("hcl"):
                        hclValid = checkHcl(fieldValues.get(i));
                        break;
                    case("ecl"):
                        eclValid = checkEcl(fieldValues.get(i));
                        break;
                    case("pid"):
                        pidValid = checkPid(fieldValues.get(i));
                        break;
                    case("cid"):
                        break;
                }
            }
        }

        return (byrValid && iyrValid && eyrValid && hgtValid && hclValid && eclValid && pidValid);
    }

    public int calcValidPassports(ArrayList<String> passports) {
        int validPprts = 0;
        for(String p : passports) {
            if (checkValidPassport(p)) validPprts = validPprts + 1;
        }
        return validPprts;
    }

    public boolean checkByr(String s) {
        boolean valid = false;
        int byr = Integer.parseInt(s);
        if (byr >= 1920 && byr <=2002) valid = true;
        return valid;
    }

    public boolean checkIyr(String s) {
        boolean valid = false;
        int iyr = Integer.parseInt(s);
        if (iyr >= 2010 && iyr <= 2020) valid = true;
        return valid;
    }

    public boolean checkEyr(String s) {
        boolean valid = false;
        int eyr = Integer.parseInt(s);
        if (eyr >= 2020 && eyr <= 2030) valid = true;
        return valid;
    }

    public boolean checkHgt(String s) {
        boolean valid = false;
        if (s.contains("in")) {
            int hgt = Integer.parseInt(s.split("i")[0]);
            if (hgt >= 59 && hgt <= 76){
                valid = true;
            }
        } else if (s.contains("cm")) {
            int hgt = Integer.parseInt(s.split("c")[0]);
            if (hgt >=150 && hgt <=193) {
                valid = true;
            }
        }
        return valid;
    }

    public boolean checkHcl(String s) {
        boolean valid = true;
        char[] hclChars = s.toCharArray();
        ArrayList<Character> validChars = new ArrayList<>();
        validChars.add('0');
        validChars.add('1');
        validChars.add('2');
        validChars.add('3');
        validChars.add('4');
        validChars.add('5');
        validChars.add('6');
        validChars.add('7');
        validChars.add('8');
        validChars.add('9');
        validChars.add('a');
        validChars.add('b');
        validChars.add('c');
        validChars.add('d');
        validChars.add('e');
        validChars.add('f');

        if (hclChars[0]=='#' && hclChars.length==7) {
            for (int i=1; i<7; i++) {
                if (!validChars.contains(hclChars[i])) {
                    valid = false;
                    break;
                }
            }
        } else {
            valid = false;
        }

        return valid;
    }

    public boolean checkEcl(String s) {
        boolean valid = false;
        ArrayList<String> validEcl = new ArrayList<>();
        validEcl.add("amb");
        validEcl.add("blu");
        validEcl.add("brn");
        validEcl.add("gry");
        validEcl.add("grn");
        validEcl.add("hzl");
        validEcl.add("oth");

        if(validEcl.contains(s)) valid = true;

        return valid;
    }

    public boolean checkPid(String s) {
        boolean valid = true;
        char[] pidChars = s.toCharArray();

        if (pidChars.length == 9) {

            for (char c : pidChars) {
                if (!Character.isDigit(c)) valid = false;
                break;
            }

        } else valid = false;

        return valid;
    }
}
