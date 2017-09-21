import java.util.*;

//import jdk.nashorn.internal.runtime.FindProperty;

public class h1_draft00 
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
    private static final int num_cases = 8;
    
    private static Random rn = new Random();
    private static boolean findSol = false;

    public static boolean[][] visited_state = new boolean[board_edge_size][board_edge_size];
    public static int[] tour_x_trace = new int[board_size];
    public static int[] tour_y_trace = new int[board_size];
    
    // when the value of to_search_case >= 8, no more sol; it stores the un-explored case_ind 
    public static int[] to_search_cases = new int[board_size];
    
    public static int cur_m_ind = 0;
//    public static int cur_x = start_x;
//    public static int cur_y = start_y;

    public static void main(String[] args)
    {
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    	cur_m_ind = 0;
    	
    	resetSol();
     
    	String text = "KT: 8x8, strategy = 1, start = 2,3";
    	System.out.println(text);

    	runStrategy0(0);
    	
        return ;
    }

    public static void resetSol()
    {
    	for (int m_ind = 1; m_ind < board_size; m_ind++)
    	{
    		int x_ind = getXFromInd(m_ind);
    		int y_ind = getYFromInd(m_ind);
    		
			visited_state[x_ind][y_ind] = false;
			tour_x_trace[m_ind] = -1;
			tour_y_trace[m_ind] = -1;
			to_search_cases[m_ind] = 0;
    	}
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    	to_search_cases[0] = 0;
    	cur_m_ind = 0;
    	findSol = false;
    }
    
    // All the data from #reset_ind will be wiped, reset_ind >0;
    public static void resetSol(int reset_ind)
    {
    	assert(reset_ind > 0);
    	for (int m_ind = reset_ind; m_ind < board_size; m_ind++)
    	{    		
    		int x_ind = getXFromInd(m_ind);
    		int y_ind = getYFromInd(m_ind);
    		
    		if(!(visited_state[x_ind][y_ind]) && (tour_x_trace[m_ind]<0))
    		{
    			break;
    		}    		
    		visited_state[x_ind][y_ind] = false;    		
    		tour_x_trace[m_ind] = -1;
    		tour_y_trace[m_ind] = -1;
    		to_search_cases[m_ind] = 0;
    	}
    	
    	cur_m_ind = reset_ind - 1;
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
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
    
    public static boolean isVisited(int x, int y)
    {
    	return visited_state[x][y];
    }

    public static boolean isStartGrid(int x, int y)
    {
    	if(x==2 && y==3)
    	{
    		return true;
    	}
    	return false;
    }
    
    public static boolean testLMove(int cur_x, int cur_y, int move_case)
    {
        boolean valid_move = true;
    //    int next_x = cur_x + getMoveX(move_case);
    //    int next_y = cur_y + getMoveY(move_case);
        int next_x = cur_x + getMoveX(move_case);
        int next_y = cur_y + getMoveY(move_case);
                
        if(!withinBound(next_x, next_y))
        {
        	return false;
        }
        
        if(isVisited(next_x, next_y))
        {
            return false;
        }
        return valid_move;
    }
    
    public static boolean validLast(int x_last, int y_last)
    {
    	int x_diff = start_x - x_last;
    	int y_diff = start_y - y_last;
    	if ((x_diff == 2) || (x_diff == -2))
    	{
    		if ((y_diff == -1) || (y_diff == 1))
    		{
    			return true;
    		}
    	}
    	if ((y_diff == 2) || (y_diff == -2))
    	{
    		if ((x_diff == -1) || (x_diff == 1))
    		{
    			return true;
    		}
    	}
    	
        return false;
    }

    public static int sampleMove()
    {
    	int sample = rn.nextInt() % board_edge_size;
    	assert((sample>=0) && (sample<board_edge_size));
    	return sample;
    }
        
    // start_ind is the last node in the tour_x/y_trace, being explored
    // start_ind < board_size
    public static boolean runStrategy0 (int start_ind)
    {       	
    	int cur_m_ind = start_ind;
    	int next_m_ind = cur_m_ind + 1;
    	
    	if (next_m_ind == board_size)
    	{
    		if(validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind]))
    		{
	    		printSol();
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		printSol();
    		resetSol(cur_m_ind);
    		return false;
    	}
    	
    	for (int case_ind = to_search_cases[cur_m_ind]; case_ind < num_cases; case_ind++)
		{
    		int cur_x = tour_x_trace[cur_m_ind];
    		int cur_y = tour_y_trace[cur_m_ind];
    		boolean is_valid_move = testLMove(cur_x, cur_y, case_ind);
    		
    		if(is_valid_move)
    		{    			
    			int x_step = getMoveX(case_ind);
    			int y_step = getMoveY(case_ind);
    			tour_x_trace[next_m_ind] = x_step + cur_x;
    			tour_y_trace[next_m_ind] = y_step + cur_y;
    			visited_state[x_step + cur_x][y_step + cur_y] = true;
			   			
    			boolean found_sol = runStrategy0(next_m_ind);
    			
    			
    		//	resetSol(next_m_ind);
    		//	if(found_sol)
    		//	{
    			//	resetSol(next_m_ind);
    		//		return true;
    		//	}
    		//	else 
    		//	{
//    				resetSol(next_m_ind);
    		//	}
    			// if have explored all cases    			    				
    		}
    		to_search_cases[cur_m_ind] = to_search_cases[cur_m_ind]+1; 
		}
		// next time, should explore case_ind  + 1
		to_search_cases[cur_m_ind] = 8;    			

    	resetSol(cur_m_ind);
    	return false;
    	
    }

    public static void printSol()
    {
    	// TODO: change the heading 
    	System.out.print("001:");
    	
    	for (int i_ind = 0; i_ind < board_size; i_ind++)
    	{
    		System.out.print(" ");
    		System.out.print(tour_x_trace[i_ind]);
    		System.out.print(",");
    		System.out.print(tour_y_trace[i_ind]);    		
    	}
    	System.out.print(" ");
		System.out.print(start_x);
		System.out.print(",");
		System.out.print(start_y); 
    	System.out.print("\n");
    }
}
