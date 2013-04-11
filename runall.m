RMSE_map = cell(5,1);
RMSE_traj = cell(5,1);
x = cell(5,1);
P = cell(5,1);
Traj = cell(5,1);

for ii = 1:5
    [x{ii} P{ii} Traj{ii} RMSE_map{ii} RMSE_traj{ii}] = slam(0.5, [1.5 2], num2str(ii));
    figure(ii + 5);
    imagesc(P{ii});
end

[m1 T1] = run_mapjoin('bot1');
[m2 T2] = run_mapjoin('bot2');
[m3 T3] = run_mapjoin('bot3');
[m4 T4] = run_mapjoin('bot4');
[m5 T5] = run_mapjoin('bot5');

[U1, r1] = kabsch(m1,m2);
[U2, r2] = kabsch(m1,m2);
[U3, r3] = kabsch(m1,m2);
[U4, r4] = kabsch(m1,m2);

m1_k = m1;
m2_k = U1*m2+r1;
m3_k = U2*m3+r2;
m4_k = U3*m4+r3;
m5_k = U4*m5+r4;

mk_g = join_maps(m1_k(4:end), m2_k(4:end));
mk_g = join_maps(mk_g(4:end), m3_k(4:end));
mk_g = join_maps(mk_g(4:end), m4_k(4:end));
mk_g = join_maps(mk_g(4:end), m5_k(4:end));
