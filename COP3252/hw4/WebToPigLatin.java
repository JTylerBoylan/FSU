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

    private static void convert(String inputFilePath, String outputFilePath) throws IOException {

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

            // Write to output
            writer.write("<" + tag + ">");

            // Get text outside of html tags
            String text = next.substring(close+1, next.length());

            // Split text by whitepaces
            String[] parts = text.split(" ");

            for (String part : parts) {

                // Find html markers
                int marker = part.indexOf("&");
                int endmarker = part.indexOf(";");

                String remaining = part;

                // If there is one, ignore it
                while (marker != -1 && endmarker != -1 && marker < endmarker){

                    // Seperate marker tag from rest of text
                    String markerTag = remaining.substring(marker, endmarker+1);
                    
                    // Get word before marker
                    String word = remaining.substring(0,marker);

                    // Write converted word and marker tag to output
                    writer.write(toPigLatin(word) + markerTag);

                    // Update remaining
                    remaining = remaining.substring(endmarker+1, remaining.length());

                    // Update indices
                    marker = remaining.indexOf("&");
                    endmarker = remaining.indexOf(";");

                }

                // Write remainder converted and whitespace
                writer.write(toPigLatin(remaining) + " ");

            }

        }

        // Close scanner and writer
        scanner.close();
        writer.close();

    }

    private static String toPigLatin(String word) {

        // Word must have vowels
        if (!hasVowels(word))
            return word;

        // Character array of the word
        char[] chars = word.toCharArray();

        // Determine if there is a newline at the end of the word
        boolean newline = false;
        if (chars[chars.length-1] == '\n'){
            newline = true;
            chars[chars.length-1] = '\0';
        }

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
        
        // Add newline
        if (newline)
            successor += '\n';

        // If the first letter is a vowel, then the prefix is empty and the suffix is 'way'
        String suffix = prefix.length() > 0 ? prefix + "ay" : "way";  

        // The remaining letters are the body of the word
        String body = word.substring(i, e+1);

        // If the first letter is capitalized, correct the converted word
        if (suffix.length() > 0 && body.length() > 0 && isCapitalized(suffix.charAt(0))){
            char lower = uncapitalize(suffix.charAt(0));
            suffix = lower + suffix.substring(1, suffix.length());
            char upper = capitalize(body.charAt(0));
            body = upper + body.substring(1, body.length());
        }

        // Return word in new pig-latin format
        return precursor + body + suffix + successor;

    }

    private static boolean isVowel(char c) {
        for (char v : vowels)
            if (c == v)
                return true;
        return false;
    }

    private static boolean isLetter(char c){
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    private static boolean isCapitalized(char c){
        return c >= 65 && c <= 90;
    }

    private static char capitalize(char c){
        return c >= 97 && c <= 122 ? (char)(c-32) : c;
    }

    private static char uncapitalize(char c){
        return c >= 65 && c <= 90 ? (char)(c+32) : c;
    }

    private static boolean hasVowels(String word){
        for (char c : word.toCharArray())
            if (isVowel(c))
                return true;
        return false;
    }


}