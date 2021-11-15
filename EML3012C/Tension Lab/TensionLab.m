% Name: Jonathan Tyler Boylan
% Date: 10/21/2021
% EML3012C
% Tension Lab MATLAB

clear
clc
close all
format compact

it = "Interpreter";
lx = "Latex";

% Standard Values for Specific Tensile Properties
standard = readtable('STANDARD_PROPERTIES');

% Change figure visibility
set(0,'DefaultFigureVisible','off')

% Tensile Properties Save File
tensile_file = fopen('tensile_properties.txt','w');

% Save Figures to jpg?
savefig = true;

% Specimen names
specimen_names = [
    "1018 Cold Rolled Steel",...
    "A36 Hot Rolled Steel",...
    "Grey 20 Cast Iron",...
    "AA 6061 0HR Heat Treatment",...
    "AA 6061 2HR Heat Treatment",...
    "AA 6061 4HR Heat Treatment",...
    "AA 6061 6HR Heat Treatment"
    ];

% Specimen data files
file_names = [
    "1018_STEEL",...
    "A36_STEEL",...
    "CAST_IRON",...
    "AA_6061_0HR",...
    "AA_6061_2HR",...
    "AA_6061_4HR",...
    "AA_6061_6HR"
    ];

% Measured Data
lengths_0 = [50,50,50,50,50,50,50]; % mm
diameters_0 = [8.65,8.65,8.65,8.65,8.65,8.65,8.65].*1e-3; % m

lengths_f = [56.4 66.6 51 57.7 59.8 58.6 55.3]; % mm
diameters_f = [5.8 5.3 8.5 7.6 7.1 8.0 8.0]*1e-3; % m

% Number of Points after Fracture (to be removed)
PAF = [2 1 1 1 1 2 1];

% Plastic Region Start
plastic_strain = [0.01 0.023 0.004 0.01 0.01 0.01 0.01];

