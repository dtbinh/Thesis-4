% Init Environment
function [x P Traj RMSE_map RMSE_traj] = slam(p_val, coef, robot, data)
% Optimal coef may be 1.5179    2.0
load(data)
groundtruth = waypoint_list;
measurement = eval(strcat('observations_', robot));
odometry = eval(strcat('odometry_', robot));

mapspace = [1 1 1 reshape(repmat(map_true(:,1)', [3,1]), [1,3* landmarks])];
mapmgr = [0 0 0 mapspace(4:end)];


% Start SLAM
Zs = find(measurement(:,2) == 0);
measurement(Zs, :) = [];
% end strip
t0 = 0;
t_end = measurement(end,1);
step = 1;
x = zeros(3 * landmarks + 3,1);
%disp(x(1:3))
P = eye(size(x,1)) * p_val;

u = [t0;0;0];
index = 1;
lmk0 = 1;
counter = 1;
old = 0;
Traj = [];
for t = t0:step:t_end
    if counter > size(groundtruth,1)
        break % this should never happen!
    end
    u = odometry(index,1:3);  
    if u(1) <= t
        index = index + 1;
    end
    
    j = 1; z = []; c = [];
    %disp('*****')
    rendevous = [];
    while size(measurement,1) >= lmk0 && measurement(lmk0,1) < t
        obs = measurement(lmk0,2:end)';
%         if find(Ignore == obs(1))
%             rendevous = [rendevous obs];
%         else
            z = [z obs];
        %end

        j = j + 1;
        lmk0 = lmk0 + 1;
    end

    % move lmk id to bottom

    if ~isempty(z)
        z([1 3], :) = z([3 1], :);
        z([1 2], :) = z([2 1], :);
        lmks = z(3,:);
        lmk_ct = size(z,2);
        z_ctr = 1;
        for i = 1:lmk_ct
            c = [c;find(mapspace == lmks(i))'];
            if isempty(find(mapspace == lmks(i)))
                z(:,z_ctr) = [];
                z_ctr = z_ctr - 1;
            end
            z_ctr = z_ctr + 1;
        end
        mapmgr(c) = 0;
    end
    x(find(mapmgr)) = 0;


    [x, P] = EKF(x, P, u(2:3), z, c, landmarks, coef); %, r_coef, q_coef);
    Traj = [Traj; t x(1:3)'];
    
    dirname = strcat('./bot', num2str(robot));
    filename = strcat(dirname, '/localmap_', num2str(counter));
    local_ids = [0;0;0;reshape(repmat(map_true(:,1)',[2,1]), [1,landmarks*2])'];
    localmap_st = x;
    localmap_st(6:3:end) = []; % remove ids
    localmap_st = [local_ids localmap_st];
    localmap_P = P;
    localmap_P(:,6:3:end) = [];
    localmap_P(6:3:end,:) = [];
    save(filename, 'localmap_st', 'localmap_P', 'rendevous', '-v7.3');

end


T = map_true(:,[2 3 1])'; 
T = T(:); 

RMSE_map = sqrt(mean((x(4:end)-T).^2)/length(x));

start_t = Traj(1);
if start_t < 1
    start_t = 1;
end
T = groundtruth(start_t:end,2:end); % skip initial pose
Traj_r = Traj(1:length(Traj)/length(T):end, :); Traj_r = Traj_r(:,2:3)'; 
Traj_r = Traj_r(:);
T = T';
Tr = T(:);
%Tr = reshape(T, [len, 1]);
RMSE_traj = sqrt(mean((Traj_r - Tr).^2)/length(Traj_r));