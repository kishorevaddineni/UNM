%% Import data from text file.
% Script for importing data from the following text file:
%
%    C:\Users\Vishal\Desktop\output
%
% To extend the code to different selected data or a different text file,
% generate a function instead of a script.

% Auto-generated by MATLAB on 2014/10/21 17:28:31

%% Initialize variables.
%filename = 'C:\Users\SyamPrasad\Documents\MATLAB\output';
%delimiter = '\t';

%% Format string for each line of text:
%   column1: text (%s)
%	column2: double (%f)
%   column3: double (%f)
%	column4: double (%f)
% For more information, see the TEXTSCAN documentation.
%formatSpec = '%s%f%f%f%[^\n\r]';

%% Open the text file.
%fileID = fopen(filename,'r');

%% Read columns of data according to format string.
% This call is based on the structure of the file used to generate this
% code. If an error occurs for a different file, try regenerating the code
% from the Import Tool.
%dataArray = textscan(fileID, formatSpec, 'Delimiter', delimiter, 'EmptyValue' ,NaN, 'ReturnOnError', false);

%% Close the text file.
%fclose(fileID);

%% Post processing for unimportable data.
% No unimportable data rules were applied during the import, so no post
% processing code is included. To generate code which works for
% unimportable data, select unimportable cells in a file and regenerate the
% script.

%% Allocate imported array to column variable names
%alcohol = projectdata{:, 1};
%state = projectdata{:, 2};
%gender = projectdata{:, 3};
%year = projectdata{:, 4};

%% Clear temporary variables
%clearvars filename delimiter formatSpec fileID dataArray ans;
%dataset = [alcohol,state, gender, year];
[idx,C,sumd,D]=kmeans(projectdata,3);
ca=[idx,projectdata(:,1),projectdata(:,2),projectdata(:,3), projectdata(:,4)];
%ca(:,1)=idx;
%ca(:,2)=tf;
%ca(:,3)=idf;
%figure(1)
% plot(projectdata(idx==1,1),projectdata(idx==1,2),'r.','MarkerSize',12)
% hold on
% plot(projectdata(idx==2,1),projectdata(idx==2,2),'b.','MarkerSize',12)
dm=[1,2,3];
plot3(projectdata(idx==1,dm(1)),projectdata(idx==1,dm(2)),projectdata(idx==1,dm(3)),'r.','MarkerSize',12);
hold on;
plot3(projectdata(idx==2,dm(1)),projectdata(idx==2,dm(2)),projectdata(idx==2,dm(3)),'b.','MarkerSize',12);
plot3(projectdata(idx==3,dm(1)),projectdata(idx==3,dm(2)),projectdata(idx==3,dm(3)),'g.','MarkerSize',12);
plot3(C(:,dm(1)),C(:,dm(2)),C(:,dm(3)),'ko','MarkerSize',12,'LineWidth',2);
% plot(C(:,1),C(:,2),'kx',...
%      'MarkerSize',12,'LineWidth',2)
% plot(C(:,1),C(:,2),'ko',...
%      'MarkerSize',12,'LineWidth',2)
legend('Cluster 1','Cluster 2','Cluster 3','Centroids',...
       'Location','NW')
%plotmatrix(projectdata)
%plot(C(:,1),C(:,2),'*');
%plot(D(:,1),D(:,2),'*');
%plot3(projectdata(:,1),projectdata(:,2),projectdata(:,3),projectdata(:,4));
%stem(projectdata(:,1),projectdata(:,2),C(:,1),C(:,2));

%ylabel('IDF'); 
%title('Centriod plot of the clusters');
xlabel('Alcoholic');
ylabel('States');
zlabel('Gender');