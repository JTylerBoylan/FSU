proj5: matrix.o driver.o
	g++ -o matrix matrix.o driver.o
matrix.o: matrix.cpp matrix.h
	g++ -c matrix.cpp matrix.h
driver.o: driver.cpp matrix.h
	g++ -c driver.cpp
clean:
	rm *.o proj5 *.gch