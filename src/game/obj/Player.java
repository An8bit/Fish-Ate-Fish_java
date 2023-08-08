/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.obj;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 *
 * @author ACER
 */
public class Player {
    public static final double PLAYER_SIZE = 64; //KHAI BÁO KÍCH THƯỚC NGƯỜI CHƠI
    private double x; // vị trí x
    private  double y; // vị trí y
    //khai báo hướng mặt
    private float angle = 0f; // góc
    private Image fishleft;
    private Image fishright; 
    private boolean right; // hướng mặt phải
    private int fishWidth = 50; // rộng
    private int fishHeight = 60; // cao
    private int level = 1; // level
    private int point = 0; // điểm 
    private double coin = 0; // vàng
    private int heart = 0;
    private float speed  = 1.5f;
    private int status = 0;

    public Player() {
        // kích thước ban đầu lv1

        Image fishleft = new ImageIcon(getClass().getResource("/game/image/ca-bon-lv2.png")).getImage(); // cá trái
        Image fishright = new ImageIcon(getClass().getResource("/game/image/ca-bon-lv21.png")).getImage(); // cá phải
        
        this.fishleft = fishleft.getScaledInstance(fishWidth, fishHeight, Image.SCALE_SMOOTH);
        this.fishright = fishright.getScaledInstance(fishWidth, fishHeight, Image.SCALE_SMOOTH);
    }
      
    // M: cập nhật ảnh
    public void setImage(Image _image, String imageString) {
        Image img = new ImageIcon(getClass().getResource(imageString)).getImage();
        _image = img.getScaledInstance(fishWidth, fishHeight, Image.SCALE_SMOOTH);
    }
    
    // M: cập nhật điểm
    public void updatePoint(int point) {
        this.point += point;
    }
    
    // M: cập nhật vàng
    public void updateCoin(double coin) {
        this.coin += coin;
    }
    
    // M: cập nhật level
    public void updateLevel() {
        level += 1;
    }

    // M: cập nhật kích thước
    public void updateSize(int pointFish) {
        int nextLevel = getLevel() + 1;
        int pointNextLevel = (int)((nextLevel - 1) * ((double)nextLevel / 2) * 100);
        int pointOldLevel = (int)((getLevel() - 1) * ((double)getLevel() / 2) * 100);
        
        int sizeIncrease = 10;
        int nextSize = (int)(((double)sizeIncrease / (pointNextLevel - pointOldLevel)) * (double)pointFish);
        
        fishWidth += nextSize; 
        fishHeight += nextSize;
        
        Image fishleft = new ImageIcon(getClass().getResource("/game/image/ca-bon-lv2.png")).getImage(); // cá trái
        Image fishright = new ImageIcon(getClass().getResource("/game/image/ca-bon-lv21.png")).getImage(); // cá phải
        
        this.fishleft = fishleft.getScaledInstance(fishWidth, fishHeight, Image.SCALE_SMOOTH);
        this.fishright = fishright.getScaledInstance(fishWidth, fishHeight, Image.SCALE_SMOOTH);
    }
    
    // M: kiểm tra lên level
    public boolean checkLevelUp() {
        int nextLevel = getLevel() + 1;
        int pointNextLevel = (int)((nextLevel - 1) * ((double)nextLevel / 2) * 100);
        
        System.out.println("next level: "+ nextLevel);
        System.out.println("next point: "+ pointNextLevel);
        
        if (getPoint() >= pointNextLevel)
            return true;
        
        return false;
    }
  
    // M: thay đổi vị trí
    public void changLocation(double x ,double y){
        this.x = x;
        this.y = y;
    }
    
    // M: thay đổi góc
    public  void changAngle(float angle){
        if(angle < 0) {
            angle=359;
        } else if(angle>359)
            angle=0;
        
        this.angle=angle;
    }
    
    // M: tạo phương thức vẽ
    public void draw(Graphics2D g2){
        //tạo sao lưu với biến số cũ
        AffineTransform olTransform = g2.getTransform();
        
        //phương thức chuyển đổi ảnh tức chạy tiến lùi lên xuống
        g2.translate(x, y);
        
        //cá quay xe
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+45),PLAYER_SIZE/2,PLAYER_SIZE/2);
        g2.drawImage(right ? fishright: fishleft,0,0,null);
        
        //gọi lại biến số cũ
        g2.setTransform(olTransform);
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
    
    public void right(){
        right=true;
    }
    
    public void left(){
        right=false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int points) {
        this.point = points;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coins) {
        this.coin = coins;
    }

    public int getFishWidth() {
        return fishWidth;
    }

    public void setFishWidth(int fishWidth) {
        this.fishWidth = fishWidth;
    }

    public int getfishHeight() {
        return fishHeight;
    }

    public void setfishHeight(int fishHeight) {
        this.fishHeight = fishHeight;
    }

    public int getHeart() {
        return heart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
