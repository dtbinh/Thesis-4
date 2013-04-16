clear
load dataset
load join_final

T = {T1, T2, T3, T4, T5};

Gt = Robot1_Groundtruth(:,2:end)'; Gt = Gt(:);
count = size(Gt,1);

for k = 1:5
    dif = count - size(T{k}, 1);
    dif3 = dif/3;
    Tk = T{k};
    Tr = [repmat(Tk(1:3)', [1,dif3])'; T{k}];
    R(k) = sqrt(mean(Gt - Tr).^2)/length(Tr);
end
R = [R mean(R) std(R)]