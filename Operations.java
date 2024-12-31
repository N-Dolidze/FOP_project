public class Operations {
    
    public static int evaluateExpression(String expression) {
        String[] tokens = expression.split("\\s+");
        int result = resolveValue(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            String operator = tokens[i];
            int operand = resolveValue(tokens[i + 1]);
            switch (operator) {
                case "/" -> {
                    if (operand == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result /= operand;
                }
                case "%" -> result %= operand;
                case "MOD" -> result %= operand;
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
            }
        }
        return result;
    }

    public static int resolveValue(String token) {
        if (handles.getIntvariables().containsKey(token)) {
            return handles.getIntvariables().get(token);
        } else {
            try {
                return Integer.parseInt(token);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value: "+token);
            }
        }
    }

    public static boolean evaluateCondition(String condition) {
        String[] parts = condition.split(" ");
        int left = resolveValue(parts[0]);
        int right = resolveValue(parts[2]);
        return switch (parts[1]) {
            case "!=" -> left != right;
            case ">" -> left > right;
            case "<" -> left < right;
            case ">=" -> left >= right;
            case "<=" -> left <= right;
            case "==" -> left == right;
            default -> throw new IllegalArgumentException("Unsupported operator: " + parts[1]);
        };
    }
}
