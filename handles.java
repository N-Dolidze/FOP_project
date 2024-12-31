import java.util.HashMap;
import java.util.Map;

//class to handle some stuff from BASIC
public class handles {
    private static final Map<String, Integer> intVariables = new HashMap<>();
    private static final Map<String, String> stringVariables = new HashMap<>();

    //handles DIM
    public static void handleDim(String line) {
        String[] parts = line.split(" as ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid dim statement: " + line);
        }
        String varName = parts[0].replace("dim", "").trim();
        String varType = parts[1].trim();

        if ("integer".equals(varType)) {
            intVariables.put(varName, 0);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + varType);
        }
    
    }

    //handles Assignment
    public static void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + line);
        }
        String varName = parts[0].trim();
        String value = parts[1].trim();

        if (value.startsWith("\"") && value.endsWith("\"")) {
            stringVariables.put(varName, value.substring(1, value.length() - 1));
        } else {
            int intValue = Operations.evaluateExpression(value);
            intVariables.put(varName, intValue);
        }
    }

    //handles Printing
    public static void handlePrint(String line) {
        String content = line.substring(5).trim();
        String[] parts = content.split(";");
        StringBuilder output = new StringBuilder();
        for (String part : parts) {
            part = part.trim();
            if (stringVariables.containsKey(part)) {
                output.append(stringVariables.get(part)).append(" ");
            } else if (intVariables.containsKey(part)) {
                output.append(intVariables.get(part)).append(" ");
            } else {
                output.append(part).append(" ");
            }
        }
        System.out.println(output.toString().trim());
    }

    //handles for loop
    public static int handleFor(String line, String[] lines, int index) {
        String[] parts = line.split(" ");
        if (parts.length != 6 || !"TO".equals(parts[4])) {
            throw new IllegalArgumentException("Invalid FOR loop syntax: " + line);
        }

        String varName = parts[1];
        int start = Operations.evaluateExpression(parts[3]);
        int end = Operations.evaluateExpression(parts[5]);

        intVariables.put(varName, start);
        int loopStart = index + 1;

        while (intVariables.get(varName) <= end) {
            int i = loopStart;
            while (i < lines.length && !lines[i].trim().equals("NEXT")) {
                Main.process(lines[i].trim());
                i++;
            }
            intVariables.put(varName, intVariables.get(varName) + 1);
        }

        while (index < lines.length && !lines[index].trim().equals("NEXT")) {
            index++;
        }

        return index;
    }

    //handles while lopp
    public static int handleWhile(String line, String[] lines, int index) {
        String condition = line.substring(6).trim();
        int startIndex = index + 1;

        while (Operations.evaluateCondition(condition)) {
            int i = startIndex;
            while (i < lines.length && !lines[i].trim().equals("WEND")) {
                Main.process(lines[i].trim());
                i++;
            }
        }

        while (index < lines.length && !lines[index].trim().equals("WEND")) {
            index++;
        }
        return index;
    }

    //handles if-else statement
    public static int handleIfElse(String line, String[] lines, int index) {
        String condition = line.substring(2).trim();
        boolean conditionMet = Operations.evaluateCondition(condition);
        int startIndex = index + 1;

        while (startIndex < lines.length && !lines[startIndex].trim().equals("ELSE") && !lines[startIndex].trim().equals("END IF")) {
            if (conditionMet) {
                Main.process(lines[startIndex].trim());
            }
            startIndex++;
        }

        if (!conditionMet) {
            while (startIndex < lines.length && !lines[startIndex].trim().equals("END IF")) {
                if (lines[startIndex].trim().equals("ELSE")) {
                    startIndex++;
                    while (startIndex < lines.length && !lines[startIndex].trim().equals("END IF")) {
                        Main.process(lines[startIndex].trim());
                        startIndex++;
                    }
                }
                startIndex++;
            }
        }

        while (index < lines.length && !lines[index].trim().equals("END IF")) {
            index++;
        }

        return index;
    }

    //getters
    public static Map<String, Integer> getIntvariables() {
        return intVariables;
    }

    public static Map<String, String> getStringvariables() {
        return stringVariables;
    }
}

