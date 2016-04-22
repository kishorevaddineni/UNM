idx=zeros(10000,10);
nObser=10000;
kf=10;
c=cvpartition(nObser,'kfold',kf); % Returns cvpartition class object for kfold cross validation on n Observations(nObser) 
for i=1:1:10
    idx(:,i)=test(c,i); %returns the logical vector idx of test indices for repetition i of an object c of the cvpartition class of type 'kfold'
end

%Finding the Training and Test indexes
trainingindex=zeros(9000,10);
testindex=zeros(1000,10);

n=1;
while n<=10 
    k=1;
    p=1;
    j=1;
    while j<=10000
        if(idx(j,n)==0)
        trainingindex(k,n)=j;
        k=k+1;
        else 
        testindex(p,n)=j;
        p=p+1;
        end
        j=j+1;
    end
    n=n+1;
end


% Creating a Pattern Recognition Network
hiddenLayerSize = 10;
net = patternnet(hiddenLayerSize);

% Choosing Input and Output Pre/Post-Processing Functions
net.input.processFcns = {'removeconstantrows','mapminmax'}; %Input Pre/Post-Processing Functions
net.output.processFcns = {'removeconstantrows','mapminmax'}; %Output Pre/Post-Processing Functions


acc=zeros(1,10); % Defining the variable to store accuracies

% Taking the labels
Labels(1:5000,1)=1;
Labels(5001:10000,1)=0;
Labels(:,2)=~label(:,1);

for i=1:1:10
    
% Setup Division of Data for Training, Validation, Testing
% Choosing to divide the data randomly
net.divideFcn = 'divideind';
% Choosing Division Mode
net.divideMode = 'sample'; 
% Assigning the Training indexes
%net.divideParam.trainInd=trainingindex(i,1:8000);
net.divideParam.trainInd=trainingindex(1:8100,i);
% Assigning the Validation indexes
%net.divideParam.valInd=trainingindex(i,8001:9000);
net.divideParam.valInd=trainingindex(8101:9000,i);
% Assigning the Test indexes
%net.divideParam.testInd=testindex(i,:);
net.divideParam.testInd=testindex(:,i);

% Choosing the Training Function
net.trainFcn = 'trainscg';  % Scaled conjugate gradient

% Choosing the Performance Function
net.performFcn = 'crossentropy';  % Cross-entropy

% Choosing the Plot Functions
net.plotFcns = {'plotperform','plottrainstate','ploterrhist', ...
  'plotregression', 'plotfit'};

% Defining the network input X and network target T
X = data';
T = Labels';

% Training the Network
[net,tr] = train(net,X,T); % trains a network net with the input X and target T. Returns the new network 'net' and training record tr.

% Testing the Network
y = net(X); % Returns network output
errors = gsubtract(T,y); % Returns the errors
Tindices = vec2ind(T);  % Converting target vector T to indices
yindices = vec2ind(y); % % Converting output vector Y to indices
percentErrors = sum(Tindices ~= yindices)/numel(Tindices); 
performance = perform(net,T,y); % Calculates the network performance

% Recalculate Training, Validation and Test Performance
trainTargets = T.* tr.trainMask{1};
valTargets = T .* tr.valMask{1};
testTargets = T .* tr.testMask{1};
trainPerformance = perform(net,trainTargets,y); % Training Performance
valPerformance = perform(net,valTargets,y); % Validation Performance
testPerformance = perform(net,testTargets,y); % Test Performance

[c,cm,ind,per] = confusion(T,y); % Returns the Confusion Value c, Confusion Matrix cm,indices ind and the percentges associated per
acc(1,i)=((1-c)*100); % Calculating the accuracy

end
% To View the Neural Network Diagram
view(net)
% Plotting the Confusion Matrix
figure, plotconfusion(T,y)
%To know the average of all the accuracies obtained in the training
accuracy=mean(acc)

