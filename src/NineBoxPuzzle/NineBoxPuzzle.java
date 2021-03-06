package NineBoxPuzzle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class NineBoxPuzzle extends JPanel { 
  
  private NineBoxPuzzle Panel;
  private JFrame frame;
  
  private int size;
  
  private int box;
  
  private int dimension;
  
  private static final Color FOREGROUND_COLOR = new Color(100, 200, 80); // we use arbitrary color
  
  private static final Random RANDOM = new Random();
  
  private int[] box_grid;
  
  private int box_size;
  
  private int blankPos;
  
  private JLabel CurrentScore;
  private int margin;
  
  private int gridSize;
  private boolean gameOver; 
  private int score=0;
  public NineBoxPuzzle(int size, int dim, int mar,JFrame frame) {
	  this.frame = frame;
	  Panel = this;
    this.size = size;
    dimension = dim;
    margin = mar;
    
    box = size * size - 1; 
    box_grid = new int[size * size];
    
    
    gridSize = (dim - 2 * margin);
    box_size = gridSize / size;
    
    setPreferredSize(new Dimension(dimension, dimension + margin));
    setBackground(Color.WHITE);
    setForeground(FOREGROUND_COLOR);
    setFont(new Font("SansSerif", Font.BOLD, 60));
    
    gameOver = true;
    
    addMouseListener (new MouseAdapter() {
  

	

	@Override
      public void mousePressed(MouseEvent e) {
        
        if (gameOver) {
          newGame();
        } else {
          
          int ex = e.getX() - margin;
          int ey = e.getY() - margin;
          
          
          if (ex < 0 || ex > gridSize  || ey < 0  || ey > gridSize)
            return;
          
          
          int c1 = ex / box_size;
          int r1 = ey / box_size;
          
          int c2 = blankPos % size;
          int r2 = blankPos / size;
          
          int clickPos = r1 * size + c1;
          
          int dir = 0;
          
          if (c1 == c2  &&  Math.abs(r1 - r2) > 0)
            dir = (r1 - r2) > 0 ? size : -size;
          else if (r1 == r2 && Math.abs(c1 - c2) > 0)
            dir = (c1 - c2) > 0 ? 1 : -1;
            

            
            
          if (dir != 0) {
            
            do {
              int newBlankPos = blankPos + dir;
              box_grid[blankPos] = box_grid[newBlankPos];
              blankPos = newBlankPos;
              score+=1;
            } while(blankPos != clickPos);
            
            box_grid[blankPos] = 0;
          }
          
          
          gameOver = isSolved();
        }
        
        
        repaint();
      }
    });
    
    
  }
  

  
  private void newGame() {
    do {
      reset(); 
      shuffle(); 
    } while(!isSolvable()); // make it until grid be solvable
    
    gameOver = false;
  }
  
  private void reset() {
    for (int i = 0; i < box_grid.length; i++) {
      box_grid[i] = (i + 1) % box_grid.length;
    }
    
    
    blankPos = box_grid.length - 1;
  }
  
  private void shuffle() {
    
    int n = box;
    
    while (n > 1) {
      int r = RANDOM.nextInt(n--);
      int tmp = box_grid[r];
      box_grid[r] = box_grid[n];
      box_grid[n] = tmp;
    }
  }
  
  
  private boolean isSolvable() {
    int countInversions = 0;
    
    for (int i = 0; i < box; i++) {
      for (int j = 0; j < i; j++) {
        if (box_grid[j] > box_grid[i])
          countInversions++;
      }
    }
    
    return countInversions % 2 == 0;
  }
  
  private boolean isSolved() {
    if (box_grid[box_grid.length - 1] != 0) 
      return false;
    
    for (int i = box - 1; i >= 0; i--) {
      if (box_grid[i] != i + 1)
        return false;
    }
    
    return true;
  }
  
  private  void drawGrid (Graphics2D g) {
    for (int i = 0; i < box_grid.length; i++) {
      
      int r = i / size;
      int c = i % size;
      
      int x = margin + c * box_size;
      int y = margin + r * box_size;
      
      
      if(box_grid[i] == 0) {
        if (gameOver) {
          g.setColor(FOREGROUND_COLOR);

        }
        
        continue;
      }
      
      // for other box_grid
      g.setColor(getForeground());
      g.fillRoundRect(x, y, box_size, box_size, 25, 25);
      g.setColor(Color.BLACK);
      g.drawRoundRect(x, y, box_size, box_size, 25, 25);
      g.setColor(Color.WHITE);
      g.setColor(Color.BLACK);
      g.setFont(new Font("serif",Font.BOLD,25));
      if( ! isSolved()) {
    	  //DataBase d = new DataBase("jitu",30);
    	  g.drawString(" your current score = "+score,150,30); 
      }
      else {
    	  g.drawString("game over and your final score is "+score,150,30);
    	  Panel.setVisible(false);
    	  frame.setContentPane(new PlayerData(score,frame));
    	  score = 0;
    	  
    	  //PlayerData Player = new PlayerData(score);
      }
      drawCenteredString(g, String.valueOf(box_grid[i]), x , y);
    }
  }
  
  private void drawStartMessage(Graphics2D g) {
    if (gameOver) {
      g.setFont(getFont().deriveFont(Font.BOLD, 18));
      g.setColor(FOREGROUND_COLOR);
      String s = "Click to start new game";
      g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2,
          getHeight() - margin);
     
    }
  }
  
  
  
  private void drawCenteredString(Graphics2D g, String s, int x, int y) {
    // center string s for the given tile (x,y)
    FontMetrics fm = g.getFontMetrics();
    int asc = fm.getAscent();
    int desc = fm.getDescent();
    g.drawString(s,  x + (box_size - fm.stringWidth(s)) / 2,
        y + (asc + (box_size - (asc + desc)) / 2));
  }
  
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    //g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    drawGrid(g2D);
    drawStartMessage(g2D);
  }
  
  public static void main(String[] args) {
    
      JFrame frame = new JFrame();
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Nine Box Puzzle");
      frame.setResizable(false);
      frame.add(new NineBoxPuzzle(2, 550, 100,frame), BorderLayout.CENTER);
      frame.pack();
      
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    
  }

  
}

