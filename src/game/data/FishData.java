package game.data;
import game.obj.Fish;
import java.util.ArrayList;

public class FishData { 
    public static ArrayList<Fish> fishMatchs = new ArrayList<>();
    public static ArrayList<Fish> fishShops = new ArrayList<>();
    
    // thêm cá trong shop
    public static void addFishShop(String name, double price, double width, double height, String desString, int fishType, String imageStringRight, String imageStringLeft, float speed) {
        Fish fish = new Fish(name, price, width, height, desString, fishType, imageStringRight, imageStringLeft, speed);
        fishShops.add(fish);
    }
    
    // thêm cá trong trận
    public static void addFishMatch(int level, String name, double width, double height, double goldReceived, int fishType, String imageStringRight, String imageStringLeft, float speed, int point) {
        Fish fish = new Fish(level, name, width, height, goldReceived, fishType, imageStringRight, imageStringLeft, speed, point);
        fishMatchs.add(fish);
    }
    
    // danh sách cá trong shop
    public static ArrayList<Fish> initDataForFishShops() {
        
        return fishShops;
    }
    
    // danh sách cá trong trận
    public static ArrayList<Fish> initDataForFishMatchs() {
        addFishMatch(1, "Cá xanh",       40, 30, 10, 1, "/game/image/ca-xanh-lv1.png", "/game/image/ca-xanh-lv1-trai.png", 0.5f, 10);
        addFishMatch(2, "Cá bơn",       60, 70, 10, 1, "/game/image/ca-bon-lv2.png", "/game/image/ca-bon-lv2-trai.png", 0.5f, 15);
        addFishMatch(3, "Cá ngừ",       70, 80, 20, 1, "/game/image/ca-ngu-lv3.png", "/game/image/ca-ngu-lv3-trai.png", 0.5f, 25);
//        addFishMatch(4, "Cá lồng đèn",  80, 90, 40, 1, "/game/image/ca-long-den-lv4.png", , 0.5f, 30);
//        addFishMatch(5, "Cá mập",       63, 49, 80, 1, "/game/image/ca-map-lv5.png", , 0.5f, 35);
//        addFishMatch(6, "Cá voi",     100, 110, 160, 1, "/game/image/ca-voi-lv6.png", , 0.5f, 40);        
//        addFishMatch(7, "Thủy quái",     79, 36, 160, 1, "/game/image/thuy-quai-lv7.png", , 0.5f, 40);

        return fishMatchs;
    }
}