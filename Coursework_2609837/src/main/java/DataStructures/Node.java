package DataStructures;

//Node<T> T value is a placeholder that is used to provide a data type for the created node
public class Node<T> {
    //Defining the variables and since they are in the class default values are added
    private T data;
    private Node<T> next;

    //Constructs a node object with the current value and next node to null
    public Node (T data){
        //Assigning the variable values to the current node
        this.data = data;
        this.next = null;
    }

    //Since the data and next values are set to private a getter method is required to get values
    public T getData() { return this.data; }
    public Node<T> getNext() { return this.next; }

    // Since the next values are set to private a setter method is required to set values
    public void setNext(Node<T> next) { this.next = next; }
}