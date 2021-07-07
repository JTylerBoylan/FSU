function b_P = RotateByMatrix(a_P,a_R_b)

rotmtx = a_P'.*a_R_b;

b_P = [sum(rotmtx(:,1)) sum(rotmtx(:,2))];

end

