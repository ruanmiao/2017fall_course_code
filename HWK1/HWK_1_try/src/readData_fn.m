function [error_1, error_2, error_3] = readData_fn(A, B)

    [error_1, error_2, error_3] = textread(A,B,'headerlines',1);

end




