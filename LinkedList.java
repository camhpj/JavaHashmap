public class LinkedList<E>{
    Node<E> head;
    int size;

    static class Node<E>{
        E value;
        Node<E> next;

        Node(E value){
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        Node<E> currentNode = this.head.next;
        result.append("[");
        for(int i = 0; i < size - 1; i++){
            if(i == size - 2){
                result.append(currentNode.value.toString());
            }
            else{
                result.append(currentNode.value.toString()).append(", ");
                currentNode = currentNode.next;
            }
        }
        result.append("]");
        return result.toString();
    }

    public static void main(String[] args){
        LinkedList<String> LList = new LinkedList<>();
        LList.add("cock");
        LList.add("and");
        LList.add("balls");
        System.out.println(LList);
        LList.remove("and");
        System.out.println(LList);
    }

    public LinkedList(){
        this.head = new Node<>(null);
        this.head.next = null;
        this.size = 1;
    }

    public void add(E value){
        Node<E> currentNode = this.head;
        while(currentNode.next != null){
            currentNode = currentNode.next;
        }
        currentNode.next = new Node<>(value);
        if(size == 1){
            this.head.value = this.head.next.value;
        }
        size++;
    }

    public void remove(E value){
        if(this.head.next == null){
            return;
        }
        Node<E> currentNode = this.head.next;
        Node<E> previousNode = currentNode;
        while(currentNode.value != value){
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        previousNode.next = currentNode.next;
        size--;
    }
}