function G = fitness(X, m1, m2)
    % m1 and m2 must be the same size!
    m1_f = m1(6:3:end);
    m2_f = m2(6:3:end);
    common = intersect(m1_f, m2_f);
    P = eye(size(m1, 1)) * .002;
    r = X(1:2);
    a = X(3);
    % TODO: Do I need to prep m2 into m1's frame?
    X_join = (m1 - H(X, common));
    Y_join = (m2 - H(X, common));
    G = (X_join' * inv(P) * X_join) + (Y_join' * inv(P) * Y_join);
end


function H_j = H(X, com)
    % X : [Xr0 Xr1 f1...fj fj+1 fl]'
    a0 = X(3);
    H_j = zeros(size(X(4:end)));
    H_j(1:3) = X(4:6);
    Y = X(1:2);
    len = length(X(7:end));
    for i = 7:3:len
        id = X(i + 2);
        if find(com == id)
            H_j(i:i+1) = R(a0) * (X(i:i+1) - Y);
            H_j(i+2) = id;
        else  % new lmk
            H_j(i:i+2) = X(i:i+2);
        end
    end
end

function A = R(a)
    A = [cos(a) -sin(a); 
         sin(a)  cos(a)];
end
