clear all
close all
%[x,y] = textread('out_cut.txt','%n %n', 'headerlines',2);

num_skip = 0;
start_ind = num_skip * 66;

fid = fopen('out.txt','rt');
input = textscan(fid,'%d %d', 65, 'headerlines', start_ind + 2);
x = cell2mat(input(:,1));
y = cell2mat(input(:,2));

x_start = 2;
y_start = 3;

x_end = x(64);
y_end = y(64);

show_ind = 1:64;
figure
plot(x(show_ind),y(show_ind),'r*-', ...
    x_start, y_start,'bo',...
    x_end, y_end,'bo')
grid on




