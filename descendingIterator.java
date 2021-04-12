import java.util.Iterator;

public class descendingIterator<e> extends LinkedList<e> implements Iterator<e>{
    Node<e> currentNode;

    public descendingIterator(LinkedList<e> LList){
        currentNode = LList.head;
    }

    public boolean hasNext(){
        return currentNode.next != null;
    }

    public e next() {
        if(currentNode == null){
            return null;
        }
        else{
            currentNode = currentNode.next;
            return currentNode.value;
        }
    }

    public void remove(LinkedList<e> LList){
        LList.remove(currentNode.value);
    }
}