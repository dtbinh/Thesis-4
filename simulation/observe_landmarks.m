function [obs, ids] = observe_landmarks(visible, noise)
    % FUNCTION observe_landmarks
    %    get range bearing observations of all visible landmarks
    %  INPUT:
    %   visible - set of visible landmarks
    %   noise - observational noise
    %  OUTPUT:
    %   obs - list of range/bearing measurements
    %   ids - ids corresponding to observations
    len = size(visible,1);
    ids = visible(:,1);
    obs = visible(:,2:3);
    for ii = 1:2:len
        x = obs(ii,1);
        y = obs(ii,2);
        r = sqrt(x^2 + y^2);
        b = atan2(y,x);
        obs(ii,:) = [r b] + rand([1,2]) * noise;
    end
end