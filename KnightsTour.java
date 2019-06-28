
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class KnightsTour {

    private static long finish_time; //Time when the algorithm has completed 
    private static long start_time; //Time when the algorithm hs begun
    private static long total_time = 0; //The total amount of time required to complete computation
    private static Position initial_position; //initial_positionialize the starting position of the knight
    private static boolean running = true;
    private static int board_size; //The enter size of the board n*m
    private static int board_dimension; //The dimension or size of the board
    private  static int x; //x co-ordinate of the board
    private static int y; //y co-ordiante of the board
    private static double seconds;

    /*
        The only real difference between the two approaches in this code lie in the neighbor calculation method
        for each. In checkMoves() all empty neighbors are returned as possibilities; however, with nextMoveWarnsdorff()
        only one new position is returned at a time, dramatically shortening runtime.
     */


    public static void main(String[] args) {
        int[][]mytour;
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter: ");
        System.out.println("1 for Depth First Search Knight's Tour");
        System.out.println("2 for Brute Force Knights Tour");
        System.out.println("3 for Heuristics Applied Knights Tour");
        int option = sc.nextInt(); //Allows the user to select the approach
        try{
            if(option == 1){
                System.out.println("Proceeding with Depth First Search...");
                System.out.println("Please enter the dimension of the board");              
                board_dimension = sc.nextInt();
                if(board_dimension < 5){
                    System.out.println("Solution doesn't exist");
                }
                else{
                    board_size = new Integer(board_dimension*board_dimension);
                    System.out.println("Please enter the x and y co-ordinates for the starting position of the Knight on the board");
                    
                    x = sc.nextInt();
                    y = sc.nextInt();
                    initial_position = new Position(x,y);
                    //Depth First Search Approach
                    dfsTour();
                    //printTour(mytour);
                }
            }
            if(option == 2){
                System.out.println("Proceeding with Brute Force...");
                System.out.println("Please enter the dimension of the board");              
                board_dimension = sc.nextInt();
                if(board_dimension < 5){
                    System.out.println("Solution doesn't exist");
                }
                else{
                    board_size = new Integer(board_dimension*board_dimension);
                    System.out.println("Please enter the x and y co-ordinates for the starting position of the Knight on the board");
                    
                    x = sc.nextInt();
                    y = sc.nextInt();
                    initial_position = new Position(x,y);
                    //Brute force approach
                    bruteForceTour();
                }
            }
            if(option == 3){
                System.out.println("Proceeding with Heuristics...");
                System.out.println("Please enter the dimension of the board");
            
                board_dimension = sc.nextInt();
                System.out.println("Please enter the x and y co-ordinates for the starting position of the Knight on the board");

                x = sc.nextInt();
                y = sc.nextInt();
                board_size = new Integer(board_dimension*board_dimension);
                initial_position = new Position(x,y); //Calls the parameterized constructor of Class Position to assign the position co-ordinates
                //Run heuristic analysis with Warnsdorf
                mytour = heuristicTour();
                printTour(mytour);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void dfsTour(){
        int tour[][] = new int[board_dimension][board_dimension];
        //initializing the board with default value of -1
        for(int i = 0; i < board_dimension; i++)
        {
            for(int j = 0; j < board_dimension; j++)
            {
                tour[i][j]=-1;
            }
        }
        solveDFSTour(tour);
    }

    public static void solveDFSTour(int tour[][])
    {
        tour[x][y]=0;
        // int xMove[] = {2, 1, -1, -2, -2, -1, 1, 2};
        // int yMove[] = {1, 2, 2, 1, -1, -2, -2, -1};
        //At every cell the knight has 8 possible positions to move to. We maintain/refer to these 8 positions from the below two arrays
        int xMove[] = {1, 2, 2, 1, -1, -2, -2, -1};
        int yMove[] = {2, 1, -1, -2, -2, -1, 1, 2};
               
        start_time = System.nanoTime();
        if(nextMove(x,y,1,tour,xMove,yMove)==1) //Passing the initial_positionial count value as 1 to indicate it is the starting position of the Knight
        {
        finish_time = System.nanoTime();
        total_time = finish_time - start_time;
        seconds = (double)total_time / 1_000_000_000.0;
        printTour(tour);   
        }
        else
        System.out.println("Solution doesn't exist");
    }

    public static int nextMove(int x,int y, int count,int tour[][], int xMove[],int yMove[])
    {
        if (count == board_size) //A check to see if count has covered the entire surface of the board. If it has then it has successfully traversed the board.
            return 1;
        int x1,y1;
        for(int i=0;i<8;i++)
        {
            //Keeps trying different possible traversal options from the available 8 positions. 
            x1=x+xMove[i];
            y1=y+yMove[i];
            //Checks to see if the next move is a valid one or not. If not a valid move will try a different route.
            if(isValid(x1,y1,tour)==1)
            {
                //If it is a valid move, the count value which keeps track of the knight's traversal will be added to the traversal route solution
                tour[x1][y1]=count;
                if(nextMove(x1,y1,count+1,tour,xMove,yMove)==1)
                    return 1;
                tour[x1][y1]=-1;                          
            }
            
        }
        return -1;
    }

    public static int isValid(int x,int y,int tour[][])
        {
            if(x >= 0 && y >= 0){
                //Check to see if the position is within the scope of the board
                if(x < board_dimension && y < board_dimension){
                    //Check to see if the position has not been visited
                    if(tour[x][y] == -1){                 
                        return 1;
                    }   
                }
            }
            return -1;
        }

    public static void bruteForceTour(){
        ArrayList<Position> current_tour = new ArrayList<>();
        current_tour.add(initial_position);
        int[][] visited = new int[board_dimension][board_dimension];
        //Initializing the board with value 0
        for(int i = 0; i < board_dimension; i++)
        {
            for(int j = 0; j < board_dimension; j++)
            {
                visited[i][j]=0;
            }
        }
        //Marking the starting position of the Knight on the board
        visited[initial_position.getXcoord()][initial_position.getYcoord()] = 1;
        start_time = System.nanoTime();
        bruteForceBacktrack(current_tour, visited);
        finish_time = System.nanoTime();
        total_time = finish_time - start_time;
        seconds = (double)total_time / 1_000_000_000.0;
         int[][] tour = new int[board_dimension][board_dimension];
         for(int i = 0; i < board_size; i++){
             tour[current_tour.get(i).getXcoord()][current_tour.get(i).getYcoord()] = i+1;
         }
        printTour(tour);
    }

    private static void bruteForceBacktrack(ArrayList<Position> tour, int[][]visited){
        if(tour.size() >= board_size){
            System.out.println("SOLVED");
            running = false;
        }
        else{
            List<Position> moves = checkMoves(tour, visited);
            for(Position p : moves){
                tour.add(p);
                visited[p.getXcoord()][p.getYcoord()] = 1;
                bruteForceBacktrack(tour, visited);
                if(!running){
                    return;
                }
                else{
                    tour.remove(p);
                    visited[p.getXcoord()][p.getYcoord()] = 0;
                }
            }
        }
    }

    public static int[][] heuristicTour(){
        ArrayList<Position> current_tour = new ArrayList<>();
        current_tour.add(initial_position);
        int[][] visited = new int[board_dimension][board_dimension];
        for(int[] row : visited){
            Arrays.fill(row, 0);
        }
        visited[initial_position.getXcoord()][initial_position.getYcoord()] = 1;
        start_time = System.nanoTime();
        warnBacktrack(current_tour, visited);
        finish_time = System.nanoTime();
        total_time = finish_time - start_time;
        seconds = (double)total_time / 1_000_000_000.0;
        int[][] tour = new int[board_dimension][board_dimension];
        for(int i = 0; i < board_size; i++){
            Position cur = current_tour.get(i);
            tour[cur.getXcoord()][cur.getYcoord()] = i+1;
        }

        return tour;
    }

    private static void warnBacktrack(ArrayList<Position> tour, int[][]visited){

        if(tour.size() == board_size){
            System.out.println("SOLVED");
            running = false;
        }
        else{
            Position next_move = nextMoveWarnsdorff(tour, visited);
            tour.add(next_move);
            visited[next_move.getXcoord()][next_move.getYcoord()] = 1;
            warnBacktrack(tour, visited);
            if(!running){
                return;
            }
            else{
                tour.remove(next_move);
                visited[next_move.getXcoord()][next_move.getYcoord()] = 0;
            }

        }
    }

    private static List<Position> checkMoves(ArrayList<Position> tour, int[][] visited){
        ArrayList<Position> possMoves = new ArrayList<>();
        Position current_position = tour.get(tour.size()-1);
        int x = current_position.getXcoord();
        int y = current_position.getYcoord();

        if(x-2 >= 0 && y+1 < board_dimension){
         if(visited[x-2][y+1] != 1) {
            possMoves.add(new Position(x-2, y+1));
            }
        }
        if(x-2 >= 0 && y-1 >= 0){
            if(visited[x-2][y-1] != 1){
            possMoves.add(new Position(x-2,y-1));
            }
        }
        if(x-1 >= 0 && y+2 < board_dimension){
            if(visited[x-1][y+2] != 1){
            possMoves.add(new Position(x-1,y+2));
            }
        }
        if(x-1 >= 0 && y-2 >= 0){
            if(visited[x-1][y-2] != 1){
            possMoves.add(new Position(x-1,y-2));
            }
        }
        if(x+1 < board_dimension && y-2 >= 0){
            if(visited[x+1][y-2] != 1){
            possMoves.add(new Position(x+1,y-2));
            }
        }
        if(x+1 < board_dimension && y+2 < board_dimension){
            if(visited[x+1][y+2] != 1){
            possMoves.add(new Position(x+1,y+2));
            }
        }
        if(x+2 < board_dimension && y-1 >= 0){ 
            if(visited[x+2][y-1] != 1){
            possMoves.add(new Position(x+2,y-1));
            }
        }
        if(x+2 < board_dimension && y+1 < board_dimension){
            if(visited[x+2][y+1] != 1){
            possMoves.add(new Position(x+2,y+1));
            }
        }
        return possMoves;
    }

    private static Position nextMoveWarnsdorff(ArrayList<Position> tour, int[][] visited){
        Position next_move = new Position();
        ArrayList<Position> possible_moves = new ArrayList<>();
        Position current_position = tour.get(tour.size()-1);
        int x = current_position.getXcoord();
        int y = current_position.getYcoord();
        int min_weight;
        Position p;

        if(x-2 >= 0 && y+1 < board_dimension){
            if(visited[x-2][y+1] != 1)
            {
                p = new Position(x - 2, y + 1);
                p.setWeight(calcWeight(p, visited));
                possible_moves.add(p);
            }
        }
        if(x-2 >= 0 && y-1 >= 0){
            if(visited[x-2][y-1] != 1){
                p = new Position(x-2,y-1);
                p.setWeight(calcWeight(p, visited));
                possible_moves.add(p);
            }
        }
        if(x-1 >= 0 && y+2 < board_dimension){
            if(visited[x-1][y+2] != 1){
                p = new Position(x-1,y+2);
                p.setWeight(calcWeight(p, visited));
                possible_moves.add(p);
            }
        }
        if(x-1 >= 0 && y-2 >= 0 && visited[x-1][y-2] != 1){
            p = new Position(x-1,y-2);
            p.setWeight(calcWeight(p, visited));
            possible_moves.add(p);
        }
        if(x+1 < board_dimension && y-2 >= 0 && visited[x+1][y-2] != 1){
            p = new Position(x+1,y-2);
            p.setWeight(calcWeight(p, visited));
            possible_moves.add(p);
        }
        if(x+1 < board_dimension && y+2 < board_dimension && visited[x+1][y+2] != 1){
            p = new Position(x+1,y+2);
            p.setWeight(calcWeight(p, visited));
            possible_moves.add(p);
        }
        if(x+2 < board_dimension && y-1 >= 0 && visited[x+2][y-1] != 1){
            p = new Position(x+2,y-1);
            p.setWeight(calcWeight(p, visited));
            possible_moves.add(p);
        }
        if(x+2 < board_dimension && y+1 < board_dimension && visited[x+2][y+1] != 1){
            p = new Position(x+2,y+1);
            p.setWeight(calcWeight(p, visited));
            possible_moves.add(p);
        }

        min_weight = 8;
        for(Position pos : possible_moves){
            if(pos.getWeight()<min_weight){
                min_weight = pos.getWeight();
            }
        }
        for (Position q :possible_moves){
            if(q.getWeight()==min_weight){
                next_move = q;
            }
        }
        //System.out.println("next Move:"+next_move.getXcoord()+"\t"+next_move.getYcoord());
        return next_move;
    }

    //Calculates neighbor move with least amount of viable available moves and returns that position

    private static int calcWeight(Position position, int[][] visited){
        int weight = 0;
        int x = position.getXcoord();
        int y = position.getYcoord();    

        if(x-2 >= 0 && y+1 < board_dimension && visited[x-2][y+1] != 1) {
            weight++;
        }
        if(x-2 >= 0 && y-1 >= 0 && visited[x-2][y-1] != 1){
            weight++;
        }
        if(x-1 >= 0 && y+2 < board_dimension && visited[x-1][y+2] != 1){
            weight++;
        }
        if(x-1 >= 0 && y-2 >= 0 && visited[x-1][y-2] != 1){
            weight++;
        }
        if(x+1 < board_dimension && y-2 >= 0 && visited[x+1][y-2] != 1){
            weight++;
        }
        if(x+1 < board_dimension && y+2 < board_dimension && visited[x+1][y+2] != 1){
            weight++;
        }
        if(x+2 < board_dimension && y-1 >= 0 && visited[x+2][y-1] != 1){
            weight++;
        }
        if(x+2 < board_dimension && y+1 < board_dimension && visited[x+2][y+1] != 1){
            weight++;
        }
        return weight;
    }

    public static void printTour(int[][] tour){
        System.out.println();
        System.out.println("                 Solution:");
        System.out.println("      Calculation time: "+seconds+" seconds");
        System.out.println("-----------------------------------------------");
        System.out.println(); 
        for(int i = 0; i<tour.length; i++){
            for(int j = 0; j<tour.length; j++){
                if(j == tour.length-1){
                    System.out.print(tour[i][j]+ "\n");
                }else{
                    System.out.print(tour[i][j]+"\t");
                }

            }
        }
    }
}

/*
    Simple position class used to store previously visited positions on board. Weight variable is used to
    determine next move with Warnsdorff
 */
class Position {
    private int xCord;
    private int yCord;
    private int weight;

    public Position() {

    }

    public Position(int xCord, int yCord) {

        this.xCord= xCord;
        this.yCord = yCord;
    }

    public int getXcoord() {
        return xCord;
    }

    public int getYcoord() {
        return yCord;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

}