import java.util.Iterator;

public class OnePlayerBoggle {

    //One player boggle solver -- do not edit

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("ERROR: require dictionary file");
            System.exit(1);
        }

        String filename=args[0];
        int seed = 100;

        if(args.length >= 2){
            seed = Integer.parseInt(args[1]);
        }

        Boggle bg = new Boggle(seed,filename);
        System.out.println(bg);

        LinkedList<String> ll = bg.allWords();
        int points = Boggle.countPoints(ll);
        Iterator<String> i = new descendingIterator<>(ll);
        while(i.hasNext()){
            System.out.println(i.next());
        }
        System.out.println("Total Points: " + points);
    }
}
