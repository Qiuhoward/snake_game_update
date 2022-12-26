package org.example.bean;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameFrame extends JFrame
{
    /**
     * 建構一波
     */
    public GameFrame() {
        this.add(new GamePannel());//把面板加進來框架
        this.setTitle("snake game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack(); // 將其尺寸設定為可以將frame所有元件包起來的大小
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);//自動置中
        this.addKeyListener(new keyAdapter());
    }

    /**
     *
     * u:上 d:下 l:左 r:右
     */

        public static class  keyAdapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent event)
            {
                int key=event.getKeyCode();
                switch (key) {

                    case KeyEvent.VK_LEFT:
                        if(GamePannel.direction !='l'){
                            GamePannel.direction = 'l';
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if(GamePannel.direction !='r'){
                            GamePannel.direction = 'r';
                        }
                        break;

                    case KeyEvent.VK_UP:
                        if(GamePannel.direction !='u'){
                            GamePannel.direction = 'u';
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if(GamePannel.direction !='d'){
                            GamePannel.direction = 'd';
                        }
                        break;
                }

            }
        }


    }


