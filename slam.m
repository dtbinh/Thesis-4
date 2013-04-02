% Init Environment
function [x P Traj RMSE_map RMSE_traj] = slam(p_val, coef, robot)
% Optimal coef may be 1.5179    2.0
load dataset
groundtruth = eval(strcat('Robot', robot, '_Groundtruth'));
measurement = eval(strcat('Robot', robot, '_Measurement'));
odometry = eval(strcat('Robot', robot, '_Odometry'));

Ignore = Barcodes(1:n_robots,2)';
mapspace = [1 1 1 reshape(repmat(Barcodes(n_robots+1:end,2)',[3,1]), [1,n_landmarks*3])];
mapmgr = [0 0 0 mapspace(4:end)];
% 
% % Graphics
mapFig = figure(str2num(robot));
%cla;
axis([-20 20 -20 20])
axis square
WG =  line('parent',gca,...
    'linestyle','none',...
    'marker','o',...
    'color','r',...
    'xdata',Landmark_Groundtruth(:,2),...
    'ydata',Landmark_Groundtruth(:,3));
Rt =  line('parent',gca,...
    'linestyle','none',...
    'marker','+',...
    'color','r',...
    'xdata',[],...
    'ydata',[]);
R =  line('parent',gca,...
    'linestyle','none',...
    'marker','x',...
    'color','b',...
    'xdata',[],...
    'ydata',[]);
L =  line('parent',gca,...
    'linestyle','none',...
    'marker','*',...
    'color','b',...
    'xdata',[],...
    'ydata',[]);

eG = zeros(1,15);
for i = 1:numel(eG)
    eG(i) = line(...
        'parent', gca,...
        'color','g',...
        'xdata',[],...
        'ydata',[]);
end
% % End Graphics


% Strips robots from measurements!
% for i = 1:length(measurement)
%     if find(Ignore == measurement(i,2))
%         measurement(i,:) = zeros(1,4);
%     end
% end

% Start SLAM
Zs = find(measurement(:,2) == 0);
measurement(Zs, :) = [];
% end strip
t0 = measurement(1) + .001;
t_end = length(odometry);
step = 1;
x = [groundtruth(1,2:end)'; zeros(3 * n_landmarks,1)];
disp(x(1:3))
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
    disp('*****')
    rendevous = [];
    while size(measurement,1) >= lmk0 && measurement(lmk0,1) < t
        obs = measurement(lmk0,2:end)';
        if find(Ignore == obs(1))
            rendevous = [rendevous obs];
        else
            z = [z obs];
        end
        disp(z)
        disp('^^^^')
        disp(rendevous)
        disp('%%%%')
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
            c = [c;find(mapspace == lmks(i))];
            if isempty(find(mapspace == lmks(i)))
                z(:,z_ctr) = [];
                z_ctr = z_ctr - 1;
            end
            z_ctr = z_ctr + 1;
        end
        mapmgr(c) = 0;
    end
    x(find(mapmgr)) = 0;


    [x, P] = EKF(x, P, u(2:3), z, c, n_landmarks, coef); %, r_coef, q_coef);
    Traj = [Traj; t x(1:3)'];
    
    dirname = strcat('./bot', num2str(robot));
    filename = strcat(dirname, '/localmap_', num2str(counter));
    local_ids = [0;0;0;reshape(repmat(Barcodes(n_robots+1:end,1)',[2,1]), [1,n_landmarks*2])'];
    localmap_st = x;
    localmap_st(6:3:end) = []; % remove ids
    localmap_st = [local_ids localmap_st];
    localmap_P = P;
    localmap_P(:,6:3:end) = [];
    localmap_P(6:3:end,:) = [];
    save(filename, 'localmap_st', 'localmap_P', 'rendevous', '-v7.3');

    lid_ct = 1;
    %if c
      for lid = 4:3:size(mapspace,2) %c(:,1)
        le = x(lid:lid+1);
        LE = P(lid:lid+1,lid:lid+1);
        [X,Y] = cov2elli(le,LE,3,16);
        set(eG(lid_ct),'xdata',X,'ydata',Y);
        lid_ct = lid_ct + 1;
      end
    %end
    
    set(R, 'xdata', x(1), 'ydata', x(2));
    set(Rt, 'xdata', groundtruth(counter, 2), 'ydata', groundtruth(counter, 3));
    set(L, 'xdata', x(4:3:end), 'ydata', x(5:3:end));
    counter = counter + 1;
    drawnow
end


T = Landmark_Groundtruth(:,[2 3 1])'; T = T(:); T(3:3:end) = Barcodes(6:end,2);
RMSE_map = sqrt(mean((x(4:end)-T).^2)/length(x));

start_t = Traj(1);
if start_t < 1
    start_t = 1;
end
T = groundtruth(start_t:end,2:end); % skip initial pose
RMSE_traj = sqrt(mean((Traj(:,2:end) - T).^2)/length(Traj)); %TODO Reshape this
