#include "test.h"

Test::Test(int i, char c){
    test_i = i;
    test_c = c;
}

int Test::getTestInt(){
    return test_i;
}

char Test::getTestChar(){
    return test_c;
}