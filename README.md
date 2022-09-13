Readme: Drone
--------------
A Drone Controlling System capable of loading/ delivering medication items and monitoring drones.  

Requirements
-------------
You will need 
    JDK 1.8,
    Spring-boot 2.7.3,
    Apache Maven 3.6.0.
    
Running the application locally
-------------------------------
Clone the project and use following command to run the project locally. Please make sure port 8085 is open before hand.

`mvn spring-boot:run `

Assumptions
-----------
1. A medication item can only be loaded to a drone once. 

2. `/drone/load-medications` API will only change drone state from 'IDLE' to 'LOADING'. The user has to manually change the drone state to 'LOADED' from 'LOADING'.
Note: Separate API need to be implemented to handle this scenario.  

3. Only the drone id and medication id are used in implemented communication APIs in order to preserve data integrity. 

4. Weights assumed to be integers.

5. Medications can only be loaded to drones in 'IDLE' state or 'LOADING' state


    



