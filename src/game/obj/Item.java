package game.obj;
import static game.obj.Fish.FISH_SIZE;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Random;
import javax.swing.ImageIcon;


public class Item {
    private int width;
    private int height;
    private String name;
    private String describe;
    private double price;
    private float speed;
    private Image image;
    private double delayTime;// tâng số
    private double ration;// tỉ lệ
    private int goldReceived;// vàng nhận
    private double x;
    private double y;
    private float angle;
    private String imageString;

    // cá trong trận
    public Item(int width, int height, String name, String describe, float speed, String imageString, double delayTime, double ration, int goldReceived) {
        this.width = width;
        this.height = height;
        this.name = name;
        this.describe = describe;
        this.speed = speed;
        this.imageString = imageString;
        this.delayTime = delayTime;
        this.ration = ration;
        this.goldReceived = goldReceived;
        setImage();
    }

    // cá trong cửa hàng
    public Item(String name, String describe, double price, String imageString) {
        this.name = name;
        this.describe = describe;
        this.price = price;
        this.imageString = imageString;
        setImage();
    }
    
    public Item(Item item) {
        this.width = item.width;
        this.height = item.height;
        this.name = item.name;
        this.describe = item.describe;
        this.speed = item.speed;
        this.imageString = item.imageString;
        this.delayTime = item.delayTime;
        this.ration = item.ration;
        this.goldReceived = item.goldReceived;
        setImage();
    }
    
    public void setImage() {
        Image _image = new ImageIcon(getClass().getResource(imageString)).getImage();
        this.image = _image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
    }

    //thay đổi vị trí
    public void changLocation(double x ,double y){
        this.x = x;
        this.y = y;
    }
    
    //phương thức thay đổi góc
    public  void changAngle(float angle){
        if (angle < 0)
            angle = 359;
        else if (angle > 359)
            angle = 0;
        
        this.angle = angle;
    }
    
    // cập nhật và thay đổi biến x và y = thuật toán Sin, Cos
    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }
    
    // M: hiển thị đồ họa
    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        
        // dịch cá sang vị trí x và y 
        g2.translate(x, y);
        
        // xoay hình ảnh
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), FISH_SIZE / 2, FISH_SIZE / 2);
        
        // vẽ hình 
        g2.drawImage(image, 0, 0, null);
        
        // đặt oldTransform thành đối tượng 2d để khôi phục về mặc định
        g2.setTransform(oldTransform);
    }
    
    public static boolean randomWithProbability(int probability, int times) {
        long endTime = System.currentTimeMillis() + times;
        Random random = new Random();

        while (System.currentTimeMillis() < endTime) {
            int randomNumber = random.nextInt(100) + 1;

            if (randomNumber <= probability) {
                return true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(double delayTime) {
        this.delayTime = delayTime;
    }

    public double getRation() {
        return ration;
    }

    public void setRation(double ration) {
        this.ration = ration;
    }

    public int getGoldReceived() {
        return goldReceived;
    }

    public void setGoldReceived(int goldReceived) {
        this.goldReceived = goldReceived;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
    
    
}
