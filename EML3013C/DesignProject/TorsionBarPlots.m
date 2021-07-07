%% Torsion Bar Suspension Design Project
% Plots
% Jonathan T. Boylan 3/29

clear
clc
close all
format compact

%%

% Constants
% W k b l L I theta0 theta omega
g = 9.81; % m/s^2
W = 250*g; % N
k = 3000; % N/rad
b = 300; % N*s/rad
l = 0.15; % m
L = 0.5; % m 
I = 50; % kg*m^2

theta0 = pi/6; % rad

v_rel = 0; % m/s

t_int = [0 3]; % s

init = [theta0 0];

[t,q] = ode45(@(t,q) AngularODE(t,q,W,g,k,b,l,L,I,theta0,v_rel,1), t_int, init);

theta = q(:,1);
omega = q(:,2);

alpha = zeros(length(t),1);
y = zeros(length(t),1);
for i = 1:length(t)
    [~,rA] = R(v_rel,t(i),1);
    alpha(i) = Alpha(W,rA,k,b,l,L,I,theta0,theta(i),omega(i));
    y(i) = Y( l,L,theta(i) );
end

% Plot Angle
fig1 = figure();
set(fig1,'position',[100 100 750 750])
% Theta
subplot(3,1,1);
plot(t,theta.*180/pi);
ylabel('\theta [deg]');
grid on
% Omega
subplot(3,1,2);
plot(t,omega.*180/pi);
ylabel('\omega [deg/s]');
grid on
% Alpha
subplot(3,1,3);
plot(t,alpha.*180/pi);
ylabel('\alpha [deg/s^2]');
xlabel('Time [s]');
grid on

% Plot Height
fig2 = figure();
set(fig2,'position',[1000 100 750 750])
% Height
plot(t,-y);
xlabel('Time [s]');
ylabel('Car Height [m]');
grid on

saveas(fig1,'AnglePlots.png');
saveas(fig2,'HeightPlot.png');