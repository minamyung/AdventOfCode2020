import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BinaryBoarding {

    public static void main(String[] args) throws IOException {
        BinaryBoarding bny = new BinaryBoarding();
        ArrayList<String> seats = bny.fileReader();
        System.out.println(bny.findMyID(seats));

    }

    public ArrayList<String> fileReader() throws IOException {
        ArrayList<String> seats = new ArrayList<>();
        FileReader fr = new FileReader("inputs/BinaryBoardingInput.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;
        try {
            while((line = br.readLine()) != null) {
                seats.add(line);
            }
        } finally {
            br.close();
        }
        return seats;
    }

    public int calcRow(String seat) {
        ArrayList<Integer> possibleRows = new ArrayList<>();
        for(int i=0; i<128; i++){
            possibleRows.add(i);
        }
        char[] seatChars = seat.toCharArray();
        for (int i=0; i<7; i++) {
            ArrayList<Integer> newPossibleRows = new ArrayList<>();
            int newSize = possibleRows.size()/2;
            int cutoff = possibleRows.get(newSize);
            if (seatChars[i]=='F') {
                for (int j = 0; j< possibleRows.size(); j++) {
                    if (possibleRows.get(j) < cutoff) newPossibleRows.add(possibleRows.get(j));
                }
            } else {
                for (int j = 0; j< possibleRows.size(); j++) {
                    if (possibleRows.get(j) >= cutoff) newPossibleRows.add(possibleRows.get(j));
                }
            }
            possibleRows = newPossibleRows;

        }
        return possibleRows.get(0);
    }

    public int calcColumn(String seat) {
        ArrayList<Integer> possibleColumns = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            possibleColumns.add(i);
        }
        char[] seatChars = seat.toCharArray();

        for(int i=7; i<10; i++) {
            ArrayList<Integer> newPossibleColumns = new ArrayList<>();
            int newSize = possibleColumns.size()/2;
            int cutoff = possibleColumns.get(newSize);
            if(seatChars[i]=='L') {
                for (int j = 0; j< possibleColumns.size(); j++) {
                    if (possibleColumns.get(j) < cutoff) newPossibleColumns.add(possibleColumns.get(j));
                }
            } else {
                for (int j = 0; j< possibleColumns.size(); j++) {
                    if (possibleColumns.get(j) >= cutoff) newPossibleColumns.add(possibleColumns.get(j));
                }
            }
            possibleColumns = newPossibleColumns;
        }
        return possibleColumns.get(0);
    }

    public int calcSeatID(String seat) {
        int row = calcRow(seat);
        int col = calcColumn(seat);
        int ID = row*8 + col;
        return ID;
    }

    public int findHighestID(ArrayList<String> seats) {
        int highestID = 0;
        for(String seat : seats){
            int ID = calcSeatID(seat);
            if(ID>highestID) highestID = ID;
        }
        return highestID;
    }

    public ArrayList<Integer> findSeatIDs(ArrayList<String> seats) {
        ArrayList<Integer> flightSeatIDs = new ArrayList<>();
        for(String seat : seats){
            flightSeatIDs.add(calcSeatID(seat));
        }
        return flightSeatIDs;
    }

    public int findMyID(ArrayList<String> seats) {
        ArrayList<Integer> allSeatIDs = new ArrayList<>();
        for(int i=0; i<128; i++) {
            for(int j=0; j<8; j++) {
                allSeatIDs.add(i*8+j);
            }
        }

        ArrayList<Integer> flightSeatIDs = findSeatIDs(seats);

        int myID = 0;

        for(int id : allSeatIDs) {
            if (!flightSeatIDs.contains(id)) {
                if (flightSeatIDs.contains(id+1) && flightSeatIDs.contains(id-1)) {
                    myID = id;
                    break;
                }
            }
        }
        return myID;
    }
}
