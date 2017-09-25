import java.util.*;

//import jdk.nashorn.internal.runtime.FindProperty;

public class KnightsTour 
{

//	UP_LEFT,    0        UP_RIGHT,    1
//	RIGHT_UP,   2        RIGHT_DOWN,  3
//	DOWN_RIGHT, 4        DOWN_LEFT,     5
//	LEFT_DOWN,  6        LEFT_UP      7

    private static final int max_num_states = 1000000;
    
    private static final int board_edge_size = 8;
    private static final int board_size = board_edge_size * board_edge_size;

    private static final int move_offset = 1;
    private static final int start_x_const = 1;
    private static final int start_y_const = 2;
    private static int start_x = 1;
    private static int start_y = 2;
    private static final int num_cases = 8;
    private static final int max_sol_count = 1000000;
    private static final int max_move_count = 1000000;
    private static final double nano_to_second = 1000000000.0;
    
    private static Random rn = new Random();

    public static boolean[][] visited_state = new boolean[board_edge_size][board_edge_size];
    public static int[][] fixed_degree = new int[board_edge_size][board_edge_size];
    public static int[][] dynamics_degree = new int[board_edge_size][board_edge_size];
    public static int[] tour_x_trace = new int[board_size];
    public static int[] tour_y_trace = new int[board_size];
    
    // when the value of to_search_case >= 8, no more sol; it stores the un-explored case_ind 
    public static int[] to_search_cases = new int[board_size];
    //public static boolean[][] valid_to_search_cases = new boolean[board_size][num_cases];
    
    public static int sol_count = 1;
    public static int move_count = 1;
    
