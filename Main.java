public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Elevator Simulation Start ===");
        
        Elevator elevator = new Elevator(10);
        Thread elevatorThread = new Thread(elevator);
        elevatorThread.start();

        System.out.println("\n--- Initial Requests ---");
        elevator.requestFloor(3);
        Thread.sleep(500);
        elevator.requestFloor(7);
        Thread.sleep(500);

        System.out.println("\n--- Adding new requests while elevator is moving ---");
        Thread.sleep(2000);
        elevator.requestFloor(2);
        Thread.sleep(500);
        elevator.requestFloor(9);

        System.out.println("\n--- Elevator operating normally ---");
        Thread.sleep(10000);  

        System.out.println("\n--- No requests, elevator should idle ---");
        Thread.sleep(3000);

        System.out.println("\n--- Final requests ---");
        elevator.requestFloor(5);
        Thread.sleep(500);
        elevator.requestFloor(1);

        Thread.sleep(8000);  

        System.out.println("\n--- Stopping simulation ---");
        elevator.stopElevator();
        elevatorThread.join();
    }
}
