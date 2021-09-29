proj6: main.o account.o
	g++ -o proj6 main.o account.o portfolio.o
account.o: account.cpp account.h savings.h checking.h investment.h portfolio.cpp portfolio.h
	g++ -c account.cpp account.h savings.h checking.h investment.h portfolio.cpp portfolio.h
main.o: main.cpp portfolio.h
	g++ -c main.cpp
clean:
	rm *.o *.gch proj6