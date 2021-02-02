import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultWindow extends JFrame {
    public ResultWindow(){
        setSize(180,100);
        JLabel winner=new JLabel();
        winner.setSize(100,100);
        winner.setLocation(0,0);

        if (TikToe.isDraw()){
            setTitle("Ничья!!!");
            winner.setText("Победила дружба!!!!!!!!!!!");
        }
        if (TikToe.getWinner()=='O'){
            setTitle("Победил Компуктер!");
            winner.setText("Техника захватывает мир!!!");
        }
        if (TikToe.getWinner()=='X'){
            setTitle("Победило Человечество!");
            winner.setText("Если вы это увидели, значит что то пошло не так(");
        }
        ;
        winner.setLayout(new BorderLayout());
        add(winner,BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}