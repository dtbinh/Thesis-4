function [X T] = run_mapjoin(d)
    load(strcat(d,'/localmap_1'));
    T = [];
    X = localmap_st(:,2);
    old_X = X;
    for ii = 2:2000
        mapname = strcat(d,'/localmap_', num2str(ii));
        if isempty(dir(strcat(mapname, '.mat')))
            break
        end
        load(mapname)

        map2 = localmap_st(:,2);
        if length(old_X) - 3 > length(map2)
            keyboard;
        end
        X = join_maps(X, map2);
        old_X = X;
        T = [T; X(1:3)];
        X(1:3) = [];
    end
end
