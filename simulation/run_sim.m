config_sim;

map_true = create_landmarks(map_radius, landmarks);
waypoint_list = create_waypoints(map_radius, waypoints);

% mapFig = figure(1);
% axis([-map_radius map_radius -map_radius map_radius])
% axis square
% LG =  line('parent',gca,...
%     'linestyle','none',...
%     'marker','o',...
%     'color','r',...
%     'xdata',map_true(:,2),...
%     'ydata',map_true(:,3));

% WG =  line('parent',gca,...
%     'linestyle','none',...
%     'marker','x',...
%     'color','b',...
%     'xdata',waypoint_list(:,2),...
%     'ydata',waypoint_list(:,3));

% R =  line('parent',gca,...
%     'linestyle','none',...
%     'marker','+',...
%     'color','r',...
%     'xdata',[],...
%     'ydata',[]);

pose = start_pose;

odometry = [0 0 0]; % t v w
time = 0;
observations = [get_visible_landmarks(map_true, pose, sensor_range, sensor_arc, observation_noise, time)]; % t id x y

for ii = 1:waypoints
    wpt = waypoint_list(ii,2:3);
    pose_old = pose;

    while pdist([wpt;pose(1:2)'], 'euclidean') > close;
        delta_a = atan2(wpt(2) - pose(2), wpt(1) - pose(1)) - pose(3);
        if abs(delta_a) > 10 * eps
            u = [0 rotate(pose, wpt(1:2), dt)];
        else
            u = [velocity(pose(1:2)', wpt(1:2), 1) 0]; 
        end
        time = time + dt;
        [odo, pose] = move_robot(pose, u, dt, motion_noise);
        obs = get_visible_landmarks(map_true, pose, sensor_range, sensor_arc, observation_noise, time);
        odometry = [odometry; time odo'];
        observations = [observations; obs];
%         set(R, 'xdata', pose(1), 'ydata', pose(2));
        
%         drawnow;
    end
end