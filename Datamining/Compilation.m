dataset.trainx = data;
dataset.testx = data;
params.minptslb = 1;
params.minptsub = 100;
params.ptsStep = 10;
params.theta = 2;

lof(dataset, params)