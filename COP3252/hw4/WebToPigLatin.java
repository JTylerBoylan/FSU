import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class WebToPigLatin {

    private static final char[] vowels = {'A','a','E','e','I','i','O','o','U','u'};
    public static void main(String[] args){
        
        // Check if there are enough arguments
        if (args.length < 2){
            System.out.println("Missing input/output arguments!");
            return;
        }

        // Get file paths
        String inputFilePath = args[0];
        String outputFilePath = args[1];

        // Convert
        try {
            convert(inputFilePath,outputFilePath);
        } catch (IOException e){
            e.printStackTrace();
        } 

    }

    public static void convert(String inputFilePath, String outputFilePath) throws IOException {

        // Get file objects
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        // Get file scanner and writer streams
        Scanner scanner = new Scanner(inputFile);
        FileWriter writer = new FileWriter(outputFile);

        // Start at every html tag
        scanner.useDelimiter("<");

        // Run through entire file
        while (scanner.hasNext()) {

            String next = scanner.next();

            // Find where html tag closes
            int close = next.indexOf(">");

            // Get tag body
            String tag = next.substring(0, close);

            // Get text outside of html tags
            String text = next.substring(close+1, next.length());

            // Split text into distinct words
            String[] words = text.split(" ");

            String converted = new String();

            // Convert words to pig latin
            for (String word : words){
                converted += toPigLatin(word) + " ";
            }

            // Write to file
            writer.write("<" + tag + ">" + converted);

        }

        // Close scanner and writer
        scanner.close();
        writer.close();

    }

    public static String toPigLatin(String word) {

        if (word.length() == 0)
            return word;

        // Character array of the word
        char[] chars = word.toCharArray();

        // Counter variable
        int i = 0;

        // Seperate non-letter chars from beggining
        String precursor = new String();
        while (i < chars.length && !isLetter(chars[i])){
            precursor += chars[i++];
        }

        // Find letters before first vowel to add to the end with 'ay'
        String prefix = new String();
        while (i < chars.length && !isVowel(chars[i])) {
            prefix += chars[i++];
        }

        int e = chars.length-1;

        // Seperate non-letter chars from end
        String successor = new String();
        while (e >= 0 && !isLetter(chars[e])){
            successor += chars[e--];
        }

        // If the first letter is a vowel, then the prefix is empty and the suffix is 'way'
        String suffix = prefix.length() > 0 ? prefix + "ay" : "way";        

        // Return word in new pig-latin format if possible
        // If it errors, just return the original word
        try {
            return precursor + word.substring(i, e+1) + suffix + successor;
        } catch (StringIndexOutOfBoundsException ex){
            return word;
        }

    }

    public static boolean isVowel(char c) {
        for (char v : vowels)
            if (c == v)
                return true;
        return false;
    }

    public static boolean isLetter(char c){
        int ascii = (int) c;
        return (ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122);
    }


}