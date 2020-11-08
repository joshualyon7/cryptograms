import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

class Cryptograms{

    ArrayList<String> dictionary;
    ArrayList<String> input;
    char[] alpha = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static void main(String[] args){
        Cryptograms c = new Cryptograms();
        c.dictionary = c.parseFile("dictionary.txt"); // parse the dictionary text file
        c.input = new ArrayList<String>(Arrays.asList(args));
        System.out.println("input = " + c.input);



        /*for every letter in input:
        1. set letter = to letter in alphabet
        2. check dictionary to see if sequence is possible
            2a. if it isn't, break, move on to next letter
        3. if it is, add it to the current word, move to next letter
        4. 
        */


    }

    public Vector<String> decode(ArrayList<String> input){
        Vector<String> retList = new Vector<String>();
        HashMap<Character, Character> curMap = new HashMap<Character, Character>();

        for(String word : input){
            String test = "";
            for(int i = 0; i < word.length(); i++){
                for(int j = 0; j < alpha.length; j++){
                    curMap.put(word.charAt(i), alpha[j]);
                    test += curMap.get(word.charAt(i));

                    if(checkSubstring(test)){
                        
                    }
                }
                
            }
        }

        return retList;
    }

    public boolean checkSubstring(String test){
        for(String word : dictionary){
            if(word.contains(test)){
                return true;
            }
        }
        return false;
    }


    public ArrayList<String> parseFile(String filename){
        ArrayList<String> stringList = new ArrayList <String>();
        try (Scanner s = new Scanner(new File("dictionary.txt")).useDelimiter("\\Z")){
            String curStr = s.next();
            String[] strings = curStr.split("\n");
            stringList = new ArrayList<String>(Arrays.asList(strings));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringList;
    }

}   
