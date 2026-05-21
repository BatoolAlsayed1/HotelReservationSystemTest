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
    }

    // CM_004: Add customer with negative ID
    @Test
    public void CM_004_addCustomerWithNegativeId() {
        Customer customer = new Customer(-5, "Fatima", "12345678");
        assertFalse(system.addCustomer(customer));
    }


    // CM_005: Add customer with null name
    @Test
    public void CM_005_addCustomerWithNullName() {
        Customer customer = new Customer(1, null, "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_006: Add customer with empty name
    @Test
    public void CM_006_addCustomerWithEmptyName() {
        Customer customer = new Customer(1, "", "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_007: Whitespace-only name should be rejected.
    // DEFECT: code uses isEmpty(), so "   " is accepted -> this test FAILS.
    @Test
    public void CM_007_addCustomerWithWhitespaceName() {
        Customer customer = new Customer(1, "   ", "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_008: Name with leading/trailing spaces should be trimmed or rejected.
    // DEFECT: code stores "  Fatima  " as-is -> this test FAILS.
    @Test
    public void CM_008_addCustomerWithUntrimmedName() {
        Customer customer = new Customer(1, "  Fatima  ", "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_009: Numeric-only name should be rejected (names contain letters).
    // DEFECT: code accepts "123456" -> this test FAILS.
    @Test
    public void CM_009_addCustomerWithNumericName() {
        Customer customer = new Customer(1, "123456", "12345678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_010: Very long name should be rejected (reasonable max length).
    // DEFECT: code has no maximum length check -> this test FAILS.
    @Test
    public void CM_010_addCustomerWithVeryLongName() {
        Customer customer = new Customer(1, "A".repeat(300), "12345678");
        assertFalse(system.addCustomer(customer));
    }


    // CM_011: Add customer with null phone
    @Test
    public void CM_011_addCustomerWithNullPhone() {
        Customer customer = new Customer(1, "Fatima", null);
        assertFalse(system.addCustomer(customer));
    }

    // CM_012: Phone shorter than 8 digits is rejected (lower boundary - 1)
    @Test
    public void CM_012_addCustomerWithPhoneTooShort() {
        Customer customer = new Customer(1, "Fatima", "1234567");
        assertFalse(system.addCustomer(customer));
    }

    // CM_013: Phone with exactly 8 digits is accepted (lower boundary)
    @Test
    public void CM_013_addCustomerWithPhoneExactly8Digits() {
        Customer customer = new Customer(1, "Fatima", "12345678");
        assertTrue(system.addCustomer(customer));
    }

    // CM_014: Phone within valid range (11 digits) is accepted.
    // International hotel guests have numbers of varying length (E.164 allows up to 15).
    @Test
    public void CM_014_addCustomerWithValidLongPhone() {
        Customer customer = new Customer(1, "Fatima", "12345678999");
        assertTrue(system.addCustomer(customer));
    }

    // CM_015: Phone exceeding the E.164 maximum (15 digits) should be rejected.
    // DEFECT: code has no maximum length check, so a 20-digit phone is accepted -> FAILS.
    @Test
    public void CM_015_addCustomerWithPhoneTooLong() {
        Customer customer = new Customer(1, "Fatima", "12345678901234567890");
        assertFalse(system.addCustomer(customer));
    }

    // CM_016: Phone with letters should be rejected (digits only).
    // DEFECT: code only checks length -> "12AB5678" accepted -> this test FAILS.
    @Test
    public void CM_016_addCustomerWithLettersInPhone() {
        Customer customer = new Customer(1, "Fatima", "12AB5678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_017: Phone with symbols should be rejected (digits only).
    // DEFECT: "1234-678" accepted -> this test FAILS.
    @Test
    public void CM_017_addCustomerWithSymbolsInPhone() {
        Customer customer = new Customer(1, "Fatima", "1234-678");
        assertFalse(system.addCustomer(customer));
    }

    // CM_018: Phone with spaces should be rejected (digits only).
    // DEFECT: "1234 5678" accepted -> this test FAILS.
    @Test
    public void CM_018_addCustomerWithSpacesInPhone() {
        Customer customer = new Customer(1, "Fatima", "1234 5678");
        assertFalse(system.addCustomer(customer));
    }


    // CM_019: Add customer with duplicate ID
    @Test
    public void CM_019_addDuplicateCustomerId() {
        Customer customer1 = new Customer(1, "Fatima", "12345678");
        Customer customer2 = new Customer(1, "Sara", "87654321");
        assertTrue(system.addCustomer(customer1));
        assertFalse(system.addCustomer(customer2));
        assertEquals(1, system.getCustomersCount());
    }

    // CM_020: Duplicate phone number should be rejected.
    // DEFECT: code checks duplicate ID only, not duplicate phone -> this test FAILS.
    @Test
    public void CM_020_addDuplicatePhoneNumber() {
        Customer customer1 = new Customer(1, "Fatima", "12345678");
        Customer customer2 = new Customer(2, "Sara", "12345678");
        system.addCustomer(customer1);
        assertFalse(system.addCustomer(customer2));
    }


    // CM_021: Delete an existing customer with no reservations
    @Test
    public void CM_021_deleteExistingCustomer() {
        Customer customer = new Customer(1, "Fatima", "12345678");
        system.addCustomer(customer);
        assertTrue(system.deleteCustomer(1));
        assertEquals(0, system.getCustomersCount());
    }

    // CM_022: Delete a customer that does not exist
    @Test
    public void CM_022_deleteNonExistingCustomer() {
        assertFalse(system.deleteCustomer(99));
    }

    // CM_023: Delete customer who has an active (non-cancelled) reservation
    @Test
    public void CM_023_deleteCustomerWithActiveReservation() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Fatima", "12345678");
        system.addRoom(room);
        system.addCustomer(customer);
        Reservation reservation = new Reservation(1, customer, room, "2026-05-20", "2026-05-25");
        system.bookReservation(reservation);
        assertFalse(system.deleteCustomer(1));
        assertEquals(1, system.getCustomersCount());
    }

    // CM_024: Delete customer after their reservation is cancelled
    @Test
    public void CM_024_deleteCustomerAfterCancelledReservation() {
        Room room = new Room(101, "Single", 50);
        Customer customer = new Customer(1, "Fatima", "12345678");
        system.addRoom(room);
        system.addCustomer(customer);
        Reservation reservation = new Reservation(1, customer, room, "2026-05-20", "2026-05-25");
        system.bookReservation(reservation);
        system.cancelReservation(1);
        assertTrue(system.deleteCustomer(1));
    }


    // CM_025: Count on an empty system = 0
    @Test
    public void CM_025_countOnEmptySystem() {
        assertEquals(0, system.getCustomersCount());
    }

    // CM_026: Count after adding two customers = 2
    @Test
    public void CM_026_countAfterAddingTwoCustomers() {
        system.addCustomer(new Customer(1, "Fatima", "12345678"));
        system.addCustomer(new Customer(2, "Sara", "87654321"));
        assertEquals(2, system.getCustomersCount());
    }

    // CM_027: Count after adding two then deleting one = 1
    @Test
    public void CM_027_countAfterAddingAndDeleting() {
        system.addCustomer(new Customer(1, "Fatima", "12345678"));
        system.addCustomer(new Customer(2, "Sara", "87654321"));
        system.deleteCustomer(1);
        assertEquals(1, system.getCustomersCount());
    }
}
