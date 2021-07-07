%% Torsion Bar Suspension Animation
% Jonathan T. Boylan 3/30

clear
clc
close all
format compact

%% Constants

% W k b l L I theta0 theta omega
g = 9.81; % m/s^2
W = 250*g; % N
k = 5000; % N/rad
b = 100; % N*s/rad
l = 0.15; % m
L = 0.5; % m 
I = 50; % kg*m^2
theta0 = pi/6; % rad

v_s = 5; % m/s

%% Differentiation

t_int = [0 5]; % s

init = [pi/6 0];

[t1,qA] = ode45(@(t,q) AngularODE(t,q,W,g,k,b,l,L,I,theta0,v_s,1), t_int, init);
[t,qB] = ode45(@(t,q) AngularODE(t,q,W,g,k,b,l,L,I,theta0,v_s,2), t1, init);

thetaA = qA(:,1);
omegaA = qA(:,2);

thetaB = qB(:,1);
omegaB = qB(:,2);

[gndA, gnd_accA] = R(v_s,t,1); 
[gndB, gnd_accB] = R(v_s,t,2); 

alphaA = zeros(1,length(t));
aA = zeros(1,length(t));
vA = zeros(1,length(t));
yA = zeros(1,length(t));
alphaB = zeros(1,length(t));
aB = zeros(1,length(t));
vB = zeros(1,length(t));
yB = zeros(1,length(t));
for i = 1:length(t)
    alphaA(i) = Alpha( W,(W/g)*gnd_accA(i),k,b,l,L,I,theta0,thetaA(i),omegaA(i) );
    aA(i) = A( W,(W/g)*gnd_accA(i),k,b,l,L,I,theta0,thetaA(i),omegaA(i) );
    vA(i) = omegaA(i) * X( l,L,thetaA(i) );
    yA(i) = Y( l,L,thetaA(i) );
    alphaB(i) = Alpha( W,(W/g)*gnd_accB(i),k,b,l,L,I,theta0,thetaB(i),omegaB(i) );
    aB(i) = A( W,(W/g)*gnd_accB(i),k,b,l,L,I,theta0,thetaB(i),omegaB(i) );
    vB(i) = omegaB(i) * X( l,L,thetaB(i) );
    yB(i) = Y( l,L,thetaB(i) );
end

%% Animation

gifFile = 'TBSAnimation.gif';

% Figure
fig = figure('visible','off');
set(fig,'position',[100,100,1500,800]);

ax = [-1.5 1.5 -0.15 1];
h = 0.25; % m
split = 0.20; % m
of = split/2;

% Animation Plot
subplot(2,2,[1 2]);
axis(ax);

% Wheel A Positions

thA = thetaA(1);

wheelA = [X(l,L,thA)+of gndA(1)+h]; 
pivotA = wheelA + [-L*sin(thA) L*cos(thA)];
barA = pivotA + [-l*cos(thA) -l*sin(thA)];

wheelA_x = [0 0 0.15 0.15]; 
wheelA_y = [h*2 0 0 h*2];

% Wheel B Positions

thB = thetaB(1);

wheelB = [-X(l,L,thB)-of gndB(1)+h]; 
pivotB = wheelB + [L*sin(thB) L*cos(thB)];
barB = pivotB + [l*cos(thB) -l*sin(thB)];

wheelB_x = [0 0 0.15 0.15]; 
wheelB_y = [h*2 0 0 h*2];

% Chassis Position


chassis_x = [-0.2 -0.2 split+0.2 split+0.2];
chassis_y = [0.3 -0.15 -0.15 0.3];
delta_y = barB(2)-barA(2);
rot = atan(delta_y/split);
rotMtx = [cos(rot) sin(rot) ; -sin(rot) cos(rot)];
rot_chassis = rotMtx*[chassis_x;chassis_y];


% Chassis Drawing

hold on
drwCHS = fill(barB(1) + rot_chassis(1,:), barB(2) + rot_chassis(2,:),'b');

% Wheel A Drawing

hold on
drwWA = fill(wheelA_x + X(l,L,thA)+of, wheelA_y + gndA(1),'k');

lnWPA = line(fig,wheelA,pivotA,'r',5);
lnPBA = line(fig,pivotA,barA,'r',5);

pntWA = point(fig,wheelA,'k',5);
pntPA = point(fig,pivotA,'k',5);
pntBA = point(fig,barA,'g',8);

axis([-0.5 1.5 -0.5 1.5]);

% Wheel B Drawing

hold on
drwWB = fill(X(l,L,thB)-wheelB_x-of, wheelB_y + gndB(1),'k');

lnWPB = line(fig,wheelB,pivotB,'r',5);
lnPBB = line(fig,pivotB,barB,'r',5);

pntWB = point(fig,wheelB,'k',5);
pntPB = point(fig,pivotB,'k',5);
pntBB = point(fig,barB,'g',8);

