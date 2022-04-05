# RobotVoltec2022
## Introduction
Team 6647's repository for the 2022 Season Rapid React robot

![Imgur](https://i.imgur.com/fQhMzNn.jpeg)

## Subsystems

The robot is organized in different subsystems
* Chassis
* Intake
* Shooter
* Climber

## Capabilities
The robot is capable of certain things that might stand out and that increases it's performance
* Adjustment of range and distance using a Limelight
* 3 Different shooting settings depending on the position of the robot (Can be chosen by driver)
* Release ball through shooter if it is from the opposite alliance
* Release ball through intake if it is from the opposite alliance
* Voluntary break to avoid being pushed (We recommend to avoid using this and/or improve it to avoid damage to your motors and voltage spikes) 
* Toggle motor reductions to improve/decrease speed

## Autonomous
The autonomous moves out of the tarmac and shoots two balls 

## Button bindings
### Driver 1 (Joystick 1)
* Chassis
  * Left and right joysticks for movement
  * Y Button toggle toggle motor reductions
  * Right Bumper to toggle motor breaks
  * Left Bumper for LimeLight range and distance automatic adjustment

### Driver 2 (Joystick 2)
* Shooter
  * A Button to shoot close to the fender
  * B Button to shoot 1 meter from the fender
  * Y Button to release the ball through the shooter if it is from the opposite alliance
  * Right Bumper to push out balls from within the indexer if they are stuck 
* Intake
  * X to toggle intake
  * Left Trigger to move intake wheels backwards (kicks and pushs balls)
  * Right Trigger to move intake wheels forward (pulls balls into the robot)
* Climber
  * Dpad Up to extend climber
  * Dpad Down to retract climber

## External libraries
We encourage the use of team 6647's library for JSON-oriented object initialization. We have used certain features from the library to program the robot. If you are interesed in finding more about said library, it can be found [here](https://github.com/VOLTEC6647/lib6647)
