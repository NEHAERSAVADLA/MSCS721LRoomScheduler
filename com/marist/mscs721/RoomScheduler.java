package com.marist.mscs721;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** author michael gildein
contribution neha**/
/**
 *In this class the user has choice to choose from options such as add a room, remove a room,
 * schedule a room, list the schedule, list the rooms, export the rooms and meetings to JSON, and import the rooms
 * and meetings from JSON.
 //TODO Import and export using Json
 * 
 */
public class RoomScheduler {
  protected static Scanner scanner = new Scanner(System.in);
  static final String ERR_MSG1 = "Integers only, please. Try again.";
  static Logger logger = Logger.getLogger(RoomScheduler.class);
  static boolean debugMode = true;
  static boolean systemTest = true;
  private static final Gson gson = new Gson();

  /**
   * This is the main menu
   //TODO Add a default case to the swith case
   //FIXME logger instead of system.out
   //TODO add a case to quit program
   */
  public static void main(String[] args) throws IOException {
    Boolean end = false;
    ArrayList<Room> rooms = new ArrayList<Room>();


    while (!end) {
      switch (mainMenu()) {

        case 1:
          String return1 = addRoom(rooms);
          System.out.println(return1);
          logger.info(return1);
          break;
        case 2:
          String return2 = removeRoom(rooms);
          System.out.println(return2);
          logger.info(return2);
          break;
        case 3:
          String return3 = scheduleRoom(rooms);
          System.out.println(return3);
          logger.info(return3);
          break;
        case 4:
          String return4 = listSchedule(rooms);
          System.out.println(return4);
          logger.info(return4);
          break;
        case 5:
          String return5 = listRooms(rooms);
          System.out.println(return5);
          logger.info(return5);
          break;
        case 6:
          String return6 = exportToJson(rooms);
          System.out.println(return6);
          logger.info(return6);
          break;
        case 7:
          String return7 = importFromJson(rooms);
          System.out.println(return7);
          logger.info(return7);
          break;
        case 8:
          end = true;
          logger.info("Quit program");
          break;
        default:
          break;
      }

    }

  }

