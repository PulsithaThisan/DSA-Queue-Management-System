package Services;

//Importing the other packages
import DataStructures.MyQueue;
import DataStructures.MyStack;
import DataStructures.MyLinkedList;
import Model.ActionRecord;
import Model.Player;
import DataStructures.Node;

//Importing scanner library to detect inputs
import java.util.Scanner;

public class QueueManagementSystem {

    //Create and set all linked list, queues and stacks objects to private so other classes can not access them
    private MyLinkedList<Player> playerList = new MyLinkedList<>();
    private MyQueue<Player> lowPriorityQueue = new MyQueue<>();
    private MyQueue<Player> mediumPriorityQueue = new MyQueue<>();
    private MyQueue<Player> highPriorityQueue = new MyQueue<>();
    private MyStack<ActionRecord> actionHistory = new MyStack<>();
    //Create one scanner object to be used throughout the system
    private Scanner scanner = new Scanner(System.in);

    //This is the main method which is used to run the program
    public static void main (String[] args) {
        System.out.println("\n---- Starting Queue Management System ----\n");
        //Since other methods are not static an object is created in order to access them
        QueueManagementSystem qms = new QueueManagementSystem();
        //Loop while user does not want to exit the system
        boolean isExit = false;
        while (!isExit) {
            //Load the main menu
            isExit = qms.loadMainMenu();
        }
        //If user decides to exit the system exit as not an error
        if (isExit){
            System.out.println("Terminating Queue Management System....");
            System.exit(0);
        }

    }

    //This method provides the add player functionality
    public void addPlayer (){
        //Prompts the user to enter the relevant values into the system
        System.out.println("\n---- Register Player Menu ----\n");
        System.out.print("Enter player id                             : ");
        String playerId = scanner.nextLine();
        System.out.print("Enter player name                           : ");
        String playerName = scanner.nextLine();
        System.out.print("Enter skill level (Low/Medium/High)         : ");
        String playerSkillLevel = scanner.nextLine();

        //Check if the ID is a duplicate and is in a valid format
        boolean isIdValid = false;
        boolean isNotDuplicate = true;
        //Checks if the id matches the given format
        if (playerId.matches("P[0-9]{3}")){
            isIdValid = true;
            //Checks if the id is a duplicate
            int duplicate = playerList.searchDuplicate(playerId);
            if (duplicate == 1) isIdValid = isNotDuplicate = false;
        }

        //Checks if the name is in alphabetical characters
        boolean isNameValid = playerName.matches("[a-zA-Z]+");

        //Checks if skill level is either low, medium or high and formats it accordingly
        boolean isSkillLevelValid = false;
        String formattedSkillLevel = "";
        if(playerSkillLevel.equalsIgnoreCase("low") || playerSkillLevel.equalsIgnoreCase("medium") || playerSkillLevel.equalsIgnoreCase("high")){
            formattedSkillLevel = playerSkillLevel.substring(0,1).toUpperCase() + playerSkillLevel.substring(1);
            isSkillLevelValid = true;
        }

        //If all validations are passed create the player object
        if (isIdValid && isNameValid && isSkillLevelValid) {
            Player player = new Player(playerId, playerName, formattedSkillLevel, -1, false);
            //Add player to the linked list
            playerList.insertPlayer(player);
            //Add player to priority queue
            prioritizePlayer(player);
            System.out.println("\nPlayer added successfully!");
        }else{
            //If validation are not passed display the relevant error
            System.out.println("\nInvalid inputs were provided !");
            if(!isIdValid) {
                if (isNotDuplicate) {
                    System.out.println("Please enter a valid player id (Eg: P001, P002)");
                }else System.out.println("Player id previously added, Please enter a different player id");
            }
            if(!isNameValid) { System.out.println("Please enter a valid player name"); }
            if(!isSkillLevelValid) { System.out.println("Please enter a valid skill level"); }
            System.out.println();
        }
    }