% Ground Line
lnGNDA = line(fig,[-3 gndA(1)],[0 gndA(1)],'k',10);
lnGNDB = line(fig,[0 gndB(1)],[3 gndB(1)],'k',10);

% Ground plot A
subplot(2,2,4);
axis([[t(1)-1 t(1)+1],ax([3,4])]);
hold on
pltGNDA = plot(t,gndA,'k','LineWidth',5);
currA = plot([t(1) t(1)],[-3 3],'--r');
ylabel('Wheel A Height [m]')
xlabel('Time [s]');

% Ground plot B
subplot(2,2,3);
axis([[t(1)-1 t(1)+1],ax([3,4])]);
hold on
pltGNDB = plot(t,gndB,'k','LineWidth',5);
currB = plot([t(1) t(1)],[-3 3],'--r');
ylabel('Wheel B Height [m]')
xlabel('Time [s]');

for i = 1:length(t)

% Animation Plot
subplot(2,2,[1 2]);
    
axis(ax);
   
% Wheel A Position
thA = thetaA(i);

wheelA = [X(l,L,thA)+of gndA(i)+h]; 
pivotA = wheelA + [-L*sin(thA) L*cos(thA)];
barA = pivotA + [-l*cos(thA) -l*sin(thA)];

% Wheel B Position

thB = thetaB(i);

wheelB = [-X(l,L,thB)-of gndB(i)+h]; 
pivotB = wheelB + [L*sin(thB) L*cos(thB)];
barB = pivotB + [l*cos(thB) -l*sin(thB)];

% Chassis Position

chassis_x = [-0.2 -0.2 split+0.2 split+0.2];
chassis_y = [0.3 -0.15 -0.15 0.3];
delta_y = barB(2)-barA(2);
rot = atan(delta_y/split);
rotMtx = [cos(rot) sin(rot) ; -sin(rot) cos(rot)];
rot_chassis = rotMtx*[chassis_x;chassis_y];

% Chassis Drawing

set(drwCHS,'xdata',barB(1) + rot_chassis(1,:),'ydata',barB(2) + rot_chassis(2,:));

% Wheel A Drawing

set(drwWA,'xdata', wheelA_x + X(l,L,thA)+of,'ydata', wheelA_y + gndA(i));

set(pntWA,'xdata',wheelA(1),'ydata',wheelA(2));
set(pntPA,'xdata',pivotA(1),'ydata',pivotA(2));
set(pntBA,'xdata',barA(1),'ydata',barA(2));

set(lnWPA,'xdata',[wheelA(1) pivotA(1)],'ydata',[wheelA(2) pivotA(2)]);
set(lnPBA,'xdata',[pivotA(1) barA(1)],'ydata',[pivotA(2) barA(2)]);

% Wheel B Drawing

set(drwWB,'xdata', -X(l,L,thB)-wheelB_x-of,'ydata', wheelB_y + gndB(i));

set(pntWB,'xdata',wheelB(1),'ydata',wheelB(2));
set(pntPB,'xdata',pivotB(1),'ydata',pivotB(2));
set(pntBB,'xdata',barB(1),'ydata',barB(2));

set(lnWPB,'xdata',[wheelB(1) pivotB(1)],'ydata',[wheelB(2) pivotB(2)]);
set(lnPBB,'xdata',[pivotB(1) barB(1)],'ydata',[pivotB(2) barB(2)]);


% Ground Line A
set(lnGNDA, 'xdata', [0 3], 'ydata', [gndA(i) gndA(i)]);

% Ground Line B
set(lnGNDB, 'xdata', [-3 0], 'ydata', [gndB(i) gndB(i)]);

% Ground plot A
subplot(2,2,4);
axis([[t(i)-1 t(i)+1],ax([3,4])]);
set(currA,'xdata',[t(i) t(i)],'ydata',[-3 3]);

% Ground plot B
subplot(2,2,3);
axis([[t(i)-1 t(i)+1],ax([3,4])]);
set(currB,'xdata',[t(i) t(i)],'ydata',[-3 3]);

% Animation rate
% pause(1/length(t))

% Save GIF
% {
filenamegif = gifFile;
drawnow;

frame = getframe(fig);
im = frame2im(frame);
[imind,cm] = rgb2ind(im,256);

if i == 1
    imwrite(imind,cm,filenamegif,'gif','DelayTime',1/length(t),'Loopcount',inf);
elseif i == numel(i)
    imwrite(imind,cm,filenamegif,'gif','DelayTime',1/length(t),'WriteMode','append');
else
    imwrite(imind,cm,filenamegif,'gif','DelayTime',1/length(t),'WriteMode','append');
end
% }

end




%% Functions

function plt = point(fig,P,color,size)

figure(fig);

hold on

plt = plot(P(1),P(2),strcat('o',color),'MarkerSize',size,'MarkerFaceColor','w');

hold off

end

function ln = line(fig,S,F,style,width)

figure(fig);

hold on

X = [S(1) F(1)];
Y = [S(2) F(2)];

ln = plot(X,Y,style,'LineWidth',width);

hold off

end
