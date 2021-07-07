%% Torsion Bar Suspension Design Project
% Damping Plots
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
l = 0.15; % m
L = 0.5; % m 
I = 50; % kg*m^2
theta0 = pi/6; % rad
v_rel = 5; % m/s

t_int = 0:0.01:10; % s

init = [theta0 0];

% Damping array
b = [10 100 250 500 1000];

% Solving ODE
y = zeros(length(b),length(t_int));
for i = 1:length(b)
    [~,q] = ode45(@(t,q) AngularODE(t,q,W,g,k,b(i),l,L,I,theta0,v_rel,1), t_int, init);
    theta = q(:,1)';
    y(i,:) = Y(l,L,theta);
end

% Road Height
[ry,~] = R(v_rel,t_int,1);

% Finding Equilibrium
[base_t,base] = ode45(@(t,q) AngularODE(t,q,W,g,k,10000,l,L,I,theta0,0,1), 0:100, init);
base_th = base(:,1);
theta_eq = base_th(end);
y_eq = -Y(l,L,theta_eq);

% Plotting
fig = figure();
set(fig,'position',[100,100,1800,500]);
for i = 1:length(b)
    subplot(1,length(b),i);
    hold on
    axis([4 9 -0.05 0.05])
    grndh = plot(t_int,ry,'k','DisplayName','Ground');
    carh = plot(t_int,y(i,:)+y_eq+ry,'r','DisplayName','Car');
    xlabel('Time [s]')
    if (i == 1)
        ylabel('Height [m]')
    end
    title(['b = ' num2str(b(i)) ' [N*m*s/rad]']);
    legend([grndh carh]);
end

saveas(gcf,'DampingPlots.png');
