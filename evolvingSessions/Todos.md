R = Refactoring
F = Feature

R000 ```Cell``` depends on derived class ```LiveCell``` and uses ```instanceof``` operator.
R001 Several tests in ```CellTest``` can be refactored into a more concise form.

F000 A concept like a _Board_ or _World_ should be introduced to hold the ```Cell```s.
F001 A concept like a _Game_ should be introduced to control user input, configuration, initialization and logic.
F002 A concept for the view should be introduced to display a graphical representation of the game.

