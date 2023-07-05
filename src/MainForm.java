import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {

    public MainForm()
    {

        setTitle("XO game GUI");

        setBounds(300, 300, 455, 505);

        setResizable(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MainGameField gameField = MainGameField.getInstance();

        JPanel buttonPanel = new JPanel(new GridLayout());

        add(gameField, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);
        JButton btnStart = new JButton("Начать новую игру");

        buttonPanel.add(btnStart);




        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGameField gameField = MainGameField.getInstance();





                gameField.startNewGame();

                gameField.gameMode = 2;



            }
        });

        setVisible(true);
    }
}