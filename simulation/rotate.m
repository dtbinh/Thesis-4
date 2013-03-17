function [ w ] = rotate( pose, goal, dt )
%ROTATE Calculate odometry for in place rotation towards goal point

    % calculate angle towards point
    heading = pose(3);
    goal(1) = goal(1) - pose(1);
    goal(2) = goal(2) - pose(2);
    a = atan2(goal(2), goal(1)) - heading;

    % calculate radians / dt
    w = a / dt;

end

