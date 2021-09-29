% Example use of DataAnalysis class
% Written by Jonathan T. Boylan

clc
clear

%% Creating a DataAnalysis Variable

% Import Data
data = [48.1 44.7 43.9 45.0 38.6 44.2];

% Create DataAnalysis variable
analysis = DataAnalysis(data);

% Set ILE of measurements
analysis.ile = 0.01;

% Print out DataAnalysis (Best +- Error)
disp(['Original: D = ',analysis.tostr]);

%% Rejecting Data

% Chauvenet Data Rejection
analysis = analysis.reject(chauvenet(analysis.size));

disp(['After Rejection: D = ',analysis.tostr]);

%% Statistical Analysis of Data

% Get best estimate and error
best = analysis.best;
error = analysis.le;

% Find upper and lower bounds
upper_bound = analysis.upper;
lower_bound = analysis.lower;

%% Changing Error Determination Function

% Change Limit of Error function to Maximum Uncertainty (ILE + SLE)
analysis.le_func = @(ile,sle) ile + sle;

% Find max error
error_max = analysis.le;

disp(['Max Uncertainty: D = ',analysis.tostr]);

%% Using Error Propagation in Addition, Subtraction, Multiplication, Division and Exponents

% Create 2 data sets
data_A = [6.5 6.2 5.9 5.6 5.7 6.7 6.5 6.5 6.4];
data_B = [12.3 11.4 12.6 12.7 12.5 12.4 13.7];

% Create DataAnalysis variables for data sets
analysis_A = DataAnalysis(data_A);
analysis_B = DataAnalysis(data_B);

disp(['A = ',analysis_A.tostr]);
disp(['B = ',analysis_B.tostr]);

% Use '+' operator for addition
analysis_C = analysis_A + analysis_B;
disp(['A + B = ',analysis_C.tostr]);

% Use '-' operator for subtraction
analysis_D = analysis_A - analysis_B;
disp(['A - B = ',analysis_D.tostr]);

% Use '*' operator for multiplication
analysis_E = analysis_A * analysis_B;
disp(['A * B = ',analysis_E.tostr]);

% Use '/' operator for division
analysis_F = analysis_A / analysis_B;
disp(['A / B = ',analysis_F.tostr]);

% Use '^' operator for exponentiating
analysis_G = analysis_A ^ 3;
disp(['A^3 = ',analysis_G.tostr]);