    //This method removes a player
    public void removePlayer(){
        //Ask user to enter the desired player id
        System.out.println("\n---- Remove Player Menu ----\n");
        System.out.print("Enter player id to remove        : ");
        String playerId = scanner.nextLine();
        //Search if the player id is added
        Node<Player> result = playerList.searchPlayerId(playerId);
        //Exit if player id is not found
        if (result == null){}
        else{
            //If player is active while removing
            if(result.getData().getIsActive() == true){
                //Remove player from linked list
                boolean removed = playerList.removePlayer(playerId);
                if(removed) {
                    //Record the action in action history stack
                    System.out.println("Player removed successfully");
                    ActionRecord actionRecord = new ActionRecord("Remove" ,result.getData(), result.getData().getStationAssigned());
                    actionHistory.push(actionRecord);
                }else{
                    //Display remove player failed
                    System.out.println("Player removal failed");
                }
            }else {
                //Inactive player is removed
                boolean removed = playerList.removePlayer(playerId);
                String level = result.getData().getLevel();
                //The player is removed from the relevant queue
                boolean removeQueue = false;
                if (level.equals("Low")) {
                    removeQueue = lowPriorityQueue.removeInactivePlayer(playerId);
                }else if (level.equals("Medium")) {
                    removeQueue = mediumPriorityQueue.removeInactivePlayer(playerId);
                }else if (level.equals("High")) {
                    removeQueue = highPriorityQueue.removeInactivePlayer(playerId);
                }
                //If player is removed from linked list and queue display success message
                if (removed && removeQueue) {
                    System.out.println("Player removed successfully");
                    //Record the action
                    ActionRecord actionRecord = new ActionRecord("Remove" ,result.getData(), result.getData().getStationAssigned());
                    actionHistory.push(actionRecord);
                }else {
                    //Display remove player failed
                    System.out.println("Player removal failed");
                }
            }
        }
    }

    //This method will search for a specific player from the linked list
    public void searchPlayer(){
        //Ask user to enter the player id or name
        System.out.println("\n---- Search Player Menu ----\n");
        System.out.print("Enter player ID or name to search  : ");
        String playerDetails = scanner.nextLine();
        //Set result to null before searching
        Node<Player> result = null;
        //Decide whether the value is an id or a name
        if(playerDetails.matches("P[0-9]{3}")){
            //Call id search
            result = playerList.searchPlayerId(playerDetails);
        }else{
            //Call name search
            result = playerList.searchPlayerName(playerDetails);
        }
        //If search result is found display the player details
        if(result != null){
            System.out.println("\n----Player found successfully----");
            System.out.println("Player ID - " + result.getData().getId());
            System.out.println("Player name - " + result.getData().getName());
            System.out.println("Player skill level - " + result.getData().getLevel());
            System.out.println("Station -" + ((result.getData().getStationAssigned() == -1) ? " Not Assigned" : " Assigned"));
            System.out.println("Player is currently - " + ((result.getData().getIsActive()) ? "Playing" : "Waiting"));
        }
    }

    //This method assigns the player to a station according to the skill level priority
    public void assignNextPlayer (){
        //Ask user to enter the station number
        System.out.println("\n---- Assign Station To Player Menu ----\n");
        System.out.print("Enter station number to assign next  : ");
        String stationNumber = scanner.nextLine();
        //Validate station number entered
        boolean isValid = false;
        if (stationNumber.matches("[0-9]+")) isValid = true;
        //If valid station
        if (isValid) {
            //Create a node to store the player data
            Node<Player> nextPlayer = null;
            //Check the high, medium, low queues respectively since they are prioritized in the given order
            //According to the priority remove the next player in the queue
            if (!highPriorityQueue.isEmpty()){
                nextPlayer = highPriorityQueue.removeFromQueue();
            }else if(!mediumPriorityQueue.isEmpty()){
                nextPlayer = mediumPriorityQueue.removeFromQueue();
            }else if(!lowPriorityQueue.isEmpty()){
                nextPlayer = lowPriorityQueue.removeFromQueue();
            }
            //If to be assigned player was found
            if (nextPlayer != null){
                //Set the objects station and active values
                nextPlayer.getData().setStationAssigned(Integer.parseInt(stationNumber));
                nextPlayer.getData().setIsActive(true);
                //Record and display the output
                System.out.println("Player " + nextPlayer.getData().getId() + " assigned successfully");
                ActionRecord actionRecord = new ActionRecord("Assign" ,nextPlayer.getData(), nextPlayer.getData().getStationAssigned());
                actionHistory.push(actionRecord);
            }else{
                //Display if no player are waiting to be assigned
                System.out.println("No players waiting to be assigned");
            }
        }else{
            //Display to show invalid inputs
            System.out.println("Invalid station number was provided !");
        }

    }

