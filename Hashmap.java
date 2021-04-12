import java.util.*;

public class Hashmap {
    final double maxLoad = 0.75;
    public double currentLoad;
    public int initialCapacity;          // starting hashmap capacity
    private int capacity;                // # of buckets (starts at initialCapacity)
    private ArrayList<LinkedList<String>> buckets;
    private String value;
    private int sizeOf = 0;

    public static void main(String[] args){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hashmap hashmap = (Hashmap) o;
        return Objects.equals(value, hashmap.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    // Hashmap constructor
    public Hashmap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.capacity = initialCapacity;
        initializeBuckets(true);
    }

    public void initializeBuckets(boolean newMap){
        if(newMap) {
            buckets = new ArrayList<>(this.capacity);
            for(int i = 0; i < this.initialCapacity; i++) {
                this.buckets.add(i, new LinkedList<>());
            }
        }
        else{
            String[] valuesTemp = new String[sizeOf];
            int valuesIndex = 0;
            for(LinkedList<String> bucket: buckets){
                Iterator<String> i = new descendingIterator<>(bucket);
                while(i.hasNext()){
                    valuesTemp[valuesIndex] = i.next();
                    valuesIndex += 1;
                }
            }
            this.buckets = new ArrayList<>(this.capacity);
            for(int i = 0; i < this.capacity; i++){
                buckets.add(i, new LinkedList<>());
            }
            sizeOf = 0;
            for(String i: valuesTemp){
                put(i);
            }
        }
    }

    public int findIndex(String value){
        this.value = value;
        int key = hashCode();
        return key & (capacity - 1);
    }

    public void calculateLoad(double sizeOf, double capacity){
        currentLoad = sizeOf / capacity;
    }

    // Adds value to Hashmap
    public void put(Object value){
        String input = "" + value;
        input = input.trim();
        // check for null input
        if(value == null){
            return;
        }
        int index = findIndex(input);
        // id is the bitwise operation on hashed value and (buckets - 1)
        buckets.get(index).add(input);
        sizeOf += 1;
        calculateLoad(sizeOf, capacity);
        if(currentLoad >= maxLoad){
            resize(1);
        }
    }

    public Boolean get(Object value){
        String input = "" + value;
        input = input.trim();
        // check for null input
        if(value == null){
            return false;
        }
        int index = findIndex(input);
        Iterator<String> i = new descendingIterator<>(buckets.get(index));
        while(i.hasNext()){
            String temp = i.next();
            if(temp.equals(input)){
                return true;
            }
        }
        return false;
    }

    public void remove(Object value){
        String input = "" + value;
        input = input.trim();
        // check for null input
        if(value == null){
            return;
        }
        int index = findIndex(input);
        Iterator<String> i = new descendingIterator<>(buckets.get(index));
        while(i.hasNext()){
            String temp = i.next();
            if(temp.equals(input)){
                i.remove();
                sizeOf -= 1;
            }
        }
        calculateLoad(sizeOf+1, capacity);
        if(currentLoad <= 0.375){
            resize(0);
        }
    }

    public ArrayList<LinkedList<String>> getMap(){
        return buckets;
    }

    public int mapSize(){
        return sizeOf;
    }

    // 1 = size up, 2 = size down
    public void resize(int sizeUp){
        if(sizeUp == 1){
            capacity *= 2;
            initializeBuckets(false);
        }
        else if((sizeUp == 0) || (capacity != initialCapacity)){
            capacity /= 2;
            initializeBuckets(false);
        }
    }

    public LinkedList<String> traverse(){
        LinkedList<String> valuesTemp = new LinkedList<>();
        for(LinkedList<String> bucket: buckets){
            Iterator<String> i = new descendingIterator<>(bucket);
            while(i.hasNext()){
                valuesTemp.add(i.next());
            }
        }
        return valuesTemp;
    }

    public boolean containsPrefix(Object value){
        String input = "" + value;
        input = input.trim();
        // check for null input
        if(value == null){
            return false;
        }
        for(LinkedList<String> bucket: buckets){
            Iterator<String> i = new descendingIterator<>(bucket);
            while(i.hasNext()){
                if(i.next().startsWith(input)){
                    return true;
                }
            }
        }
        return false;
    }
}