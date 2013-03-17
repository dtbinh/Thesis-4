function [visible] = get_visible_landmarks(lm, pose, range, arc, noise, time)
    %FUNCTION get_visible_landmarks
    %   INPUT:
    %   pose - pose of the sensor in the global frame [x;y;a]
    %   range - max range of the sensor
    %   arc - max arc of the sensor relative to local x axis
    %   lm - a matrix of landmarkx [id_1 x_1 y_1
    %                                ...
    %                                id_n x_n y_n]
    %   OUTPUT:
    %    visible - the set of visible landmarks for this sensor
    %              in the same format as lm
    visible = lm;
    for ii = 1:size(lm, 1)
        lmk_g = lm(ii,:);
        % transform to local frame
        t = pose(1:2);
        a = pose(3);
        R = [cos(a) -sin(a); sin(a) cos(a)];
        lmk =  R * lmk_g(2:3)' + t;
        r = sqrt(lmk(1)^2 + lmk(2)^2);
        b = abs(atan2(lmk(2), lmk(1)));
        if r > range || b > arc
            jj = find(visible(:,1) == ii);
            visible(jj,:) = [];
        end
    end
    z = zeros(size(visible,1),1) + time;
    visible = [z perturb(visible, noise)];
    
end

function X = perturb(X_true, noise)
    x = X_true(:,2:3);
    len = size(x,1);
    X = [X_true(:,1), noise.*randn(len,2)];
end

