%% Measurement and Error Analysis MATLAB
% Jonathan Tyler Boylan
% 9/11/2021
% EML3012C Measurements Lab

clc
clear
format compact

%% Load Data
data = readtable('LabData.csv'); % import data csv as table

sphere_data = data.Diameters';

block_length_data = rmmissing(data.Lengths');
block_width_data = rmmissing(data.Widths');
block_height_data = rmmissing(data.Heights');

%% Calculate Means, Modes, and Standard Deviations
sphere_avg = mean(sphere_data);
sphere_std = std(sphere_data,1); % Normalize by N
sphere_mode = mode(sphere_data);

length_avg = mean(block_length_data);
length_std = std(block_length_data);
length_mode = mode(block_length_data);

width_avg = mean(block_width_data);
width_std = std(block_width_data);
width_mode = mode(block_width_data);

height_avg = mean(block_height_data);
height_std = std(block_height_data);
height_mode = mode(block_height_data);

%% Normalizations

sphere_z_data = (sphere_data - sphere_avg)./sphere_std;

length_z_data = (block_length_data - length_avg)./length_std;
width_z_data = (block_width_data - width_avg)./width_std;
height_z_data = (block_height_data - height_avg)./height_std;

%% Data Rejection

% Chauvenet's criterion for n = 100 and n = 20
chauvenet_100 = 2.807;
chauvenet_20 = 2.241;

sphere_data = sphere_data(abs(sphere_z_data) < chauvenet_100); % 0 removed

block_length_data = block_length_data(abs(length_z_data) < chauvenet_20); % 1 removed
block_width_data = block_width_data(abs(width_z_data) < chauvenet_20); % 1 removed
block_height_data = block_height_data(abs(height_z_data) < chauvenet_20); % 0 removed

%% Recalculate Means and Standard Deviations

sphere_avg = mean(sphere_data);
sphere_std = std(sphere_data,1); % Normalize by N

length_avg = mean(block_length_data);
length_std = std(block_length_data);

width_avg = mean(block_width_data);
width_std = std(block_width_data);

height_avg = mean(block_height_data);
height_std = std(block_height_data);

%% Limits of Error

ILE = 0.001;
SLE = @(std,n) 3*std/sqrt(n);

sphere_SLE = SLE(sphere_std,length(sphere_data));
sphere_LE = norm([ILE sphere_SLE]);

length_SLE = SLE(length_std,length(block_length_data));
length_LE = norm([ILE length_SLE]);

width_SLE = SLE(width_std,length(block_width_data));
width_LE = norm([ILE width_SLE]);

height_SLE = SLE(height_std,length(block_height_data));
height_LE = norm([ILE height_SLE]);

%% Block Volume

volume_best = length_avg * width_avg * height_avg;
volume_uncert = volume_best * norm([length_LE/length_avg width_LE/width_avg height_LE/height_avg]);

%% Gaussian Normal Distribution Function
gaussian = @(Z) (1/(sqrt(2*pi))) .* exp(-Z.^2/2);

%% Sphere Histograms

% Figure 1: Diameter measurement distribution
fig1 = figure(1);
n_bins = 8; %  Number of histogram bins

% Create distribution histogram
h1 = histogram(sphere_data,n_bins);
ylim([0 max(h1.Values)*1.25]) % Add margin to y-axis

% Using LaTeX for font and text formatting
title('\textbf{Sphere Diameter Distribution}','Interpreter','latex')
xlabel('Diameter (in)','Interpreter','latex')
ylabel('Count','Interpreter','latex')

% Save first figure to jpg
saveas(gcf, 'SphereHistogram1.jpg');

% Figure 2: Diameter probability distribution
fig2 = figure(2);

% Create probability density histogram
h2 = histogram(sphere_z_data,-3:3,'Normalization','pdf');
ylim([0 max(h2.Values)*1.25]) % Add margin to y-axis
xlim([-3.25 3.25]) % add margin to x-axis

% Add Gaussian Normal Distribution Function to plot
Z = -5:0.1:5; % graph 5 st. devs

hold on
p2 = plot(Z,gaussian(Z),'r');
plot([0 0],[0 gaussian(0)], 'r'); % Plot mean
legend(p2,'$f(z) = \frac{1}{\sqrt{2\pi}}e^{-\frac{1}{2}z^2}$','Interpreter','latex'); % Add legend

title('\textbf{Sphere Diameter Probability Distribution}', 'Interpreter','latex')
xlabel('Standard Deviation ($z$)','Interpreter','latex')
ylabel('Probability Density ($f(z)$)','Interpreter','latex')

% Save second figure to jpg
saveas(gcf,'SphereHistogram2.jpg');

%% Block Histograms

% Figure 3: Block measurement distributions
fig3 = figure(3);

n_bins = 8; % Number of bins

% 3 Histograms: Length, Width, Height
% Length
subplot(3,1,1)
h1_length = histogram(block_length_data,n_bins);
ylim([0 max(h1_length.Values)*1.25]) % Add margin to y-axis

title('\textbf{Block Measurement Distribution}','Interpreter','latex')
ylabel('Count', 'Interpreter','latex')
xlabel('Length (in)','Interpreter','latex')

% Width
subplot(3,1,2)
h1_width = histogram(block_width_data,n_bins);
ylim([0 max(h1_width.Values)*1.25])

ylabel('Count', 'Interpreter','latex')
xlabel('Width (in)','Interpreter','latex')

% Height
subplot(3,1,3)
h1_height = histogram(block_height_data,n_bins);
ylim([0 max(h1_height.Values)*1.25])

ylabel('Count', 'Interpreter','latex')
xlabel('Height (in)','Interpreter','latex')

% Save figure to jpg
saveas(gcf,'BlockHistogram1.jpg')


% Figure 4: Block probability density
fig4 = figure(4);

% Length
subplot(3,1,1)
h2_length = histogram(length_z_data,-3:3,'Normalization','pdf');
ylim([0 max(h2_length.Values)*1.25]) % Add margin to y-axis
xlim([-3.25 3.25]) % add margin to x-axis

% Plot Gaussian
hold on
p4 = plot(Z,gaussian(Z),'r');
plot([0 0],[0 gaussian(0)], 'r'); % Plot mean
legend(p4,'$f(z) = \frac{1}{\sqrt{2\pi}}e^{-\frac{1}{2}z^2}$','Interpreter','latex'); % Add legend

title('\textbf{Block Measurement Probability Distribution}', 'Interpreter','latex')
xlabel('Length Standard Deviation ($z$)','Interpreter','latex')
ylabel('$f(z)$','Interpreter','latex')

% Width
subplot(3,1,2)
h2_width = histogram(width_z_data,-3:3,'Normalization','pdf');
ylim([0 max(h2_width.Values)*1.25]) % Add margin to y-axis
xlim([-3.25 3.25]) % add margin to x-axis

% Plot Gaussian
hold on
p4 = plot(Z,gaussian(Z),'r');
plot([0 0],[0 gaussian(0)], 'r'); % Plot mean

xlabel('Width Standard Deviation ($z$)','Interpreter','latex')
ylabel('$f(z)$','Interpreter','latex')

% Height
subplot(3,1,3)
h2_height = histogram(height_z_data,-3:3,'Normalization','pdf');
ylim([0 max(h2_height.Values)*1.25]) % Add margin to y-axis
xlim([-3.25 3.25]) % add margin to x-axis

% Plot Gaussian
hold on
p4 = plot(Z,gaussian(Z),'r');
plot([0 0],[0 gaussian(0)], 'r'); % Plot mean

xlabel('Height Standard Deviation ($z$)','Interpreter','latex')
ylabel('$f(z)$','Interpreter','latex')

% Save second figure to jpg
saveas(gcf,'BlockHistogram2.jpg');
