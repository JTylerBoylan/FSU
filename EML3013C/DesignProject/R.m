function [yR,aR] = R(v_s,t,i)

if (i == 1)
    yR = 0.02.*sin(v_s.*t);
    aR = -0.02.*v_s^2.*sin(v_s.*t);
else
    yR = 0.02.*sin(v_s.*t);
    aR = -0.02.*v_s^2.*sin(v_s.*t);
end

end
