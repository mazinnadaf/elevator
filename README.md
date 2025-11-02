# Elevator Simulation

## Features Implemented
- Single elevator with directional scheduling (up/down queues)
- Dynamic requests while moving
- Capacity limit (max 5 passengers)
- Idle optimization (waits when no requests)
- 5% chance per floor for elevator malfunction
- Elevator tracks current direction to optimize floor servicing
- Threaded simulation

## Assumptions
- Building has `n` floors numbered from 1 to `n`
- Elevator starts at floor 1
- Each request represents one person
- Console-based simulation (no GUI)
- Elevator moves one floor at a time with small delays

## Features Not Yet Implemented
- Multiple elevators
- GUI / visual display
- Advanced scheduling algorithms beyond basic up/down queues
- Prioritization by waiting time or passenger type