    public static void main(String[] args)
    {
    	visited_state[start_x][start_y] = true;
    	tour_x_trace[0] = start_x;
    	tour_y_trace[0] = start_y;
    	
    	// initializeAllDegree();
    	String text = "";
    	
    	long startTime = 0;
    	long estimatedTime1 = 0;
    	long estimatedTime2 = 0;
    	long estimatedTime3 = 0;
    	long estimatedTime4 = 0;
    	long estimatedTime5 = 0;
    	long estimatedTime6 = 0;

    	
    	int num_sol1 = 0;
    	int num_sol2 = 0;
    	int num_sol3 = 0;
    	int num_sol4 = 0;
    	int num_sol5 = 0;
    	int num_sol6 = 0;
    	
    	
    	
    	// Statistic for S1
    	resetAll();     
    	text = "KT: 8x8, strategy = 1, start = 2,3";
    	System.out.println(text);
    	sol_count = 0;
    	move_count = 0;
    	  
    	
    	startTime = System.nanoTime();
    	runStrategy1(0);
    	estimatedTime1 = System.nanoTime()-startTime;
    	num_sol1 = sol_count;
    	
    	
    	// Statistic for S2
    	System.out.println();
    	resetAll();     
    	text = "KT: 8x8, strategy = 2, start = 2,3";
    	System.out.println(text);

    	startTime = System.nanoTime();
    	runStrategy2(0);
    	estimatedTime2 = System.nanoTime()-startTime;
       	num_sol2 = sol_count;
    	
    	    	
    	// Statistic for S3
       	System.out.println();
       	resetAll();     
    	text = "KT: 8x8, strategy = 3, start = 2,3";
    	System.out.println(text);

    	startTime = System.nanoTime();
    	runStrategy3(0);
    	estimatedTime3 = System.nanoTime()-startTime;
    	num_sol3 = sol_count;
    	

    	
    	// Statistic for S4
       	System.out.println();
      	resetAll();     
    	text = "KT: 8x8, strategy = 4, start = 2,3";
    	System.out.println(text);
    	
    	startTime = System.nanoTime();
    	runStrategy4(0);
    	estimatedTime4 = System.nanoTime() - startTime;
    	num_sol4 = sol_count;
    	

    	
    	// Statistic for S5
    	System.out.println();
    	resetAll();     
    	text = "KT: 8x8, strategy = 5, start = 2,3";
    	System.out.println(text);
    	
    	startTime = System.nanoTime();
    	runStrategy5(0);
    	estimatedTime5 = System.nanoTime() - startTime;
    	num_sol5 = sol_count;
		
    	
    	
    	// Statistic for S6
    	System.out.println();
    	resetAll();     
    	text = "KT: 8x8, strategy = 6, start = 2,3";
    	System.out.println(text);
    	
    	startTime = System.nanoTime();
    	runStrategy6(0);
    	estimatedTime6 = System.nanoTime() - startTime;
    	num_sol6 = sol_count;
		

    	
    	// Statistic for S1
    	double time_in_second1 = estimatedTime1 / nano_to_second;
    	System.out.println("************* Strategy 1 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second1);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol1);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol1/time_in_second1);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second1);

    	

    	// Statistic for S2
    	double time_in_second2 = estimatedTime2 / nano_to_second;
    	System.out.println("************* Strategy 2 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second2);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol2);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol2/time_in_second2);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second2);


    	// Statistic for S3
    	double time_in_second3 = estimatedTime3 / nano_to_second;
    	System.out.println("************* Strategy 3 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second3);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol3);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol3/time_in_second3);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second3);

    	// Statistic for S4
    	double time_in_second4 = estimatedTime4 / nano_to_second;
    	System.out.println("************* Strategy 4 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second4);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol4);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol4/time_in_second4);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second4);
    	
    	
    	
    	// Statistic for S5
    	double time_in_second5 = estimatedTime5 / nano_to_second;
    	System.out.println("************* Strategy 5 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second5);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol5);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol5/time_in_second5);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second5);

    	
    	
    	// Statistic for S6
    	double time_in_second6 = estimatedTime6 / nano_to_second;
    	System.out.println("************* Strategy 6 ******************");
    	System.out.println("The end: total time elapsed is : ");
    	System.out.println(time_in_second6);
    	System.out.println("The end: num of sols is : ");
    	System.out.println(num_sol6);
//    	System.out.println("The end: average time elapsed each sol is : ");
//    	System.out.println(num_sol6/time_in_second6);
//    	System.out.println("The end: average time elapsed each move is : ");
//    	System.out.println(max_move_count/time_in_second6);
    	
    	    	
        return ;
    }

    
    /////////////////////////// reset, initialization, prepare for new run //////////////////
    
    public static void resetAll()
    {
    	start_x = 1;
    	start_y = 2;
    	sol_count = 1;
    	move_count = 1;
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
    	//cur_m_ind = 0;
    	//findSol = false;
    	
    	initializeAllDegree();
    }
    
    public static void resetAllWithNewStart(int x_new, int y_new)
    {
    	start_x = x_new;
    	start_y = y_new;
    	sol_count = 1;
    	move_count = 1;
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
    	//cur_m_ind = 0;
    	//findSol = false;
    	
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
    	move_case = (move_offset + move_case)%num_cases;
    	
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
    	move_case = (move_offset + move_case)%num_cases;

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
    	int sample = rn.nextInt() % num_cases;
    	assert((sample>=0) && (sample<num_cases));
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

    public static int getNeighborDynamicsOneInd(int x_cur, int y_cur) 
    {
    	int ind = -1;
       	for (int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
    		int x_step = getMoveX(case_ind);
			int y_step = getMoveY(case_ind);
			int x_next = x_step + x_cur;
			int y_next = y_step + y_cur;
			
    		boolean is_valid_move = testLMove(x_next, y_next, case_ind);
			if (is_valid_move)
			{
				if(dynamics_degree[x_next][y_next]==1)
				{
					ind = case_ind;
				}		    						
			}
    	}
    	    
    	return ind;
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
    	if(x==start_x && y==start_y)
    	{
    		return true;
    	}
    	return false;
    }

    public static boolean isConstStartGrid(int x, int y)
    {
    	if(x==start_x_const && y==start_y_const)
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
    
    public static int numNeighborDynamicsDegreeOne(int x_cur, int y_cur)
    {
    	int count = 0;
    	
    	for (int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
    		int x_step = getMoveX(case_ind);
			int y_step = getMoveY(case_ind);
			int x_next = x_step + x_cur;
			int y_next = y_step + y_cur;
			
    		boolean is_valid_move = testLMove(x_next, y_next, case_ind);
			if (is_valid_move)
			{
				if(dynamics_degree[x_next][y_next]==1)
				{
					count++;
				}		    						
			}
    	}
    	    	
    	return count;
    }

    public static int numNeighborDynamicsDegreeTwo(int x_cur, int y_cur)
    {
    	int count = 0;
    	
    	for (int case_ind = 0; case_ind < num_cases; case_ind++)
    	{
    		int x_step = getMoveX(case_ind);
			int y_step = getMoveY(case_ind);
			int x_next = x_step + x_cur;
			int y_next = y_step + y_cur;
			
    		boolean is_valid_move = testLMove(x_next, y_next, case_ind);
			if (is_valid_move)
			{
				if(dynamics_degree[x_next][y_next]<=2)
				{
					if(!isStartGrid(x_next, y_next))
					{
						count++;	
					}
				}		    						
			}
    	}
    	    	
    	return count;
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
				else if (next_fixed_degree == min_num_cases)
				{
					if (rn.nextInt(num_cases) < num_cases/3 )
					{
						next_case_ind = case_ind;
						min_num_cases = next_fixed_degree;						
					}
				}
			}
    	}    	
    	// return -1 if no valid move found;
    	return next_case_ind;
    }
    
    public static int nextMoveByDynamicsDegree(int cur_x, int cur_y, int[] searched_list)
    {
    	int next_case_ind = -1;
    	int min_num_cases = 100;
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
				int next_fixed_degree = dynamics_degree[cur_x+x_step][cur_y+y_step];
				if(next_fixed_degree < min_num_cases)
				{
					next_case_ind = case_ind;
					min_num_cases = next_fixed_degree;
				}
				else if (next_fixed_degree == min_num_cases)
				{
					if (rn.nextInt(num_cases) < num_cases/3 )
					{
						next_case_ind = case_ind;
						min_num_cases = next_fixed_degree;						
					}
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
				
				int dynamics = dynamics_degree[x_neighbor][y_neighbor];
				assert(dynamics >= 0);
				assert(dynamics <= 8);
				
			}

    	}
    	return ;
    }
    
    ///////////////////////////// strategy function ///////////////////////
    
    // start_ind is the last node in the tour_x/y_trace, being explored
    // start_ind < board_size
    public static boolean runStrategy0 (int start_ind)
    {   
    	move_count++;
    	if(move_count > max_move_count)
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
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
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

    	//while(move_case >= 0)
    	while ((move_case >= 0) && (((move_count <= max_move_count) || (sol_count <= max_sol_count))))
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
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
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
//    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		{    			
    			visited_state[cur_x][cur_y] = true;
	    		printSol();
	    		sol_count = sol_count+1;
	    	    //resetSol(cur_m_ind);
	    		to_search_cases[cur_m_ind] = 8;
	    		backDynamicsDegree(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		backDynamicsDegree(cur_m_ind);
    	    resetSol(cur_m_ind);
    		return false;
    	}
    	
    	int[] searched_list = new int [num_cases];
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1;
    	}

    	int move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
    	
    //	if (move_case<0)
    //	{
    //		move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    //	}
    	
    	//int move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);

    	// while(move_case >= 0)
    	while ((move_case >= 0) && ((move_count <= max_move_count) || (sol_count <= max_sol_count)))
    	{		
    		searched_list[move_case] = move_case;
    		
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
		   			
			boolean found_sol = runStrategy2(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy2(next_m_ind);    				
			}
			//backDynamicsDegree(next_m_ind);
		    //resetSol(next_m_ind);
		    move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
		//    if (move_case<0)
	    //	{
	    //		move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
	    //	}
		  // move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	}
    	
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    	
    }
    
    // When selecting a move, if more than one of the available neighbors has a 
    // dynamic degree of 1, this is a failure
    public static boolean runStrategy3(int start_ind)
    {
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
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
	    		
	    		backDynamicsDegree(cur_m_ind);
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		backDynamicsDegree(cur_m_ind);
    		resetSol(cur_m_ind);
    		return false;
    	}

    	int[] searched_list = new int [num_cases];
    	    	
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1; 		
    	}

    	int move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	
		int x_step = 0;
		int y_step = 0;
		int x_next = 0;
		int y_next = 0;
		int count_neighbor_dynamic_degree = numNeighborDynamicsDegreeOne(cur_x, cur_y);
		
		if (count_neighbor_dynamic_degree > 1)
		{
			backDynamicsDegree(cur_m_ind);
			resetSol(cur_m_ind);
			return false;
		}
		
		while ((move_case >= 0) && (count_neighbor_dynamic_degree <= 1) && ((move_count <= max_move_count) || (sol_count <= max_sol_count)))
    	// while((move_case >= 0) && (count_neighbor_dynamic_degree <= 1))
    	{			    		
    		searched_list[move_case] = move_case;
    		x_step = getMoveX(move_case);
    		y_step = getMoveY(move_case);
    		x_next = x_step + cur_x;
    		y_next = y_step + cur_y;
			
    		tour_x_trace[next_m_ind] = x_step + cur_x;
			tour_y_trace[next_m_ind] = y_step + cur_y;
			visited_state[x_step + cur_x][y_step + cur_y] = true;
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

			
			boolean found_sol = runStrategy3(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy3(next_m_ind);    				
			}
			//backDynamicsDegree(next_m_ind);
		    //resetSol(next_m_ind);
		    
		    move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
		    /*
		    if (move_case >= 0)
		    {
	    		searched_list[move_case] = move_case;
				x_step = getMoveX(move_case);
				y_step = getMoveY(move_case);
				x_next = x_step + cur_x;
				y_next = y_step + cur_y;
		    	count_neighbor_dynamic_degree = numNeighborDynamicsDegreeOne(x_next, y_next);			
		    }
		    */
    	}
    	
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    }
    
    // When selecting a move, if there is only one available neighbor with a dynamic 
    // degree of 1, move there.
    public static boolean runStrategy4(int start_ind)
    {
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
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
	    		
	    		backDynamicsDegree(cur_m_ind);
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// printSol();
    		backDynamicsDegree(cur_m_ind);
    		resetSol(cur_m_ind);
    		return false;
    	}

    	int[] searched_list = new int [num_cases];
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1;
    	}

    	// count the number of neighbor with dynamic_degree one
    	int move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	
    	int x_step = 0;
		int y_step = 0;
		int x_next = 0;
		int y_next = 0;
		int count_neighbor_dynamic_degree = numNeighborDynamicsDegreeOne(cur_x, cur_y);	
		
		if(count_neighbor_dynamic_degree ==1)
		{
			move_case = getNeighborDynamicsOneInd(cur_x, cur_y);
		}		    	
    	
    	//while(move_case >= 0)
		while ((move_case >= 0) && ((move_count <= max_move_count) || (sol_count <= max_sol_count)))
    	{		
    	//	assert(move_case < num_cases);
    		searched_list[move_case] = move_case;
		    x_step = getMoveX(move_case);
		    y_step = getMoveY(move_case);
		    x_next = x_step + cur_x;
		    y_next = y_step + cur_y;

			tour_x_trace[next_m_ind] = x_step + cur_x;
			tour_y_trace[next_m_ind] = y_step + cur_y;
			visited_state[x_step + cur_x][y_step + cur_y] = true;
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
			
			boolean found_sol = runStrategy4(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy4(next_m_ind);    				
			}
			//backDynamicsDegree(next_m_ind);
		    //resetSol(next_m_ind);
		    
		    move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
		    if (count_neighbor_dynamic_degree == 1)
		    {
		    	move_case = -1;
		    }
    	}
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    	
    }
    

