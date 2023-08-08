package game.data;

import game.obj.Item;
import java.util.ArrayList;

public class ItemData {
    private static ArrayList<Item> itemMatchs = new ArrayList();
    private static ArrayList<Item> itemShop = new ArrayList();

    // thêm Item trong shop
    public static void addItemShop(String name, String describe, int price, String image) {
        Item item = new Item(name, describe, 0, image);
        itemShop.add(item);
    }

    // thêm Item trong game
    public static void addItemGame(int width, int height, String name, String describe, float speed, String image, int frequency, int ration, int gold) {
        Item item = new Item(width, height, name, describe, speed, image, frequency, ration, gold);
        itemMatchs.add(item);
    }

    // dạnh sách vật phẩm trong game
    public static ArrayList<Item> initDataForItems() {
        addItemShop("Supper Healthy", "Giúp cá tăng 2 leverl tác dụng trong 15 giây khi bắt đầu trò chơi", 1000, "/game/image/item-super-healthy.jpg");
        addItemShop("Protective Circle", "Tạo ra một vòng bảo vệ trong (10s), giúp người chơi tránh né mọi đòn tấn công từ những con cá khác, một màn chơi được sử dụng tối đa 3 lần ", 500, "/game/image/item-protect.jpg");
        return itemShop;
    }

    // danh sách vật phẩm có trong shop
    public static ArrayList<Item> initDataForMatchs() {
        addItemGame(30, 40, "Rác", "Cá người chơi ăn phải sẽ bị trúng độc và giảm tốc độ di chuyển 40%, trừ 20 điểm sức mạnh và trừ 50 vàng  ", 0.5f, "/game/image/rac.png", 15000, 7, 0);
        addItemGame(30, 30, "Tim", "Tăng thêm mạng cho người chơi (tối đa 3 mạng) ", 0.5f, "/game/image/tim.png", 60000, 5, 0);
        addItemGame(40, 40, "Ngọc Trai", "Người chơi sẽ nhận 100 đồng vàng", 0.5f, "/game/image/ngoc-trai.png", 30000, 6, 100);
        return itemMatchs;
    } 
}