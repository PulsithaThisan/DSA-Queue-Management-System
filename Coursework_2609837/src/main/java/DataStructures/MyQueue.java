package DataStructures;

import Model.Player;

//This class is made a subclass of Player class therefore only allowing players to be added to the queue
public class MyQueue<T extends Player> {
    //This lines creates 2 node variables to store the front and rear positions
    Node<T> front, rear;

    //This method adds a player to the queue
    public boolean addToQueue (T id){
        //Creates a node with the current player reference
        Node<T> currentPlayer = new Node<>(id);
        //Checks if the queue is empty and add player to the queue
        if (isEmpty()){
            front = currentPlayer;
            rear = currentPlayer;
            return true;
        }else {
            //Add player to the rear of the queue
            rear.setNext(currentPlayer);
            rear = currentPlayer;
            return true;
        }
    }

    //This method will remove a player from the queue
    public Node<T> removeFromQueue(){
        //Checks if queue is empty
        if (isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }else {
            //Since queue removes the top value top is stored
            Node<T> removedPlayer = front;
            //Top value is set to the next value
            front = front.getNext();
            //If newly added value is empty set rear to null since queue is empty
            if (front == null){
                rear = null;
            }
            return removedPlayer;
        }
    }

    //This method checks if the linked list is empty
    public boolean isEmpty(){ return front == null;}

    //This method prints the queue
    public void viewQueue(){
        //Checks if queue is empty
        if (isEmpty()){
            System.out.println("Queue is empty");
        }else {
            //A pointer reference is created
            Node<T> currentPlayer = front;
            //Looped through till the pointer reaches the end point
            while (currentPlayer != null){
                System.out.println("ID : " + currentPlayer.getData().getId() + " | Name : " + currentPlayer.getData().getName() + " | Level : " +
                        currentPlayer.getData().getLevel() + " | Station :" + ((currentPlayer.getData().getStationAssigned()) == -1 ? "Not Assigned" : currentPlayer.getData().getStationAssigned()) +
                        " | Status : " + ((currentPlayer.getData().getIsActive()) ? "Playing" : "Waiting"));
                currentPlayer = currentPlayer.getNext();
            }
        }
    }


    //This method is made explicitly to remove a player from the queue when in active
    public boolean removeInactivePlayer(String id){
        //Checks if the queue is empty
        if (isEmpty()){
            System.out.println("Queue is empty");
            return false;
        }else{
            //If the data within the front equals to the search data remove it
            if (front.getData().getId().equals(id)){
                Node<T> removedPlayer = front;
                front = front.getNext();
                //If front is now null means queue is empty set rear to null
                if (front == null){
                    rear = null;
                }
                return true;
            }
            //Create 2 reference values for previous and current positions for values which are not in the front position
            Node<T> previous = front;
            Node<T> pointer = front.getNext();
            //Loop through the linked list while pointer is not null and data is not the search value
            while (pointer != null && !pointer.getData().getId().equals(id)){
                //Increment pointer and previous
                previous = pointer;
                pointer = pointer.getNext();
            }
            //If pointer is null return player was not found
            if (pointer == null){
                System.out.println("Player not found");
                return false;
            }
            //If pointer has found a value set previous to the value after pointer
            previous.setNext(pointer.getNext());
            //If pointer was the end of the queue set previous to rear
            if (pointer == rear){
                rear = previous;
            }
            return true;
        }
    }

}
