# Flip Box Game

Game simulation and Algorithm that predicts the result

## Getting Started

```
git clone https://github.com/dimmonn/gameBox.git
```

### Prerequisites

before to start make sure the the checkbox below in idea is ticked

![Alt text](src/main/resources/idea_setup.png?raw=true "Idea Setup")

## Running the tests

the most important test is in here

```
com.local.boxes.GameTest.tenMSimulationWithAlgorithm
```
it compares return value calculated by algorithm to the one game simulation produces
it runs 10M times

### DEMO

Algorithm demo

```
com.local.boxes.AlgorithmCounterDemo
```

Game Simulation Demo

```
com.local.boxes.GameSimulationDemo
```

### Upcoming Tasks

* **De-couple the shuffle algorithm from the game object**, shuffle has to be injected and shall follow some strategy design pattern (similar to algorithm context implementation here), so we can use any shuffle algorithm we needed