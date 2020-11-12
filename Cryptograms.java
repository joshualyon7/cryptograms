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
    ArrayList<String> foundWords = new ArrayList<String>();
    public static void main(String[] args){
        Cryptograms c = new Cryptograms();
        c.dictionary = c.parseFile("dictionary.txt"); // parse the dictionary text file
        c.input = new ArrayList<String>(Arrays.asList(args));
        System.out.println("input = " + c.input);
        System.out.println(((c.input.toString()).length() - 2 - ((c.input.size() - 1) * 2)));
        //ArrayList<String> solutions = new ArrayList<String>();
        //ArrayList<String> currentSol = new ArrayList<String>();
        //c.buildSolutions(solutions, currentSol, "", c.input, 0, 0);
        /*for(int i : solutions.length() - 1){
            System.out.println(solutions.get(i), " \n")
        }
        */
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
                    ArrayList<String> newSol = new ArrayList<String>();
                    //int mark = alpha.indexOf(curSol.charAt(0));
                    for(int k = 0; k < curSol.size() - 1; k++){
                        newSol.add(curSol.get(k));
                    }

                    //curWord = 

                    if(checkSubstring(curWord.substring(0, i - 1))){
                        //do some stuff
                    }

                    return buildSolutions(sol, newSol, curWord, input, 0, j);

                    //return 1; // this got to the end of a solution and added
                }

                else{ // 2b -> will just create a new word in curSol
                    return buildSolutions(sol, curSol, "", input, ++i, 0);
                }
            }

            else{ // 1
                curWord += "a"; //if curWord.length == 1, return buildSolutions(sol, curSol, curWord, input, i, j); (with most recent j here)
                return buildSolutions(sol, curSol, curWord, input, i, 1);
            }
                
        }
        else{ // if not, break branch, check next letter in alpha
            if(j == 26){ // this got to the end of the alphabet, backtrack
                return 0;
            }
            String key = String.valueOf(input.get(i).charAt(curWord.length() - 1));
            curWord = updateWord(curWord, j, input, i);
            return buildSolutions(sol, curSol, curWord, input, i, ++j);
        }
    }

    public String updateWord(String word, int j, ArrayList<String> input, int i){
        String key = String.valueOf(input.get(i).charAt(word.length() - 1));

        if (letterMap.containsKey(key)) { // if we've already mapped a letter to another, just add that to the curWord
            return word.substring(0, word.length() - 2) + letterMap.get(key);
            // make a condition so that if already in sol, won't create duplicates
        } else {
            letterMap.put(key, alpha[j]);
            return word.substring(0, word.length() - 2) + alpha[j];
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
