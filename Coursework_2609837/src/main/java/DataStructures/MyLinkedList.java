package DataStructures;

import Model.Player;

//This class is made a subclass of Player class therefore only allowing players to be added
public class MyLinkedList<T extends Player> {
    //This is a pointer that is made in order to point to the head of the linked list
    private Node<T> head;

    //This method will insert a player into the linked list
    public void insertPlayer (T player){
        Node<T> newPlayer = new Node<>(player);
        //Checks if any players are previously added and if yes set the head to new player
        if (isEmpty()){
            head = newPlayer;
        }
        //If players are already added within the linked list
        else {
            Node<T> current = head;
            //Loop through the linked list until the current node next value is null indicating the end of the linked list
            while (current.getNext() != null){
                //Setting the current to the last null node
                current = current.getNext();
            }
            //Set the found node with the new data values
            current.setNext(newPlayer);
        }
    }

    //This method will loop through the list and find a matching value
    public Node<T> searchPlayerId (String player){
        Node<T> currentPlayer = head;
        //Checks if the head value is null to identify an empty linked list
        if (isEmpty()){
            System.out.println("You have no players added into the system");
            return null;
        }
        //If the list is not empty loop through the list till a node containing the matching id is found
        while (currentPlayer != null ){
            if (currentPlayer.getData().getId().equals(player)){
                return currentPlayer;
            }
            currentPlayer = currentPlayer.getNext();
        }
        //If no matching value is found return null
        System.out.println("Player not found");
        return null;
    }

    //This method will loop through the list and find a matching value
    public Node<T> searchPlayerName (String player){
        Node<T> currentPlayer = head;
        //Checks if the head value is null to identify an empty linked list
        if (isEmpty()){
            System.out.println("You have no players added into the system");
            return null;
        }
        //If the list is not empty loop through the list till a node containing the matching id is found
        while (currentPlayer != null ){
            if (currentPlayer.getData().getName().equals(player)){
                return currentPlayer;
            }
            currentPlayer = currentPlayer.getNext();
        }
        //If no matching value is found return null
        System.out.println("Player not found");
        return null;
    }

    //This method will remove a player from the linked list
    public boolean removePlayer (String id){
        Node<T> currentPlayer = head;
        //Checks if the head value is null to identify an empty linked list
        if (isEmpty()){
            System.out.println("You have no players added into the system");
            return false;
        }
        else {
            //If head value contains the matching id value then set head to next value since next is null
            if (head.getData().getId().equals(id)){
                head = head.getNext();
                return true;
            }else {
                //Loop through the list while next value is not null and the next node value is not the id
                while(currentPlayer.getNext() != null && !currentPlayer.getNext().getData().getId().equals(id)){
                    currentPlayer = currentPlayer.getNext();
                }
                //If value is null it indicates an id matching to be deleted was not found
                if (currentPlayer.getNext() == null){
                    System.out.println("Value not found");
                    return false;
                }
                //Set the currentPlayer nodes next value to the value after skipping one node
                currentPlayer.setNext(currentPlayer.getNext().getNext());
                return true;
            }
        }
    }

    //This method checks if the linked list is empty
    public boolean isEmpty (){ return head == null; }

    //This method allows to view the added players within the linked list
    public void viewPlayers (){
        //Prints if linked list is empty
        if (isEmpty()){
            System.out.println("You have no players added into the system");
        }else {
            //Creates a pointer node for reference
            Node<T> currentPlayer = head;
            //Loop while the pointer is not null and print pointer players details
            while (currentPlayer != null){
                System.out.println("ID : " + currentPlayer.getData().getId() + " | Name : " + currentPlayer.getData().getName() + " | Level : " +
                                    currentPlayer.getData().getLevel() + " | Station :" + ((currentPlayer.getData().getStationAssigned()) == -1 ? "Not Assigned" : currentPlayer.getData().getStationAssigned()) +
                                    " | Status : " + ((currentPlayer.getData().getIsActive()) ? "Playing" : "Waiting"));
                //Increments the pointer
                currentPlayer = currentPlayer.getNext();
            }
        }
    }

    //This method checks if there is an id already added by the id given in the parameter
    public int searchDuplicate (String id){
        //If list is empty return -1
        if (isEmpty()){
            return -1;
        }else{
            //Sets a pointer to check the linked list and list traversed while pointer is not null
            Node<T> currentPlayer = head;
            while (currentPlayer != null){
                //If already added return 1
                if (currentPlayer.getData().getId().equals(id)){
                    return 1;
                }
                //Increment pointer
                currentPlayer = currentPlayer.getNext();
            }
            //If id is not a duplicate return 0
            return 0;
        }
    }
}
