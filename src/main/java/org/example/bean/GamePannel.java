package org.example.bean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePannel extends JPanel implements ActionListener {
    static final int width=800;
    static final int height=800;
    static final int grid=50;//格子長跟寬的大小
    static final int delay=100;

    final int[] snakeX =new int[256];
    final int[] snakeY =new int[256];
    int snakeBody=6;
    int appleEaten;
    int appleX;
    int appleY;

   static char direction='r';
    boolean running=false;
    Timer timer;
    Random random;
    public GamePannel(){
        this.random=new Random();
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setFocusable(false);
        start();
    }


    /**
     * 開始遊戲
     * 1.創造apple
     * 2.跑起來
     * 3.建構timer
     * 4.Time start會啟用內部thread多執行序
     */
    public  void start(){
        newApple();
        running=true;
        timer = new Timer(delay,this);
        timer.start();
    }

    /**
     * 繼承父類 並進行覆寫
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g){
//        super.paintComponent(g);
        draw(g); //複寫
    }

    /**
     * 設計apple隨機位置 (因為有格子需考慮座標以及長寬大小)
     *
     */
    public void newApple() {
        appleX = random.nextInt(width / grid) * grid;
        appleY = random.nextInt(height / grid) * grid;
    }


    /**
     * 覆寫:把畫面畫上去 1.畫線 2.蘋果(oval畫出圓形) 3.蛇頭蛇身用不同顏色畫(rect畫出正方形)
     */
    public void draw(Graphics g){

        if (running) {
                //畫線測量大小
           for (int i = 0; i < height / grid; i++) {
                g.drawLine(i * grid, 0, i * grid, height);
                g.drawLine(0, i * grid, width, i * grid);
                g.setColor(Color.cyan);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, grid, grid);


            for (int i = 0; i < snakeBody; i++) {
                if (i == 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(snakeX[i], snakeY[i], grid, grid);
                } else {
                    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255)));
                    g.fillRect(snakeX[i], snakeY[i], grid, grid);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("PLAYER",Font.BOLD,40));
            //捨置完font必需丟到fontmentric才能顯示
            FontMetrics fontMetrics=getFontMetrics(g.getFont());
            //game over要調整x軸
            g.drawString("SCORE:"+appleEaten,(width-fontMetrics.stringWidth("SCORE"+appleEaten))/2,height/16);

        }
        else
        {
            gameOver(g);
        }
    }

    /**
     * 移動上下左右出問題
     */
    public void move(){
        for(int i=snakeBody;i>0;i--)
        {
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }
        System.out.println(direction);
        // Y軸向上為+
        switch (direction) {
            case 'u'-> snakeY[0] = snakeY[0] - grid;
            case 'd'-> snakeY[0] = snakeY[0] + grid;
            case 'l' -> snakeX[0] = snakeX[0] - grid;
            case 'r' -> snakeX[0] = snakeX[0] + grid;
        }
        System.out.println("x="+snakeX[0]+",y="+snakeY[0]);

    }


    /**
     *  吃到apple觸發 1:增加數量 2.蛇身增加 3.重置蘋果位置
     */
    public void checkApple(){
        if(snakeX[0]==appleX&&snakeY[0]==appleY)
        {
            appleEaten++;
            snakeBody++;
            newApple();
        }
    }

    /**
     * 檢查蛇的身體有沒有碰到牆壁四周以及自己身體
     */
    public void checkCollision(){

        for(int i=snakeBody;i>0;i--)
        {
            //蛇撞到牆壁
            if (snakeX[i] < 0 || snakeX[i] > width || snakeY[i] < 0 || snakeY[i] > height) {
                running = false;
            }
            if(snakeX[i]==snakeX[0]&&snakeY[i]==snakeY[0]){
                running = false;
            }
            if(!running)
            {
                timer.stop();
            }
        }

    }

    /**
     * 畫出結束畫面
     */
    public void gameOver(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("PLAYER",Font.BOLD,40));
        //捨置完font必需丟到fontmentric才能顯示
        FontMetrics fontMetrics1=getFontMetrics(g.getFont());
        //game over要調整x軸
        g.drawString("SCORE:"+appleEaten,(width-fontMetrics1.stringWidth("SCORE"+appleEaten))/2,height/16);
        g.setColor(Color.RED);
        g.setFont(new Font("GAME",Font.BOLD,75));
        //捨置完font必需丟到fontmentric才能顯示
        FontMetrics fontMetrics2=getFontMetrics(g.getFont());
        //game over要調整x軸
        g.drawString("Game Over!!!",(width-fontMetrics2.stringWidth("Game Over!!!"))/2,height/2);

    }


    /**
     * actionListener實作類
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollision();
        }
         repaint();
    }

   //keyAdapter為接收鍵盤事件的抽象適配器類別。此類別中的方法為空。此類別存在的目的是方便創建偵聽器物件
   //擴展此類別即可創建 KeyEvent 偵聽器並覆寫所需事件的方法。


}
