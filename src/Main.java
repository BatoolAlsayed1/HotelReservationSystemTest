public class Main {
    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();

        Room room1 = new Room(101, "Single", 50);
        Room room2 = new Room(102, "Double", 80);

        Customer customer1 = new Customer(1, "Batool", "12345678");

        System.out.println(system.addRoom(room1));
        System.out.println(system.addRoom(room2));
        System.out.println(system.addCustomer(customer1));

        Reservation reservation1 = new Reservation(1, customer1, room1, "2026-05-20", "2026-05-25");

        System.out.println(system.bookReservation(reservation1));
        System.out.println(system.cancelReservation(1));
    }
}