import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

class Cryptograms {

    ArrayList<String> dictionary;
    ArrayList<String> input;
    HashMap<String, String> letterMap = new HashMap<String, String>();
    String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z" };
    ArrayList<String> foundWords = new ArrayList<String>();

    public static void main(String[] args) {
        Cryptograms c = new Cryptograms();
        c.dictionary = c.parseFile("dictionary.txt"); // parse the dictionary text file
        //c.input = new ArrayList<String>(Arrays.asList(args));
        c.input = new ArrayList<String>();
        c.input.add("gop");
        c.input.add("pog");
        System.out.println("input = " + c.input);
        Vector<ArrayList<String>> solutions = new Vector<ArrayList<String>>();
        ArrayList<String> currentSol = new ArrayList<String>();
        c.buildSolutions(solutions, currentSol, "", c.input, 0, 0, -1);
         for(ArrayList<String> a : solutions){
              System.out.println(a);
        }
        
        // System.out.println(solutions);

        /*
         * for every letter in input: 1. set letter = to letter in alphabet 2. check
         * dictionary to see if sequence is possible 2a. if it isn"t, break, move on to
         * next letter 3. if it is, add it to the current word, move to next letter 4.
         */
    }

    public int buildSolutions(Vector<ArrayList<String>> sol, ArrayList<String> curSol, String curWord, ArrayList<String> input, int i, int j, int c){
        if(checkSubstring(curWord)){ // pruning - check if curSol is substring of a word in dict
            /*
             * 1. not the end of a word, add a new letter and start from beginning of alpha
             * 2. end of a word, add it to the working solution
             *  2a. working solution is a complete solution, 
             *  2b. working solution isn"t a complete solution: start a new word at beginning of alpha
             */
            if(curWord.length() == input.get(i).length()){ // 2
                
                if(!dictionary.contains(curWord)){
                    curWord = updateWord(curWord, j, input, i, c);
                    return buildSolutions(sol, curSol, curWord, input, i, ++j, c);
                }

                if(!foundWords.contains(curWord)){
                    curSol.add(curWord);
                }
                
                if (curSol.size() == input.size()) { // 2a
                    sol.add(curSol);
                    /* to create a new curSol when finished with current:
                    ArrayList<String> newSol = new ArrayList<String>
                    int mark = alpha.indexOf(curSol.charAt(0))
                    return buildSolutions(sol, newSol, "", input, 0, mark) (instead of ret 1)
                    will at least start the next curSol with the latest value of j in the first index?
                    might need a flag for when we get to line 65, j = 1
                    */
                    return 1;
                    //return 1; // this got to the end of a solution and added
                }

                else{ // 2b -> will just create a new word in curSol
                    return buildSolutions(sol, curSol, "", input, ++i, 0, 0);
                }
            }

            else{ // 1
                //String key = String.valueOf(input.get(i).charAt(c));
                //letterMap.put(key, "a");
                curWord += "a"; //if curWord.length == 1, return buildSolutions(sol, curSol, curWord, input, i, j); (with most recent j here)
                
                System.out.println("added a: " + curWord);
                return buildSolutions(sol, curSol, curWord, input, i, 1, ++c);
            }
                
        }
        else{ // if not, break branch, check next letter in alpha
            if(j == 26){ // this got to the end of the alphabet, backtrack
                letterMap.put(String.valueOf(input.get(i).charAt(c)), "a");
                return 0;
            }

            curWord = updateWord(curWord, j, input, i, c);
            System.out.println("updated bad substring: " + curWord);

            if(buildSolutions(sol, curSol, curWord, input, i, ++j, c) == 1){
                if(curWord.substring(0, c - 1).contains(String.valueOf(curWord.charAt(c)))){
                    return 0;
                }
                else{
                    letterMap.remove(String.valueOf(input.get(i).charAt(curWord.length() - 1)));
                    updateWord(curWord, ++j, input, i, c);
                    return buildSolutions(sol, curSol, curWord, input, i, j, c);
                }
            }
            else {
                updateWord(curWord, j, input, i, --c);
                return buildSolutions(sol, curSol, curWord, input, i, j, c);
            }
        }
        
    }

    

    public String updateWord(String word, int j, ArrayList<String> input, int i, int c) { // word = baa, j = 1, input = gop pgo, i = 0
        String key = String.valueOf(input.get(i).charAt(word.length() - 1));
        System.out.println(letterMap);
        if(!letterMap.containsKey(String.valueOf(input.get(i).charAt(0)))){
            letterMap.put(String.valueOf(input.get(i).charAt(0)), "a");
        }
        if (letterMap.containsKey(key) && word.substring(0, c - 1).contains(String.valueOf(word.charAt(c)))) { // if we've already mapped a letter to another, just add that to the curWord
            return word.substring(0, word.length() - 2) + letterMap.get(key);
        } 
        else {
            letterMap.put(key, alpha[j]);
            return word.substring(0, word.length() - 1) + alpha[j];
        }

    }

    /*
     * public String letterSub(String curWord, int a){ return curWord + alpha[a]; }
     */

    public boolean checkSubstring(String test) {
        for (String word : dictionary) {
            if (word.startsWith(test)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> parseFile(String filename) {
        ArrayList<String> stringList = new ArrayList<String>();
        try (Scanner s = new Scanner(new File("dictionary.txt")).useDelimiter("\\Z")) {
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


/**
 * 
 * 
 * 
 * 
 */
