date: polynomial.o driver.o
	g++ -o polynomial polynomial.o driver.o
polynomial.o: polynomial.cpp polynomial.h
	g++ -c polynomial.cpp polynomial.h
driver.o: driver.cpp polynomial.h
	g++ -c driver.cpp
clean:
	rm *.o polynomial *.gch