    //This method adds a player to the relevant priority queue
    public void prioritizePlayer(Player player){
        //Checks if the skill level is low and if yes add to low priority queue
        if (player.getLevel().equalsIgnoreCase("low")){
            lowPriorityQueue.addToQueue(player);
        //Checks if the skill level is medium and if yes add to medium priority queue
        }else if (player.getLevel().equalsIgnoreCase("medium")){
            mediumPriorityQueue.addToQueue(player);
        //Checks if the skill level is high and if yes add to high priority queue
        }else if (player.getLevel().equalsIgnoreCase("high")){
            highPriorityQueue.addToQueue(player);
        //If and error occurred display an error
        }else{
            System.out.println("Add to priority queue failed !");
        }
    }

    //This method views the priority queues according to the priority
    public void viewPriorityQueue(){
        System.out.println("\n----Viewing priority queue----\n");
        System.out.println("1. High Priority Queue");
        highPriorityQueue.viewQueue();
        System.out.println("\n2. Medium Priority Queue");
        mediumPriorityQueue.viewQueue();
        System.out.println("\n3. Low Priority Queue");
        lowPriorityQueue.viewQueue();
    }

    //This method restores the last action
    public void undoLastAction (){
        System.out.println("\n---- Undo Last Action ----\n");
        //If no action has been done return the function
        if (actionHistory.isEmpty()){
            System.out.println("No action has been done to be restored !");
        }else {
            //Obtain the latest action
            ActionRecord record = actionHistory.pop();
            //If action is an assignment set player to an un-assigned state
            if (record.getActionRecord().equals("Assign")){
                //Reset player data
                record.getPlayer().setIsActive(false);
                record.getPlayer().setStationAssigned(-1);
                //Re-run queue function and display result
                prioritizePlayer(record.getPlayer());
                System.out.println("Action restored - " + record.getPlayer().getId() + " assignment reversed");
            //If action is a removal restore the player
            }else if (record.getActionRecord().equals("Remove")){
                //Inserts the player to the linked list
                playerList.insertPlayer(record.getPlayer());
                //If player had a station re-insert player in an active state
                if (record.getStation() != -1){
                    //Set values display output
                    record.getPlayer().setIsActive(true);
                    record.getPlayer().setStationAssigned(record.getStation());
                    System.out.println("Action restored - " + record.getPlayer().getId() + " added to station " + record.getPlayer().getStationAssigned());
                }else {
                    //If player was not active re-run and add to priority queue
                    record.getPlayer().setIsActive(false);
                    record.getPlayer().setStationAssigned(-1);
                    prioritizePlayer(record.getPlayer());
                    System.out.println("Action restored - " + record.getPlayer().getId() + " inactive player added to queue");
                }
            }else{
                //Display if any error occurs while restoring
                System.out.println("Action restoration failed");
            }
        }
    }

    //This method displays the main menu
    public boolean loadMainMenu (){
        //Display the main menu selection options
        System.out.println("\n---- Main Menu ----\n");
        System.out.println("1. Register Player");
        System.out.println("2. Remove Player");
        System.out.println("3. Search Player");
        System.out.println("4. Assign Player Station");
        System.out.println("5. View Players by Priority");
        System.out.println("6. Undo Last Action");
        System.out.println("7. Exit System");
        System.out.print("\nEnter Operation Number To Continue : ");
        String operation = scanner.nextLine();
        //Add a flag to check if user wants to exit the system
        boolean isExit = false;
        //Use switch case decision-making and call relevant function for relevant operations
        switch (operation){
            case "1":
                addPlayer();
                break;
            case "2":
                removePlayer();
                break;
            case "3":
                searchPlayer();
                break;
            case "4":
                assignNextPlayer();
                break;
            case "5":
                viewPriorityQueue();
                break;
            case "6":
                undoLastAction();
                break;
            case "7":
                isExit = true;
                break;
            //If operation number is not valid display invalid number
            default:
                System.out.println("Invalid Operation Number !");
        }
        //Return the function
        return isExit;
    }


}
