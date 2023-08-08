/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game.component;

import javax.swing.*;
import java.awt.*;

public class ScoreDisplay extends JComponent {

    private JLabel scoreLabel;

    public ScoreDisplay() {
        // Thiết lập layout cho khung điểm
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        // Tạo label hiển thị điểm
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel);
    }

    // Phương thức cập nhật điểm
    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
