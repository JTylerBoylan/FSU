function out1 = A(W,E,k,b,l,L,I,theta0,theta,omega)
%A
%    OUT1 = A(W,E,K,B,L,L,I,THETA0,THETA,OMEGA)

%    This function was generated by the Symbolic Math Toolbox version 8.5.
%    01-Apr-2021 17:48:42

t2 = cos(theta);
t3 = sin(theta);
t4 = L.^2;
t5 = l.^2;
t6 = t2.^2;
t7 = t3.^2;
out1 = -(E.*t4.*t7+E.*t5.*t6+W.*t4.*t7+W.*t5.*t6-b.*l.*omega.*t2+k.*l.*t2.*theta0-k.*l.*t2.*theta-L.*b.*omega.*t3+L.*k.*t3.*theta0-L.*k.*t3.*theta+E.*L.*l.*t2.*t3.*2.0+L.*W.*l.*t2.*t3.*2.0)./I;
