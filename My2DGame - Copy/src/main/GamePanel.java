package main;

import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable{
    
    
	//ukuran jendela
     int ukuranAsliJendela = 16;
     int skala = 3;
    
    
    public final int ukuranJendela = ukuranAsliJendela*skala;
    //ukuran jendela manual
      public final int lebarJendela=16;
      public final int tinggiJendela=12;
       public final int screenWidth=ukuranJendela*lebarJendela;
       public final int screenHeight=ukuranJendela*tinggiJendela;
       
       //kecepatan objek
       int FPS=60;
       
       TileManager tileM= new TileManager(this);
       KeyHandler keyH= new KeyHandler();
       Thread gameThread;
       Player player = new Player(this,keyH);
       
       
       public GamePanel() {
           this.setPreferredSize(new Dimension(screenWidth,screenHeight));
           this.setBackground(Color.black);
           this.setDoubleBuffered(true);
           this.addKeyListener(keyH);
           this.setFocusable(true);
           
       }
       
       public void startGameThread(){
           
          gameThread=new Thread(this);
           gameThread.start();
       }

   
         @Override
       public void run(){
            double drawInterval=1000000000/FPS;
            double delta=0;
            long lastTime=System.nanoTime();
            long currentTime;
            long timer=9;
            int drawCount=0;
            
            while(gameThread !=null){
                currentTime=System.nanoTime();
                delta +=(currentTime - lastTime)/ drawInterval;
                timer +=(currentTime - lastTime);
                
                lastTime=currentTime;
                
                if(delta>=1){
                    update();
                    repaint();
                    delta--;
                    drawCount++;
                }
                if(timer>= 1000000000){
                    System.out.println("FPS"+ drawCount);
                    drawCount=0;
                    timer=0;
                }
            }
       }
        public void update(){
            player.update();
            
        }
        @Override
        public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
        }
}
