import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CancelReservationTest {

    private HotelReservationSystem system;
    private Room room;
    private Customer customer;
    private Reservation reservation;

    @BeforeEach
    public void setup() {
        system = new HotelReservationSystem();
        room = new Room(101, "Single", 50);
        customer = new Customer(1, "Bushra", "12345678");
        system.addRoom(room);
        system.addCustomer(customer);
        reservation = new Reservation(1, customer, room, "2026-05-20", "2026-05-25");
    }

    // CR_001: Cancel an active reservation successfully
    @Test
    public void CR_001_cancelActiveReservation() {
        system.bookReservation(reservation);
        assertTrue(system.cancelReservation(1));
    }

    // CR_002: Room becomes available after cancellation
    @Test
    public void CR_002_roomBecomesAvailableAfterCancel() {
        system.bookReservation(reservation);
        system.cancelReservation(1);
        assertTrue(system.getAvailableRooms().contains(room));
    }

    // CR_003: Cancel a non-existing reservation ID
    @Test
    public void CR_003_cancelNonExistingReservation() {
        assertFalse(system.cancelReservation(999));
    }

    // CR_004: Cancel an already cancelled reservation
    @Test
    public void CR_004_cancelAlreadyCancelledReservation() {
        system.bookReservation(reservation);
        system.cancelReservation(1);
        assertFalse(system.cancelReservation(1));
    }

    // CR_005: Room can be reserved again after cancellation
    @Test
    public void CR_005_roomCanBeReservedAfterCancel() {
        Customer customer2 = new Customer(2, "Sara", "87654321");
        system.addCustomer(customer2);

        system.bookReservation(reservation);
        system.cancelReservation(1);

        Reservation newReservation = new Reservation(2, customer2, room, "2026-06-01", "2026-06-05");
        assertTrue(system.bookReservation(newReservation));
    }

    // CR_006: Cancel reservation when system has no reservations
    @Test
    public void CR_006_cancelWhenNoReservationsExist() {
        assertFalse(system.cancelReservation(1));
    }

    // CR_007: Reservation count remains unchanged after cancellation
    @Test
    public void CR_007_reservationCountUnchangedAfterCancel() {
        system.bookReservation(reservation);
        int countBefore = system.getReservationsCount();
        system.cancelReservation(1);
        assertEquals(countBefore, system.getReservationsCount());
    }

    // CR_008: Customer can still be deleted after their reservation is cancelled
    @Test
    public void CR_008_customerDeletableAfterReservationCancelled() {
        system.bookReservation(reservation);
        system.cancelReservation(1);
        assertTrue(system.deleteCustomer(1));
    }
}