import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerManagementTest {
    private HotelReservationSystem system;

    @BeforeEach
    public void setup() {
        system = new HotelReservationSystem();
    }

    // CM_001: Add a valid customer
    @Test
    public void CM_001_addValidCustomer() {
        Customer customer = new Customer(1, "Fatima", "12345678");

        assertTrue(system.addCustomer(customer));
        assertEquals(1, system.getCustomersCount());
    }

    // CM_002: Add a null customer
    @Test
    public void CM_002_addNullCustomer() {
        assertFalse(system.addCustomer(null));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_003: Add customer with ID = 0
    @Test
    public void CM_003_addCustomerWithZeroId() {
        Customer customer = new Customer(0, "Fatima", "12345678");

        assertFalse(system.addCustomer(customer));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_004: Add customer with negative ID
    @Test
    public void CM_004_addCustomerWithNegativeId() {
        Customer customer = new Customer(-5, "Fatima", "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_005: Add customer with empty name
    @Test
    public void CM_005_addCustomerWithEmptyName() {
        Customer customer = new Customer(1, "", "12345678");

        assertFalse(system.addCustomer(customer));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_006: Add customer with phone number shorter than 8 digits
    @Test
    public void CM_006_addCustomerWithPhoneTooShort() {
        Customer customer = new Customer(1, "Fatima", "1234567");

        assertFalse(system.addCustomer(customer));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_007: Add customer with duplicate customer ID
    @Test
    public void CM_007_addDuplicateCustomerId() {
        Customer customer1 = new Customer(1, "Fatima", "12345678");
        Customer customer2 = new Customer(1, "Sara", "87654321");

        assertTrue(system.addCustomer(customer1));
        assertFalse(system.addCustomer(customer2));
        assertEquals(1, system.getCustomersCount());
    }

    // CM_008: Delete an existing customer with no active reservation
    @Test
    public void CM_008_deleteExistingCustomer() {
        Customer customer = new Customer(1, "Fatima", "12345678");

        system.addCustomer(customer);

        assertTrue(system.deleteCustomer(1));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_009: Delete a customer that does not exist
    @Test
    public void CM_009_deleteNonExistingCustomer() {
        assertFalse(system.deleteCustomer(99));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_010: Do not delete a customer with an active reservation
    @Test
    public void CM_010_deleteCustomerWithActiveReservation() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Fatima", "12345678");
        Reservation reservation = new Reservation(1, customer, room, "2026-05-20", "2026-05-25");

        system.addRoom(room);
        system.addCustomer(customer);
        system.bookReservation(reservation);

        assertFalse(system.deleteCustomer(1));
        assertEquals(1, system.getCustomersCount());
    }

    // CM_011: Delete a customer after their reservation is cancelled
    @Test
    public void CM_011_deleteCustomerAfterCancelledReservation() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Fatima", "12345678");
        Reservation reservation = new Reservation(1, customer, room, "2026-05-20", "2026-05-25");

        system.addRoom(room);
        system.addCustomer(customer);
        system.bookReservation(reservation);
        system.cancelReservation(1);

        assertTrue(system.deleteCustomer(1));
        assertEquals(0, system.getCustomersCount());
    }
}