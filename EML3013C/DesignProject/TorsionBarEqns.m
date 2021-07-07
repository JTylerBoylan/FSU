%% Torsion Bar Suspension Design Project
% Equations
% Jonathan T. Boylan 3/30

clear
clc
close all
format compact

%%

% Known: W g k b l L I theta0 theta omega
% Unknown: m x N tau T C alpha a
syms W E aw g k b l L I theta0 theta omega m x N tau alpha a

eqn(1) = m == W/g;
eqn(2) = x == l*cos(theta) + L*sin(theta);
eqn(3) = tau == k*(theta-theta0) + b*omega;
eqn(4) = N == W + E + m*a;
eqn(5) = I*alpha == W*x + E*x - tau;
eqn(6) = a == -alpha*x;

answers = [m x N tau alpha a];

S = solve(eqn,answers);

vars = [W E k b l L I theta0 theta omega];

matlabFunction(S.alpha,'File','Alpha','Vars',vars);
matlabFunction(S.a,'File','A','Vars',vars);
matlabFunction(S.x,'File','X','Vars',[l L theta]);