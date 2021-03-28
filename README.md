# Adams Ale
**A Potable Water Location Tagging System for Victims of Natural Calamities.**
## Introduction
- No one has control over any natural calamity or disaster. But, we can surely control how it affects our lives by acting fast enough. It is seen that the unavailability of potable water after a disaster is a cause of distress to many and consumption of water unfit for drinking leads to various water-borne diseases and maybe, even a pandemic.
- The Adam’s Ale (meaning ‘Water’) project, when implemented, would be highly beneficial to people in case of disasters and natural calamities.
- We hope to significantly reduce the post-disaster epidemics due to water-borne diseases.

## Features
The personnel from rescue team (referred to here as ‘Tester’) will be carrying ‘Testing Probes’ to check the quality of water in local water sources. If it is found fit for drinking, the location will be saved for the victims to consume water from that.

1. Operator’s side: 
The testing probe will be able to identify water sources safe for drinking and tag its geolocation.
2. User’s side: 
Victim’s mobile application will be able to identify the nearest potable water source in online/offline modes and the guide to it.


## Parts
The project is divided into 4 parts:
#### 1. Tester’s side
- The testing probe will be equipped with a Multi-Sensor Array (MSA) that will be able to measure the temperature, pH, turbidity and Oxidation Reduction Potential (ORP) of the water body and follow the parameters from WHO guidelines to determine whether the water is safe for drinking.
- If yes, it will be communicated to the tester’s phone via Bluetooth. Application there will be able to communicate the same to the server using Online / Offline (SMS) methods depending on the situation.
#### 2. Cloud
- The data (geolocations) of the water sources will be stored in a firebase database. Now as we have mentioned about offline access to the database, it is achieved by the use of a virtual machine and Twilio SMS API. Even the route calculation for offline users using Google Maps API (discussed in next slide) is done in the Virtual Machine.
#### 3. User/Victim’s side
- The user side application can be used in Online as well as offline modes(usually there are connectivity uses after natural calamities).

|  Online Mode                                         |                            Offline Mode      |                         
|:----------------------------------------------------:|:--------------------------------------------:|
| The online mode captures the geolocation of the user and sends it to the cloud VM. It directs the user to the nearest water source using Google Maps after receiving  the coordinates from the cloud.| The offline mode sends the current GPS coordinates to the SMS base station via a SMS message and shows the route directions to the nearest water source.|  


#### 4. SMS Base station
The SMS Base station receives the GPS coordinates of the user via SMS and sends it to the cloud. Since we are well aware of the fact that the user is offline, we are fetching route directions and sending it back via another SMS.

## Tech Stack
...
## Future Scope
- As cellular network might be disrupted in case of natural calamities, even the offline way of updating the database might not work. Hence, we have decided to add Lo-Ra WAN feature to the testing probe in later versions of the project. 
Then, the base station would be able to communicate the same to the server.
- In further versions of the project, we aim to develop the application for Kai OS and other feature phones so that no one is deprived of the basic amenity, drinking water.
- Backend Modification and implementation of load balancing to make Adam’s Ale available to more number of users at a time.

