import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookReservationTest {
    private HotelReservationSystem system;

    @BeforeEach
    public void setup() {
        system = new HotelReservationSystem();
    }

    // HR_001: Reserve available room successfully
    @Test
    public void HR_001_reserveAvailableRoom() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Batool", "12345678");

        system.addRoom(room);
        system.addCustomer(customer);

        Reservation reservation =
                new Reservation(1, customer, room, "2026-05-20", "2026-05-25");

        assertTrue(system.bookReservation(reservation));
    }

    // HR_002: Reserve already reserved room
    @Test
    public void HR_002_reserveAlreadyReservedRoom() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Batool", "12345678");

        system.addRoom(room);
        system.addCustomer(customer);

        Reservation reservation1 =
                new Reservation(1, customer, room, "2026-05-20", "2026-05-25");

        Reservation reservation2 =
                new Reservation(2, customer, room, "2026-06-01", "2026-06-05");

        assertTrue(system.bookReservation(reservation1));
        assertFalse(system.bookReservation(reservation2));
    }

    // HR_003: Reserve non-existing room
    @Test
    public void HR_003_reserveNonExistingRoom() {
        Customer customer = new Customer(1, "Batool", "12345678");
        Room nonExistingRoom = new Room(105, "Single", 50);

        system.addCustomer(customer);

        Reservation reservation =
                new Reservation(3, customer, nonExistingRoom, "2026-05-20", "2026-05-25");

        assertFalse(system.bookReservation(reservation));
    }

    // HR_004: Reserve for non-existing customer
    @Test
    public void HR_004_reserveForNonExistingCustomer() {
        Room room = new Room(101, "Single", 50);
        Customer nonExistingCustomer = new Customer(105, "Sara", "87654321");

        system.addRoom(room);

        Reservation reservation =
                new Reservation(4, nonExistingCustomer, room, "2026-05-20", "2026-05-25");

        assertFalse(system.bookReservation(reservation));
    }

    // HR_005: Duplicate reservation ID
    @Test
    public void HR_005_duplicateReservationId() {
        Room room1 = new Room(101, "Single", 50);
        Room room2 = new Room(102, "Double", 80);
        Customer customer = new Customer(1, "Batool", "12345678");

        system.addRoom(room1);
        system.addRoom(room2);
        system.addCustomer(customer);

        Reservation reservation1 =
                new Reservation(1, customer, room1, "2026-05-20", "2026-05-25");

        Reservation reservation2 =
                new Reservation(1, customer, room2, "2026-06-01", "2026-06-05");

        assertTrue(system.bookReservation(reservation1));
        assertFalse(system.bookReservation(reservation2));
    }
}

