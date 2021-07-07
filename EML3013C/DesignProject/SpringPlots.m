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
b = 250; % N*m*s/rad
l = 0.15; % m
L = 0.5; % m 
I = 50; % kg*m^2
theta0 = pi/6; % rad
v_rel = 5; % m/s

t_int = 0:0.01:10; % s

init = [theta0 0];

% Spring array
k = [1000 2500 5000 7500 10000];

% Solving ODE
y = zeros(length(k),length(t_int));
y_eq = zeros(length(k),1);
for i = 1:length(k)
    [~,q] = ode45(@(t,q) AngularODE(t,q,W,g,k(i),b,l,L,I,theta0,v_rel,1), t_int, init);
    theta = q(:,1)';
    y(i,:) = Y(l,L,theta);
    
    % Finding Equilibrium
    [base_t,base] = ode45(@(t,q) AngularODE(t,q,W,g,k(i),10000,l,L,I,theta0,0,1), 0:100, init);
    base_th = base(:,1);
    theta_eq = base_th(end);
    y_eq(i) = -Y(l,L,theta_eq);
end

% Road Height
[ry,~] = R(v_rel,t_int,1);


% Plotting
fig = figure();
set(fig,'position',[100,100,1800,500]);
for i = 1:length(k)
    subplot(1,length(k),i);
    hold on
    axis([4 9 -0.05 0.05])
    grndh = plot(t_int,ry,'k','DisplayName','Ground');
    carh = plot(t_int,y(i,:)+y_eq(i)+ry,'r','DisplayName','Car');
    xlabel('Time [s]')
    if (i == 1)
        ylabel('Height [m]')
    end
    title(['k = ' num2str(k(i)) ' [N*m/rad]']);
    legend([grndh carh]);
end

saveas(gcf,'SpringPlots.png');
