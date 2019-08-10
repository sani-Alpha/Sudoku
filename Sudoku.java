import java.applet.* ;
import java.awt.* ;

//Solves suduko using backtracking and recurssion
public class Sudoku extends Applet implements Runnable
{
   protected int grid[][] ;

   protected Button view[][] ;

   protected void createGrid()
   {
      grid = new int[9][9] ;

      // Clear all cells
      for( int row = 0; row < 9; row++ )
         for( int col = 0; col < 9; col++ )
            grid[row][col] = 0 ;

      // Create the initial situation
           grid[0][3] = 1 ;
           grid[0][6] = 5 ;
           grid[0][8] = 2 ;

           grid[1][4] = 9 ;

           grid[2][1] = 9 ;
           grid[2][2] = 8 ;
           grid[2][3] = 5 ;
           grid[3][7] = 7 ;
      
           grid[3][4] = 6 ;
           grid[3][5] = 1 ;
      
           grid[4][2] = 5 ;
           grid[4][7] = 4 ;
    
           grid[5][0] = 9 ;
           grid[5][1] = 2 ;
           grid[5][5] = 5 ;
           grid[5][7] = 3 ;

           grid[6][3] = 7 ;
           grid[6][5] = 4 ;
           grid[6][8] = 8 ;

           grid[7][6] = 8 ;
           grid[7][8] = 5 ;
     
           grid[8][0] = 3 ;
           grid[8][1] = 5 ;
           grid[8][8] = 6 ;
   }


   /** Creates an empty view */
   protected void createView()
   {
      setLayout( new GridLayout(9,9) ) ;

      view = new Button[9][9] ;

      // Create an empty view
      for( int row = 0; row < 9; row++ )
         for( int col = 0; col < 9; col++ )
         {
            view[row][col]  = new Button() ;
            add( view[row][col] ) ;
         }
   }

   /** Updates the view from the grid */
   protected void updateView()
   {
      for( int row = 0; row < 9; row++ )
         for( int col = 0; col < 9; col++ )
            if( grid[row][col] != 0 )
               view[row][col].setLabel( String.valueOf(grid[row][col]) ) ;
            else
               view[row][col].setLabel( "" ) ;
   }

   /** This method is called by the browser when the applet is loaded */
   public void init()
   {
      createGrid() ;
      createView() ;
      updateView() ;
   }

   /** Checks if num is an acceptable value for the given row */
   protected boolean checkRow( int row, int num )
   {
      for( int col = 0; col < 9; col++ )
         if( grid[row][col] == num )
            return false ;

      return true ;
   }

   /** Checks if num is an acceptable value for the given column */
   protected boolean checkCol( int col, int num )
   {
      for( int row = 0; row < 9; row++ )
         if( grid[row][col] == num )
            return false ;

      return true ;
   }

   /** Checks if num is an acceptable value for the box around row and col */
   protected boolean checkBox( int row, int col, int num )
   {
      row = (row / 3) * 3 ;
      col = (col / 3) * 3 ;

      for( int r = 0; r < 3; r++ )
         for( int c = 0; c < 3; c++ )
         if( grid[row+r][col+c] == num )
            return false ;

      return true ;
   }

   /** This method is called by the browser to start the applet */
   public void start()
   {
      // This statement will start the method 'run' to in a new thread
      (new Thread(this)).start() ;
   }

   /** The active part begins here */
   public void run()
   {
      try
      {
         // Let the observers see the initial position
         Thread.sleep( 1000 ) ;

         // Start to solve the puzzle in the left upper corner
         solve( 0, 0 ) ;
      }
      catch( Exception e )
      {
      }
   }

   /** Recursive function to find a valid number for one single cell */
   public void solve( int row, int col ) throws Exception
   {
      // Throw an exception to stop the process if the puzzle is solved
      if( row > 8 )
         throw new Exception( "Solution found" ) ;

      // If the cell is not empty, continue with the next cell
      if( grid[row][col] != 0 )
         next( row, col ) ;
      else
      {
         // Find a valid number for the empty cell
         for( int num = 1; num < 10; num++ )
         {
            if( checkRow(row,num) && checkCol(col,num) && checkBox(row,col,num) )
            {
               grid[row][col] = num ;
               updateView() ;

               // Let the observer see it
               Thread.sleep( 1000 ) ;

               // Delegate work on the next cell to a recursive call
               next( row, col ) ;
            }
         }

         // No valid number was found, clean up and return to caller
         grid[row][col] = 0 ;
         updateView() ;
      }
   }

   /** Calls solve for the next cell */
   public void next( int row, int col ) throws Exception
   {
      if( col < 8 )
         solve( row, col + 1 ) ;
      else
         solve( row + 1, 0 ) ;
   }
}
