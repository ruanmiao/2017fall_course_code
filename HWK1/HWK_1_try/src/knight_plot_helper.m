clear all
close all
%[x,y] = textread('out_cut.txt','%n %n', 'headerlines',2);

board_edge = 8;
board_size = board_edge^2;

num_skip = 2;
start_ind = num_skip * (board_size + 2);

fid = fopen('out.txt','rt');
%fid = fopen('out_terminal.txt','rt');
input = textscan(fid,'%d %d', board_size+1, 'headerlines', start_ind + 4);
x = cell2mat(input(:,1));
y = cell2mat(input(:,2));

x_start = 2;
y_start = 3;

x_end = x(board_size);
y_end = y(board_size);

show_ind = 1:board_size;
figure
plot(x(show_ind),y(show_ind),'r*-', ...
    x_start, y_start,'bo',...
    x_end, y_end,'bo')
tickValues = 0:1:(board_edge-1);
set(gca,'XTick',tickValues)
grid on




