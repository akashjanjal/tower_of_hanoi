
import javax.swing.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class Hanoi extends JFrame implements Runnable
{   
     private Tower [] theTowers;
     private ShapePanel drawPanel;
     private JPanel bPanel;
     private JButton runDemo, playGame;
     private JButton [] towerButtons;
     private JLabel info;
     private Thread runThread;
     private int size, delay;
     private Rectangle2D currDisk;
     private boolean selected;
     private  Rectangle2D [] danda;
     private  Rectangle2D [] base;
     public Hanoi()
     {   
            super("Towers of Hanoi Exercise");
            drawPanel = new ShapePanel(0, 0);
            
             drawPanel.setBackground(Color.GREEN);
            runDemo = new JButton("Run Demo");
            playGame = new JButton("Play Game");
            
            ButtonHandler bhandler = new ButtonHandler();
            runDemo.addActionListener(bhandler);
            playGame.addActionListener(bhandler);

            TowerHandler thandler = new TowerHandler();
            bPanel = new JPanel();
            bPanel.setLayout(new GridLayout(1,3));
            bPanel.setBackground(Color.GREEN);
            towerButtons = new JButton[3];
            for (int i = 0; i < 3; i++)
            {
                 towerButtons[i] = new JButton("Tower " + (i+1));
                 towerButtons[i].setVisible(false);
                 towerButtons[i].setEnabled(false);
                 towerButtons[i].addActionListener(thandler);
                 bPanel.add(towerButtons[i]);
            }
           
            info = new JLabel("Choose to Play game OR Run the Demo", (int) CENTER_ALIGNMENT);
            info.setForeground(Color.RED);
            
            Container c = getContentPane();
            c.add(drawPanel, BorderLayout.CENTER);
            c.add(runDemo, BorderLayout.WEST);
            c.add(playGame, BorderLayout.EAST);
            c.add(bPanel, BorderLayout.SOUTH);
            c.add(info, BorderLayout.NORTH);
           
            
            theTowers = new Tower[3];

            runThread = null;
		
            setSize(1360, 720);
            show();
     }
     
     public void solveHanoi(int tsize, int start, int middle, int end)
     {
    	    
    	 if (tsize == 1)
                  theTowers[start].move(theTowers[end]);
            else
            {
                  solveHanoi(tsize-1, start, end, middle);
                  theTowers[start].move(theTowers[end]);
                  solveHanoi(tsize-1, middle, start, end);
            }
     }

     public void run()
     {
            solveHanoi(size, 0, 1, 2);
            playGame.setEnabled(true);
            info.setText("Choose to Play game OR Run the Demo");
            if(theTowers[0].isEmpty()&&theTowers[1].isEmpty())
            {
            	info.setText("*** PRESS 'ok' ***");
            }
            
     }


     public static void main(String [] args)
     {
            new Hanoi();

     }

     public class ButtonHandler implements ActionListener
     {
            public void actionPerformed(ActionEvent e) 
            {
            	
            	if (e.getSource() == runDemo)
                 {  
            		   playGame.setText("ok");
                       playGame.setEnabled(false);
                       runDemo.setEnabled(false);
                       try{
                       do
                       {
                             size = Integer.parseInt(
                                   JOptionPane.showInputDialog(Hanoi.this, 
                                                       "How many disks? (<= 10)")); 
                             if(size>10)
                          	   JOptionPane.showMessageDialog(Hanoi.this,"Enter number less than 11 only");
                       } while (size < 1 || size > 10);
                       

                       do
                       {
                             delay = Integer.parseInt(
                                  JOptionPane.showInputDialog(Hanoi.this, 
                                                              "Delay between moves? (unit of time is millisecond)")); 
                       } while (delay < 0); 
                       double n=(Math.pow(2,size)-1)*(delay/1000.0);
                       JOptionPane.showMessageDialog(Hanoi.this, 
                               " It will take "+n+"seconds to solve, please press 'ok' ");
                       danda=new Rectangle2D[3];
                       base=new Rectangle2D[3];
                       for (int i = 0; i < 3; i++)
                            theTowers[i] = new Tower(size, i);
                       theTowers[0].fill();
                     
                       for (int i = 0; i < 3; i++)
                       {
                            towerButtons[i].setVisible(true);
                       }
                  
                       info.setText("Running demonstration...please wait until it finishes");
                       if (runThread == null || (!runThread.isAlive()))
                       {
                            runThread = new Thread(Hanoi.this);
                            runThread.start();
                       }
                      
                       repaint(); } catch(Exception e1){
                    	   runDemo.setEnabled(true);
                    	   playGame.setEnabled(true);
                           playGame.setText("Play Game");
                       }
                    	  
                 } 
                 else if (e.getSource() == playGame)
                 {
                      if (playGame.getText() == "Play Game")
                       {
                             runDemo.setEnabled(false);
                             try{
                             do
                             {
                                   size = Integer.parseInt(
                                          JOptionPane.showInputDialog(Hanoi.this, "How many disks? (<= 10)")); 
                                   if(size>10)
                                  	   JOptionPane.showMessageDialog(Hanoi.this,"Enter number less than 11 only");
                             } while (size < 1 || size > 10); 
                              
                             danda=new Rectangle2D[3];
                             base=new Rectangle2D[3];
                             for (int i = 0; i < 3; i++)
                             { 
                               theTowers[i] = new Tower(size, i);
                             }
                            
                             theTowers[0].fill();
                             
                             for (int i = 0; i < 3; i++)
                             {
                                  towerButtons[i].setVisible(true);
                             }
                             towerButtons[0].setEnabled(true);
                             selected = false;
                             info.setText("Select Tower to REMOVE disk from");
                             playGame.setText("OK");  } catch(Exception e1){
                            	 runDemo.setEnabled(true);
                            	 playGame.setEnabled(true);
                             }
                       }
                      else if (playGame.getText() == "OK")
                       {
                             runDemo.setEnabled(true);
                             if(theTowers[0].isEmpty()&&theTowers[1].isEmpty())
                             {
                            	 info.setText("Choose for Run Demo OR Play Game");
                                 playGame.setText("Play Game");
                                	   JOptionPane.showMessageDialog(Hanoi.this,"CONGRATULATION!!!!");
                             }
                             else {
                            	 info.setText("Choose for Run Demo OR Play Game"
                            	 		+ "");
                                 playGame.setText("Play Game");
                                	   JOptionPane.showMessageDialog(Hanoi.this,"??SORRY...YOU CAN TRY AGAIN ??");
                             }
                             for (int i = 0; i < 3; i++)
                             {
                                  towerButtons[i].setVisible(false);
                                  towerButtons[i].setEnabled(false);
                                  while(!theTowers[i].isEmpty())
                                  theTowers[i].remove();
                             }
                       }
                      else if(playGame.getText()=="ok"){
                    	  while(!theTowers[2].isEmpty())
                    	  { try{
                    		  theTowers[2].remove();
                    	     }catch(Exception e1){
                    	    	 
                    	     }
                    	  }
                    	  runDemo.setEnabled(true);
                    	  playGame.setText("Play Game");
                    	  info.setText("Choose Run Demo OR Play Game");
                      }
                 }
            }
     } 

     private class TowerHandler implements ActionListener
     {
            public void actionPerformed(ActionEvent e)
            {
                 for (int i = 0; i < 3; i++)
                 {
                      if (e.getSource() == towerButtons[i])
                      {
                            if (!selected)
                            {
                                 selected = true;
                                 currDisk = theTowers[i].remove();
                                 int wid = (int) currDisk.getWidth();
                                 for (int j = 0; j < 3; j++)
                                 {
                                      if (theTowers[j].isEmpty() || 
                                               wid < theTowers[j].topWidth())
                                            towerButtons[j].setEnabled(true);
                                      else
                                            towerButtons[j].setEnabled(false);
                                 }
                                 info.setText("Select Tower to ADD disk to");
                            }
                            else
                            {
                                 selected = false;
                                 theTowers[i].add(currDisk);
                                 for (int j = 0; j < 3; j++)
                                 {
                                      if (!theTowers[j].isEmpty())
                                           towerButtons[j].setEnabled(true);
                                      else
                                           towerButtons[j].setEnabled(false);
                                 }
                                 info.setText("Select Tower to REMOVE disk from");
                            }
                            repaint();
                            break;
                      }
                 }
            }
     } 

     private class ShapePanel extends JPanel
     {
      
         private int prefwid, prefht;
      
         public ShapePanel (int pwid, int pht)
         {
          
             prefwid = pwid;   
             prefht = pht;     
         }

         public Dimension getPreferredSize()
         {
             return new Dimension(prefwid, prefht);
         }
            
         public void paintComponent (Graphics g)       
         {                                             
             super.paintComponent(g); 
             Graphics2D g2d = (Graphics2D) g; 
             for (int i = 0; i < 3; i++) 
                  if (theTowers[i] != null)                  
                      theTowers[i].draw(g2d);
         }
     }  

     private class Tower extends ButtonHandler
     {
         public final int MAXWIDTH = 260, HEIGHT = 40;
         private Rectangle2D [] disks;
         private int number, xoffset;
     
         public Tower(int maxsize, int id)
         {
        	 int x=226+id*360;
             disks = new Rectangle2D[maxsize];
             danda[id]=new Rectangle2D.Double(x,70,8,430);
             number = 0;
             xoffset = (id * (MAXWIDTH + 100)) + 100;
         }
         public void fill()
         {
             int width = MAXWIDTH;
             for(int i=0;i<3;i++)
             { int p=90+i*360;
            	 base[i]=new Rectangle2D.Double(p,500,280,8);   
             }
       
             for (int i = 0; i < disks.length; i++)
             {
                    int x = xoffset + (MAXWIDTH - width)/2;
                    int y = 500 - ((i+1) * HEIGHT);
                    disks[i] = new Rectangle2D.Double(x, y, width, 40);
                    width = width - 20;
             }
             number = disks.length;
         }

         public void move(Tower dest)
         {
             try
             {
                 Thread.sleep(delay);
             }
             catch(InterruptedException e) {}

             Rectangle2D currentDisc = this.remove();
             dest.add(currentDisc);
             repaint();
         }

         public Rectangle2D remove()
         {
             Rectangle2D curr = disks[number - 1];
             number--;
             return curr;
         }

         public void add(Rectangle2D curr)
         {
             int width = (int) curr.getWidth();
             int x = xoffset + (MAXWIDTH - width)/2;
             int y = 500 - ((number+1) * HEIGHT);
             curr.setFrame(x, y, width, 40);
             disks[number] = curr;
             number++;
         }

         public boolean isEmpty()
         {
             return (number == 0);
         }

         public int topWidth()
         {
             return ( (int) disks[number-1].getWidth());
         }
             
         public void draw(Graphics2D g2d)
         {  
        	 for(int i=0;i<3;i++)
           {
        	 g2d.setColor(Color.red);
        	 g2d.fill(danda[i]);
        	 g2d.setColor(Color.black);
        	 g2d.fill(base[i]);
        	 g2d.setColor(Color.red);
        	 g2d.draw(danda[i]);
           }
        	 for (int i = 0; i < number; i++)
             {
                  g2d.setColor(Color.RED);
                  g2d.fill(disks[i]);
                  g2d.setColor(Color.black);
                  g2d.draw(disks[i]);
             }
         }

    }
                              
}