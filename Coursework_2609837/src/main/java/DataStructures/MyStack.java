package DataStructures;

//This class is initialized with the generic class provided with the calling function
public class MyStack<T> {
    //This lines creates 1 node variables to store the top and an integer to store the stack size.
    Node<T> top;
    int stackSize;

    //This is the constructor method created to initialize the stack upon calling
    public MyStack() {
        this.top = null;
        this.stackSize = 0;
    }

    //This method checks if the stack is empty and returns a boolean
    public boolean isEmpty() { return this.top == null; }

    //This method adds value into the stack
    public boolean push(T actionRecord) {
        //A node is created with the given reference
        Node<T> action = new Node<>(actionRecord);
        //Set the top reference to the value after current value
        action.setNext(top);
        //Set top to current value and increment action
        top = action;
        stackSize++;
        return true;
    }

    //This method removes the top value from the stack
    public T pop(){
        //Checks if the stack is empty
        if (isEmpty()){
            System.out.println("Stack is empty");
            return null;
        }else {
            //Create a temp reference for the top value
            Node<T> temp = top;
            //Set top to the next value and decrement stack size
            top = top.getNext();
            stackSize--;
            //Return top reference values data
            return temp.getData();
        }
    }
}
