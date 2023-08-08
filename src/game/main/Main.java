package game.main;

import game.component.FishEatFishGame;
import game.component.MP3Player;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main extends JFrame {
    public Main() {
        init();
    }
    
    private void init() {
        // Tiêu đề khung
        setTitle("Fish Eat Fish");
        
        // cài đặt kích thước
        setSize(800, 600);
        
        // Làm khung hiển thị ở giữa màn hình
        setLocationRelativeTo(null);
        
        // cài đặt kích thức cố định
        setResizable(false);
        
        // Thoát khỏi chương trình khi JFrame đã đóng
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Đặt khung hình J thành bố cục đường viền
        setLayout(new BorderLayout());
        
        // Tạo đối tượng từ bảng điều khiển --> thêm vào khung J
        FishEatFishGame fishEatFishGame = new FishEatFishGame();
        add(fishEatFishGame);
        MP3Player mP3Player = new MP3Player("src/game/data/map.mid");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                fishEatFishGame.start();
                mP3Player.play();
            }
        });
    }
    
    public static void main(String[] args) {
        // Hiển thị khung
        Main main = new Main();
        main.setVisible(true);
    }
}
