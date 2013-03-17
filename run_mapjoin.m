function [X T] = run_mapjoin(d)
    load(strcat(d,'/localmap_1'));
    T = [];
    X = localmap_st(:,2);
    for ii = 2:2000
        mapname = strcat(d,'/localmap_', num2str(ii));
        load(mapname)
        if isempty(dir(mapname))
            break
        end
        map2 = localmap_st(:,2);
        X = join_maps(X, map2);
        T = X(1:3);
        X(1:3) = [];
    end
end
