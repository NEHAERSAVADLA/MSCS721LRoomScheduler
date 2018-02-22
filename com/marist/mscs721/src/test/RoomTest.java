import com.marist.mscs721.Meeting;
import com.marist.mscs721.Room;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 * Created by neha
 */
public class RoomTest {

  Room testRoom = new Room("test", 30, "testBuilding", "testLocation");

  /**
   * This method tests if the name given to testRoom is the same name as the one that gets returned by getName()
   */
  @Test
  public void testGetName(){
    assertEquals("test", testRoom.getName());
  }

  /**
   * This method tests if the capacity given to testRoom is the same as the capacity that gets returned by getCapacity()
   */
  @Test
  public void testGetCapacity(){
    assertEquals(30, testRoom.getCapacity());
  }

  /**
   * This method tests if the setName() method gives testRoom a new name
   */
  @Test
  public void testSetName(){
    testRoom.setName("New name");
    assertEquals("New name", testRoom.getName());
  }

  /**
   * This method tests if setCapacity gives testRoom a new capacity
   */
  @Test
  public void testSetCapacity(){
    testRoom.setCapacity(10);
    assertEquals(10, testRoom.getCapacity());
  }

  /**
   * This method tests the addMeeting function and checks that the meeting added gets return as the first meeting
   * in the array list returned by getMeetings()
   */
  @Test
  public void testAddMeeting(){
    Meeting testMeeting = new Meeting(Timestamp.valueOf("2018-02-22 3:00:00.0"), Timestamp.valueOf("2018-02-22 4:00:00.0"),
        "MSCS721");
    testRoom.addMeeting(testMeeting);
    assertEquals(testMeeting, testRoom.getMeetings().get(0));
  }

  /**
   * This method tests the setMeetings method by making three meetings and adding them to an array list. It then compares
   * each meeting to the meetings in the array list returned by getMeetings()
   */
  @Test
  public void testSetMeetings(){
    ArrayList<Meeting> meetings = new ArrayList<>();
    Meeting testMeeting0 = new Meeting(Timestamp.valueOf("2018-02-22 3:00:00.0"), Timestamp.valueOf("2018-02-22 4:00:00.0"),
        "MSCS721");
    Meeting testMeeting1 = new Meeting(Timestamp.valueOf("2018-02-22 5:00:00.0"), Timestamp.valueOf("2018-02-22 6:00:00.0"),
        "MSCS721");
    Meeting testMeeting2 = new Meeting(Timestamp.valueOf("2018-02-22 7:00:00.0"), Timestamp.valueOf("2018-02-22 8:00:00.0"),
        "MSCS721");
    meetings.add(testMeeting0);
    meetings.add(testMeeting1);
    meetings.add(testMeeting2);
    testRoom.setMeetings(meetings);

    assertEquals(testMeeting0, testRoom.getMeetings().get(0));
    assertEquals(testMeeting1, testRoom.getMeetings().get(1));
    assertEquals(testMeeting2, testRoom.getMeetings().get(2));
  }

  /**
   * This method tests the setCapacity method by setting the capacity to the min value and checking that it returns
   * that same value.
   */
  @Test
  public void testMinCapacity(){
    testRoom.setCapacity(5);
    assertEquals(5, testRoom.getCapacity());
  }

  /**
   * This method tests the setCapacity method by setting the capacity to the min value minus one. Then it checks to see
   * if the capacity returns -1 which is what happens when capacity gets set to an invalid integer.
   */
  @Test
  public void test1BelowMinCapacity(){
    testRoom.setCapacity(4);
    assertEquals(-1, testRoom.getCapacity());
  }

  /**
   * This method tests if the setBuilding method sets a new building name to the Room class.
   */
  @Test
  public void testSetBuilding(){
    testRoom.setBuilding("newBuilding");
    assertEquals("newBuilding", testRoom.getBuilding());
  }

  /**
   * This method tests that the getBuilding method returns the original building name for the Room class.
   */
  @Test
  public void testGetBuilding(){
    assertEquals("testBuilding", testRoom.getBuilding());
  }

  /**
   * This method tests if the setLocation method sets a new location name to the Room class.
   */
  @Test
  public void testSetLocation(){
    testRoom.setLocation("newLocation");
    assertEquals("newLocation", testRoom.getLocation());
  }

  /**
   * This method tests that the getLocation method returns the original location name for the Room class.
   */
  @Test
  public void testGetLocation(){
    assertEquals("testLocation", testRoom.getLocation());
  }
}
