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

%% Use DataAnalysis

sphere = DataAnalysis(sphere_data);
sphere.ile = 0.001;
block_length = DataAnalysis(block_length_data);
block_length.ile = 0.001;
block_width = DataAnalysis(block_width_data);
block_width.ile = 0.001;
block_height = DataAnalysis(block_height_data);
block_height.ile = 0.001;

block_volume = block_length * block_width * block_height;

%% Print Results

disp(['Sphere: ',sphere.tostr])
disp(['Block Length: ',block_length.tostr])
disp(['Block Width: ',block_width.tostr])
disp(['Block Height: ',block_height.tostr])
disp(['Block Volume: ',block_volume.tostr(1)])