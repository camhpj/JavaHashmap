import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Boggle {

    //Constants (do not edit)
    final int BOGGLE_NUM_DICE = 25;
    final int BOGGLE_DICE_SIDES = 6;
    final int BOGGLE_DIMENSION = 5;


    final String[] boggle_dice = {
            "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
            "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
            "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR",
            "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
            "FIPRSY", "GORRVW", "IPRRRY", "NOOTUW", "OOOTTU",
    };

    //class variables (do not edit)
    private char[][] board;
    private Hashmap dict;
    private final Random rand;

    //constructors (do not edit)

    public Boggle(int seed, String filename) {
        rand = new Random(seed); //board based on seed
        initBoard();
        initDict(filename);
    }

    //initialize the board (do not edit)
    private void initBoard() {
        board = new char[BOGGLE_DIMENSION][BOGGLE_DIMENSION];

        //setup the board by rolling dice
        boolean[] used = new boolean[BOGGLE_NUM_DICE];
        for (int i = 0; i < BOGGLE_NUM_DICE; i++) {
            while (true) {
                //choose a dice to roll
                int d = rand.nextInt(BOGGLE_NUM_DICE);
                if (used[d]) continue; //keep choosing if already rolles

                //choose a random side
                int s = rand.nextInt(BOGGLE_DICE_SIDES);
                board[i / BOGGLE_DIMENSION][i % BOGGLE_DIMENSION] = boggle_dice[d].charAt(s);
                used[d] = true; //mark that dice as rolled
                break;
            }
        }

    }

    //initialize the dictionary
    private void initDict(String filename) {
        dict = new Hashmap(16);

        //Edit this if you want to do something fancy, or faster :)

        File dict_f = new File(filename);
        try {
            Scanner sc = new Scanner(dict_f);
            String line;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                dict.put(line.strip().toUpperCase());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //print the board (do not edit)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(".-----------.\n");
        for (int r = 0; r < 5; r++) {
            s.append("| ");
            for (int c = 0; c < 5; c++) {
                s.append(board[r][c]);
                if (board[r][c] == 'Q') s.append("u");
                else s.append(" ");
            }
            s.append("|\n");
        }
        s.append("'-----------'\n");
        return s.toString();
    }
    //point counting (do not edit)
    public static int countPoints(LinkedList<String> ll) {
        int pts = 0;
        Iterator<String> i = new descendingIterator<>(ll);
        while(i.hasNext()) {
            switch (i.next().length()) {
                case 0:
                case 1:
                case 2:
                    break;
                case 3:
                case 4:
                    pts += 1;
                    break;
                case 5:
                    pts += 2;
                    break;
                case 6:
                    pts += 3;
                    break;
                case 7:
                    pts += 5;
                    break;
                default:
                    pts += 11;
                    break;
            }
        }
        return pts;
    }

    //entry function for finding all words recursively
    public LinkedList<String> allWords(){

        //create a map to store the found words
        Hashmap hm = new Hashmap(16);

        //To mark which dice have been visited
        boolean[][] visited = new boolean[BOGGLE_DIMENSION][BOGGLE_DIMENSION];

        //perform a recursive call per dice
        for(int r=0;r<BOGGLE_DIMENSION;r++){
            for(int c=0;c<BOGGLE_DIMENSION;c++){
                allWords(hm,r,c,"",visited);
            }
        }

        //return the traversal of that map
        return hm.traverse();
    }

    //helper function to perform the recursion
    private void allWords(Hashmap hm, int r, int c, String curWord, boolean[][] visited){

        if(r < 0 || c < 0 || r >= BOGGLE_DIMENSION || c >= BOGGLE_DIMENSION){
            return;
        }

        if(visited[r][c]){
            return;
        }

        if(curWord.length() == 9){
            return;
        }

        if(dict.get(curWord) && curWord.length() > 2 && !hm.get(curWord) && dict.containsPrefix(curWord)){
            hm.put(curWord);
        }

        curWord = curWord + board[r][c];
        visited[r][c] = true;

        //get these recursive calls for free :)
        allWords(hm,r+1,c,curWord,visited);
        allWords(hm,r+1,c-1,curWord,visited);
        allWords(hm,r+1,c+1,curWord,visited);
        allWords(hm,r-1,c,curWord,visited);
        allWords(hm,r-1,c-1,curWord,visited);
        allWords(hm,r-1,c-1,curWord,visited);
        allWords(hm,r,c-1,curWord,visited);
        allWords(hm,r,c+1,curWord,visited);
        visited[r][c] = false;
    }
}