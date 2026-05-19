import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HotelReservationSystemRoomTest {

    @Test
    public void testAddValidRoom() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "Single", 50.0);

        boolean result = system.addRoom(room);

        assertTrue(result);
        assertEquals(1, system.getRoomsCount());
    }

    @Test
    public void testAddNullRoom() {
        HotelReservationSystem system = new HotelReservationSystem();

        boolean result = system.addRoom(null);

        assertFalse(result);
        assertEquals(0, system.getRoomsCount());
    }

    @Test
    public void testAddRoomWithInvalidRoomNumber() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(0, "Single", 50.0);

        boolean result = system.addRoom(room);

        assertFalse(result);
        assertEquals(0, system.getRoomsCount());
    }

    @Test
    public void testAddRoomWithEmptyType() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "", 50.0);

        boolean result = system.addRoom(room);

        assertFalse(result);
        assertEquals(0, system.getRoomsCount());
    }

    @Test
    public void testAddRoomWithInvalidPrice() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "Single", 0);

        boolean result = system.addRoom(room);

        assertFalse(result);
        assertEquals(0, system.getRoomsCount());
    }

    @Test
    public void testAddDuplicateRoomNumber() {
        HotelReservationSystem system = new HotelReservationSystem();

        Room room1 = new Room(101, "Single", 50.0);
        Room room2 = new Room(101, "Double", 80.0);

        assertTrue(system.addRoom(room1));
        assertFalse(system.addRoom(room2));
        assertEquals(1, system.getRoomsCount());
    }

    @Test
    public void testDeleteExistingRoom() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "Single", 50.0);

        system.addRoom(room);
        boolean result = system.deleteRoom(101);

        assertTrue(result);
        assertEquals(0, system.getRoomsCount());
    }

    @Test
    public void testDeleteNonExistingRoom() {
        HotelReservationSystem system = new HotelReservationSystem();

        boolean result = system.deleteRoom(999);

        assertFalse(result);
    }

    @Test
    public void testDeleteReservedRoom() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "Single", 50.0);

        system.addRoom(room);
        room.setReserved(true);

        boolean result = system.deleteRoom(101);

        assertFalse(result);
        assertEquals(1, system.getRoomsCount());
    }

    @Test
    public void testDeleteRoomTwice() {
        HotelReservationSystem system = new HotelReservationSystem();
        Room room = new Room(101, "Single", 50.0);

        system.addRoom(room);

        assertTrue(system.deleteRoom(101));
        assertFalse(system.deleteRoom(101));
        assertEquals(0, system.getRoomsCount());
    }
}
