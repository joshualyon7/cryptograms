import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;


public class Crypto {

    String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z" };

    ArrayList<String> dictionary = parseFile("dictionary.txt");
    ArrayList<String> inList;
    ArrayList<String> inWords;
    Vector<String> sol = new Vector<String>();
    public static final boolean DEBUG = false;

    public static void main(String[] args) {
        
        Crypto c = new Crypto();

        /* <set up input array of chars> */
        Scanner si = new Scanner(System.in);
        String input = si.nextLine().toLowerCase();

        input = input.strip(); // get rid of trailing + leading spaces

        c.inWords = new ArrayList<String>(Arrays.asList(input.split(" ")));

        c.inList = new ArrayList<String>();

        for (char ch : input.toCharArray()) { // expand input to list of char w/space
            c.inList.add(String.valueOf(ch)); 
        }

        HashMap<String, String> map = new HashMap<String, String>();
        c.decrypt(map, "a", 0, 0);
        System.out.println(c.sol.size());
        for(String s : c.sol){
            System.out.println(s);
        }

        si.close();
    }

    public void decrypt(HashMap<String, String> map, String cur, int i, int wordInd){

        if(inList.get(i).equals(" ")){
            if(!dictionary.contains(cur.split(" ")[0])){
                return;
            }
            cur += " ";
            i++;
            wordInd++;
        }

        if(map.containsKey(inList.get(i)) && map.get(inList.get(i)) != null){
            
            cur += map.get(inList.get(i));
            if(!checkSubstring(cur.split(" ")[cur.split(" ").length - 1])){
                return;
            }
            if(dictionary.containsAll(Arrays.asList(cur.split(" "))) && cur.length() == inList.size() && !sol.contains(cur)){
                sol.add(cur);

                return;
            }
            else{
                if(cur.length() < inList.size()){
                    
                    decrypt(map, cur, i + 1, wordInd);
                }
                
                return;
            }
        }
        else{
            cur += "a";
        }
        
        for(int j = 0; j < 26; j++){

            cur = cur.substring(0, i) + alpha[j];

            String[] curWords = cur.split(" ");
            String curWord = curWords[wordInd];

            HashMap<String, String> nMap = new HashMap<String, String>();
            for (int k = 0; k < i + 1; k++) { // clear all mappings after i
                if (map.containsKey(String.valueOf(inList.get(k))) && map.get(String.valueOf(inList.get(k)) ) != null) {
                    nMap.put(inList.get(k), map.get(inList.get(k)));
                }
            }
            map = nMap;

            if(!checkSubstring(curWord) || map.containsValue(alpha[j])){
                    continue;
            }

            map.put(inList.get(i), alpha[j]);

            if(dictionary.containsAll(Arrays.asList(cur.split(" "))) && cur.length() == inList.size() && !sol.contains(cur)){ // if the solution exists, add it to the list
                sol.add(cur);
            }
            
            if(cur.length() < inList.size()){
                decrypt(map, cur, i + 1, wordInd);
            }
        }
    }

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
        try (Scanner s = new Scanner(new File(filename)).useDelimiter("\\Z")) {
            String curStr = s.next();
            String[] strings = curStr.split("\n");
            stringList = new ArrayList<String>(Arrays.asList(strings));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
