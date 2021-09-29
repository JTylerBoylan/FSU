#include "account.h"
#include "portfolio.h"

#include <iostream>
#include <cctype>

using namespace std;

void printMenu();

int main(){

    Portfolio p;

    printMenu();

    char input = ' ';

    while (input != 'Q'){
        
        cin >> input;

        input = toupper(input);

        switch (input){

            case 'I':
                char import[30];
                cout << "Enter filename: ";
                cin >> import;
                if (!p.importFile(import))
                    cout << "Invalid file. No data imported" << endl;
                cout << endl;
                break;
            case 'S':
                p.showAccounts();
                cout << endl;
                break;
            case 'E':
                char export_[30];
                cout << "Enter filename: ";
                cin >> export_;
                if(!p.createFileReport(export_))
                    cout << "Error exporting file" << endl;
                cout << endl;
                break;
            case 'M':
                printMenu();
                break;
            case 'O':
                p.sort();
                cout << endl;
                break;
            case 'Q':
                cout << "Goodbye!" << endl;
                break;

        }

    }


    return 0;
}

void printMenu(){

    cout << endl;
    cout << "**Portfolio Management Menu***" << endl;
    cout << endl;
    cout << "I\tImport accounts from a file" << endl;
    cout << "S\tShow accounts (brief)" << endl;
    cout << "E\tExport a banking report (to file)" << endl;
    cout << "O\tSort accounts by name" << endl;
    cout << "M\tShow this menu" << endl;
    cout << "Q\tQuit program" << endl;
    cout << endl;

}