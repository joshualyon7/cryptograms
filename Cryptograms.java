import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//import jdk.internal.joptsimple.internal.Strings;

class Cryptograms{


public static void main(String[] args){
    Cryptograms c = new Cryptograms();
    c.parseFile("dictionary.txt");

}


public List<String> parseFile(String filename){
    List<String> stringList = new ArrayList <String>();
    try (Scanner s = new Scanner(new File("dictionary.txt")).useDelimiter("\\Z")){
        String curStr = s.next();
        String[] strings = curStr.split("\n");
        stringList = new ArrayList<String>(Arrays.asList(strings));
        //strings.add(curStr);
       // strings = curStr.split("\n");
        System.out.print(stringList);

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return stringList;
}

}   
