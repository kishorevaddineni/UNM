%To find the local outlier factors and number of outier factors for each k 
function results = lof(dataset, params)
%checking the trainingdata and testdata fields in the dataset
   if (~isfield(dataset, 'trainingdata') || ~isfield(dataset, 'testdata'))
   error('LocalOutlierFactor:Dataset structure must contain trainingdata and testdata fields');
   end
%checking the lowerbound, upperbound and thetathreshold fields in the params   
   if ~isfield(params, 'lowerbound') || ~isfield(params, 'upperbound') || ~isfield(params, 'thetathreshold')
   error('LocalOutlierFactor:Params structure must contain lowerbound, upperbound and thetathreshold fields');
   end
%checking the step in params to assign kvaluestep    
    if isfield(params, 'step')
        kvaluestep = params.step;
    else
        kvaluestep = 1;
    end
%assigning the size of trainingdata to trainN variable    
    trainN = size(dataset.trainingdata,1);
%assigning the size of testdata to testN variable
    testN = size(dataset.testdata, 1);
%assigning the training data to traindata from dataset
    traindata = dataset.trainingdata;
%assigning the test data to testdata from dataset
    testdata = dataset.testdata;
%assigning the params upperbound value to upperbound
    upperbound = params.upperbound;
%assigning the params lowerbound value to lowerbound
    lowerbound = params.lowerbound;
%assigning the kvalues
    kvalues = lowerbound:kvaluestep:upperbound;
%assigning the kcount
    kCount = size(kvalues,2);
%assigning zeros to results.yprob
    results.yprob = zeros(testN,1);
%assigning zeros to results.lof
    results.lof = zeros(testN, kCount);    

% calculating kdist and kneighbors for samples in training data
    kdist = zeros(trainN, kCount);
    kneighbors = cell(trainN, kCount);
    for i = 1:trainN
        dist = sum((traindata - repmat(traindata(i,:), trainN, 1)).^2, 2);%calculating dist
        dist(i) = max(dist);%maximum dist
        sorteddist = sort(dist);%sorted dist values
        kdist(i,:) = sorteddist(kvalues);%sorted kvalues
        for k = 1:kCount
            kneighbors{i, k} = find(dist <= kdist(i,k));
        end
    end

% for every sample in test data
    for i = 1:testN
% finding k-distance and neighbors of test sample
        dist = sum((traindata - repmat(testdata(i,:), trainN, 1)).^2, 2);%calculating distance
        sorteddist = sort(dist);%sorting distance
        for k = 1:kCount
            kdist = sorteddist(kvalues(k));
            neighbors = find(dist <= kdist);
            neighborCount = size(neighbors,1);
            % calculate reachability distance of test sample to each sample
            % in neighborhood
            reachdist = max( kdist(neighbors, k), dist(neighbors));%reachability distance
            localreachdist = neighborCount ./ sum(reachdist);% local reachability distance
            % calculate local reachability distance of all neighbors
            neighbourlocalreachdist = zeros(1, neighborCount);
            for n = 1:neighborCount
                nkneighbors = kneighbors{neighbors(n), k};
                nkncount = size(nkneighbors,1);
                ndist = sum( (traindata(nkneighbors,:) - repmat(traindata(neighbors(n),:), nkncount, 1)).^2, 2);
                neighbourreachdist = max( kdist(nkneighbors, k), ndist);%calculating neighbourhood reachability distance
                neighbourlocalreachdist(1, n) = nkncount ./ sum(neighbourreachdist);%calculating neighbourhood local reachability distance
            end
            lof = sum(neighbourlocalreachdist) ./ (neighborCount .* localreachdist);%calculating local outlier factor
            if isnan(lof) || isinf(lof)
                lof = 0; %assigning local outlier factor as zero
            end
            results.lof(i,k)  = lof;
            
        end
        results.yprob(i) = max(results.lof(i,:));
    end
    results.yprob= results.yprob;
    results.y = (results.yprob > params.thetathreshold);
    plot(kvalues,sum(results.y));%plotting the results
end

