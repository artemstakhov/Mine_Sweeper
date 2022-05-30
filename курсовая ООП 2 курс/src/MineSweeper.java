import javax.swing.*;
import java.awt.*;
import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Locale;

public class MineSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 10;
    private final int ROWS = 10;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE=20;
    /*public String[] items = {
            "10x10",
            "20x20",
            "30х30"
    };
    private JComboBox comboBox = new JComboBox(items);

    public String[] bombi = {
            "10",
            "20",
            "30"
    };
    private JComboBox combo_Bombs = new JComboBox(bombi);

     */
    public static void main(String[] args)
    {
        new MineSweeper();
    }
    private MineSweeper ()
    {
        /*combo_Bombs.setEditable(true);
        comboBox.setEditable(true);

         */
        game = new Game(COLS,ROWS,BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel()
    {
        label = new JLabel("Welcome");
        add(label,BorderLayout.PAGE_START);
    }
    private  void initPanel()
    {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Coord coord : Ranges.getAllCoords()) {

                    g.drawImage((Image)game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,coord.y * IMAGE_SIZE, this);

                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton()==MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton()==MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                if (e.getButton()==MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x*IMAGE_SIZE,Ranges.getSize().y*IMAGE_SIZE));
        panel.setMaximumSize(new Dimension(100,100));
        add (panel);
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED : return "GO PLAY";
            case BOMBED : return "Seeya";
            case WINNER : return "Well Done";
            default     : return "Welcome";
        }
    }

    private  void setImages()
    {
        for(Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }
    private void initFrame()
    {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MineSweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }
    private Image getImage (String name)
    {
        String filename = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
