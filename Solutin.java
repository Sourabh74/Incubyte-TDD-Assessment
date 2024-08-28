import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalculator {

    public static int add(String numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }

        String delimiter = ",|\n";
        String numbersPart = numbers;

        if (numbers.startsWith("//")) {
            int delimiterEnd = numbers.indexOf("\n");
            String customDelimiter = numbers.substring(2, delimiterEnd).trim();
            if (customDelimiter.startsWith("[")) {
                customDelimiter = customDelimiter.substring(1, customDelimiter.length() - 1);
            }
            numbersPart = numbers.substring(delimiterEnd + 1);
            delimiter = Pattern.quote(customDelimiter);
        }

   
        String[] numberStrings = numbersPart.split(delimiter);
  
        int total = 0;
        List<Integer> negativeNumbers = new ArrayList<>();
        
        for (String numberString : numberStrings) {
            if (!numberString.isEmpty()) {
                try {
                    int number = Integer.parseInt(numberString);
                    if (number < 0) {
                        negativeNumbers.add(number);
                    } else {
                        total += number;
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format: " + numberString);
                }
            }
        }
        
        if (!negativeNumbers.isEmpty()) {
            String negatives = String.join(", ", negativeNumbers.stream()
                .map(String::valueOf)
                .toArray(String[]::new));
            throw new IllegalArgumentException("Negative numbers not allowed: " + negatives);
        }
        
        return total;
    }


    public static void main(String[] args) {
        try {
            System.out.println(add(""));           
            System.out.println(add("1"));           
            System.out.println(add("1,5"));        
            System.out.println(add("1\n2,3"));     
            System.out.println(add("//;\n1;2"));   
            System.out.println(add("//[***]\n1***2***3")); 
            System.out.println(add("//;\n1;2;-3")); // Throws Exception: Negative numbers not allowed: -3
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
