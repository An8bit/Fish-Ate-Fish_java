package game.component;

import game.data.FishData;
import game.data.ItemData;
import game.obj.Fish;
import game.obj.Item;
import game.obj.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class FishEatFishGame extends JComponent {
    // Đồ họa đối tượng 2D và hình ảnh đệm
    private Graphics2D g2;
    private BufferedImage image;
    
    // Lưu trữ kích thước
    private int width;
    private int height;
    
    // Biến thread phân tách quy trình vòng lặp trò chơi để hiển thị đồ họa
    private Thread thread;
    
    // Biến bắt đầu
    private boolean start = true;
    
    // xậy dựng FPS trò chơi để hiện thị
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    
    // game obj
    private Player player = new Player();
    private ArrayList<Fish> fishs;
    private ArrayList<Item> items;
    
    // phím
    private Key key = new Key();
    
    // danh sách cá trong trận
    private ArrayList<Fish> fishMatchs = FishData.initDataForFishMatchs();
    private ArrayList<Item> dropItems = ItemData.initDataForMatchs();
    
    //sound
    MP3Player mP3Player = new MP3Player("src/game/data/eating.wav");
    
    // random
    Random random;
    
    public FishEatFishGame() {
        
    }
    
    // thời gian hiệu ứng
    private long timeEffect = 0;
    
    // M: bắt đầu trò chơi
    public void start() {
        width = getWidth();
        height = getHeight();
        
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // khởi tạo đồ họa bằng ảnh đệm
        g2 = image.createGraphics();
        
        //khởi tạo một đối tượng BufferedImage và một đối tượng Graphics2D từ đối tượng ảnh đệm đó, 
        //sau đó bật tính năng anti-aliasing để làm mượt các đường vẽ và đối tượng đồ họa.
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
                    }
                }
            }
        });
        
        inintObjectGame();
        intitKeyboard();
        thread.start();
    }   

    public void printInfo() {
        System.out.println("Level: " + player.getLevel());
        System.out.println("Point: " + player.getPoint());
        System.out.println("Coint: " + player.getCoin());
        System.out.println("Heart: " + player.getHeart());
        System.out.println("Width: " + player.getFishWidth());
        System.out.println("Height: " + player.getfishHeight());
        System.out.println("--------------------------");
    }
    
    // thiết lập game ban đầu 
    private void inintObjectGame(){
        player = new Player();
        random = new Random();
        
        // vị trí cá
        player.changLocation(width / 2, height / 2); 
        
        fishs = new ArrayList<>();
        items = new ArrayList<>();
        
        // Lưuồng tạo cá
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addFish();
                    sleep(3000);
                }
            }
        }).start();
        
        // Vật phẩm
        for (int i = 0; i < dropItems.size(); i++) {
            Item item = dropItems.get(i);
            dropItem(item, item.getDelayTime(), item.getRation());
        }
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
                if(e.getKeyCode() == KeyEvent.VK_A)
                    key.setKey_left(true);
                else if(e.getKeyCode()==KeyEvent.VK_D)
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
                if(e.getKeyCode() == KeyEvent.VK_A){
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
                    float speedPlayer = player.getSpeed();
                    double x = player.getX();
                    double y = player.getY();
                    if(key.isKey_left()) {
                       player.left();
                       x -= speedPlayer;                      
                    }
                    if(key.isKey_right()){
                       player.right();
                       x += speedPlayer;
                    }
                    if(key.isKey_up())
                       y -= speedPlayer;
                    if(key.isKey_down())
                       y += speedPlayer;

                    // Kiểm tra và điều chỉnh giá trị x và y nếu vượt quá giới hạn
                    if (x < 0)
                        x = 0;
                    else if (x > width - 40)
                        x = width - 40;          
                    
                    if (y < 0)
                        y = 0;
                    else if (y > height - 50)
                        y = height - 50;
                    player.changLocation(x, y);             
                    
                    // cập nhật vị trí tương đối của cá trong danh sách
                    for (int i = 0; i < fishs.size(); i++) {
                        Fish fish  = fishs.get(i);
                        if (fish != null) {
                            fish.update();
                        }
                    }
                    
                    // cập nhật vị trí tương đối của vật phẩm trong danh sách
                    for (int i = 0; i < items.size(); i++) {
                        Item item  = items.get(i);
                        if (item != null) {
                            item.update();
                        }
                    }
                    
                    // kiểm tra người chơi dính hiệu ứng
                    if (player.getStatus() == 1) {
                        deleteEffectGarbage();
                    }
                    
                    sleep(5);             
                }
            }
        }).start();
    }
    
    // M: vẽ trò chơi
    private void drawGame() {  
        player.draw(g2);
        // render cá lên màn hình
        for (int i = 0; i < fishs.size(); i++) {
            Fish fish  = fishs.get(i);
            if (fish != null) {
                fish.draw(g2);
                if (eatFish(fish)) {
                    fishs.remove(i);
                } 
            }
        }
        
        
        // render vật phẩm lên màn hình
        for (int i = 0; i < items.size(); i++) {
            Item item  = items.get(i);
            if (item != null) {
                item.draw(g2);
                if (eatItem(item)) {
                    itemEffect(item);
                    items.remove(i);
                }
            }
        }
        
        // vẽ điểm
        showPoint();
        
        // vẽ vàng
        showCoin();
    }
    
    // M: render
    private void render() {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null); // vẽ hình được lưu trữ trong biến lên màn hình
        g.dispose(); // giải phóng bộ nhớ đệm vài tài nguyên
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
    
    // M: hiển thị điểm
    public void showCoin() {
        int rectWidth = 150;
        int rectHeight = 40;
        int cornerRadius = 30; // Bán kính bo góc
        int locationX = 10;
        int locationY = 10;

        // Đường viền
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        RoundRectangle2D borderRect = new RoundRectangle2D.Double(locationX, locationY, rectWidth, rectHeight, cornerRadius, cornerRadius);
        g2.draw(borderRect);

        // Ô điểm
        g2.setColor(Color.WHITE);
        g2.fill(borderRect);

        // Chữ
        String text = "Vàng: " + player.getCoin();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Roboto", Font.ROMAN_BASELINE, 16));

        // Tính kích thước của chữ
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        // Tính vị trí để vẽ chữ nằm giữa ô
        int x = (rectWidth - textWidth) / 2 + locationX;
        int y = (rectHeight - textHeight) / 2 + fontMetrics.getAscent() + locationY;

        g2.drawString(text, x, y);
    }
    
    // M: hiển thị điểm
    public void showPoint() {
        int rectWidth = 150;
        int rectHeight = 40;
        int cornerRadius = 30; // Bán kính bo góc
        int locationX = 10 + 150 + 10;
        int locationY = 10;

        // Đường viền
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(2));
        RoundRectangle2D borderRect = new RoundRectangle2D.Double(locationX, locationY, rectWidth, rectHeight, cornerRadius, cornerRadius);
        g2.draw(borderRect);

        // Ô điểm
        g2.setColor(Color.WHITE);
        g2.fill(borderRect);

        // Chữ
        String text = "Điểm: " + player.getPoint();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Roboto", Font.ROMAN_BASELINE, 16));

        // Tính kích thước của chữ
        FontMetrics fontMetrics = g2.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        // Tính vị trí để vẽ chữ nằm giữa ô
        int x = (rectWidth - textWidth) / 2 + locationX;
        int y = (rectHeight - textHeight) / 2 + fontMetrics.getAscent() + locationY;

        g2.drawString(text, x, y);
    }
    
    /* -------------------------------------------- PLAYER -------------------------------------------- */
    // M: Kiểm tra người chơi còn mạng
    public boolean checkStillHeart() {
        if (player.getHeart() > 0) // còn mạng
            return true;
        return false; // không còn mạng
    }
    
    // M: cập nhật cá người chơi
    public void updatePlayer(Fish fish) {
        printInfo();

        player.updatePoint(fish.getPoint());
        
        player.updateCoin(fish.getGoldReceived());
        
        player.updateSize(fish.getPoint());
        
        if (player.checkLevelUp()) { 
            player.updateLevel();
        }
    }
    
    /* -------------------------------------------- FISH -------------------------------------------- */
    // M: tạo cá ngẫu nhiên
    public void addFish() {
        Random ran = new Random();
        
        int locationY = ran.nextInt(height - 100) + 25;
        Fish fish = new Fish(fishMatchs.get(ran.nextInt(fishMatchs.size())), 1);
        fish.changLocation(0, locationY);
        fish.changAngle(0);
        fishs.add(fish);
        
        int locationY2 = locationY + 100;
        Fish fish2 = new Fish(fishMatchs.get(ran.nextInt(fishMatchs.size())), 2);
        fish2.changLocation(width, locationY2);
        fish2.changAngle(180);
        fishs.add(fish2);
    }
    
    // M: hàm ăn cá
    public boolean eatFish(Fish fish) {
        // cá người chơi
        double leftPlayer = player.getX();
        double rightPlayer = player.getX() + 40;
        double topPlayer = player.getY();
        double bottomPlayer = player.getY() + 50;

        // cá random
        double leftFish = fish.getX();
        double rightFish = fish.getX() + 40;
        double topFish = fish.getY();
        double bottomFish = fish.getY() + 50;


        // Kiểm tra xem có xảy ra va chạm hay không
        if (rightPlayer >= leftFish && leftPlayer <= rightFish && bottomPlayer >= topFish && topPlayer <= bottomFish) {
            // Xảy ra va chạm
            if (checkFishEatingLevel(fish) == true) {
                updatePlayer(fish);
                mP3Player.play();
                return true;
            } else {
                // kiểm tra cá người chơi còn mạng
                if (checkStillHeart()) {
                    
                    player.setHeart(player.getHeart() - 1);
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over");
                    System.exit(0); // Đóng cửa sổ và kết thúc ứng dụng
                }
            }
        }

        // Không có va chạm
        return false;
    }
    
    // M: kiểm tra cá đủ cấp để ăn cá khác
    public boolean checkFishEatingLevel(Fish fish) {
        if (player.getLevel() >= fish.getLevel())
            return true;
        return false;
    }
    
    
    
    /* -------------------------------------------- ITEM -------------------------------------------- */
    // M: rơi vật phẩm
    public void dropItem(Item item, double delayTime, double dropRate) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    double randoNumber = random.nextDouble(10) + 1;
                    System.out.println(randoNumber);
                    if (randoNumber <= dropRate) {
                        addItem(item);
                        System.out.println("True");
                    }
                    sleep((int)delayTime);
                }
            }
        }).start();
    }
    
    // M: Thêm vật phẩm
    public void addItem(Item item) {
        int locationY = random.nextInt(width - 100) + 25;
        Item newItem = new Item(item);
        newItem.changLocation(locationY, 0);
        newItem.changAngle(90);
        items.add(newItem);
    }
    
    // M: Ăn vật phẩm
    public boolean eatItem(Item item) {
        // cá người chơi
        double leftPlayer = player.getX();
        double rightPlayer = player.getX() + 40;
        double topPlayer = player.getY();
        double bottomPlayer = player.getY() + 50;

        // cá random
        double leftItem = item.getX();
        double rightItem = item.getX() + 40;
        double topItem = item.getY();
        double bottomItem = item.getY() + 50;


        // Kiểm tra xem có xảy ra va chạm hay không
        if (rightPlayer >= leftItem && leftPlayer <= rightItem && bottomPlayer >= topItem && topPlayer <= bottomItem) { // Xảy ra va chạm
            mP3Player.play();
            return true;
        }

        // Không có va chạm
        return false;
    }
    
    // M: Tác dụng của vật phẩm
    public void itemEffect(Item item) {
        if (item.getName().equals("Rác")) {
            garbageItemEffect();
        } else if (item.getName().equals("Tim")) {
            heartItemEffect();
        } else if (item.getName().equals("Ngọc Trai")) {
            pearlItemEffect();
        }
    }
    
    // M: xóa hiệu ứng người chơi
    public void deleteEffectGarbage() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - timeEffect >= 5000) {
            player.setSpeed(1.5f);
            player.setStatus(0);
        }
    }
    
    // M: Tác dụng vật phẩm rác
    public void garbageItemEffect() {
        long currentTime = System.currentTimeMillis();
        timeEffect = currentTime;
        
        player.setSpeed(player.getSpeed() * 0.4f);
        player.setStatus(1);
        
        if (player.getPoint() - 20 < 0)
            player.setPoint(0);
        else
            player.setPoint(player.getPoint() - 20);
        
        if (player.getCoin() - 50 < 0)
            player.setCoin(0);
        else
            player.setCoin(player.getCoin() - 50);
    }
    
    // M: Tác dụng vật phẩm trái tim
    public void heartItemEffect() {
        int quantityHeart = player.getHeart();
        if (quantityHeart < 3)
            player.setHeart(quantityHeart + 1);
    }
    
    // M: Tác dụng vật phẩm ngọc trai
    public void pearlItemEffect() {
        player.updateCoin(100);
    }
}
