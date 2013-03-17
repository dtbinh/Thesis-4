function [landmarks] = create_landmarks(max_distance, n_landmarks)
    % FUNCTION create_landmarks - create a list of 2d landmarks
    %    INPUT:
    %    max_distance - the maximum distance of a landmark from the origin
    %    n_landmarks - the number of landmarks to create
    %    
    %    OUTPUT:
    %    landmarks - a matrix of landmarkx [id_1 x_1 y_1
    %                                       ...
    %                                       id_n x_n y_n]
    
    landmarks = zeros(n_landmarks, 3);
    for id = 1:n_landmarks
        r = unifrnd(0, max_distance);
        b = unifrnd(0, 2*pi - 2*eps);
        landmarks(id,:) = [id r*cos(b) r*sin(b)];
    end
end