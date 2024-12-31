public class Main {
    
    public static void process(String code) {
        String[] lines = code.split("\n");
        int i = 0;

        while (i < lines.length) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                i++;
                continue;
            }

            if (line.startsWith("FOR")) {
                i = handles.handleFor(line, lines, i);
            } else if (line.startsWith("WHILE")) {
                i = handles.handleWhile(line, lines, i);
            } else if (line.startsWith("PRINT")) {
                handles.handlePrint(line);
            } else if (line.startsWith("dim")) {
                handles.handleDim(line);
            } else if (line.contains("=")) {
                handles.handleAssignment(line);
            } else if (line.startsWith("IF")) {
                i = handles.handleIfElse(line, lines, i);
            }
            i++;
        }
    }
    
    
    public static void main(String[] args) {
        
        String BASIC_code = ReadsFile.Reader();
        Main.process(BASIC_code);

    }
}

