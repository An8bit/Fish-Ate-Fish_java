package game.obj;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Fish {
    private int level; // level
    private String name; // tên cá
    private double price; // giá
    private double width; // chiều cao
    private double height; // chiều dài
    private String desString ; // mô tả
    private double goldReceived; // vàng nhận được
    private int fishType; // loại
    private int point; // điểm số
    private String imageStringRight; // đường dẫn hình 
    private String imageStringLeft;
    public static final double FISH_SIZE = 50; // kích thước cá
    private double x; // vị trí x
    private double y; // vị trí y
    private float speed = 0.5f; // tốc độ 
    private float angle = 0; // góc thay đổi hướng của cá
    private Image image; // hình ảnh 

    // hàm khởi tạo cá trong shop
    public Fish(String name, double price, double width, double height, String desString, int fishType, String imageStringRight, String imageStringLeft, float speed) {
        this.name = name;
        this.price = price;
        this.width = width;
        this.height = height;
        this.desString = desString;
        this.fishType = fishType;
        this.imageStringRight = imageStringRight;
        this.imageStringLeft = imageStringLeft;
        this.speed = speed;
    }
    
    // hàm khởi tạo cá trong trận
    public Fish(int level, String name, double width, double height, double goldReceived, int fishType, String imageStringRight, String imageStringLeft, float speed, int point) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.goldReceived = goldReceived;
        this.fishType = fishType;
        this.imageStringRight = imageStringRight;
        this.imageStringLeft = imageStringLeft;
        this.point = point;
        this.speed = speed;
    }
    
    // hàm khởi tạo cá có tham số là Fish
    public Fish(Fish fish, int direction) {
        this.level = fish.level;
        this.name = fish.name;
        this.price = fish.price;
        this.width = fish.width;
        this.height = fish.height;
        this.desString = fish.desString;
        this.goldReceived = fish.goldReceived;
        this.fishType = fish.fishType;
        this.point = fish.point;
        this.imageStringRight = fish.imageStringRight;
        this.imageStringLeft = fish.imageStringLeft;
        this.image = fish.image;
        if (direction == 1)
            setImage(imageStringLeft);
        else
            setImage(imageStringRight);
        
        this.speed = fish.speed;
    }
    
    public void setImage(String img) {
        Image _image = new ImageIcon(getClass().getResource(img)).getImage();
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
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public float getAngle(){
        return angle;
    }

    public float getSpeed() {
        return speed;
    }

    public int getPoint() {
        return point;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getGoldReceived() {
        return goldReceived;
    }

    public void setGoldReceived(double goldReceived) {
        this.goldReceived = goldReceived;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    
}