    public static boolean runStrategy5(int start_ind)
    {
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
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
	    		
	    		backDynamicsDegree(cur_m_ind);
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		backDynamicsDegree(cur_m_ind);
    		resetSol(cur_m_ind);
    		return false;
    	}

    	int[] searched_list = new int [num_cases];
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1;
    	}
    	
    	int move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
    	//if (move_case<0)
    	//{
    	//	move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	//}
    	
    	int x_step = 0;
		int y_step = 0;
		int x_next = 0;
		int y_next = 0;
		int count_neighbor_dynamic_degree = numNeighborDynamicsDegreeOne(cur_x, cur_y);	
		
		if(count_neighbor_dynamic_degree ==1)
		{
			move_case = getNeighborDynamicsOneInd(cur_x, cur_y);
		}	
		else if (count_neighbor_dynamic_degree > 1)
		{
			move_case = -1;
		}
		
    	//while(move_case >= 0)
    	while((move_case >= 0) && ((move_count <= max_move_count) || (sol_count <= max_sol_count)))
    	{			
    		searched_list[move_case] = move_case;
    		
    		x_step = getMoveX(move_case);
		    y_step = getMoveY(move_case);
		    x_next = x_step + cur_x;
		    y_next = y_step + cur_y;
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

					dynamics_degree[x_neighbor][y_neighbor] = dynamics_degree[x_neighbor][y_neighbor]-1;
				}
	    	}

		   			
			boolean found_sol = runStrategy5(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy5(next_m_ind);    				
			}
			//backDynamicsDegree(next_m_ind);
		    //resetSol(next_m_ind);
		    
		    move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
		    //if (move_case<0)
	    	//{
	    	//	move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
	    	//}
		    
		    if (count_neighbor_dynamic_degree == 1)
		    {
		    	move_case = -1;
		    }	    
    	}
    	
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    	
    }
    
    public static boolean strategy6Launch(int x_new, int y_new)
    {
    	// set virtual start x, start y
//    	resetAllWithNewStart(x_new, y_new);
    	
    	
    	int x_step = 0;
		int y_step = 0;
		int x_next = 0;
		int y_next = 0;
    	
		x_step = start_x;
	    y_step = start_y;
	    x_next = -x_step + start_x;
	    y_next = -y_step + start_y;
		tour_x_trace[1] = x_next;
		tour_y_trace[1] = y_next;
		visited_state[x_next][y_next] = true;
		
		x_step = start_y;
	    y_step = start_x;
	    x_next = x_step + start_x;
	    y_next = y_step + start_y;
		tour_x_trace[2] = x_next;
		tour_y_trace[2] = y_next;
		visited_state[x_next][y_next] = true;
    	
		runStrategy6(2);		
		
    	return false;
    }
    

    public static boolean runStrategy6(int start_ind)
    {
    	move_count++;
    	if((move_count > max_move_count) || (sol_count > max_sol_count))
    	{
    		return false;
    	}
    	
    	int cur_m_ind = start_ind;
    	int next_m_ind = cur_m_ind + 1;
		int cur_x = tour_x_trace[cur_m_ind];
		int cur_y = tour_y_trace[cur_m_ind];
		boolean cur_visited = isVisited(cur_x, cur_y);
		assert(cur_visited);
    	    	
		if(cur_m_ind==0) 
		{
			
		}
		
    	if (next_m_ind == board_size)
    	{
    		if((validLast(tour_x_trace[cur_m_ind], tour_y_trace[cur_m_ind])) && to_search_cases[cur_m_ind]<8)
    		{
    			to_search_cases[cur_m_ind] = 8;
    			
    			visited_state[cur_x][cur_y] = true;
	    		printTwinsSol(0);
	    		sol_count = sol_count+1;
	    		sol_count = sol_count+1;
	    	//	resetSol(cur_m_ind);
				return true;			
    		}
    		// resetSol(cur_m_ind);
    		return false;
    	}

    	int[] searched_list = new int [num_cases];
    	for(int case_id = 0; case_id < num_cases; case_id++)
    	{
    		searched_list[case_id] = -1;
    	}
    	
    	int move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
    	if (move_case<0)
    	{
    		move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
    	}
    	
    	int x_step = 0;
		int y_step = 0;
		int x_next = 0;
		int y_next = 0;
		
		int count_neighbor_dynamic_degree = numNeighborDynamicsDegreeOne(cur_x, cur_y);	
		int count_neighbor_dynamics_degree_two = numNeighborDynamicsDegreeTwo(cur_x, cur_y);
		
		if(count_neighbor_dynamic_degree ==1)
		{
			move_case = getNeighborDynamicsOneInd(cur_x, cur_y);
		}	
		else if (count_neighbor_dynamic_degree > 1)
		{
			move_case = -1;
		}
		
		if(count_neighbor_dynamics_degree_two > 1)
		{
			move_case = -1;
		}
		
    	//while(move_case >= 0)
    	while((move_case >= 0) && ((move_count <= max_move_count) || (sol_count <= max_sol_count)))
    	{			
    		searched_list[move_case] = move_case;
    		
    		x_step = getMoveX(move_case);
		    y_step = getMoveY(move_case);
		    x_next = x_step + cur_x;
		    y_next = y_step + cur_y;
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

					dynamics_degree[x_neighbor][y_neighbor] = dynamics_degree[x_neighbor][y_neighbor]-1;
				}
	    	}

		   			
			boolean found_sol = runStrategy6(next_m_ind);
			
			while(found_sol)
			{
				found_sol = runStrategy6(next_m_ind);    				
			}
			backDynamicsDegree(next_m_ind);
		    resetSol(next_m_ind);
		    
		    move_case = nextMoveByDynamicsDegree(cur_x, cur_y, searched_list);
		    if (move_case<0)
	    	{
	    		move_case = nextMoveByFixedDegree(cur_x, cur_y, searched_list);
	    	}
		    
		    if (count_neighbor_dynamic_degree == 1)
		    {
		    	move_case = -1;
		    }
		   
		    if(count_neighbor_dynamics_degree_two > 1)
			{
				move_case = -1;
			}
		   

    	}
    	
    	backDynamicsDegree(cur_m_ind);
		resetSol(cur_m_ind);
		return false;
    	
    }
    
    
    ///////////////////////////// print helper /////////////////////////////
    
    public static void printSol()
    {
    	/*
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
    	*/
    	
    	
    	if(sol_count < 10)
    	{
        	System.out.print("00"+sol_count);
    	}
    	else if (sol_count<100)
    	{
    		System.out.print("0"+sol_count);
    	}
    	else
    	{
    		System.out.print(sol_count);
    	}
    	System.out.print(":");
    	
    	for (int i_ind = 0; i_ind < board_size; i_ind++)
    	{
    		System.out.print(" ");
    		System.out.print(tour_x_trace[i_ind]+1);
    		System.out.print(",");
    		System.out.print(tour_y_trace[i_ind]+1);    		
    	}
    	System.out.print(" ");
		System.out.print(start_x+1);
		System.out.print(",");
		System.out.print(start_y+1); 
    	System.out.print("\n");
    	
    }

    public static void printTwinsSol(int start_ind)
    {
    	/*
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
    	*/
    	
    	
    	if(sol_count < 10)
    	{
        	System.out.print("00"+sol_count);
    	}
    	else if (sol_count<100)
    	{
    		System.out.print("0"+sol_count);
    	}
    	else
    	{
    		System.out.print(sol_count);
    	}
    	System.out.print(":");
    	
    	for (int i_ind = 0; i_ind < board_size; i_ind++)
    	{
    		int real_ind = (i_ind + start_ind) % board_size;
    		System.out.print(" ");
    		//System.out.print("\n");
    		System.out.print(tour_x_trace[real_ind]+1);
    		System.out.print(",");
    		//System.out.print(" ");
    		System.out.print(tour_y_trace[real_ind]+1);    		
    	}
    	System.out.print(" ");
    	//System.out.print(" \n");
		System.out.print(start_x+1);
		System.out.print(",");
		//System.out.print(" ");
		System.out.print(start_y+1); 
    	System.out.print("\n");
    	
    	int next_sol = sol_count + 1;
    	if(next_sol < 10)
    	{
        	System.out.print("00"+next_sol);
    	}
    	else if (next_sol<100)
    	{
    		System.out.print("0"+next_sol);
    	}
    	else
    	{
    		System.out.print(next_sol);
    	}
    	System.out.print(":");

    	System.out.print(" ");
    	//System.out.print(" \n");
		System.out.print(start_x+1);
		System.out.print(",");
		//System.out.print(" ");
		System.out.print(start_y+1); 
    	for (int i_ind = board_size-1; i_ind >=  0; i_ind--)
    	{
    		int real_ind = (i_ind + start_ind) % board_size;
    		
    		System.out.print(" ");
    		//System.out.print("\n");
    		System.out.print(tour_x_trace[i_ind]+1);
    		System.out.print(",");
    		//System.out.print(" ");
    		System.out.print(tour_y_trace[i_ind]+1);    		
    	}
    	System.out.print("\n");
    	
    }

    
    
    
}
