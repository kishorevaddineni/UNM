%Initiating the variable with no. of folds
kf=10;

% Taking the labels
Labels=zeros(10000,1);
Labels(1:5000,1)=1;

Indices = crossvalind('Kfold',Labels, kf);   % Returns randomly generated indices for a K-fold cross-validation of N observations(Labels)
CP = classperf(Labels);                      % Evaluating the performance of classifier

trainingIndex=0; %Initiating the trainingIndex variable
testIndex=0; %Initiating the trainingIndex variable
for i = 1:kf       
    % Assigning training and test indices
    z=(Indices==i);
    
    testIndex = z; % Getting indices of test instances
    trainingIndex = ~z; % Getting indices of training instances
    
    % Training an SVM model over training instances
    SVMStruct = svmtrain( data(trainingIndex,:), Labels(trainingIndex), 'Autoscale',true,'Showplot',false, 'Method','QP', 'BoxConstraint',2e-1, 'Kernel_Function','rbf', 'RBF_Sigma',0.2);
    
    Group = svmclassify(SVMStruct, data(testIndex,:)); %classifies each row of the data in Sample, a matrix of data, using the information in a support vector machine classifier structure SVMStruct, created using the svmtrain function
    
    CP = classperf(CP, Group, testIndex); % Evaluating the peroformance of the classifier
end

CP.CorrectRate % Getting the Classified Samples
CP.CountingMatrix % Getting the classification confusion matrix