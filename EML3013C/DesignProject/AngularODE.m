function dq = AngularODE(t,q,W,g,k,b,l,L,I,theta0,v_s,i)

theta = q(1);
omega = q(2);

[~,aR] = R(v_s,t,i);

alpha = Alpha(W,(W/g)*aR,k,b,l,L,I,theta0,theta,omega);

dq = [omega;alpha];

end
