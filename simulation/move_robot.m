function [odometry, groundtruth] = move_robot(x, u, dt, n)
    %FUNCTION move_robot
    %    INPUT:
    %      x
    %      u
    %     dt
    v = u(1);
    w = u(2);
    a = x(3);
    if w == 0  % prevent division by zero
        pose = x + [v * cos(a) * dt
                v * sin(a) * dt
                0];
    else
        vw = v/w;
        pose = x + [...
                -vw * sin(a) + vw * sin(a + w * dt)
                 vw * cos(a) - vw * cos(a + w * dt)
                 w * dt];
    end
    pose(3) = atan2(sin(pose(3)), cos(pose(3)));
    groundtruth = pose;
    odometry = u' + n * randn(2,1);
end