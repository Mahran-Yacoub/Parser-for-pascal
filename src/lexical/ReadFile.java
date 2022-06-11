package lexical;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {

    public ReadFile() {

    }

    public ArrayList<String> readFileAsList(File file) {

        ArrayList<String> arrayList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                arrayList.add(line);
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }
}
