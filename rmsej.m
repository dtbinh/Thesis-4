clear
load join_final
load dataset
Mt = [m1(4:end) m2(4:end) m3(4:end) m4(4:end) m5(4:end)];
T = Landmark_Groundtruth(:,[2 3 1])'; T = T(:); T(3:3:end) = [];
for k = 1:5
RMSE(k) = sqrt(mean(Mt(:,k) - T).^2)/length(T);
end
RMSE = [RMSE mean(RMSE) std(RMSE)]