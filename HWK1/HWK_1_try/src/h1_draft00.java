import java.util.*;

//import jdk.nashorn.internal.runtime.FindProperty;

public class h1_draft00 
{

//	UP_LEFT,    0        UP_RIGHT,    1
//	RIGHT_UP,   2        RIGHT_DOWN,  3
//	DOWN_RIGHT, 4        DOWN_LEFT,     5
//	LEFT_DOWN,  6        LEFT_UP      7

    private static final int max_num_states = 1000000;
    
    private static final int board_edge_size = 8;
    private static final int board_size = board_edge_size * board_edge_size;

    private static final int start_x = 2;
    private static final int start_y = 3;
    private static final int num_cases = 8;
    private static final int max_sol_count = 200;
    
    private static Random rn = new Random();
    private static boolean findSol = false;

    public static boolean[][] visited_state = new boolean[board_edge_size][board_edge_size];
    public static int[][] fixed_degree = new int[board_edge_size][board_edge_size];
    public static int[][] dynamics_degree = new int[board_edge_size][board_edge_size];
    public static int[] tour_x_trace = new int[board_size];
    public static int[] tour_y_trace = new int[board_size];
    
    // when the value of to_search_case >= 8, no more sol; it stores the un-explored case_ind 
    public static int[] to_search_cases = new int[board_size];
    //public static boolean[][] valid_to_search_cases = new boolean[board_size][num_cases];
    
    public static int sol_count = 0;

    
    public static void main(String[] args)
    {
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    	
    	// initializeAllDegree();
    	resetAll();
     
    	String text = "KT: 8x8, strategy = 1, start = 2,3";
    	System.out.println(text);

    	sol_count = 0;
    	runStrategy2(0);
    	
        return ;
    }

    
    /////////////////////////// reset, initialization, prepare for new run //////////////////
    
    public static void resetAll()
    {
    	for (int m_ind = 1; m_ind < board_size; m_ind++)
    	{
    		int x_ind = getXFromInd(m_ind);
    		int y_ind = getYFromInd(m_ind);
    		
			visited_state[x_ind][y_ind] = false;
			tour_x_trace[m_ind] = -1;
			tour_y_trace[m_ind] = -1;
			to_search_cases[m_ind] = 0;
			//for (int case_ind = 0; case_ind < num_cases; case_ind++)
			//{
			//	valid_to_search_cases[m_ind][case_ind] = true;				
			//}
    	}
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    	to_search_cases[0] = 0;
    	//cur_m_ind = 0;
    	findSol = false;
    	
    	initializeAllDegree();
    }
        
    public static void initializeAllDegree()
    {
    	for (int x_ind = 0; x_ind < board_edge_size; x_ind++)
    	{
    		for (int y_ind = 0; y_ind < board_edge_size; y_ind++)
    		{
        		int grid_fixed_degree = 0;
        		int m_ind = getIndFromXY(x_ind, y_ind);
        		
        		for (int case_ind = 0; case_ind < num_cases; case_ind++)
        		{
        			int x_step = getMoveX(case_ind);
        			int y_step = getMoveY(case_ind);
        			if(withinBound(x_ind + x_step, y_ind + y_step))
        			{
        				grid_fixed_degree = grid_fixed_degree + 1;
        			//	valid_to_search_cases[m_ind][case_ind] = true;
        			}
        		}
        		fixed_degree[x_ind][y_ind] = grid_fixed_degree; 
        		dynamics_degree[x_ind][y_ind] = grid_fixed_degree;
            //	to_search_cases[m_ind] = 0;
    		}
    	}    	    	
    	return ;
    }

    
    ///////////////////////// index/case motion helper function ///////////////////////// 
    
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
    
    public static int sampleMove()
    {
    	int sample = rn.nextInt() % board_edge_size;
    	assert((sample>=0) && (sample<board_edge_size));
    	return sample;
    }
    
    public static boolean inList(int move_case, int[] searched_list)
    {
    	boolean in_list = false;
    	for (int case_id = 0; case_id < num_cases; case_id++)
    	{
    		if(move_case == searched_list[case_id])
    		{
    			return true;
    		}
    	}
    	return in_list;
    }

    
    /////////////////////////// validation helper function ///////////////////////
    
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
    
        
    /////////////////////////// heuristic, motion selection /////////////////////////
    
    public static int nextMoveByFixedDegree(int cur_x, int cur_y, int[] searched_list)
    {
    	int next_case_ind = -1;
    	int min_num_cases = 9;
    	int m_ind = getIndFromXY(cur_x, cur_y);
    	
    	for(int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
    		int x_step = getMoveX(case_ind);
			int y_step = getMoveY(case_ind);
			// is_valid_move is true if withbound and not visited
			boolean is_valid_move = testLMove(cur_x, cur_y, case_ind);
			boolean is_searched = inList(case_ind, searched_list);

			if (is_valid_move && (!is_searched))
			{
				int next_fixed_degree = fixed_degree[cur_x+x_step][cur_y+y_step];
				if(next_fixed_degree < min_num_cases)
				{
					next_case_ind = case_ind;
					min_num_cases = next_fixed_degree;
				}
			}
    	}    	
    	// return -1 if no valid move found;
    	return next_case_ind;
    }
    
