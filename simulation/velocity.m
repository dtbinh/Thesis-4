function [ v ] = velocity( origin, goal, max_v)
%DRIVE calculate velocity to point

    X = [origin;goal];
    d = pdist(X, 'euclidean');
    %v = ceil(d) / max_v;
    if d > max_v
        v = max_v;
    else
        v = d;
    end
    
end