for i = 1:length(file_names)
    
    % Get initial measurements
    L0 = lengths_0(i);
    D0 = diameters_0(i);
    
    % Calculate initial area
    A0 = pi/4*D0^2; % m^2
    
    % Get specimen tensile data
    data = readtable(file_names(i));
    
    % Get Load and Strain data
    load = data.Load_kN'; % kN
    strain = data.Strain_mm'; % mm
    
    % Remove points after fracture (PAF)
    load = load(1:end-PAF(i));
    strain = strain(1:end-PAF(i));
    
    % Adjust so first point is at (0,0)
    load = load - load(1);
    strain = strain - strain(1);
    
    % Plot raw data
    figs(1) = figure();
    figf(1) = "RAW";
    scatter(strain,load,'x')
    grid on
    title([specimen_names(i) " Raw Data"],it,lx)
    ylim([0 max(load)*1.10])
    xlim([0 strain(end)*1.10])
    xlabel("$\Delta L$ (mm)",it,lx)
    ylabel("Load (kN)",it,lx)
    
    % Find Engineering Stress and Strain
    stress_eng = load./A0.*1e-3; % MPa
    strain_eng = strain./L0; % mm/mm
    
    % Plot Engineering Stress vs. Strain
    figs(2) = figure();
    figf(2) = "ENGSS";
    plot(strain_eng,stress_eng);
    grid on
    title([specimen_names(i) "Engineering Stress vs. Strain"],it,lx)
    ylim([0 max(stress_eng)*1.10])
    xlim([0 strain_eng(end)*1.10])
    xlabel("Engineering Strain (mm/mm)",it,lx)
    ylabel("Engineering Stress (MPa)",it,lx)
    
    % Calculate True Stress and Strain
    stress_true = stress_eng.*(1 + strain_eng);
    strain_true = log(1 + strain_eng);
    
    % Plot True Stress and Strain
    figs(3) = figure();
    figf(3) = "TRUESS";
    hold on
    plot(strain_true,stress_true);
    plot(strain_eng,stress_eng,'--')
    grid on
    title([specimen_names(i) " True Stress vs. Strain"],it,lx)
    ylim([0 max(stress_true)*1.10])
    xlim([0 strain_true(end)*1.10])
    xlabel("True Strain (mm/mm)",it,lx)
    ylabel("True Stress (MPa)",it,lx)
    legend(["$\sigma_T$" "$\sigma$"],it,lx)
    
    
    % Calculate Tensile Properties
    
    % Modulus of Elasticity & Proportional Limit
    
    % Get Region of Proportionality
    region = 0.002;
    if i == 3
        region = 0.001;
    end
    strain_region = strain_eng(strain_eng < region);
    stress_region = stress_eng(1:length(strain_region));
    
    Proportional_Limit = max(stress_region);
    
    % Smooth Data in Region
    strain_region = smoothdata(strain_region);
    stress_region = smoothdata(stress_region);
    
    Elasticity = mean(diff(stress_region)./diff(strain_region))*1e-3; % GPa
    
    % Plot Elasticity on Engineering Stress vs. Strain
    region = 0.007;
    strain_region = strain_eng(strain_eng < region);
    stress_region = stress_eng(1:length(strain_region));
    
    figs(4) = figure();
    figf(4) = "ELASTIC";
    hold on
    plot(strain_region,stress_region)
    plot(strain_region,Elasticity.*strain_region.*1e3,'--');
    grid on
    title([specimen_names(i) "Elastic Stress vs. Strain"],it,lx)
    ylim([0 max(stress_region)*1.25])
    xlim([0 strain_region(end)*1.1])
    xlabel("Strain (mm/mm)",it,lx)
    ylabel("Stress (MPa)",it,lx)
    
    % Yield Strength
    offset = 0.002;
    strain_region = strain_region(strain_region < stress_region./Elasticity.*1e-3 + offset);
    stress_region = stress_region(1:length(strain_region));
    if i ~= 2
        plot(strain_region + offset,Elasticity.*strain_region.*1e3,'--');
        legend(["$\sigma$ vs. $\epsilon$" "$E\epsilon$" "$E\epsilon$ (0.2\%)"],it,lx)
    else
        legend(["$\sigma$ vs. $\epsilon$" "$E\epsilon$"],it,lx)
    end
    Yield_Strength = stress_region(end);
    
    % Ultimate Tensile Strength
    Tensile_Strength = max(stress_eng);
    
    % Engineering Fracture Strength
    Fracture_Strength = stress_eng(end);
    
    % Percent Elongation (Data)
    Percent_Elongation_D = strain_eng(end)*100;
    
    % Percent Elongation (Measured)
    Percent_Elongation_M = (lengths_f(i) - L0)/L0*100;
    
    % Reduction in Area (Measured)
    Af = pi/4*diameters_f(i)^2;
    Area_Reduction = (A0 - Af)/A0 * 100;
    
    % Poisson's Ratio
    epsilon_x = diameters_f(i)/D0 - 1;
    epsilon_z = Percent_Elongation_M/100;
    Poissons_Ratio = -epsilon_x/epsilon_z;
    
    % Resilience
    Resilience = .5*Yield_Strength/Elasticity;
    
    % Tensile Toughness
    brittle = strain_eng(end) < 0.05;
    if brittle
        Toughness = (2/3)*Tensile_Strength*strain_eng(end);
    else
        Toughness = (Tensile_Strength + Yield_Strength)*strain_eng(end)/2;
    end
    
    % Strain Hardening Ratio
    
    Strain_Hardening_Ratio = Tensile_Strength/Yield_Strength;
    
    % Get LogLog Plastic Region
    region = plastic_strain(i);
    region2 = strain_true(stress_true == max(stress_true));
    strain_region = strain_true(strain_true > region & strain_true < region2);
    stress_region = stress_true(strain_true > region & strain_true < region2);
    
    % Fit line to Log plot
    p = polyfit(log(strain_region),log(stress_region),1);
    
    % Strain Hardening Exponent (slope)
    Strain_Hardening = p(1);
    
    % Strength Coefficient (bias)
    lnH = p(2);
    Strength_Coefficient = exp(lnH);
    
    % Plot Plastic Region
    figs(5) = figure();
    figf(5) = "PLOG";
    hold on
    plot(log(strain_region),log(stress_region))
    plot(log(strain_region),log(strain_region).*Strain_Hardening + lnH, '--')
    grid on
    title([specimen_names(i) "Plastic Stress vs. Strain"],it,lx)
    xlabel("log Strain (mm/mm)",it,lx)
    ylabel("log Stress (MPa)",it,lx)
    legend(["$\ln\sigma$ vs. $\ln\epsilon$" "$\ln H + n \ln \epsilon$"],it,lx)
    
    strain_region = strain_true(strain_true > region);
    stress_region = Strength_Coefficient.*strain_true(strain_true > region).^Strain_Hardening;
    
    % Plot Hardening over True
    figs(6) = figure();
    figf(6) = "HARD";
    hold on
    plot(strain_true, stress_true)
    plot(strain_region, stress_region)
    grid on
    title([specimen_names(i) "Strain Hardening on True Stress vs. Strain"],it,lx)
    ylim([0 max(stress_region)*1.25])
    xlim([0 strain_region(end)*1.1])
    xlabel("Strain (mm/mm)",it,lx)
    ylabel("Stress (MPa)",it,lx)
    legend(["$\sigma$ vs. $\epsilon$" "$k\epsilon^n$"],it,lx)
    
    Standard_Elasticity = standard.Elasticity_Gpa(i);
    Standard_Yield = standard.Strength_Yield_Mpa(i);
    Standard_Ultimate = standard.Strength_Ultimate_Mpa(i);
    Standard_Elongation = standard.Elongation(i)*100;
    Standard_Hardening = standard.Strain_Hardening(i);
    Standard_Poisson = standard.Poissons_Ratio(i);
    
    % Print Tensile Properties
    pd = 30;
    fprintf(tensile_file, "Tensile Properties of %s\n", specimen_names(i)); % header
    fprintf(tensile_file,"%s\n",pad("-",pd + 45,'-'));
    fprintf(tensile_file,"%s %s %s %s\n",pad("Property",pd),...
        pad("Value",15),pad("Standard Value", 18),pad("Error",10));
    fprintf(tensile_file,"%s\n",pad("-",pd + 45,'-'));
    fprintf(tensile_file,"%s %.2f GPa\t %.2f GPa\t\t %.2f %%\n",...
        pad("Modulus of Elasticity (E)",pd), Elasticity,...
        Standard_Elasticity, abs(Elasticity/Standard_Elasticity - 1)*100);
    fprintf(tensile_file,"%s %.2f MPa\n", pad("Proportional Limit (σ_p)",pd),Proportional_Limit);
    fprintf(tensile_file,"%s %.2f MPa\t %.2f MPa\t\t %.2f %%\n",...
        pad("Yield Strength (σ_y)",pd), Yield_Strength,...
        Standard_Yield, abs(Yield_Strength/Standard_Yield - 1)*100);
    fprintf(tensile_file,"%s %.2f MPa\t %.2f MPa\t\t %.2f %%\n",...
        pad("Tensile Strength (σ_u)",pd), Tensile_Strength,...
        Standard_Ultimate, abs(Tensile_Strength/Standard_Ultimate - 1)*100);
    fprintf(tensile_file,"%s %.2f MPa\n", pad("Fracture Strength (σ_f)",pd), Fracture_Strength);
    fprintf(tensile_file,"%s %.2f\n", pad("Strain Hardening Ratio",pd), Strain_Hardening_Ratio);
    fprintf(tensile_file,"%s %.2f %%\n", pad("Elongation [Data] (%EL)",pd), Percent_Elongation_D);
    fprintf(tensile_file,"%s %.2f %%\t\t %.2f  %%\t\t %.2f %%\n",...
        pad("Elongation [Measured] (%EL)",pd), Percent_Elongation_M,...
        Standard_Elongation, abs(Percent_Elongation_M/Standard_Elongation - 1)*100);
    fprintf(tensile_file,"%s %.2f %%\n", pad("Reduction in Area (%RA)",pd), Area_Reduction);
    fprintf(tensile_file,"%s %.2f \t\t %.2f \t\t %.2f %%\n",...
        pad("Poisson's Ratio (ν)",pd), Poissons_Ratio,...
        Standard_Poisson, abs(Poissons_Ratio/Standard_Poisson - 1)*100);
    fprintf(tensile_file,"%s %.3f kJ/m^3\n", pad("Modulus of Resilience (U_r)",pd), Resilience);
    fprintf(tensile_file,"%s %.2f MJ/m^3\n", pad("Modulus of Toughness (U_t)",pd), Toughness);
    fprintf(tensile_file,"%s %.2f \t\t %.2f \t\t %.2f %%\n",...
        pad("Strain Hardening Exponent (n)",pd), Strain_Hardening,...
        Standard_Hardening, abs(Strain_Hardening/Standard_Hardening - 1)*100);
    fprintf(tensile_file,"%s %.2f\n", pad("Strength Coefficient (H)",pd), Strength_Coefficient);
    fprintf(tensile_file,'\n\n'); % end
    
    
    % Save Figures
    if savefig
        for j = 1:length(figs)
            saveas(figs(j),strcat("Figures/",file_names(i),"_",figf(j),".jpg"))
        end
    end
end

tensile_file = fclose(tensile_file);