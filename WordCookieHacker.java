import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;


public class WordCookieHacker {

	static String original = "MORPH"; // FILL THIS IN
	static TreeSet<String> resultWords = new TreeSet<String>();
	static TreeSet<String> dict = new TreeSet<String>();
	
    public static void main(String args[]) {
    
		Scanner scan = new Scanner(System.in);
		Scanner scan2 = new Scanner(System.in);
		
		fillDict();
		
		// do first letter set
		System.out.println("Welcome to the Word Cookie Hack");
		System.out.print("Enter the letters in any order without spaces: ");
		original = scan2.nextLine().toUpperCase().trim();
		generateWords();
		System.out.println(resultWords);
		
		int choice = 1;
		String letter = "";
		while (choice > 0 && choice <= 5){
			System.out.println();
			System.out.println();
			System.out.println("1) Display all");
			System.out.println("2) Search based on starting letter");
			System.out.println("3) Search based on length");
			System.out.println("4) Search based on starting letter AND length");
			System.out.println("5) New set");
			System.out.println("Press any other key to quit");
			try {
				choice = scan.nextInt();
			}catch (InputMismatchException e){
				choice = 0;
			}
			
			TreeSet<String> refinedOutput = new TreeSet<String>(); 
			if (choice == 1){
				System.out.println(resultWords);  // Display all
			}else if (choice == 2){
				System.out.print("First letter query: ");
				letter = scan2.next().toUpperCase();
				// find words with first letter
				for (String s: resultWords){							// there is probably a way to make this more efficient
					if (s.substring(0, 1).equals(letter)){
						refinedOutput.add(s);
					}
				}
				System.out.println(refinedOutput);
			}else if (choice == 3){
				System.out.print("Length: ");
				int len = scan.nextInt();
				for (String s: resultWords){
					if (s.length() == len){
						refinedOutput.add(s);
					}
				}
				System.out.println(refinedOutput);
			}else if (choice == 4){
				System.out.print("First letter query: ");
				letter = scan2.next().toUpperCase();
				System.out.print("Length: ");
				int len = scan.nextInt();
				for (String s: resultWords){
					if (s.substring(0, 1).equals(letter) && s.length() == len){
						refinedOutput.add(s);
					}
				}
				System.out.println(refinedOutput);
			}else if (choice == 5){
				System.out.println("Enter the letters in any order without spaces: ");
				original = scan2.nextLine().toUpperCase();
				generateWords();
				System.out.println(resultWords);
			}	
		}
    }
    
    public static void generateWords(){
    	ArrayList<String> baseArray = new ArrayList<String>();
		//System.out.println(original);
		resultWords.clear();
		for (char e: original.toCharArray()){
			//System.out.println("in this loop");
			baseArray.add(String.valueOf(e));
		}
		
		System.out.println(baseArray);
		
		int top = (original.length()+1) * (int)Math.pow(10, original.length()-1);
		for (int num = 11; num < top; num ++){
			//check if num has repeats
			if (inRange(num) && noRepeats(num) && !containsZero(num)){
				//System.out.println(num);
				//convert num sequence to letters
				String s = "";
				ArrayList<Integer> numArray = intToArray(num);
				for (int i: numArray){
					s += baseArray.get(i-1);
				}
				// TODO check dictionary
				if (check_for_word(s)){
					resultWords.add(s);
				}
			}
		}
	}
	
    public static ArrayList<Integer> intToArray(int num){
    	ArrayList<Integer> numArray = new ArrayList<Integer>();
    	while (num>0){
    		numArray.add(num%10);
    		num = num/10;
    	}
    	return numArray;
    }
    
    public static Boolean noRepeats(int num){
    	ArrayList<Integer> numArray = intToArray(num);
    	TreeSet<Integer> condensedNum = new TreeSet<Integer>();
    	for (int i: numArray){
    		condensedNum.add(i);
    	}
    	if (condensedNum.size() == numArray.size()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static Boolean containsZero(int num){
    	ArrayList<Integer> numArray = intToArray(num);
    	if (numArray.contains(0)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static Boolean inRange(int num){
    	ArrayList<Integer> numArray = intToArray(num);
    	for (int i: numArray){
    		if (i > original.length()){return false;}
    	}
    	return true;
    }  	
    
    public static void fillDict(){
    	try {
            BufferedReader in = new BufferedReader(new FileReader(
                "/usr/share/dict/web2")); // this is only for Macs, use /usr/share/dict/american-english for linux systems
            String str;
            while ((str = in.readLine()) != null) {
                dict.add(str);
            }
            in.close();
        } catch (IOException e) {
        }
    
    }
    public static boolean check_for_word(String word) {
        //System.out.println(dict.size());
        if (dict.contains(word.toLowerCase())){return true;}
    	else{return false;}
    }
}