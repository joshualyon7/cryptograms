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
    HashMap<String, String> letterMap = new HashMap<String, String>();
    String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z"};
    public static void main(String[] args){
        Cryptograms c = new Cryptograms();
        c.dictionary = c.parseFile("dictionary.txt"); // parse the dictionary text file
        c.input = new ArrayList<String>(Arrays.asList(args));
        System.out.println("input = " + c.input);
        System.out.println(((c.input.toString()).length() - 2 - ((c.input.size() - 1) * 2)));
        //Vector<String> solutions = new Vector<String>();
        //c.buildSolutions(solutions, "", c.input);
        //System.out.println(solutions);


        /*for every letter in input:
        1. set letter = to letter in alphabet
        2. check dictionary to see if sequence is possible
            2a. if it isn"t, break, move on to next letter
        3. if it is, add it to the current word, move to next letter
        4. 
        */
    }

    public int buildSolutions(Vector<ArrayList<String>> sol, ArrayList<String> curSol, String curWord, ArrayList<String> input, int i, int j){
        if(checkSubstring(curWord)){ // pruning - check if curSol is substring of a word in dict
            /*
             * 1. not the end of a word, add a new letter and start from beginning of alpha
             * 2. end of a word, add it to the working solution
             *  2a. working solution is a complete solution, 
             *  2b. working solution isn"t a complete solution: start a new word at beginning of alpha
             */
            if(curWord.length() == input.get(i).length()){ // 2
                curSol.add(curWord);
                if (curSol.size() == input.size()) { // 2a
                    sol.add(curSol);
                    return 1;
                }

                else{ // 2b
                    return buildSolutions(sol, curSol, "", input, ++i, 0);
                }
            }

            else{ // 1
                curWord += "a";
                return buildSolutions(sol, curSol, curWord, input, i, 1);
            }
                
        }
        else{ // if not, break branch, check next letter in alpha
            if(j == 26){
                return 0;
            }
            String key = String.valueOf(input.get(i).charAt(curWord.length() - 1));

            if(letterMap.containsKey(key)){ // if we've already mapped a letter to another, just add that to the curWord
                curWord = curWord.substring(0, curWord.length() - 2) + letterMap.get(key);
            }

            else{
                curWord = curWord.substring(0, curWord.length() - 2) + alpha[j];
                letterMap.put(key, alpha[j]);
            }

            return buildSolutions(sol, curSol, curWord, input, i, ++j);
        }
    }

    /*public String letterSub(String curWord, int a){
        return curWord + alpha[a];
    }*/

    public boolean checkSubstring(String test){
        for(String word : dictionary){
            if(word.startsWith(test)){
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

    /*
     * public Vector<String> decode(ArrayList<String> input){ Vector<String> retList
     * = new Vector<String>(); HashMap<Character, Character> curMap = new
     * HashMap<Character, Character>();
     * 
     * for(String word : input){ String test = ""; for(int i = 0; i < word.length();
     * i++){ for(int j = 0; j < alpha.length; j++){ curMap.put(word.charAt(i),
     * alpha[j]); test += curMap.get(word.charAt(i));
     * 
     * if(checkSubstring(test)){
     * 
     * } }
     * 
     * } }
     * 
     * return retList; }
     */

}   
