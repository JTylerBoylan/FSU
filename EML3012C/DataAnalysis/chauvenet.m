% Written by Jonathan T. Boylan

function Q = chauvenet(N)
%CHAUVENET Gives chauvenet criterion for data set of length N
P = .5*(1-1/(2*N));
R = [.5-P .5+P];
I = norminv(R);
Q = I(2);
end

