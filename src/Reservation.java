public class Reservation {
    private int reservationId;
    private Customer customer;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private boolean cancelled;

    public Reservation(int reservationId, Customer customer, Room room, String checkInDate, String checkOutDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.cancelled = false;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
