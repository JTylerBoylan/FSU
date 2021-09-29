date: date.o driver.o
	g++ -o date date.o driver.o
date.o: date.cpp date.h
	g++ -c date.cpp date.h
driver.o: driver.cpp date.h
	g++ -c driver.cpp
clean:
	rm *.o date *.gch