  /**
   * This method lists the schedules for a specified room. 
   */
  protected static String listSchedule(ArrayList<Room> roomList) {
    String roomName = getRoomName();
    System.out.println(roomName + " Schedule");
    System.out.println("----------------");

    if (getRoomFromName(roomList, roomName).getCapacity() == -1) {
      logger.error("Room does not exist.");
      return "Room does not exist.";
    } else {
      for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
        System.out.println(m.toString());
      }
      System.out.println("--------------------------");

      return "";
    }
  }

  /**
   * This is the main menu for the program. It lists all of the options that the user can choose from, and then accepts
   * the user's input for a number.
   *
   * @return int  the user's number that they entered
   */
  protected static int mainMenu() throws IOException {
    int selection = 0;

    System.out.println("Main Menu:");
    System.out.println("  1 - Add a room");
    System.out.println("  2 - Remove a room");
    System.out.println("  3 - Schedule a room");
    System.out.println("  4 - List Schedule");
    System.out.println("  5 - List Rooms");
    System.out.println("  6 - Export to JSON");
    System.out.println("  7 - Import from JSON");
    System.out.println("  8 - Quit program");
    System.out.println("Enter your selection: ");

    // Forces integer input
    try {
      selection = scanner.nextInt();
      logger.info(selection);
    } catch (InputMismatchException exception) {
      logger.error(exception);
      System.out.println(ERR_MSG1);
      scanner.nextLine();
    }

    if (selection < 1 || selection > 8) {
      logger.error("Please enter a valid number from 1 - 8.");
      System.out.println("Please enter a valid number from 1 - 8.");
    }
    return selection;
  }

  /**
   * This method adds a room to the room list along with the information
   *
   * @param roomList the list of rooms that have been created
   * @return String   a string that displays the room name and that it was created successfully.
   */
  protected static String addRoom(ArrayList<Room> roomList) throws IOException {
    int capacity;
    System.out.println("Add a room:");
    String name = getRoomName();
    System.out.println("Room capacity?");

    // Forces input to be an integer
    try {
      capacity = scanner.nextInt();
      scanner.nextLine();
      logger.info(capacity);
    } catch (InputMismatchException exception) {
      logger.error(exception);
      System.out.println(ERR_MSG1);
      scanner.nextLine();
      return "";
    }
//TODO  add minimum capacity to the room
    if(capacity <= 5){
      logger.error("Room capacity must be at least 5.");
      System.out.println("Room capacity must be at least 5.");
      return "";
    }

    System.out.println("Enter building name: ");
    String building = scanner.nextLine();
    logger.info(building);

    System.out.println("Enter location name: ");
    String location = scanner.nextLine();
    logger.info(location);

    Room newRoom = new Room(name, capacity, building, location);
    roomList.add(newRoom);

    return "Room '" + newRoom.getName() + "' added successfully!";
  }

  /**
   * This method removes a room from the room list. 
   *
   * @param roomList the list of rooms that have been created
   * @return String    a string that says the room was removed successfully.
   */
  public static String removeRoom(ArrayList<Room> roomList) {
    System.out.println("Remove a room:");
    String roomName = getRoomName();

    // Checks if room exists
    if (findRoomIndex(roomList, roomName) == -1) {
      return "";
    }
    else {
      roomList.remove(findRoomIndex(roomList, roomName));
    }

    return "Successfully removed " + roomName + "!";
  }

  /**
   * This method lists the rooms in the room list. It iterates through each room.
   *
   * @param roomList the list of rooms that have been created
   * @return String    a string that displays how many rooms are in the room list
   */
  protected static String listRooms(ArrayList<Room> roomList) {
    System.out.println("Room Name  \tCapacity \tBuilding \tLocation");
    System.out.println("-----------------------");

    for (Room room : roomList) {
      System.out.println(room.getName() + "\t\t" + room.getCapacity() + "\t\t" + room.getBuilding() + "\t" +
                                                                                          room.getLocation());
    }

    System.out.println("----------------------");

    return roomList.size() + " Room(s)";
  }

  /**
   * This method schedules a meeting for a specified room.
   * <p>
   * It then checks to see if there is any schedule conflict with an existing meeting. 
   */
  protected static String scheduleRoom(ArrayList<Room> roomList) {
    System.out.println("Schedule a room:");
    scanner.nextLine();
    System.out.println("Start Date? (yyyy-mm-dd):");
    String startDate = scanner.nextLine();
    logger.info(startDate);
    System.out.println("Start Time? (h:mm):");
    String startTime = scanner.nextLine();
    logger.info(startTime);
    startTime = startTime + ":00.0";

    System.out.println("End Date? (yyyy-mm-dd):");
    String endDate = scanner.nextLine();
    logger.info(endDate);
    System.out.println("End Time? (h:mm):");
    String endTime = scanner.nextLine();
    logger.info(endTime);
    endTime = endTime + ":00.0";
    //TODO TIMESTAMP
    // Checks if user input matches proper Timestamp format
    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    try {
      format.parse(startDate + " " + startTime);
    } catch (ParseException e) {
      System.out.println("Invalid format for start date or start time.");
      logger.error(e);
      return "";
    }

    Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);

    // Same as above but for endTimestamp
    try {
      format.parse(endDate + " " + endTime);
    } catch (ParseException e) {
      System.out.println("Invalid format for end date or end time.");
      logger.error(e);
      return "";
    }

    Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

    Date date= new Date();
    long time = date.getTime();
    Timestamp currentTimestamp = new Timestamp(time);
    if(currentTimestamp.after(endTimestamp) || currentTimestamp.after(startTimestamp)) {
      System.out.println("Sorry, a meeting cannot be created in the past");
      logger.error("Sorry, a meeting cannot be created in the past");
      return "";
    }

    if(!roomList.isEmpty()) {
      System.out.println("Would you like to see a list of available rooms? Y/N");
      logger.info("Would you like to see a list of available rooms? Y/N");
      String input = scanner.next();
      logger.info(input);

      if (input.toUpperCase().charAt(0) == 'Y') {
        String answer = roomsAvailable(startTimestamp, endTimestamp, roomList);
        System.out.println(answer);
        logger.info(answer);
      }
    }

    String name = getRoomName();
    Room curRoom = getRoomFromName(roomList, name);

    if (curRoom.getCapacity() == -1) {
      return "";
    }

    if(isScheduleValid(roomList, endTimestamp, startTimestamp, name) == false && systemTest == false){
      return "";
    }

    System.out.println("Subject?");
    String subject = scanner.nextLine();
    logger.info(subject);

    Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

    curRoom.addMeeting(meeting);

    return "Successfully scheduled meeting for " + name + "!\n";
  }


  /**
   * This method allows the user to export the room list to JSON so that they can save the room list for a later
   * time. It uses gson to convert the roomlist into JSON, and then saves it to a file called "file.json".
   *
   * @param roomList the list of rooms that have been created
   * @return String  a string that displays that the objects were successfully exported to JSON
   */
  public static String exportToJson(ArrayList<Room> roomList) throws IOException {


    String json = gson.toJson(roomList);
    if(debugMode) {
      logger.debug(json);
      System.out.println(json);
    }

    System.out.print("Enter name of file you want to export to.");
    // Eats newline char
    scanner.nextLine();
    String filename = scanner.nextLine();

    try (FileWriter writer = new FileWriter(filename)) {
      gson.toJson(roomList, writer);
    } catch (IOException e) {
      logger.error(e);
      return "Error related to JSON file has occurred.";
    }

    return "Successfully exported objects to JSON!";
  }

  /**
   * This method allows a user to import a JSON file into the program. It checks for a file name "file.json" in the
   * RoomScheduler folder and converts it into an array. It then adds that array to the room list in the program.
   *
   * @param roomList the list of rooms that have been created
   * @return String  a string that displays that the import was successful
   //FIXME JSON EXCEPTION
   */
  public static String importFromJson(ArrayList<Room> roomList) throws IOException {
    System.out.println("Enter filename you wish to import from.");
    // Eats newline char
    scanner.nextLine();
    String filename = scanner.nextLine();
    logger.info(filename);

    try{
      JsonParser parser = new JsonParser();
      if(filename.contains("/")) {
        parser.parse(filename.substring(filename.lastIndexOf('/') + 1));
      }
      else if(filename.contains("\\")){
        parser.parse(filename.substring(filename.lastIndexOf('\\') + 1));
      }
      else {
        parser.parse(filename);
      }
    }
    catch(JsonSyntaxException jse) {
      logger.error(jse);
      return "Not a valid Json String";
    }

    try (Reader reader = new FileReader(filename)) {
      Room[] roomArr = gson.fromJson(reader, Room[].class);

      if(roomArr.length == 0){
        return "JSON file is empty.";
      }


      for (int i = 0; i < roomArr.length; i++) {
        roomList.add(roomArr[i]);
      }
    } catch (Exception e) { // Catches IO exceptions and null pointer exceptions
      logger.error(e);
      return "Error related to JSON file has occurred.";
    }

    return "Successfully imported JSON to objects!";
  }

  /**
   * This method gets the room object based on the user inputted room name. It takes the room list and name and checks
   * if the room is in the room list. If it is, then it returns the room.
   *
   * @param roomList the list of rooms that have been created
   * @param name     the name of the room that the user entered
   * @return Room    the room that was found in the room list
   */
  protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
    // Checks if room exists in roomList
    if (findRoomIndex(roomList, name) == -1) {
      return new Room("error", -1, "N/A", "N/A");
    }
    return roomList.get(findRoomIndex(roomList, name));
  }

  /**
   * This method is used to get the index of the room. The index can then be used to find a user specified room. The
   * index starts at 0 and compares each room in the list to the user submitted room name. If the room is found, then
   * it exits the for loop. If it is not found then it increments the room index.
   *
   * @param roomList the list of rooms that have been created
   * @param roomName the name of the room that the user entered
   * @return int     the roomIndex where the room is located
   */
  protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
    int roomIndex = 0;

    if (roomList.isEmpty()) {
      logger.error("There are no rooms with that name.");
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    for (Room room : roomList) {
      if (room.getName().compareTo(roomName) == 0) {
        break;
      }
      roomIndex++;
    }

    // If no room name matches the rooms in the room list, it returns a -1
    if (roomIndex >= roomList.size()) {
      logger.error("There are no rooms with that name.");
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    return roomIndex;
  }

  /**
   * This method prompts the user for a room name and returns the user's input.
   *
   * @return String  the name of the room that the user entered.
   */
  protected static String getRoomName() {
    String selection;

    System.out.println("Room Name?");
    // Eats new line character input
    scanner.nextLine();
    selection = scanner.nextLine();
    logger.info(selection);

    return selection;
  }

  /**
   * 
   *
   * @param startTimestamp    the timestamp for the beginning of the meeting
   * @param endTimestamp      the timestamp for the end of the meeting
   * @param roomList          the room list that contains all of the rooms
   * @return String           a string that tells the user how many rooms are available
   */
  public static String roomsAvailable(Timestamp startTimestamp, Timestamp endTimestamp, ArrayList<Room> roomList){
    ArrayList<Room> availableList = new ArrayList<>();
    boolean conflictFound = false;
    if(!roomList.isEmpty()) {
      for (int i = 0; i < roomList.size(); i++) {
        conflictFound = false;
        if(!roomList.get(i).getMeetings().isEmpty()) {
          for(int j = 0; j < roomList.get(i).getMeetings().size(); j++) {
            if (!roomList.get(i).getMeetings().get(j).getStartTime().equals(startTimestamp) &&
                    !roomList.get(i).getMeetings().get(j).getStopTime().equals(endTimestamp)) {
              conflictFound = false;
            } else if (startTimestamp.before(roomList.get(i).getMeetings().get(j).getStartTime()) &&
                    endTimestamp.before(roomList.get(i).getMeetings().get(j).getStopTime())) {
              conflictFound = false;
            } else if (startTimestamp.after(roomList.get(i).getMeetings().get(j).getStartTime()) &&
                    endTimestamp.after(roomList.get(i).getMeetings().get(j).getStopTime())) {
              conflictFound = false;
            } else {
              conflictFound = true;
              break;
            }
          }
        }
        if (conflictFound == false){
          availableList.add(roomList.get(i));
        }
      }
    }
    if(!availableList.isEmpty()) {
      System.out.println("-------------------------");
      System.out.println("List of available rooms");
      for (int i = 0; i < availableList.size(); i++) {
        System.out.println(availableList.get(i).getName());
      }
      System.out.println("-----------------------");
    }
    else{
      return "No rooms are available";
    }
    return availableList.size() + " Room(s) available\n";
  }

  /** This method Checks if a start time starts in the middle of an existing meeting, checks if an end time ends in the
   * middle of an existing meeting, checks if a meeting starts before and ends after an existing meeting, checks if a
   * start time is the same start time as an existing meeting, and checks if the end time is the same as the end time
   * of an existing meeting.
   **/
  private static boolean isScheduleValid(ArrayList<Room> roomList, Timestamp endTimestamp, Timestamp startTimestamp,
                                         String name) {

    if (!roomList.isEmpty() && getRoomFromName(roomList, name).getMeetings().size() > 0) {
      for (int i = 0; i < roomList.size(); i++) {
        for (int j = 0; j < roomList.get(i).getMeetings().size(); j++) {
          if (startTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(j).getStartTime())
                  && startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(j).getStopTime())) {
            System.out.println("A meeting cannot start in the middle of another meeting");
            logger.error("A meeting cannot start in the middle of another meeting");
            return false;
          } else if (startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(j).getStartTime())
                  && endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(j).getStopTime())) {
            System.out.println("A meeting cannot start before and end after another meeting.");
            logger.error("A meeting cannot start before and end after another meeting.");
            return false;
          } else if (endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(j).getStartTime())
                  && endTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(j).getStopTime())) {
            System.out.println("A meeting cannot end in the middle of another meeting");
            logger.error("A a meeting cannot end in the middle of another meeting");
            return false;
          } else if (startTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(j).getStartTime())) {
            System.out.println("A a meeting start time cannot be the same as another meeting start time");
            logger.error("A a meeting start time cannot be the same as another meeting start time");
            return false;
          } else if (endTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(j).getStopTime())) {
            System.out.println("A meeting end time cannot be the same as another meeting end time");
            logger.error("A a meeting end time cannot be the same as another meeting end time");
            return false;
          }
        }
      }
    }
    return true;
  }
}
