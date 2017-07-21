package pc_Q4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "/Users/mkyong/csv/users.csv";
        String line = "";
        String cvsSplitBy = ",";
        User[] users = new User[10];
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] usersLine = line.split(cvsSplitBy);
                User user = new User((usersLine[0]), usersLine[1], usersLine[2], 
                		usersLine[3], usersLine[4]);
                users[i]=user;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
