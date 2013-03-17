clear; 
clf; 
[x P Traj RMSE_map RMSE_traj] = slam(0.01, [1.5 2.0], '1'); 
f = figure(1); 
saveas(f, 'fig1', 'fig'); 
save('final1.mat')

clear; 
clf; 
[x P Traj RMSE_map RMSE_traj] = slam(0.01, [1.5 2.0], '2'); 
f = figure(2); 
saveas(f, 'fig2', 'fig'); 
save('final2.mat')

clear; 
clf; 
[x P Traj RMSE_map RMSE_traj] = slam(0.01, [1.5 2.0], '3'); 
f = figure(3); 
saveas(f, 'fig3', 'fig'); 
save('final3.mat')

clear; 
clf; 
[x P Traj RMSE_map RMSE_traj] = slam(0.01, [1.5 2.0], '4'); 
f = figure(4); 
saveas(f, 'fig4', 'fig'); 
save('final4.mat')

clear; 
clf; 
[x P Traj RMSE_map RMSE_traj] = slam(0.01, [1.5 2.0], '5'); 
f = figure(5); 
saveas(f, 'fig5', 'fig'); 
save('final5.mat')