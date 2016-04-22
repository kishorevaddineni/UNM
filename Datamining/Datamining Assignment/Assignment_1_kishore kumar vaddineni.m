% data is the variable having the given data in file(imported to the variable
% using import) 
%Plot tanspose of data
plot(data')
% Normalizing the data
for i= 1:1000
data(:,i)=zNorm(data(:,i)); %zNorm is the function in zNorm.m file used to normalize
end
% Finding Covariance matrix
C= (data'*data);
% Finding eigen vectors and eigen vlues
[V,D] =eig(C);

%To find the number of points in each cluster(denoted as cl) -- for reference
% x=data*V(:,998);  
% y=data*V(:,999); 
% cl1=0;
% cl2=0;
% cl3=0;
% cl4=0;
% cl5=0;
% cl6=0;
% cl7=0;
% cl8=0;
% cl9=0;
% for j=1:10000 
% if (x(j)<=-20 && x(j)>=-25) && (y(j)<=-6 && y(j)>=-7.5)
% cl1=cl1+1;
% elseif (x(j)<=0 && x(j)>=-8) && (y(j)<=-4 && y(j)>=-12)
% cl2=cl2+1;
% elseif (x(j)<=1 && x(j)>=-4) && (y(j)<=-1 && y(j)>=-4)
% cl3=cl3+1;
% elseif (x(j)<=1 && x(j)>=-4) && (y(j)<=3.5 && y(j)>=-0.5)
% cl4=cl4+1;
% elseif (x(j)<=-0.75 && x(j)>=-5) && (y(j)<=8 && y(j)>=5)
% cl5=cl5+1;
% elseif (x(j)<=4 && x(j)>=-0.75) && (y(j)<=8 && y(j)>=4)
% cl6=cl6+1;
% elseif (x(j)<=11 && x(j)>=4) && (y(j)<=12 && y(j)>=7.5)
% cl7=cl7+1;
% elseif (x(j)<=12 && x(j)>=2) && (y(j)<=24 && y(j)>=18)
% cl8=cl8+1;
% elseif (x(j)<=20.6 && x(j)>=20.2) && (y(j)<=-20.2 && y(j)>=-20.6)
% cl9=cl9+1;
% end
% end
% cl1 %-- 1000
% cl2 %-- 2003
% cl3 %-- 997
% cl4 %-- 1000
% cl5 %-- 1000
% cl6 %-- ~1000
% cl7 %-- 1000
% cl8 %-- 1000
% cl9 %-- 1000
plot(data*V(:,998),data*V(:,999),'*') % taking 998 and 999 columns since there is a drastic change from 998 to 997 eigen values

% I have found total 9 clusters from the plot. Each cluster is having more than 990 points. This can be found by using the commented script in the code snippet.
% There is a small gap between cluster 2(cl2) and cluster 3(cl3) and also between cluster 5(cl5) and cluster 6(cl6) which made me to consider them as 2 clusters. Shown in the reference plot.

