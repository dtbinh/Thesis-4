function [R] = rendevous(time, obs_cells)
    R   = cell(1,6);
    R{1} = time;
    id = cell(5,1);
    
    for k = 1:5
        o = obs_cells{k};
        id{k} = find(o(:,1) == time);
    end
    
    R{2} = 1;
    for k = 2:5
       if find_rvs(obs_cells{1}, id{1}, obs_cells{k}, id{k}) 
           val = R{2};
           R{2} = [val k];
       end
    end
    
    R{3} = 2;
    for k = 3:5
        if find_rvs(obs_cells{2}, id{2}, obs_cells{k}, id{k}) 
           R{3} = [R{3} k];
       end
    end
    
    R{4} = 3;
    for k = 4:5
       if find_rvs(obs_cells{3}, id{3}, obs_cells{k}, id{k}) 
           R{4} = [R{4} k];
       end
    end
    
    R{5} = 4;
    if find_rvs(obs_cells{4}, id{4}, obs_cells{5}, id{5})
        R{5} = [R{5} 5];
    end
end

function [R] = find_rvs(a, ai, b, bi)
    R = false;
    if union(a(ai,2), b(bi,2))
        R = true;
    end
end