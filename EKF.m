function [xt_est, Pt_est] = EKF(x, P, u, z, c, N, coef) %, r_coef, q_coef)
    r_coef = coef(1);
    q_coef = coef(2);
    dt = 0.02;
    Fx = [eye(3) zeros(3,3*N)];
    xt_est = x + Fx' * g(x,u,dt);
    G = Fx' * g1(x,u,dt) * Fx;
    Gt = eye(size(G)) + G;
    Rt = eye(3) * r_coef;  % 1.4963; %
    Qt = eye(3) * q_coef;  % 0.0896; %
    Pt_est = Gt * P * Gt' + Fx' * Rt *Fx;

    for i = 1:3:size(z,2)
       j = c(i,1);
       if is_new_landmark(x, j+2)
           xt_est(j:j+2) = calc_new_lmk(xt_est,z(:,i));
       end
       z_est = h(xt_est, j);
       Fxj = [Fx;zeros(3,3+3*N)];  
       Fxj(4:end,j:j+2) = eye(3);
       Hti = h1(xt_est,j) * Fxj;
       K = nanzero((Pt_est * Hti')/(Hti*Pt_est*Hti' + Qt));
       restore_lmks = xt_est(6:3:end); % in case K modifies these
       xt_est = xt_est + K * (z(:,i) - z_est);
       I = eye(size(P));
       xt_est(3) = wrap(xt_est(3)); % make sure a is btwn -pi and pi
       xt_est(6:3:end) = restore_lmks;  % landmark ids are ints
       Pt_est = (I - K*Hti) * Pt_est;
    end
end
function pose = g(x, u, dt)
    
    v = u(1);
    w = u(2);
    a = x(3);
    if w == 0  % prevent division by zero
        pose = [v * cos(a) * dt
                v * sin(a) * dt
                0];
    else
        vw = v/w;
        pose = [...
                -vw * sin(a) + vw * sin(a + w * dt)
                 vw * cos(a) - vw * cos(a + w * dt)
                 w * dt];
    end
end
function pose = g1(x, u, dt)
%TODO Work out w=0
    v = u(1);
    w = u(2);
    a = x(3);
    if w == 0  % prevent division by zero
        vw = v;
        pose = [...
            0, 0, -v * sin(a)
            0, 0, v * cos(a)
            0, 0, 0];  % Is this line correct??
    else
        vw = v/w;

        pose = [...
            0, 0, -vw * cos(a) + vw * cos(a + w * dt)
            0, 0, -vw * sin(a) + vw * sin(a + w * dt)
            0, 0,  0
           ];
    end
end
function z = h(x_est, j)
    dx = (x_est(j) - x_est(1));
    dy = (x_est(j+1) - x_est(2));
    a = x_est(3);
    d = [dx;dy]; 
    q = d' * d;
    z = [...
        sqrt(q)
        wrap(atan2(dy, dx) - a);
        x_est(j+2)];
end
function z = h1(x_est, j)
    dx = (x_est(j) - x_est(1));
    dy = (x_est(j+1) - x_est(2));
    d = [dx;dy]; 
    q = d' * d;
    if q
        k = 1/q;
    else
        k = 0;
    end
    sq = sqrt(q);
    z = k * [...
        -sq*dx, -sq*dy, 0,  sq*dx, sq*dy, 0
         dy,    -dx,   -q, -dy,    dx,    0
         0,      0,     0,  0,     0,     q];
end

function new = is_new_landmark(x, c)
    new = x(c) == 0;
end

function xj = calc_new_lmk(x ,zj)
        r = zj(1);  % range
        b = zj(2);  % bearing
        s = zj(3);  % lmk id
        a = x(3); % robot heading
        c = zj(3);
        xr = [x(1:2); c];
        xj = xr + [r*cos(a + b); r*sin(a+b); 0];
end

function x = nanzero(x)
    x(isnan(x)) = 0;
    return
end

function ao = wrap(ao)
%atan2(cos(a), sin(a))
    while ao > pi || ao < -pi
        if ao > pi
            ao = ao - 2*pi;
        end
        if ao < -pi
            ao = ao + 2*pi;
        end
    end
end
