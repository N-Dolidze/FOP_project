import java.io.FileReader;
import java.io.IOException;

public class ReadsFile {

    //Reader from input.txt
    public static String Reader() {
        String path = "./input.txt";
        String code = new String();

        try (FileReader input = new FileReader(path)) {
            int data;
            while ((data = input.read()) != -1) {
                char character = (char) data;
                code += (String.valueOf(character));
            }

            return code;
            //System.out.println(code);

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return null;
        }
    }
}
