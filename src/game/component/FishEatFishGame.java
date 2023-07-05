package game.component;

import game.obj.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class FishEatFishGame extends JComponent {
    // Đồ họa đối tượng 2D và hình ảnh đệm
    private Graphics2D g2;
    private BufferedImage image;
    
    // Lưu trữ kích thước
    private int width;
    private int height;
    
    // Biến thread phân tách quy trình vòng lặp trò chơi đẻ hiển thị đồ họa
    private Thread thread;
    
    // Biến bắt đầu
    private boolean start = true;
    
    // xậy dựng FPS trò chơi để hiện thị
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    //game obj
    private Player player = new Player();
    //phím
    private Key key = new Key();
  
   
    // M: bắt đầu trò chơi
    public void start() {
        width = getWidth();
        height = getHeight();
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // khởi tạo đồ họa bằng ảnh đệm
        g2 = image.createGraphics();
        //khởi tạo một đối tượng BufferedImage và một đối tượng Graphics2D từ đối tượng ảnh đệm đó, 
        //sau đó bật tính năng anti-aliasing để làm mượt các đường vẽ và đối tượng đồ họa.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Vòng lặp while hiển thị đồ họa của trò chơi
                while (start) {
                    // Lấy thời gian hệ thống hiện tại dưới dạng nano giây
                    long startTime = System.nanoTime();
                    
                    drawBackgroundImage();
                    drawGame();
                    render();
                    
                    long time = System.nanoTime() - startTime;
          
                    // kiểm tra nếu thời gian nhỏ hơn thời gian mục tiêu --> trì hoãn vòng lặp
                    if (time < TARGET_TIME) {
                        // Luư trữ thời gian trì hoãn vòng lặp
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                      //  System.out.println(sleep);
                    }
                }
            }
        });
        inintObjectGame();
        intitKeyboard();
        thread.start();
    }
    //thiết lập game ban đàu+vị trí cá
    private void inintObjectGame(){
        player = new Player();
        player.changLocation(300,200);
    }
    
    // M: trì hoãn vòng lặp
    public void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    //phím sự kiện kích hoạt nút và hành động
    private void intitKeyboard(){
        key=new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_A){
                    key.setKey_left(true);
                }else if(e.getKeyCode()==KeyEvent.VK_D)
                    key.setKey_right(true);
                else if(e.getKeyCode()==KeyEvent.VK_W)
                    key.setKey_up(true);
                else if(e.getKeyCode()==KeyEvent.VK_S)
                    key.setKey_down(true);
                else if(e.getKeyCode()==KeyEvent.VK_SPACE)
                    key.setKey_space(true);
                
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_A){
                    key.setKey_left(false);
                }else if(e.getKeyCode()==KeyEvent.VK_D)
                    key.setKey_right(false);
                else if(e.getKeyCode()==KeyEvent.VK_W)
                    key.setKey_up(false);
                else if(e.getKeyCode()==KeyEvent.VK_S)
                    key.setKey_down(false);
                else if(e.getKeyCode()==KeyEvent.VK_SPACE)
                    key.setKey_space(false);
            }
            
            
        });
        //tạo luồng mới lấy dữ liệu khi ấn nút cái này làm lại sao nha mình chỉ cần quay đầu là được
        new Thread(new Runnable() {
            @Override
            public void run() {
                           
                while(start){
                    float angle=player.getAngle();
                    double x = player.getX();
                    double y = player.getY();
                   if(key.isKey_left())
                   {
                       player.left();
                       x--;                      
                   }
                   if(key.isKey_right()){
                    
                       player.right();
                       x++;
                   }
                   if(key.isKey_up())
                       y--;
                   if(key.isKey_down())
                       y++;
                   
                    // Kiểm tra và điều chỉnh giá trị x và y nếu vượt quá giới hạn
                    if (x < 0)
                        x = 0;
                    else if (x > 650)
                        x = 650;                   
                    if (y < 0)
                        y = 0;
                    else if (y > 470)
                        y = 470;
                    System.out.println(".run()"+x+" "+y);
                   player.changLocation(x, y);               
                    sleep(5);             
                }
                
            }
        }).start();
    }
    // M: vẽ nền
    private void drawBackground() {
        g2.setColor(Color.BLUE);
        g2.fillRect(0, 0, width, height);
    }
    
    // M: vẽ trò chơi
    private void drawGame() {
        player.draw(g2);
    }
    
    // M: render
    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }
    //hàm này lấy ảnh làm backgruop nhaaa
    private void drawBackgroundImage() {
    // Load hình ảnh từ tệp (file) hoặc URL
    BufferedImage backgroundImage = null;
    try {
        backgroundImage = ImageIO.read(getClass().getResource("/game/image/back.jpg"));
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Kiểm tra nếu hình ảnh đã được tải thành công
    if (backgroundImage != null) {
        // Vẽ hình ảnh lên đối tượng đồ họa g2
        g2.drawImage(backgroundImage, 0, 0, width, height, null);
    } else {
        // Nếu không thể tải hình ảnh, tô màu nền mặc định
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }
}
}
