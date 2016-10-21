This version has the following properties:
- it is a working version
- it has a console output
- it has a suite of tests
- it has a golden image file (golden.img) to diff against when trying to refactor. The image file has been produced with seed '42L' for the pseudo random number generator and generating 700 generations.

Compile
-------
- ```mkdir bin```
- ```cd src```
- ```javac -d ../bin/ Board.java```
- ```javac -d ../bin/ Game.java```
- ```cd ..```

Run
---
- ```cd bin```
- ```java -cp . Game```
- ```cd ..```

Generate and diff the golden image
----------------------------------
- ```cd bin```
- You may want to comment out the ```sleep(300)``` call in ```Game.java``` before compiling and generating the golden image otherwise the program will take approx 3.5 minutes to complete.
- ```java -cp . Game > myGolden.img```
- on _Linux_ ```diff -w golden.img myGolden.img``` (ignore white-space because your architecture might differ from the one the golden image was created on)
- on _Windows_ use a tool like /winmerge/ to diff (compare) the two golden images (also ignore white-space)
- ```cd ..```

