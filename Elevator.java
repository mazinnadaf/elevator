/*Features Implemented
    - Single elevator with directional scheduling (up/down queues)
    - Dynamic requests while moving
    - Capacity limit (max 5 passengers)
    - Idle optimization (waits when no requests)
    - 5% chance per floor for elevator malfunction
    - Elevator tracks current direction to optimize floor servicing
    - Threaded simulation
Assumptions
    - Building has n floors numbered from 1 to n
    - Elevator starts at floor 1
    - Each request represents one person
    - Console-based simulation (no GUI)
    - Elevator moves one floor at a time with small delays
Features Not Yet Implemented
    - Multiple elevators
    - GUI / visual display
    - Advanced scheduling algorithms beyond basic up/down queues
    - Prioritization by waiting time or passenger type*/

import java.util.PriorityQueue;

public class Elevator implements Runnable {
    private int currentFloor;
    private int numFloors;

    private PriorityQueue<Integer> upQueue; 
    private PriorityQueue<Integer> downQueue; 

    private int capacity = 5;      
    private int currentLoad = 0;
    
    private boolean running = true;
    private boolean goingUp = true; 
    private java.util.Random random = new java.util.Random();

    public Elevator(int numFloors) {
        this.numFloors = numFloors;
        this.currentFloor = 1;

        this.upQueue = new PriorityQueue<>();                  
        this.downQueue = new PriorityQueue<>((a, b) -> b - a); 
    }

    // Stops the elevator thread
    public void stopElevator() {
        running = false;
    }

    // Adds a new floor request and updates current load
    public synchronized void requestFloor(int floor) {
        if (floor < 1 || floor > numFloors) {
            System.out.println("Invalid floor: " + floor);
            return;
        }

        if (currentLoad >= capacity) {
            System.out.println("Cannot add request for floor " + floor + ": Elevator overloaded!");
            return;
        } else {
            currentLoad++;
            System.out.println("Person entered for floor " + floor + ". Current load: " + currentLoad);
        }

        if (floor > currentFloor) {
            upQueue.add(floor);
            System.out.println("Request added to upQueue: " + floor);
        } else if (floor < currentFloor) {
            downQueue.add(floor);
            System.out.println("Request added to downQueue: " + floor);
        } else {
            System.out.println("Already on floor " + floor);
        }
    }    

    // Main elevator loop: moves the elevator according to queues and direction
    @Override
    public void run() {
        while (running) {
            synchronized (this) {
                if (goingUp) {
                    if (!upQueue.isEmpty()) {
                        while (!upQueue.isEmpty()) {
                            int target = upQueue.poll();
                            moveToFloor(target);
                        }
                    } else if (!downQueue.isEmpty()) {
                        goingUp = false; 
                    }
                } else { 
                    if (!downQueue.isEmpty()) {
                        while (!downQueue.isEmpty()) {
                            int target = downQueue.poll();
                            moveToFloor(target);
                        }
                    } else if (!upQueue.isEmpty()) {
                        goingUp = true; 
                    }
                }
            }
    
            try {
                Thread.sleep(300); 
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    
        System.out.println("Elevator stopped.");
    }
    
    // Moves elevator to target floor, handles arrival, load, and random malfunctions
    private void moveToFloor(int target) {
        while (currentFloor < target) {
            currentFloor++;
            System.out.println("Moving up... now at floor " + currentFloor);
        }
        while (currentFloor > target) {
            currentFloor--;
            System.out.println("Moving down... now at floor " + currentFloor);
        }
        // 5% chance elevator gets stuck
        if (random.nextInt(100) < 5) { 
            System.out.println("Elevator malfunction! Stuck between floors... calling for help!");
            
            try {
                for (int i = 10; i > 0; i--) {
                    System.out.println("Waiting for technician... " + i + " seconds remaining");
                    Thread.sleep(1000); 
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
            System.out.println("Maintenance resolved issue. Resuming operation...");
        }
        
        currentLoad = Math.max(0, currentLoad - 1);
        System.out.println("Arrived at floor " + target + ". Current load: " + currentLoad);
    }
}