    public static int nextMoveByDynamicsDegree(int cur_x, int cur_y)
    {
    	int next_case_ind = -1;
    	int min_num_cases = 9;
    	int m_ind = getIndFromXY(cur_x, cur_y);
    	
    	for(int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
    		int x_step = getMoveX(case_ind);
			int y_step = getMoveY(case_ind);
			// is_valid_move is true if withbound and not visited
			boolean is_valid_move = testLMove(cur_x, cur_y, case_ind);

			if (is_valid_move)
			{
				int next_fixed_degree = dynamics_degree[cur_x+x_step][cur_y+y_step];
				if(next_fixed_degree < min_num_cases)
				{
					next_case_ind = case_ind;
					min_num_cases = next_fixed_degree;
				}
			}
    	}    	
    	// return -1 if no valid move found;
    	return next_case_ind;    	
    }
    
    ///////////////////////// backup function //////////////////////////
    
    // All the data from #reset_ind will be wiped, reset_ind >0;
    public static void resetSol(int reset_ind)
    {
    	assert(reset_ind > 0);
    	assert(reset_ind < board_size);
    	for (int m_ind = reset_ind; m_ind < board_size; m_ind++)
    	{    		
    		int x_ind = tour_x_trace[m_ind];
    		int y_ind = tour_y_trace[m_ind];
    		
    		if((tour_x_trace[m_ind]<0))
    		{    			
    			return ;
    		}
    		//for (int case_ind = 0; case_ind < num_cases; case_ind++)
			//{
			//	valid_to_search_cases[m_ind][case_ind] = true;				
			//}
    		visited_state[x_ind][y_ind] = false;    		
    		tour_x_trace[m_ind] = -1;
    		tour_y_trace[m_ind] = -1;
    		to_search_cases[m_ind] = 0;
    	}
    	
    	//cur_m_ind = reset_ind - 1;
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    }

    public static void backDynamicsDegree(int out_square_id)
    {
    	for (int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
			int x_out = tour_x_trace[out_square_id];
    		int y_out = tour_y_trace[out_square_id];
			// is_valid_move is true if withbound and not visited
			boolean is_valid_move = testLMove(x_out, y_out, case_ind);
			if (is_valid_move)
			{
	    		int x_neighbor = x_out + getMoveX(case_ind);
				int y_neighbor = y_out + getMoveY(case_ind);
			//	int ind_neighbor = getIndFromXY(x_neighbor, y_neighbor);
				dynamics_degree[x_neighbor][y_neighbor] = dynamics_degree[x_neighbor][y_neighbor]+1;
			}

    	}
    	return ;
    }
    
    ///////////////////////////// strategy function ///////////////////////
    
