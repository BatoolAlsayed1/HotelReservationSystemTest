import java.util.ArrayList;
public class HotelReservationSystem {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();

    // Functionality 1: Room Management
    public boolean addRoom(Room room) {
        if (room == null) {
            return false;
        }

        if (room.getRoomNumber() <= 0 || room.getRoomType() == null || room.getRoomType().isEmpty() || room.getPrice() <= 0) {
            return false;
        }

        for (Room r : rooms) {
            if (r.getRoomNumber() == room.getRoomNumber()) {
                return false;
            }
        }

        rooms.add(room);
        return true;
    }

    public boolean deleteRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                if (room.isReserved()) {
                    return false;
                }

                rooms.remove(room);
                return true;
            }
        }

        return false;
    }

    public int getRoomsCount() {
        return rooms.size();
    }

    // Functionality 2: Customer Management
    public boolean addCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }

        if (customer.getCustomerId() <= 0 || customer.getName() == null || customer.getName().isEmpty()
                || customer.getPhone() == null || customer.getPhone().length() < 8) {
            return false;
        }

        for (Customer c : customers) {
            if (c.getCustomerId() == customer.getCustomerId()) {
                return false;
            }
        }

        customers.add(customer);
        return true;
    }

    public boolean deleteCustomer(int customerId) {
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getCustomerId() == customerId && !reservation.isCancelled()) {
                return false;
            }
        }

        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                customers.remove(customer);
                return true;
            }
        }

        return false;
    }

    public int getCustomersCount() {
        return customers.size();
    }

    // Functionality 3: Book Reservation
    public boolean bookReservation(Reservation reservation) {
        if (reservation == null || reservation.getRoom() == null || reservation.getCustomer() == null) {
            return false;
        }

        Room room = findRoom(reservation.getRoom().getRoomNumber());
        Customer customer = findCustomer(reservation.getCustomer().getCustomerId());

        if (room == null || customer == null) {
            return false;
        }

        if (room.isReserved()) {
            return false;
        }

        for (Reservation r : reservations) {
            if (r.getReservationId() == reservation.getReservationId()) {
                return false;
            }
        }

        room.setReserved(true);
        reservations.add(reservation);
        return true;
    }

    public int getReservationsCount() {
        return reservations.size();
    }

    // Functionality 4: Cancel Reservation
    public boolean cancelReservation(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                if (reservation.isCancelled()) {
                    return false;
                }

                reservation.setCancelled(true);
                reservation.getRoom().setReserved(false);
                return true;
            }
        }

        return false;
    }

    public ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (!room.isReserved()) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    private Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }

        return null;
    }

    private Customer findCustomer(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }

        return null;
    }
}
