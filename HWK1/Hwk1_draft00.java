import java.util.*;

public class Hwk1_draft00 
{

//	UP_LEFT,    0        UP_RIGHT,    1
//	RIGHT_UP,   2        RIGHT_DOWN,  3
//	DOWN_RIGHT, 4        DOWN_LEFT,     5
//	LEFT_DOWN,  6        LEFT_UP      7

    private static final int max_num_states = 1000000;
    private static final int board_size = 64;
    private static final int board_edge_size = 8;
    private static final int start_x = 2;
    private static final int start_y = 3;
    
    private static Random rn = new Random();

    public static boolean[] visited_state = new boolean[board_size];
    
    public static int cur_x = start_x;
    public static int cur_y = start_y;

    public static void main(String[] args)
    {
        
        
        runStrategy0();

        return ;
    }

    // num of cases: 8; return the x or y-step for each type of knight move
    public static int getMoveX(int move_case)
    {
    	int x = 0;
    	if (move_case == 0 || move_case == 5)
    	{
    		x = -1;
    	}
    	else if (move_case == 1 || move_case == 4)
    	{
    		x = 1;    		
    	}
    	else if (move_case == 2 || move_case == 3)
    	{
    		x = 2;
    	}
    	else 
    	{
    		x = -2;
    	}

    	return x;
    }
    public static int getMoveY(int move_case)
    {
    	int y = 0;
    	if (move_case == 0 || move_case == 1)
    	{
    		y = 2;
    	}
    	else if (move_case == 2 || move_case == 7)
    	{
    		y = 1;    		
    	}
    	else if (move_case == 3 || move_case == 6)
    	{
    		y = -1;
    	}
    	else 
    	{
    		y = -2;
    	}

    	return y;
    }
    
    public static int getXFromInd(int ind)
    {
    	int x = ind % board_edge_size;   	
    	return x;
    }
    public static int getYFromInd(int ind)
    {
    	int y = ind / board_edge_size;    	
    	return y;
    }
    public static int getIndFromXY(int x, int y)
    {
    	int ind = y * board_edge_size + x;    	
    	return ind;
    }

    public static boolean withinBound(int x, int y)
    {
    	boolean valid = true;
    	if ((0 <= x) && (x < board_edge_size))
    	{
    		if ((0 <= y) && (y < board_edge_size))
    		{
    			return valid;
    		}
    	}
    	return false;
    }

    // If it is out of bound or is visited, then return ture
    public static boolean isVisited(int ind)
    {
    	if (0 <= ind && ind < board_size)
    	{
        	return visited_state[ind];
    	}
    	return true;
    }
    public static boolean isVisited(int x, int y)
    {
    	if (withinBound(x,y)) 
    	{
    		int ind = getIndFromXY(x, y);
        	return visited_state[ind];
    	}
    	return true;    	
    }

    public static boolean isStartGrid(int x, int y)
    {
    	if(x==2 && y==3)
    	{
    		return true;
    	}
    	return false;
    }
    
    public static boolean testLMove(int move_case)
    {
        boolean valid_move = true;
        int next_x = cur_x + getMoveX(move_case);
        int next_y = cur_y + getMoveY(move_case);
        
        // withinBound Check is already in isVisited.
        if(!isVisited(next_x, next_y))
        {
            return valid_move;
        }
        return false;
    }

    public static int sampleMove()
    {
    	int sample = rn.nextInt() % board_edge_size;
    	assert((sample>=0) && (sample<board_edge_size));
    	return sample;
    }
    public static void runStrategy0 ( )
    {
    	System.out.println("hello java");
    	return ;
    }

}
