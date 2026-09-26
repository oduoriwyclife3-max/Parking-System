A fullstack automated parking management system designed to streamline vehicle entry, real time slot visualization, dynamic fee calculation, and automated barrier control.

a.
AUTOMATED PARKING SYSTEM ALGORITHM

BEGIN
Display available parking slots
WHILE system is running DO
  Display main menu:
   1) Vehicle Entry
   2) Vehicle Exit
   3) View Parking slots
   4) View Parking History
   5) Exit System
4. Read user choice
  a) IF choice = 1 THEN
  Display available parking space
  Check parking availability
  IF no Parking slot is available THEN
  Display "Parking full"
 ELSE
  Record vehicle arrival
  Allocate an available parking space
  Create parking session
  Display allocated parking slot
  Display "Vehicle Entry Successful"
END IF

 b) ELSE IF choice = 2 THEN
 Request Vehicle registration Number
 Identify vehicle
 Find active parking space
 IF active session does not exist THEN
 Display "Vehicle Not Found"
ELSE
 Record Exit time
 Calculate parking fee
 Display parking duration
 Display parking fee
 Request payment
 Process payment
IF payment successful THEN
 Open exit barrier
 Complete parking session
 Release parking slot
 Display "Vehicle Exit Successful"
ELSE
 Keep Exit barrier closed
 Display "Payment Failed"
END IF
END IF

c) ELSE IF choice = 3 THEN
Display parking slots

d) ELSE IF choice = 4 THEN
 Display parking history

e) ELSE IF choice = 5 THEN
 STOP system
ELSE
Display "Invalid Choice"

END IF
END WHILE
END

b. DESCRIBE THE DATASTRUCTURE AND REASONS FOR THEIR USE

Queues (FIFO):Utilized for managing vehicle entry and exit queues during peak hours to ensure fair, sequential processing order.
HashMaps / Dictionaries: Implemented in the backend service layer for 1 constant time lookups of active slot states and cached vehicle sessions, minimizing database query overhead.
B-Tree Indexes: Applied to relational table columns like `registrationNumber` and foreign keys to ensure rapid searches and quick lookups during high-throughput gate transactions.

c. DESIGN A DYNAMIC DATABASE FOR THE ABOVE SCENARIO

TABLE             COLUMNS	           DATATYPES	   	
parking_sessions    session_id         Long
                     uuid               UUID
                     vehicleRegNumber   String
                     slotNumber          int
                     entryTime          LocalDateTime
                     exitTime           LocakDateTime
                     fee                 Double
                     status              Enum

parking_slot         slotId	             Long
                     uuid                UUID
                     slotNumber          int	
                     status              Enum

users                userId              Long
                     userName            String
                     password            String
				
				