    // start_ind is the last node in the tour_x/y_trace, being explored
    // start_ind < board_size
    public static boolean runStrategy0 (int start_ind)
    {       	
    	if(sol_count > max_sol_count)
    	{
    		return false;
    	}
    	int cur_m_ind = start_ind;
    	int next_m_ind = cur_m_ind + 1;
		int cur_x = tour_x_trace[cur_m_ind];
		int cur_y = tour_y_trace[cur_m_ind];
		boolean cur_visited = isVisited(cur_x, cur_y);
		assert(cur_visited);
    	    	
    	if (next_m_ind == board_size)
    	{
    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		{
    			to_search_cases[cur_m_ind] = 8;
    			
    			visited_state[cur_x][cur_y] = true;
	    		printSol();
	    		sol_count = sol_count+1;
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		resetSol(cur_m_ind);
    		return false;
    	}
    	
    	int start_case = to_search_cases[cur_m_ind];
    	for (int case_ind = start_case; case_ind < num_cases; case_ind++)
		{
    		if(next_m_ind < board_size)
    		{
    			resetSol(next_m_ind);
    		}
    		
    		boolean is_valid_move = testLMove(cur_x, cur_y, case_ind);
    		
    		to_search_cases[cur_m_ind] = to_search_cases[cur_m_ind]+1; 
    		
    		if(is_valid_move)
    		{    			
    			int x_step = getMoveX(case_ind);
    			int y_step = getMoveY(case_ind);
    			tour_x_trace[next_m_ind] = x_step + cur_x;
    			tour_y_trace[next_m_ind] = y_step + cur_y;
    			visited_state[x_step + cur_x][y_step + cur_y] = true;
			   			
    			boolean found_sol = runStrategy0(next_m_ind);
    			
    			while(found_sol)
    			{
    				found_sol = runStrategy0(next_m_ind);    				
    			}
    		    resetSol(next_m_ind);
    		}
		}
		// next time, should explore case_ind  + 1
		to_search_cases[cur_m_ind] = 8;    			

        resetSol(cur_m_ind);
    	return false;
    	
    }

    public static boolean runStrategy1(int start_ind)
    {
    	if(sol_count > max_sol_count)
    	{
    		return false;
    	}
    	
    	int cur_m_ind = start_ind;
    	int next_m_ind = cur_m_ind + 1;
		int cur_x = tour_x_trace[cur_m_ind];
		int cur_y = tour_y_trace[cur_m_ind];
		boolean cur_visited = isVisited(cur_x, cur_y);
		assert(cur_visited);
    	    	
    	if (next_m_ind == board_size)
    	{
    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		{
    			to_search_cases[cur_m_ind] = 8;
    			
    			visited_state[cur_x][cur_y] = true;
	    		printSol();
	    		sol_count = sol_count+1;
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		resetSol(cur_m_ind);
    		return false;
    	}

    	int[] searched_list = new int [num_cases];
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1;
    	}

    	int move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	while(move_case >= 0)
    	{			
    		searched_list[move_case] = move_case;
			int x_step = getMoveX(move_case);
			int y_step = getMoveY(move_case);
			tour_x_trace[next_m_ind] = x_step + cur_x;
			tour_y_trace[next_m_ind] = y_step + cur_y;
			visited_state[x_step + cur_x][y_step + cur_y] = true;
			//valid_to_search_cases[cur_m_ind][move_case] = false;
		   			
			boolean found_sol = runStrategy1(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy1(next_m_ind);    				
			}
		    resetSol(next_m_ind);
		    move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	}
    	
		resetSol(cur_m_ind);
		return false;
    	
    }
    
    public static boolean runStrategy2(int start_ind)
    {
    	if(sol_count > max_sol_count)
    	{
    		return false;
    	}
    	
    	int cur_m_ind = start_ind;
    	int next_m_ind = cur_m_ind + 1;
		int cur_x = tour_x_trace[cur_m_ind];
		int cur_y = tour_y_trace[cur_m_ind];
		boolean cur_visited = isVisited(cur_x, cur_y);
		assert(cur_visited);
    	    	
    	if (next_m_ind == board_size)
    	{
    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		{
    			to_search_cases[cur_m_ind] = 8;
    			
    			visited_state[cur_x][cur_y] = true;
	    		printSol();
	    		sol_count = sol_count+1;
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		//backDynamicsDegree(cur_m_ind);
    		// resetSol(cur_m_ind);
    		return false;
    	}

    	int move_case = nextMoveByDynamicsDegree(cur_x, cur_y);
    	while(move_case >= 0)
    	{			
    		int x_step = getMoveX(move_case);
			int y_step = getMoveY(move_case);
			int x_next = x_step + cur_x;
			int y_next = y_step + cur_y;
			tour_x_trace[next_m_ind] = x_next;
			tour_y_trace[next_m_ind] = y_next;
			visited_state[x_next][y_next] = true;
			//valid_to_search_cases[cur_m_ind][move_case] = false;
			
			// decrease neighbor grid dynamic degree
	    	for (int case_ind = 0; case_ind < num_cases; case_ind++)
	    	{
				// is_valid_move is true if withbound and not visited
				boolean is_valid_move = testLMove(x_next, y_next, case_ind);
				if (is_valid_move)
				{
		    		int x_neighbor = x_next + getMoveX(case_ind);
					int y_neighbor = y_next + getMoveY(case_ind);
				//	int ind_neighbor = getIndFromXY(x_neighbor, y_neighbor);
					dynamics_degree[x_neighbor][y_neighbor] = dynamics_degree[x_neighbor][y_neighbor]-1;
				}
	    	}

		   			
			boolean found_sol = runStrategy1(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy1(next_m_ind);    				
			}
			backDynamicsDegree(next_m_ind);
		    resetSol(next_m_ind);
		    move_case = nextMoveByDynamicsDegree(cur_x, cur_y);
    	}
    	
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    	
    }
    
    ///////////////////////////// print helper /////////////////////////////
    
    public static void printSol()
    {
    	// TODO: change the heading 
    	System.out.print("This so is the ");
    	System.out.print(sol_count);
    	System.out.print("th sol :");
    	
    	for (int i_ind = 0; i_ind < board_size; i_ind++)
    	{
    		System.out.print("\n");
    		System.out.print(tour_x_trace[i_ind]);
    		System.out.print(" ");
    		System.out.print(tour_y_trace[i_ind]);    		
    	}
    	System.out.print("\n");
		System.out.print(start_x);
		System.out.print(" ");
		System.out.print(start_y); 
    	System.out.print("\n");
    }

}
