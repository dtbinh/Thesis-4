function [X, FVAL, EXITFLAG, OUTPUT, GRAD] = join_maps(m1, m2)
    %m1: [x_r;y_r;a_r;f_1x;f_1y;f_1id;...f_nx;f_ny;f_nid]
    %m2: second submap transformed into the frame of m1
    p = [m1(1:3);m2(1:3)];
    x1 = [p;m1(4:end)];
    x2 = [p;m2(4:end)];
    guess_0 = zeros(size(x1,1),1);
    fit = @(x)fitness(x1,x2,x); %(x1-x)'*q*(x1-x)+(x2-x)'*q*(x2-x);
    [X,FVAL,EXITFLAG,OUTPUT,GRAD] = fminunc(fit ,guess_0);
end

function y = fitness(x1,x2,x)
    
    x1_missing = find(x1(7:end) == 0) + 6;
    x2_missing = find(x2(7:end) == 0) + 6;
    
    %TODO: Find a better way to check if the landmark is 0
    % This can fail on [0 0 90]'
    x1(x1_missing) = x2(x1_missing);
    x2(x2_missing) = x1(x2_missing);
    
    q = eye(length(x1)); %inv(I) = I
    % get the set of landmarks in each
    % make sure the maps x1 and x2 are the union!
    
    y = (x1-x)'*q*(x1-x)+(x2-x)'*q*(x2-x);
end

function [y1 y2] = weave(x1, x2, start)
    barcodes = [72;27;54;70;36;18;25;9;81;16;90;61;45;7;63;];
    for ii = start:size(x,1)
        % stabalize missing observations
        if ~find(barcodes(x1(ii+2)))
            x1(ii:ii+2) = 0;
        end
        if ~find(barcodes(x2(ii+2)))
            x2(ii:ii+2) = 0;
        end
        
    end
end
