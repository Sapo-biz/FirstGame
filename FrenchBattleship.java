// Jason He & Jeet Singh
// 4/21/2025
// Week #2
// FrenchBattleship.java  
/* This is our game project, French Battleship. The goal is to familiarize
 * the user with French 2 words and phrases to prepare them for Semester 1
 * Finals, while making it a fun and interactive experience by integrating
 * it into a battleship game with modifications. First, the user randomly
 * selects between ten battleship layouts (there is a randomize button).
 * The user then attempts to answers a French 2 level question. 
 */

// Import everything
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.BorderFactory; // for coloring buttons ONLY
import javax.swing.ImageIcon; // for cutscene ONLY

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;



// This is the class that sets the JFrames size, it's location and then 
// it setsResizable to false. - Made by Jason
public class FrenchBattleship
{
    public FrenchBattleship()
    {
    }


    public static void main(String[] args)
    {
       FrenchBattleship fb = new FrenchBattleship();
       fb.run();
    }
    public void run() 
    {
         JFrame frame = new JFrame("French Battleship");
         frame.setSize(1000, 800);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLocation(300, 0);
         frame.setResizable(false);
         FrenchBattleshipHolder fbh = new FrenchBattleshipHolder();
         frame.getContentPane().add(fbh);
         frame.setVisible(true);
    }
}

/* CutscenePanel displays an opening GIF animation with a timer
 * that transitions to the main menu after 8 seconds or when clicked.
 */
class CutscenePanel extends JPanel implements MouseListener
{
    private FrenchBattleshipHolder panelCards;
    private CardLayout cards;
    private ImageIcon gifIcon;
    private Timer timer;
    private int seconds;

    /* Sets the layout and adds necessary components */
    public CutscenePanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        panelCards = ph;
        cards = cl;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        seconds = 0;

        // Load the GIF
        gifIcon = new ImageIcon("Ship Navy GIF by World of Warships.gif");

        // Create timer that updates every second
        timer = new Timer(1000, new TimerListener());
        timer.start();

        // Add mouse listener to panel
        addMouseListener(this);
    }

    /* This method is used to paint the GIF to the panel
     * by using the drawImage method. - Jeet
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (gifIcon != null) 
        {
            Image img = gifIcon.getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /* This listener updates the timer and transitions to instructions panel
     * after 8 seconds - Jason
     */
    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            seconds++;
            if (seconds >= 8) 
            {
                timer.stop();
                cards.show(panelCards, "First");
            }
        }
    }

    /* Mouse listener methods - only using mouseClicked */
    public void mouseClicked(MouseEvent evt)
    {
        timer.stop();
        cards.show(panelCards, "First");
    }

    public void mousePressed(MouseEvent evt) {}
    public void mouseReleased(MouseEvent evt) {}
    public void mouseEntered(MouseEvent evt) {}
    public void mouseExited(MouseEvent evt) {}
}

/* FrenchBattleshipHolder holds the game together. It sets the background to 
 * Cyan and uses a card layout. It passes the cards into the respective 
 * panel and then it adds those panels to the card layout. - made by Jason
 */
class FrenchBattleshipHolder extends JPanel 
{
    private CardLayout cards; // instance of cardlayout
    private HighScoresPanel hsp; // reference to HighScoresPanel

    public FrenchBattleshipHolder() 
    {
        setBackground(Color.CYAN);
        cards = new CardLayout();
        setLayout(cards);

        Information info = new Information();
        CutscenePanel cp = new CutscenePanel(this, cards, info);
        FirstPagePanel fpp = new FirstPagePanel(this, cards, info);
        CreditsPanel creditsP = new CreditsPanel(this, cards);
        HelpPanel hp = new HelpPanel(this, cards);
        InstructionsPanel ip = new InstructionsPanel(this, cards);
        hsp = new HighScoresPanel(this, cards);
        BattleshipLayoutPanel blp = new BattleshipLayoutPanel(this, cards, info);
        ComputersTurnPanel ctp = new ComputersTurnPanel(this, cards, info);
        FrenchQuestionPanel fqp = new FrenchQuestionPanel(this, cards, info, ctp);
        AttackPanel ap = new AttackPanel(this, cards, info);
        ShopPanel sp = new ShopPanel(this, cards, info);
        YouLosePanel ylp = new YouLosePanel(this, cards, info);
        WinPanel wp = new WinPanel(this, cards, info);
        SettingsPanel settingsP = new SettingsPanel(this, cards);
        
		add(cp, "Cutscene");
        add(fpp, "First");
        add(creditsP, "Credits");
        add(hp, "Help");
        add(ip, "Instructions");
        add(hsp, "HighScores");
        add(blp, "BattleshipLayout");
        add(fqp, "FrenchQuestion");
        add(ap, "Attack");
        add(sp, "Shop");
        add(ylp, "YouLose");
        add(ctp, "ComputersTurn");
        add(wp, "Win");
        add(settingsP, "Settings");
        
        // Show the cutscene panel first
        cards.show(this, "Cutscene");
    }
    
    // Returns the HighScoresPanel instance
    public HighScoresPanel getHighScoresPanel()
    {
        return hsp;
    }
}

/* Makes a panel and adds the background image to it, then adds all 
 * componenets. Loads the image by calling a method in the
 * Information class. It creates buttons by calling the create menu
 * button method. Then it adds all of the components to the main 
 * panel- Jeet
 */
class FirstPagePanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards;
    private CardLayout cards;
    private Information info;
    private Image bgImage; // background image on first page
    private Color titleColor; // color of the title
    private Color buttonTextColor; // color of the text on button
    private Font titleFont; // title's font
    private Font buttonFont; // button text font
    private static int gridButtonSize = 40; // Default grid button size

    public FirstPagePanel(FrenchBattleshipHolder panelCardsIn, CardLayout cardsIn, Information infoIn) 
    {
        panelCards = panelCardsIn;
        cards = cardsIn;
        info = infoIn;
        setLayout(null);

        // Initialize colors and fonts
        titleColor = Color.BLACK;
        buttonTextColor = Color.BLACK;
        titleFont = new Font("Arial", Font.BOLD, 36);
        buttonFont = new Font("Arial", Font.BOLD, 24);

        // Load background image
        bgImage = info.loadImage("BgImage.jpg");

        // Add game title
        JLabel titleLabel = new JLabel("French Battleship");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(titleColor);
        titleLabel.setBounds(350, 50, 500, 100);
        add(titleLabel);

        // Create buttons
        buttonTextColor = Color.WHITE;
        buttonFont = new Font("Arial", Font.BOLD, 24);
        
        JButton playButton = createMenuButton("Play", 180);
        JButton helpButton = createMenuButton("Help", 260);
        JButton creditsButton = createMenuButton("Credits", 340);
        JButton highScoresButton = createMenuButton("High Scores", 420);
        JButton settingsButton = createMenuButton("Settings", 500);
        JButton exitButton = createMenuButton("Exit", 580);

		PlayButtonHandler pbh = new PlayButtonHandler();
		HelpButtonHandler hbh = new HelpButtonHandler();
		CreditsButtonHandler cbh = new CreditsButtonHandler();
		HighButtonHandler hbh2 = new HighButtonHandler();
		SettingsButtonHandler sbh = new SettingsButtonHandler();
		ExitButtonHandler ebh = new ExitButtonHandler();
        // Add action listeners
        playButton.addActionListener(pbh);
        helpButton.addActionListener(hbh);
        creditsButton.addActionListener(cbh);
        highScoresButton.addActionListener(hbh2);
        settingsButton.addActionListener(sbh);
        exitButton.addActionListener(ebh);
    }

    public JButton createMenuButton(String text, int yPosition) 
    {
        JButton button = new JButton(text);
        button.setBounds(350, yPosition, 300, 60);
        button.setFont(buttonFont);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        add(button);
        return button;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (bgImage != null) 
        {
        g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class HelpButtonHandler implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "Help");
        }
    }

    class CreditsButtonHandler implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(panelCards, "Credits");
        }
    }

    class ExitButtonHandler implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            System.exit(1);
        }
    }

    class HighButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "HighScores");
        }
    }

    class PlayButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "Instructions");
        }
    }
    
    class SettingsButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "Settings");
        }
    }

    public static int getGridButtonSize() 
    {
        return gridButtonSize;
    }
    
    public static void setGridButtonSize(int size) 
    {
        gridButtonSize = size;
    }
}

/* 
 * SettingsPanel allows users to configure game settings like grid button size.
 * It uses a slider to control the size of buttons in the grid. - Jeet 
 */
class SettingsPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards;
    private CardLayout cards;
    private JSlider gridSizeSlider;
    private JLabel sizeValueLabel;
    
    public SettingsPanel(FrenchBattleshipHolder ph, CardLayout cl) 
    {
        panelCards = ph;
        cards = cl;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        
        // Create title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);
        
        // Create content panel
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBackground(Color.CYAN);
        
        // Create grid size control panel
        JPanel gridSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridSizePanel.setBackground(Color.CYAN);
        JLabel gridSizeLabel = new JLabel("Grid Button Size:");
        gridSizeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        // Create slider for grid size
        gridSizeSlider = new JSlider(JSlider.HORIZONTAL, 20, 60, FirstPagePanel.getGridButtonSize());
        gridSizeSlider.setMajorTickSpacing(10);
        gridSizeSlider.setMinorTickSpacing(5);
        gridSizeSlider.setPaintTicks(true);
        gridSizeSlider.setPaintLabels(true);
        gridSizeSlider.setBackground(Color.CYAN);
        
        // Create label to show current value
        sizeValueLabel = new JLabel(gridSizeSlider.getValue() + " pixels");
        sizeValueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Add change listener to update the size
        gridSizeSlider.addChangeListener(new GridSizeChangeListener());
        
        gridSizePanel.add(gridSizeLabel);
        gridSizePanel.add(gridSizeSlider);
        gridSizePanel.add(sizeValueLabel);
        
        contentPanel.add(gridSizePanel);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.CYAN);
        JButton homeButton = new JButton("Back to Menu");
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        
        HomeButtonListener hbl = new HomeButtonListener();
        homeButton.addActionListener(hbl);
        buttonPanel.add(homeButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    class GridSizeChangeListener implements ChangeListener 
    {
        public void stateChanged(ChangeEvent e) 
        {
            int value = gridSizeSlider.getValue();
            sizeValueLabel.setText(value + " pixels");
            FirstPagePanel.setGridButtonSize(value);
        }
    }
    
    class HomeButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
			// Reset the game state
            cards.show(panelCards, "First");
        }
    }
}

/*   This is the information class. It is used to store the name of the
* user and the welcome label. It also has a method to load an image so 
* that it can be used for any image. (An example of polymorphism) - Jason
* It also has a method to setup the computer's ships.
*/
class Information 
{
    private String name; // name of user
    private JLabel welcomeLabel; // JLabel to welcome user, get anytime
    private Timer timer; // the running timer needed for all play panels
    private int userLayout; // stores the user's selected layout
    private int computerLayout; // stores the computer's random layout
    private boolean[][] computerShips; // 2D array to track computer's ship positions (true = ship, false = empty)
    private boolean[][] playerHits; // 2D array to track player's hits (true = hit, false = miss or not attempted)
    private boolean[][] userShips; // 2D array to track user's ship positions
    private boolean[][] computerHits; // 2D array to track computer's hits
    private int coins; // tracks running coins
    private JLabel coinsLabel; // label of the coins
    private boolean tripleAttackActive; // tracks if triple attack is active
    private int remainingAttacks; // tracks remaining attacks when triple attack is active
    private boolean radarActive; // tracks if radar is active
    private boolean torpedoActive; // tracks if torpedo is active
    private boolean airstrikeActive; // tracks if airstrike is active
    private int seconds; // tracks elapsed time in seconds
    private int powerupsUsed; // tracks total powerups used

    /* initialize */
    public Information() 
    {
        name = "";
        welcomeLabel = new JLabel("Welcome");
        timer = new Timer(1000, null);
        userLayout = 0;
        computerLayout = 0;
        computerShips = new boolean[15][15]; // Initialize with all false (no ships)
        playerHits = new boolean[15][15]; // Initialize with all false (no hits)
        userShips = new boolean[15][15]; // Initialize with all false (no ships)
        computerHits = new boolean[15][15]; // Initialize with all false (no hits)
        coins = 500;
        coinsLabel = new JLabel("500");
        tripleAttackActive = false;
        remainingAttacks = 1;
        radarActive = false;
        torpedoActive = false;
        airstrikeActive = false;
        seconds = 0; // Initialize elapsed time to 0
        powerupsUsed = 0; // Initialize powerups used to 0
    }

	// sets running coins
	public void setCoins(int coinsIn)
	{
		coins = coinsIn;
	}
	
	// can be used to get the coins anytime
	public int getCoins()
	{
		return coins;
	}
	
	// need to update our label
	public JLabel getLabel()
	{
		return coinsLabel;
	}
	
	// sets it as is to be called anytime
	public void setLabel(JLabel coinsLabelIn)
	{
		coinsLabel = coinsLabelIn;
	}
	
    // simple getter method
    public String getName() 
    {
        return name;
    }

    // simple setter method
    public void setName(String nameIn) 
    {
        name = nameIn;
    }

    // simple getter method
    public JLabel getWelcomeLabel() 
    {
        return welcomeLabel;
    }

    // simple setter method
    public void setWelcomeLabel(String nameIn) 
    {
        welcomeLabel.setText("Welcome " + nameIn);
    }

    // simple getter method
    public Timer getTimer()
    {
        return timer;
    }

    // simple setter method
    public void setTimer(Timer timerIn)
    {
        timer = timerIn;
    }

    // simple getter method for user layout
    public int getUserLayout()
    {
        return userLayout;
    }

    // simple setter method for user layout
    public void setUserLayout(int layoutIn)
    {
        userLayout = layoutIn;
        // When user layout is set, also setup their ship positions
        setupUserShips(layoutIn);
    }

    // simple getter method for computer layout
    public int getComputerLayout()
    {
        return computerLayout;
    }

    // simple setter method for computer layout
    public void setComputerLayout(int layoutIn)
    {
        computerLayout = layoutIn;
        // When computer layout is set, also setup the ship positions
        setupComputerShips(layoutIn);
    }

    /* helps load an image whenever called. very convienent. */
    public Image loadImage(String imageIn)
    {
        Image bothPicture = null;
        try 
        {
            bothPicture = ImageIO.read(new File(imageIn));
        } 
        catch (IOException e) 
        {
            System.err.println("\n\n" + bothPicture + "can't be found.\n\n");
            e.printStackTrace();
        }
        return bothPicture;
    }

    // Check if a position contains a ship
    public boolean isShipAt(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            return computerShips[row][col];
        }
        return false;
    }

    // Record a hit at a position
    public void recordHit(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            playerHits[row][col] = true;
        }
    }

    // Check if a position has been hit
    public boolean isHit(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            return playerHits[row][col];
        }
        return false;
    }

    // Setup computer ships based on layout number
    public void setupComputerShips(int layoutNumber)
    {
        // Reset the ship positions
        for (int i = 0; i < 15; i++) 
        {
            for (int j = 0; j < 15; j++) 
            {
                computerShips[i][j] = false;
            }
        }

        // Set up the specific layout
        if (layoutNumber == 1) 
        {
            setupLayout1();
        } 
        else if (layoutNumber == 2) 
        {
            setupLayout2();
        } 
        else if (layoutNumber == 3) 
        {
            setupLayout3();
        } 
        else if (layoutNumber == 4) 
        {
            setupLayout4();
        } 
        else if (layoutNumber == 5) 
        {
            setupLayout5();
        } 
        else if (layoutNumber == 6) 
        {
            setupLayout6();
        } 
        else if (layoutNumber == 7) 
        {
            setupLayout7();
        } 
        else if (layoutNumber == 8) 
        {
            setupLayout8();
        } 
        else if (layoutNumber == 9) 
        {
            setupLayout9();
        } 
        else if (layoutNumber == 10) 
        {
            setupLayout10();
        } 
        else if (layoutNumber == 11) 
        {
            setupLayout11();
        } 
        else if (layoutNumber == 12) 
        {
            setupLayout12();
        } 
        else if (layoutNumber == 13) 
        {
            setupLayout13();
        } 
        else if (layoutNumber == 14) 
        {
            setupLayout14();
        } 
        else if (layoutNumber == 15) 
        {
            setupLayout15();
        } 
        else 
        {
      setupLayout1();

        }

    }

    // Helper method to place a horizontal ship
    public void placeHorizontalShip(int row, int startCol, int length)
    {
        for (int i = 0; i < length; i++) 
        {
            if (startCol + i < 15) 
            {
                computerShips[row][startCol + i] = true;
            }
        }
    }

    // Helper method to place a vertical ship
    public void placeVerticalShip(int startRow, int col, int length)
    {
        for (int i = 0; i < length; i++) 
        {
            if (startRow + i < 15) 
            {
                computerShips[startRow + i][col] = true;
            }
        }
    }

    // Setup layout 1 ship positions to match the provided image
    public void setupLayout1()
    {
        // Horizontal ships
        placeHorizontalShip(0, 3, 5);
        placeHorizontalShip(1, 10, 5);
        placeHorizontalShip(3, 4, 5);
        placeHorizontalShip(5, 10, 5);
        placeHorizontalShip(10, 3, 5);
        placeHorizontalShip(14, 2, 5);

        // Vertical ships
        placeVerticalShip(1, 0, 5);
        placeVerticalShip(8, 1, 5);
        placeVerticalShip(3, 3, 5);
        placeVerticalShip(4, 7, 5);
        placeVerticalShip(8, 10, 5);
        placeVerticalShip(10, 14, 5);
    }

    // Setup layout 2 ship positions
    public void setupLayout2()
    {
        // Horizontal ships
        placeHorizontalShip(0, 5, 5);
        placeHorizontalShip(3, 10, 5);
        placeHorizontalShip(4, 1, 5);
        placeHorizontalShip(12, 0, 5);
        placeHorizontalShip(12, 10, 5);
        placeHorizontalShip(14, 5, 5);

        // Vertical ships
        placeVerticalShip(6, 0, 5);
        placeVerticalShip(6, 2, 5);
        placeVerticalShip(6, 6, 5);
        placeVerticalShip(1, 8, 10);
        placeVerticalShip(6, 12, 5);
    }

    // Setup layout 3 ship positions
    public void setupLayout3()
    {
        // Horizontal ships
        placeHorizontalShip(0, 10, 5);
        placeHorizontalShip(3, 3, 5);
        placeHorizontalShip(7, 6, 5);
        placeHorizontalShip(9, 4, 5);
        placeHorizontalShip(10, 10, 5);
        placeHorizontalShip(13, 7, 5);

        // Vertical ships
        placeVerticalShip(1, 0, 5);
        placeVerticalShip(7, 1, 5);
        placeVerticalShip(0, 2, 5);
        placeVerticalShip(10, 4, 5);
        placeVerticalShip(1, 9, 5);
        placeVerticalShip(3, 13, 5);
    }

    // Setup layout 4 ship positions
    public void setupLayout4()
    {
        // Placeholder for layout 4
        // Horizontal ships
        placeHorizontalShip(1, 2, 5);
        placeHorizontalShip(4, 7, 5);
        placeHorizontalShip(7, 10, 5);
        placeHorizontalShip(9, 6, 5);
        placeHorizontalShip(11, 4, 5);
        placeHorizontalShip(13, 6, 5);

        // Vertical ships
        placeVerticalShip(10, 0, 5);
        placeVerticalShip(3, 1, 5);
        placeVerticalShip(9, 2, 5);
        placeVerticalShip(4, 4, 5);
        placeVerticalShip(9, 12, 5);
        placeVerticalShip(1, 13, 5);
    }

    // Setup layout 5 ship positions
    public void setupLayout5()
    {
        // Placeholder for layout 5
        // Horizontal ships
        placeHorizontalShip(0, 2, 5);
        placeHorizontalShip(1, 10, 5);
        placeHorizontalShip(2, 4, 5);
        placeHorizontalShip(4, 3, 5);
        placeHorizontalShip(6, 8, 5);
        placeHorizontalShip(13, 0, 5);

        // Vertical ships
        placeVerticalShip(1, 0, 5);
        placeVerticalShip(5, 2, 5);
        placeVerticalShip(7, 5, 5);
        placeVerticalShip(9, 9, 5);
        placeVerticalShip(8, 12, 5);
        placeVerticalShip(3, 14, 5);
    }

    // Setup layout 6 ship positions
    public void setupLayout6()
    {
        // Placeholder for layout 6
        // Horizontal ships
        placeHorizontalShip(0, 10, 5);
        placeHorizontalShip(1, 1, 5);
        placeHorizontalShip(2, 8, 5);
        placeHorizontalShip(4, 7, 5);
        placeHorizontalShip(7, 9, 5);
        placeHorizontalShip(13, 2, 5);

        // Vertical ships
        placeVerticalShip(8, 0, 5);
        placeVerticalShip(3, 2, 5);
        placeVerticalShip(2, 4, 5);
        placeVerticalShip(7, 6, 5);
        placeVerticalShip(10, 8, 5);
        placeVerticalShip(9, 11, 5);
    }

    // Setup layout 7 ship positions
    public void setupLayout7()
    {
        // Placeholder for layout 7
        // Horizontal ships
        placeHorizontalShip(1, 10, 5);
        placeHorizontalShip(3, 8, 5);
        placeHorizontalShip(5, 5, 5);
        placeHorizontalShip(7, 3, 5);
        placeHorizontalShip(9, 1, 5);
        placeHorizontalShip(11, 3, 5);

        // Vertical ships
        placeVerticalShip(1, 0, 5);
        placeVerticalShip(10, 0, 5);
        placeVerticalShip(0, 3, 5);
        placeVerticalShip(7, 9, 5);
        placeVerticalShip(6, 11, 5);
        placeVerticalShip(8, 13, 5);
    }

    // Setup layout 8 ship positions
    public void setupLayout8()
    {
        // Placeholder for layout 8
        // Horizontal ships
        placeHorizontalShip(1, 9, 5);
        placeHorizontalShip(3, 10, 5);
        placeHorizontalShip(6, 8, 5);
        placeHorizontalShip(7, 2, 5);
        placeHorizontalShip(9, 7, 5);
        placeHorizontalShip(12, 7, 5);

        // Vertical ships
        placeVerticalShip(9, 1, 5);
        placeVerticalShip(1, 3, 5);
        placeVerticalShip(1, 4, 5);
        placeVerticalShip(10, 4, 5);
        placeVerticalShip(0, 7, 5);
        placeHorizontalShip(10, 13, 5);
    }

    // Setup layout 9 ship positions
    public void setupLayout9()
    {
        // Placeholder for layout 9
        // Horizontal ships
        placeHorizontalShip(0, 7, 5);
        placeHorizontalShip(2, 8, 5);
        placeHorizontalShip(4, 9, 5);
        placeHorizontalShip(10, 9, 5);
        placeHorizontalShip(12, 3, 5);
        placeHorizontalShip(14, 8, 5);

        // Vertical ships
        placeVerticalShip(2, 0, 5);
        placeVerticalShip(10, 1, 5);
        placeVerticalShip(1, 2, 5);
        placeVerticalShip(7, 3, 5);
        placeVerticalShip(0, 4, 5);
        placeVerticalShip(5, 7, 5);
    }

    // Setup layout 10 ship positions
    public void setupLayout10()
    {
        // Placeholder for layout 10
        // Horizontal ships
        placeHorizontalShip(1, 6, 5);
        placeHorizontalShip(4, 6, 5);
        placeHorizontalShip(8, 10, 5);
        placeHorizontalShip(10, 10, 5);
        placeHorizontalShip(12, 9, 5);
        placeHorizontalShip(14, 2, 5);

        // Vertical ships
        placeVerticalShip(3, 1, 5);
        placeVerticalShip(7, 3, 5);
        placeVerticalShip(1, 4, 5);
        placeVerticalShip(6, 6, 5);
        placeVerticalShip(5, 8, 5);
        placeVerticalShip(2, 12, 5);
    }

    // Setup layout 11 ship positions
    public void setupLayout11()
    {
        // Placeholder for layout 11
        // Horizontal ships
        placeHorizontalShip(0, 1, 5);
        placeHorizontalShip(2, 0, 5);
        placeHorizontalShip(6, 7, 5);
        placeHorizontalShip(8, 10, 5);
        placeHorizontalShip(9, 5, 5);
        placeHorizontalShip(12, 10, 5);

        // Vertical ships
        placeVerticalShip(6, 1, 5);
        placeVerticalShip(10, 3, 5);
        placeVerticalShip(4, 4, 5);
        placeVerticalShip(0, 7, 5);
        placeVerticalShip(10, 8, 5);
        placeVerticalShip(0, 12, 5);
    }

    // Setup layout 12 ship positions
    public void setupLayout12()
    {
        // Placeholder for layout 12
        // Horizontal ships
        placeHorizontalShip(0, 2, 5);
        placeHorizontalShip(1, 10, 5);
        placeHorizontalShip(3, 10, 5);
        placeHorizontalShip(8, 5, 5);
        placeHorizontalShip(13, 10, 5);
        placeHorizontalShip(14, 5, 5);

        // Vertical ships
        placeVerticalShip(1, 0, 5);
        placeVerticalShip(8, 1, 5);
        placeVerticalShip(2, 3, 5);
        placeVerticalShip(8, 6, 5);
        placeVerticalShip(2, 8, 5);
        placeVerticalShip(10, 11, 5);
    }

    // Setup layout 13 ship positions
    public void setupLayout13()
    {
        // Placeholder for layout 13
        // Horizontal ships
        placeHorizontalShip(2, 7, 5);
        placeHorizontalShip(4, 10, 5);
        placeHorizontalShip(6, 7, 5);
        placeHorizontalShip(9, 3, 5);
        placeHorizontalShip(11, 4, 5);
        placeHorizontalShip(14, 2, 5);

        // Vertical ships
        placeVerticalShip(8, 0, 5);
        placeVerticalShip(2, 1, 5);
        placeVerticalShip(1, 4, 5);
        placeVerticalShip(8, 9, 5);
        placeVerticalShip(7, 12, 5);
        placeVerticalShip(9, 14, 5);
    }

    // Setup layout 14 ship positions
    public void setupLayout14()
    {
        // Placeholder for layout 14
        // Horizontal ships
        placeHorizontalShip(0, 4, 5);
        placeHorizontalShip(2, 8, 5);
        placeHorizontalShip(4, 7, 5);
        placeHorizontalShip(6, 0, 5);
        placeHorizontalShip(12, 5, 5);
        placeHorizontalShip(13, 8, 5);

        // Vertical ships
        placeVerticalShip(8, 0, 5);
        placeVerticalShip(0, 2, 5);
        placeVerticalShip(9, 3, 5);
        placeVerticalShip(3, 5, 5);
        placeVerticalShip(6, 8, 5);
        placeVerticalShip(8, 12, 5);
    }

    // Setup layout 15 ship positions
    public void setupLayout15()
    {
        // Placeholder for layout 15
        // Horizontal ships
        placeHorizontalShip(2, 7, 5);
        placeHorizontalShip(9, 1, 5);
        placeHorizontalShip(9, 7, 5);
        placeHorizontalShip(11, 8, 5);
        placeHorizontalShip(12, 0, 5);
        placeHorizontalShip(13, 9, 5);

        // Vertical ships
        placeVerticalShip(3, 1, 5);
        placeVerticalShip(1, 4, 5);
        placeVerticalShip(4, 6, 5);
        placeVerticalShip(10, 6, 5);
        placeVerticalShip(4, 11, 5);
        placeVerticalShip(1, 14, 5);
    }

    // Check if a position contains a user's ship
    public boolean isUserShipAt(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            return userShips[row][col];
        }
        return false;
    }

    // Record a computer's hit at a position
    public void recordComputerHit(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            computerHits[row][col] = true;
        }
    }

    // Check if a position has been hit by computer
    public boolean isComputerHit(int row, int col)
    {
        if (row >= 0 && row < 15 && col >= 0 && col < 15) 
        {
            return computerHits[row][col];
        }
        return false;
    }

    // Setup user ships based on layout number
    public void setupUserShips(int layoutNumber)
    {
        // Reset the ship positions
        for (int i = 0; i < 15; i++) 
        {
            for (int j = 0; j < 15; j++) 
            {
                userShips[i][j] = false;
            }
        }

        // Set up the specific layout
        if (layoutNumber == 1) 
        {
            setupLayout1ForUser();
        } 
        else if (layoutNumber == 2) 
        {
            setupLayout2ForUser();
        } 
        else if (layoutNumber == 3) 
        {
            setupLayout3ForUser();
        } 
        else if (layoutNumber == 4) 
        {
            setupLayout4ForUser();
        } 
        else if (layoutNumber == 5) 
        {
            setupLayout5ForUser();
        } 
        else if (layoutNumber == 6) 
        {
            setupLayout6ForUser();
        } 
        else if (layoutNumber == 7) 
        {
            setupLayout7ForUser();
        } 
        else if (layoutNumber == 8) 
        {
            setupLayout8ForUser();
        } 
        else if (layoutNumber == 9) 
        {
            setupLayout9ForUser();
        } 
        else if (layoutNumber == 10) 
        {
            setupLayout10ForUser();
        } 
        else if (layoutNumber == 11) 
        {
            setupLayout11ForUser();
        } 
        else if (layoutNumber == 12) 
        {
            setupLayout12ForUser();
        } 
        else if (layoutNumber == 13) 
        {
            setupLayout13ForUser();
        } 
        else if (layoutNumber == 14) 
        {
            setupLayout14ForUser();
        } 
        else if (layoutNumber == 15) 
        {
            setupLayout15ForUser();
        } 
        else 
        {
            setupLayout1ForUser();
        }
    }

    // Helper method to place a horizontal ship for user
    public void placeHorizontalShipForUser(int row, int startCol, int length)
    {
        for (int i = 0; i < length; i++) 
        {
            if (startCol + i < 15) 
            {
                userShips[row][startCol + i] = true;
            }
        }
    }

    // Helper method to place a vertical ship for user
    public void placeVerticalShipForUser(int startRow, int col, int length)
    {
        for (int i = 0; i < length; i++) 
        {
            if (startRow + i < 15) 
            {
                userShips[startRow + i][col] = true;
            }
        }
    }

    // Setup layout 1 ship positions for user
    public void setupLayout1ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 3, 5);
        placeHorizontalShipForUser(1, 10, 5);
        placeHorizontalShipForUser(3, 4, 5);
        placeHorizontalShipForUser(5, 10, 5);
        placeHorizontalShipForUser(10, 3, 5);
        placeHorizontalShipForUser(14, 2, 5);

        // Vertical ships
        placeVerticalShipForUser(1, 0, 5);
        placeVerticalShipForUser(8, 1, 5);
        placeVerticalShipForUser(3, 3, 5);
        placeVerticalShipForUser(4, 7, 5);
        placeVerticalShipForUser(8, 10, 5);
        placeVerticalShipForUser(10, 14, 5);
    }

    // Setup layout 2 ship positions for user
    public void setupLayout2ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 5, 5);
        placeHorizontalShipForUser(3, 10, 5);
        placeHorizontalShipForUser(4, 1, 5);
        placeHorizontalShipForUser(12, 0, 5);
        placeHorizontalShipForUser(12, 10, 5);
        placeHorizontalShipForUser(14, 5, 5);

        // Vertical ships
        placeVerticalShipForUser(6, 0, 5);
        placeVerticalShipForUser(6, 2, 5);
        placeVerticalShipForUser(6, 6, 5);
        placeVerticalShipForUser(1, 8, 10);
        placeVerticalShipForUser(6, 12, 5);
    }

    // Setup layout 3 ship positions for user
    public void setupLayout3ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 10, 5);
        placeHorizontalShipForUser(3, 3, 5);
        placeHorizontalShipForUser(7, 6, 5);
        placeHorizontalShipForUser(9, 4, 5);
        placeHorizontalShipForUser(10, 10, 5);
        placeHorizontalShipForUser(13, 7, 5);

        // Vertical ships
        placeVerticalShipForUser(1, 0, 5);
        placeVerticalShipForUser(7, 1, 5);
        placeVerticalShipForUser(0, 2, 5);
        placeVerticalShipForUser(10, 4, 5);
        placeVerticalShipForUser(1, 9, 5);
        placeVerticalShipForUser(3, 13, 5);
    }

    // Setup layout 4 ship positions for user
    public void setupLayout4ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(1, 2, 5);
        placeHorizontalShipForUser(4, 7, 5);
        placeHorizontalShipForUser(7, 10, 5);
        placeHorizontalShipForUser(9, 6, 5);
        placeHorizontalShipForUser(11, 4, 5);
        placeHorizontalShipForUser(13, 6, 5);

        // Vertical ships
        placeVerticalShipForUser(10, 0, 5);
        placeVerticalShipForUser(3, 1, 5);
        placeVerticalShipForUser(9, 2, 5);
        placeVerticalShipForUser(4, 4, 5);
        placeVerticalShipForUser(9, 12, 5);
        placeVerticalShipForUser(1, 13, 5);
    }

    // Setup layout 5 ship positions for user
    public void setupLayout5ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 2, 5);
        placeHorizontalShipForUser(1, 10, 5);
        placeHorizontalShipForUser(2, 4, 5);
        placeHorizontalShipForUser(4, 3, 5);
        placeHorizontalShipForUser(6, 8, 5);
        placeHorizontalShipForUser(13, 0, 5);

        // Vertical ships
        placeVerticalShipForUser(1, 0, 5);
        placeVerticalShipForUser(5, 2, 5);
        placeVerticalShipForUser(7, 5, 5);
        placeVerticalShipForUser(9, 9, 5);
        placeVerticalShipForUser(8, 12, 5);
        placeVerticalShipForUser(3, 14, 5); 
    }

    // Setup layout 6 ship positions for user
    public void setupLayout6ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 10, 5);
        placeHorizontalShipForUser(1, 1, 5);
        placeHorizontalShipForUser(2, 8, 5);
        placeHorizontalShipForUser(4, 7, 5);
        placeHorizontalShipForUser(7, 9, 5);
        placeHorizontalShipForUser(13, 2, 5);

        // Vertical ships
        placeVerticalShipForUser(8, 0, 5);
        placeVerticalShipForUser(3, 2, 5);
        placeVerticalShipForUser(2, 4, 5);
        placeVerticalShipForUser(7, 6, 5);
        placeVerticalShipForUser(10, 8, 5);
        placeVerticalShipForUser(9, 11, 5);
    }

    // Setup layout 7 ship positions for user
    public void setupLayout7ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(1, 10, 5);
        placeHorizontalShipForUser(3, 8, 5);
        placeHorizontalShipForUser(5, 5, 5);
        placeHorizontalShipForUser(7, 3, 5);
        placeHorizontalShipForUser(9, 1, 5);
        placeHorizontalShipForUser(11, 3, 5);

        // Vertical ships
        placeVerticalShipForUser(1, 0, 5);
        placeVerticalShipForUser(10, 0, 5);
        placeVerticalShipForUser(0, 3, 5);
        placeVerticalShipForUser(7, 9, 5);
        placeVerticalShipForUser(6, 11, 5);
        placeVerticalShipForUser(8, 13, 5);
    }

    // Setup layout 8 ship positions for user
    public void setupLayout8ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(1, 9, 5);
        placeHorizontalShipForUser(3, 10, 5);
        placeHorizontalShipForUser(6, 8, 5);
        placeHorizontalShipForUser(7, 2, 5);
        placeHorizontalShipForUser(9, 7, 5);
        placeHorizontalShipForUser(12, 7, 5);

        // Vertical ships
        placeVerticalShipForUser(9, 1, 5);
        placeVerticalShipForUser(1, 3, 5);
        placeVerticalShipForUser(1, 4, 5);
        placeVerticalShipForUser(10, 4, 5);
        placeVerticalShipForUser(0, 7, 5);
        placeHorizontalShipForUser(10, 13, 5);
    }

    // Setup layout 9 ship positions for user
    public void setupLayout9ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 7, 5);
        placeHorizontalShipForUser(2, 8, 5);
        placeHorizontalShipForUser(4, 9, 5);
        placeHorizontalShipForUser(10, 9, 5);
        placeHorizontalShipForUser(12, 3, 5);
        placeHorizontalShipForUser(14, 8, 5);

        // Vertical ships
        placeVerticalShipForUser(2, 0, 5);
        placeVerticalShipForUser(10, 1, 5);
        placeVerticalShipForUser(1, 2, 5);
        placeVerticalShipForUser(7, 3, 5);
        placeVerticalShipForUser(0, 4, 5);
        placeVerticalShipForUser(5, 7, 5);
    }

    // Setup layout 10 ship positions for user
    public void setupLayout10ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(1, 6, 5);
        placeHorizontalShipForUser(4, 6, 5);
        placeHorizontalShipForUser(8, 10, 5);
        placeHorizontalShipForUser(10, 10, 5);
        placeHorizontalShipForUser(12, 9, 5);
        placeHorizontalShipForUser(14, 2, 5);

        // Vertical ships
        placeVerticalShipForUser(3, 1, 5);
        placeVerticalShipForUser(7, 3, 5);
        placeVerticalShipForUser(1, 4, 5);
        placeVerticalShipForUser(6, 6, 5);
        placeVerticalShipForUser(5, 8, 5);
        placeVerticalShipForUser(2, 12, 5);
    }

    // Setup layout 11 ship positions for user
    public void setupLayout11ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 1, 5);
        placeHorizontalShipForUser(2, 0, 5);
        placeHorizontalShipForUser(6, 7, 5);
        placeHorizontalShipForUser(8, 10, 5);
        placeHorizontalShipForUser(9, 5, 5);
        placeHorizontalShipForUser(12, 10, 5);

        // Vertical ships
        placeVerticalShipForUser(6, 1, 5);
        placeVerticalShipForUser(10, 3, 5);
        placeVerticalShipForUser(4, 4, 5);
        placeVerticalShipForUser(0, 7, 5);
        placeVerticalShipForUser(10, 8, 5);
        placeVerticalShipForUser(0, 12, 5);
    }

    // Setup layout 12 ship positions for user
    public void setupLayout12ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 2, 5);
        placeHorizontalShipForUser(1, 10, 5);
        placeHorizontalShipForUser(3, 10, 5);
        placeHorizontalShipForUser(8, 5, 5);
        placeHorizontalShipForUser(13, 10, 5);
        placeHorizontalShipForUser(14, 5, 5);

        // Vertical ships
        placeVerticalShipForUser(1, 0, 5);
        placeVerticalShipForUser(8, 1, 5);
        placeVerticalShipForUser(2, 3, 5);
        placeVerticalShipForUser(8, 6, 5);
        placeVerticalShipForUser(2, 8, 5);
        placeVerticalShipForUser(10, 11, 5);
    }

    // Setup layout 13 ship positions for user
    public void setupLayout13ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(2, 7, 5);
        placeHorizontalShipForUser(4, 10, 5);
        placeHorizontalShipForUser(6, 7, 5);
        placeHorizontalShipForUser(9, 3, 5);
        placeHorizontalShipForUser(11, 4, 5);
        placeHorizontalShipForUser(14, 2, 5);

        // Vertical ships
        placeVerticalShipForUser(8, 0, 5);
        placeVerticalShipForUser(2, 1, 5);
        placeVerticalShipForUser(1, 4, 5);
        placeVerticalShipForUser(8, 9, 5);
        placeVerticalShipForUser(7, 12, 5);
        placeVerticalShipForUser(9, 14, 5);
    }

    // Setup layout 14 ship positions for user
    public void setupLayout14ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(0, 4, 5);
        placeHorizontalShipForUser(2, 8, 5);
        placeHorizontalShipForUser(4, 7, 5);
        placeHorizontalShipForUser(6, 0, 5);
        placeHorizontalShipForUser(12, 5, 5);
        placeHorizontalShipForUser(13, 8, 5);

        // Vertical ships
        placeVerticalShipForUser(8, 0, 5);
        placeVerticalShipForUser(0, 2, 5);
        placeVerticalShipForUser(9, 3, 5);
        placeVerticalShipForUser(3, 5, 5);
        placeVerticalShipForUser(6, 8, 5);
        placeVerticalShipForUser(8, 12, 5);
    }

    // Setup layout 15 ship positions for user
    public void setupLayout15ForUser()
    {
        // Horizontal ships
        placeHorizontalShipForUser(2, 7, 5);
        placeHorizontalShipForUser(9, 1, 5);
        placeHorizontalShipForUser(9, 7, 5);
        placeHorizontalShipForUser(11, 8, 5);
        placeHorizontalShipForUser(12, 0, 5);
        placeHorizontalShipForUser(13, 9, 5);

        // Vertical ships
        placeVerticalShipForUser(3, 1, 5);
        placeVerticalShipForUser(1, 4, 5);
        placeVerticalShipForUser(4, 6, 5);
        placeVerticalShipForUser(10, 6, 5);
        placeVerticalShipForUser(4, 11, 5);
        placeVerticalShipForUser(1, 14, 5);
    }

    // Add getters and setters for triple attack
    public boolean isTripleAttackActive() 
    {
        return tripleAttackActive;
    }

    public void setTripleAttackActive(boolean active) 
    {
        tripleAttackActive = active;
        if (active) 
        {
            remainingAttacks = 3;
            incrementPowerupsUsed();
        }
        else
            remainingAttacks = 1;
    }

    public int getRemainingAttacks() 
    {
        return remainingAttacks;
    }

    public void decrementRemainingAttacks() 
    {
        if (remainingAttacks > 0) 
        {
            remainingAttacks--;
        }
    }

    // Add setter for remaining attacks
    public void setRemainingAttacks(int attacks) 
    {
        remainingAttacks = attacks;
    }

    // Getter and setter methods for power-ups
    public boolean isRadarActive() 
    {
        return radarActive;
    }

    public void setRadarActive(boolean active) 
    {
        radarActive = active;
        if (active) 
        {
            incrementPowerupsUsed();
        }
    }

    public boolean isTorpedoActive() 
    {
        return torpedoActive;
    }

    public void setTorpedoActive(boolean active) 
    {
        torpedoActive = active;
        if (active) 
        {
            incrementPowerupsUsed();
        }
    }

    public boolean isAirstrikeActive() 
    {
        return airstrikeActive;
    }

    public void setAirstrikeActive(boolean active) 
    {
        airstrikeActive = active;
        if (active) 
        {
            incrementPowerupsUsed();
        }
    }

    /* Loads the contents of a file into a String array.
     * This is an example of polymorphism as it can be used to load
     * any text file, whether it's high scores or questions/answers.
     */
    public String[] loadFileContents(String fileName)
    {
        String[] contents;
        try 
        {
            // First count the number of lines
            Scanner countScanner = new Scanner(new File(fileName));
            int lineCount = 0;
            while (countScanner.hasNextLine()) 
            {
                countScanner.nextLine();
                lineCount++;
            }
            countScanner.close();

            // Initialize array with the correct size
            contents = new String[lineCount];

            // Now read the actual data
            Scanner fileScanner = new Scanner(new File(fileName));
            int index = 0;
            while (fileScanner.hasNextLine() && index < contents.length) 
            {
                contents[index] = fileScanner.nextLine();
                index++;
            }
            fileScanner.close();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error loading file " + fileName + ": " + e);
            // Initialize with empty array to prevent null pointer exceptions
            contents = new String[0];
        }
        return contents;
    }

    /* Updates coins and the coin display label atomically.
     * Returns true if the update was successful, false if it would result in negative coins
     */
    public boolean updateCoins(int amount) 
    {
        if (coins + amount < 0) 
        {
            return false;
        }
        
        coins += amount;
        coinsLabel.setText(coins + " coins");
        return true;
    }

    /* Checks if there are enough coins for a purchase
     * Returns true if the amount is available, false otherwise
     */
    public boolean hasEnoughCoins(int amount) 
    {
        if (coins >= amount)
        {
            return true;
        }
        return false;
    }

    /* Gets the cost of a powerup
     * Returns the cost in coins
     */
    public int getPowerupCost(String powerup) 
    {
        if (powerup.equals("radar"))
        {
            return 400;
        }
        if (powerup.equals("airstrike"))
        {
            return 800;
        }
        if (powerup.equals("tripleAttack"))
        {
            return 400;
        }
        if (powerup.equals("torpedo"))
        {
            return 2000;
        }
        return 0;
    }

    // Check if all computer ships are sunk
    public boolean areAllComputerShipsSunk()
    {
        for (int i = 0; i < 15; i++) 
        {
            for (int j = 0; j < 15; j++) 
            {
                if (computerShips[i][j] && !playerHits[i][j]) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if all user ships are sunk
    public boolean areAllUserShipsSunk()
    {
        for (int i = 0; i < 15; i++) 
        {
            for (int j = 0; j < 15; j++) 
            {
                if (userShips[i][j] && !computerHits[i][j]) 
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Get the elapsed time in seconds
    public int getSeconds()
    {
        return seconds;
    }
    
    // Set the elapsed time in seconds
    public void setSeconds(int secondsIn)
    {
        seconds = secondsIn;
    }
    
    // Get the number of powerups used
    public int getPowerupsUsed()
    {
        return powerupsUsed;
    }
    
    // Increment the powerups used counter
    public void incrementPowerupsUsed()
    {
        powerupsUsed++;
    }
    
    // Reset all game state variables to their initial values
    public void resetGame()
    {
        // Reset name and welcome label
        name = "";
        welcomeLabel.setText("Welcome");
        
        // Reset timer
        if (timer != null) 
        {
            timer.stop();
        }
        seconds = 0;
        
        // Reset layouts
        userLayout = 0;
        computerLayout = 0;
        
        // Reset ship positions and hits
        computerShips = new boolean[15][15];
        playerHits = new boolean[15][15];
        userShips = new boolean[15][15];
        computerHits = new boolean[15][15];
        
        // Reset coins and powerups
        coins = 500;
        coinsLabel.setText("500");
        tripleAttackActive = false;
        remainingAttacks = 1;
        radarActive = false;
        torpedoActive = false;
        airstrikeActive = false;
        powerupsUsed = 0;
        
        setupComputerShips(0);
        setupUserShips(0);
    }
}

/* 
* This is the credits panel. It displays the credits for the game.
* It also has a home button that takes the user back to the main menu. - Jeet
*/
class CreditsPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // instance of holder class
    private CardLayout cards; // card instance

    /* sets border layout and all the neccessary components for credits */
    public CreditsPanel(FrenchBattleshipHolder ph, CardLayout cl) 
    {
        panelCards = ph;
        cards = cl;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.CYAN);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Credits");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1, 10, 30));
        contentPanel.setBackground(Color.CYAN);

        JLabel codeLabel = createCreditLabel("Code: Jason He and Jeet Singh");
        JLabel graphicsLabel = createCreditLabel("Graphics: Jason He and Jeet Singh");
        JLabel testingLabel = createCreditLabel("Testing and Debugging: Jason He and Jeet Singh");
        JLabel thanksLabel = createCreditLabel("Special Thanks: Mr. Conlin, Justin Chen");

        contentPanel.add(codeLabel);
        contentPanel.add(graphicsLabel);
        contentPanel.add(testingLabel);
        contentPanel.add(thanksLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.CYAN);
        JButton homeButton = new JButton("Back to Menu");
        
        HomeButtonListener hbl2 = new HomeButtonListener();
        homeButton.addActionListener(hbl2);
        buttonPanel.add(homeButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /* example of polymorphism: method can be called anytime to create label */
    public JLabel createCreditLabel(String text) 
    {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    // listens for home button
    class HomeButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "First");
        }
    }
}

/* 
* This is the instructions panel. It displays the instructions for the game.
* It also has a return button that takes the user back to the main menu. - Jeet
*/
class InstructionsPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards;
    private CardLayout cards;
    private JRadioButton agreeButton;
    private JRadioButton disagreeButton;
    private ButtonGroup buttonGroup;
    private JButton continueButton;

    public InstructionsPanel(FrenchBattleshipHolder ph, CardLayout cl) 
    {
        panelCards = ph;
        cards = cl;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Game Rules and Terms");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        // Plain text instructions (very long)
        String instructionsText =
            "Welcome to French Battleship!\n" +
            "\n" +
            "This game combines the classic strategy of Battleship with French language learning.\n" +
            "\n" +
            "GAME SETUP:\n" +
            "- You will be presented with 15 different battleship layouts to choose from.\n" +
            "- Each layout contains multiple ships of varying sizes and positions.\n" +
            "- The computer will randomly select a different layout for itself.\n" +
            "\n" +
            "GAMEPLAY:\n" +
            "- On your turn, you must answer a French language question correctly.\n" +
            "- If you answer correctly, the computer will not attack you\n" +
            "- When you get it right you will be motivated as the computer will not be able to attack!" +
            "- If you answer incorrectly, the computer will take its turn and attempt to attack your ships.\n" +
            "- Each successful hit brings you closer to victory, while each miss gives your opponent a chance to strike back.\n" +
            "\n" +
            "POWER-UPS AND SHOP:\n" +
            "- You can visit the shop to purchase special abilities using the coins you earn.\n" +
            "- Radar: Reveals ships in a 3x3 area of your choice.\n" +
            "- Airstrike: Attacks 5 random locations on the grid.\n" +
            "- Triple Attack: Allows you to make three consecutive attacks in a single turn.\n" +
            "- Torpedo: Attacks an entire row.\n" +
            "- Manage your coins wisely and choose the right moment to use each power-up.\n" +
            "\n" +
            "WINNING CONDITIONS:\n" +
            "- The game ends when either player sinks all of the opponent's ships.\n" +
            "- Your final score is based on the number of coins collected and the number of ships you have sunk.\n" +
            "\n" +
            "TIPS AND STRATEGIES:\n" +
            "- Pay attention to the feedback after each attack.\n" +
            "- Use power-ups strategically.\n" +
            "- Practice your French! The more questions you answer correctly, the more opportunities you have to attack and win.\n" +
            "- Remember that the computer's moves are random, so stay alert and adapt your strategy as the game progresses.\n" +
            "- Review the help section if you get stuck or need a refresher on the rules.\n" +
            "\n" +
            "TERMS AND CONDITIONS:\n" +
            "- By playing this game, you agree to use it for educational purposes only.\n" +
            "- Do not attempt to modify or hack the game in any way.\n" +
            "- All game content is provided for learning and practice purposes.\n" +
            "- Progress is not saved between sessions. Each time you play, you start fresh.\n" +
            "- Enjoy the game and do your best to improve your French skills!\n" +
            "\n" +
            "If you have any questions or feedback, please refer to the help section or contact your instructor. Good luck, and have fun playing French Battleship!\n";

        // Create the text area
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setText(instructionsText);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setEditable(false);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsArea.setBackground(Color.WHITE);
        instructionsArea.setMargin(new Insets(30, 30, 30, 15));

        // Put the text area in a scroll pane (let layout manager handle size)
        JScrollPane scrollPane = new JScrollPane(instructionsArea);

        // Panel for radio buttons and continue button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.CYAN);
        agreeButton = new JRadioButton("I agree to the terms and conditions");
        disagreeButton = new JRadioButton("I do not agree to the terms and conditions");
        agreeButton.setFont(new Font("Arial", Font.BOLD, 16));
        disagreeButton.setFont(new Font("Arial", Font.BOLD, 16));
        buttonGroup = new ButtonGroup();
        buttonGroup.add(agreeButton);
        buttonGroup.add(disagreeButton);
        bottomPanel.add(agreeButton);
        bottomPanel.add(disagreeButton);
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        
        ContinueButtonListener cbl3 = new ContinueButtonListener();
        continueButton.addActionListener(cbl3);
        bottomPanel.add(continueButton);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            if (agreeButton.isSelected()) 
			{
				cards.show(panelCards, "BattleshipLayout");
            } 
				else if (disagreeButton.isSelected()) 
            {
                System.exit(1);
            }
        }
    }
}

/* 
* This is the help panel. It displays the help information for the game.
* It also has a home button that takes the user back to the main menu. - Jason
*/
class HelpPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // instance of holder class
    private CardLayout cards; // card instance

    public HelpPanel(FrenchBattleshipHolder ph, CardLayout cl) 
    {
        panelCards = ph;
        cards = cl;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBackground(Color.CYAN);

        // html for help text
        String helpText = "<html><div style='text-align: center; width: 700px;'>" +
            "Are some of the questions too hard for you? Do you feel like you are unable<br>" +
            "to solve a single problem even in easy mode? Well fear not, this is the help<br>" +
            "section where we will help you overcome your challenges.<br><br>" +
            "Firstly, try to figure out what the question is asking. A very prestigious study<br>" +
            "says that 99.99% of the time people answer questions wrong, they don't<br>" +
            "even know what the question means. So a very promising first step would<br>" +
            "probably to figure out what the question means.<br><br>" +
            "Can't figure out what the question means? Well maybe you can build on your<br>" +
            "previous knowledge. While you may not know what the question means you<br>" +
            "can probably figure out what a big part of the question means and guess the<br>" +
            "part that you don't know.<br><br>" +
            "If you still can't figure the question out remember that it is ok to ask for<br>" +
            "help or to search up the answer, just make sure not to make a habit of it." +
            "</div></html>";

        JLabel helpTextLabel = new JLabel(helpText);
        helpTextLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(helpTextLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.CYAN);
        JButton homeButton = new JButton("Back to Menu");
        
        HomeButtonListener hbl4 = new HomeButtonListener();
        homeButton.addActionListener(hbl4);
        buttonPanel.add(homeButton);

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // button to return home 
    class HomeButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "First");
        }
    }
}

/* 
* This is the highscores panel. It displays the highscores for the game.
* It also has a return button that takes the user back to the main menu. - Jason
*/
class HighScoresPanel extends JPanel
{
    private FrenchBattleshipHolder panelCards; // instance of holder class
    private CardLayout cards; // card instance
    private String[] highScores; // Array to store high scores
    private Information info; // instance of information class
    private JTextArea scoresArea; // Reference to the scores text area
    private String sortCriteria; // Current sort criteria

    public HighScoresPanel(FrenchBattleshipHolder ph, CardLayout cl) 
    {
        panelCards = ph;
        cards = cl;
        info = new Information();
        sortCriteria = "coins"; // Default sort by coins
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        // Create menu bar with sort options
        JMenuBar menuBar = new JMenuBar();
        JMenu sortMenu = new JMenu("Sort By");
        
        SortMenuListener sml = new SortMenuListener("coins");
        JMenuItem coinsItem = new JMenuItem("Coins");
        coinsItem.addActionListener(sml);
        sortMenu.add(coinsItem);
        
        SortMenuListener sml2 = new SortMenuListener("time");
        JMenuItem timeItem = new JMenuItem("Time");
        timeItem.addActionListener(sml2);
        sortMenu.add(timeItem);
        
        SortMenuListener sml3 = new SortMenuListener("powerups");
        JMenuItem powerupsItem = new JMenuItem("Powerups Used");
        powerupsItem.addActionListener(sml3);
        sortMenu.add(powerupsItem);
        
        menuBar.add(sortMenu);
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.CYAN);
        
        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(menuBar, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentPanel.setBackground(Color.CYAN);

        // Load high scores from file using polymorphic method
        highScores = info.loadFileContents("highScores.txt");

        // Create a JTextArea to display high scores
        scoresArea = new JTextArea(15, 30);
        scoresArea.setEditable(false);
        scoresArea.setFont(new Font("Arial", Font.PLAIN, 16));
        scoresArea.setBackground(Color.WHITE);
        scoresArea.setMargin(new Insets(10, 10, 10, 15));
        
        // Add each high score to the text area
        updateScoresDisplay();
        
        // Add the text area to a scroll pane
        JScrollPane scrollPane = new JScrollPane(scoresArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        contentPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.CYAN);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        ExitButtonListener ebl = new ExitButtonListener();
        exitButton.addActionListener(ebl);

        JButton homeButton = new JButton("Go to home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        HomeButtonListener hbl = new HomeButtonListener();
        homeButton.addActionListener(hbl);

        buttonPanel.add(exitButton);
        buttonPanel.add(homeButton);

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadHighScores() 
    {
        try 
        {
            // First count the number of lines
            Scanner countScanner = new Scanner(new File("highScores.txt"));
            int lineCount = 0;
            while (countScanner.hasNextLine()) 
            {
                countScanner.nextLine();
                lineCount++;
            }
            countScanner.close();

            // Initialize array with the correct size
            highScores = new String[lineCount];

            // Now read the actual scores
            Scanner scoreScanner = new Scanner(new File("highScores.txt"));
            int index = 0;
            while (scoreScanner.hasNextLine() && index < highScores.length) 
            {
                highScores[index] = scoreScanner.nextLine();
                index++;
            }
            
            scoreScanner.close();
            
            // Update the scores display
            updateScoresDisplay();
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error loading high scores: " + e);
            // Initialize with empty array to prevent null pointer exceptions
            highScores = new String[0];
        }
    }
    
    // Updates the scores display with current high scores
    public void updateScoresDisplay() 
    {
        // Sort the scores based on current criteria
        sortScores();
        
        // Clear the text area
        scoresArea.setText("");
        
        // Add each high score to the text area
        for (int i = 0; i < highScores.length; i++)
        {
            if (highScores[i] != null && highScores[i].trim().length() > 0) 
            {
                scoresArea.setText(scoresArea.getText() + highScores[i] + "\n");
            }
        }
    }
    
    // Sort the scores based on the current sort criteria
    public void sortScores()
    {
        if (highScores == null || highScores.length <= 1)
        {
            return; // Nothing to sort
        }
        
        // Bubble sort the scores
        for (int i = 0; i < highScores.length - 1; i++) 
        {
            for (int j = 0; j < highScores.length - i - 1; j++) 
            {
                if (highScores[j] != null && highScores[j+1] != null)
                {
                    int valueA = extractValue(highScores[j], sortCriteria);
                    int valueB = extractValue(highScores[j+1], sortCriteria);
                    
                    // Sort in descending order (higher values first)
                    if (valueA < valueB)
                    {
                        // Swap
                        String temp = highScores[j];
                        highScores[j] = highScores[j+1];
                        highScores[j+1] = temp;
                    }
                }
            }
        }
    }
    
    // Extract the value from a score string based on the criteria
    public int extractValue(String scoreString, String criteria)
    {
        if (criteria.equals("coins"))
        {
            // Format: "Name - X coins - Time: Y seconds - Powerups: Z"
            int coinStart = scoreString.indexOf(" - ") + 3;
            int coinEnd = scoreString.indexOf(" coins");
            if (coinStart >= 3 && coinEnd > coinStart)
            {
                String coinStr = scoreString.substring(coinStart, coinEnd);
                return Integer.parseInt(coinStr);
            }
        }
        else if (criteria.equals("time"))
        {
            // Extract time value
            int timeStart = scoreString.indexOf("Time: ") + 6;
            int timeEnd = scoreString.indexOf(" seconds");
            if (timeStart >= 6 && timeEnd > timeStart)
            {
                String timeStr = scoreString.substring(timeStart, timeEnd);
                return Integer.parseInt(timeStr);
            }
        }
        else if (criteria.equals("powerups"))
        {
            // Extract powerups value
            int powerupsStart = scoreString.indexOf("Powerups: ") + 10;
            if (powerupsStart >= 10 && powerupsStart < scoreString.length())
            {
                String powerupsStr = scoreString.substring(powerupsStart);
                return Integer.parseInt(powerupsStr);
            }
        }
        
        return 0; // Default if no valid value found
    }
    
    // Listener for sort menu items
    class SortMenuListener implements ActionListener
    {
        private String criteria;
        
        public SortMenuListener(String criteriaIn)
        {
            criteria = criteriaIn;
        }
        
        public void actionPerformed(ActionEvent evt)
        {
            sortCriteria = criteria;
            updateScoresDisplay();
        }
    }

    // button to return home 
    class ExitButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            System.exit(1);
        }
    }

    // listens for home button
    class HomeButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "First");
        }
    }
}

/* 
* This is the battleship layout panel. It allows the user to select a battleship layout.
* Inside this panel we can see an image of the grid and a reroll button. It also uses
* mouse listeners to handle the reroll button. It also has a continue button that
* takes the user to the French question panel. - Jeet
*/
class BattleshipLayoutPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // instance of holder class
    private Information info; // instance of information class
    private CardLayout cards; // card instance

    private Image gridImage; // image of the grid
    private Image rerollImage; // green recycle image

    private GridPanel gridPanel; // grid panel instance
    private RerollPanel rerollPanel; // reroll panel instance
    private TimerPanel timerPanel; // instance of the timer panel

    private int currentLayout; // current, blank grid = 0, random 1-15
    private boolean isFirstView; // is it a blank grid? (t/f)
    private Timer timer; // the timer
    private int seconds; // running seconds
    private JLabel timerLabel; // label of timer to display
    private JButton continueButton; // Continue button reference
    private JLabel gridLabel; // Added for the new ContinueButtonListener

    public BattleshipLayoutPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        info = infoIn;
        panelCards = ph;
        cards = cl;
        isFirstView = true;
        seconds = 0;

        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        // Initialize timer components
        timerPanel = new TimerPanel();
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerPanel.add(timerLabel);

        // Create timer that updates every second
        timer = info.getTimer();
        TimerListener tl = new TimerListener();
        timer.addActionListener(tl);

        try 
        {
            // Load the initial grid image
            gridImage = ImageIO.read(new File("Grid.png"));
            rerollImage = ImageIO.read(new File("Reroll.png"));
        } 
        catch (IOException e) 
        {
            System.out.println("Error loading images: " + e);
        }

        // Create the title at the top with timer
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.CYAN);

        JPanel titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabelPanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Choose a Battleship Layout");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabelPanel.add(titleLabel);

        titlePanel.add(titleLabelPanel, BorderLayout.CENTER);
        titlePanel.add(timerPanel, BorderLayout.EAST);

        // Create grid panel that paints the grid image
        gridPanel = new GridPanel();

        // Create the right side panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.CYAN);

        // Create "Swap arrangement" label
        JPanel swapLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        swapLabelPanel.setBackground(Color.CYAN);
        JLabel swapLabel = new JLabel("Swap arrangement");
        swapLabel.setFont(new Font("Arial", Font.BOLD, 24));
        swapLabelPanel.add(swapLabel);

        // Create reroll panel that paints the reroll image
        rerollPanel = new RerollPanel();
        RerollMouseListener rml = new RerollMouseListener();
        rerollPanel.addMouseListener(rml);

        JPanel rerollContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rerollContainer.setBackground(Color.CYAN);
        rerollContainer.add(rerollPanel);

        // Add components to right panel
        rightPanel.add(swapLabelPanel, BorderLayout.NORTH);
        rightPanel.add(rerollContainer, BorderLayout.CENTER);

        // Create bottom panel with Continue button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.CYAN);
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl5 = new ContinueButtonListener();
        continueButton.addActionListener(cbl5);
        bottomPanel.add(continueButton);

        // Add all panels to the main panel
        add(titlePanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Initialize current layout
        currentLayout = 0;

        // Added for the new ContinueButtonListener
        gridLabel = new JLabel();
        bottomPanel.add(gridLabel);
    }

    // Method to change the layout randomly - Jeet
    public void changeLayout()
    {
        if (isFirstView)
        {
            isFirstView = false;
            currentLayout = (int)(Math.random() * 15) + 1;
        }
        else 
        {
            // Generate a random number between 1 and 15
            int newLayout;
            do 
            {
                newLayout = (int)(Math.random() * 15) + 1;
            } 
            while (newLayout == currentLayout);

            currentLayout = newLayout;
        }

        // Use the existing info instance instead of creating a new one
        gridImage = info.loadImage("layout" + currentLayout + ".png");
        gridPanel.repaint();
    }

    // Grid panel class, draws the actual image
    class GridPanel extends JPanel
    {
        public GridPanel()
        {
            setBackground(Color.CYAN);
            setPreferredSize(new Dimension(500, 500));
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(gridImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Reroll panel class, draws the actual image
    class RerollPanel extends JPanel
    {
        public RerollPanel()
        {
            setBackground(Color.CYAN);
            setPreferredSize(new Dimension(100, 100));
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(rerollImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // listener for mouse. Four are not needed.
    class RerollMouseListener implements MouseListener
    {
        public void mouseClicked(MouseEvent evt)
        {
            changeLayout();
        }

        public void mousePressed(MouseEvent evt) {}

        public void mouseReleased(MouseEvent evt) {}

        public void mouseEntered(MouseEvent evt) {}

        public void mouseExited(MouseEvent evt) {}
    }

    // Continues onto the question panel
    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            // Save the user's selected layout
            info.setUserLayout(currentLayout);

            // Only continue if a layout has been selected
            if (currentLayout == 0 || gridImage.toString().contains("Grid.png")) 
            {
                // Display a message that layout needs to be selected
                gridLabel.setText("Please select a layout before continuing.");
                gridLabel.setForeground(Color.RED);
                return;
            }

            // Generate a random layout for the computer (1-15)
            int compLayout;
            do 
            {
                compLayout = (int)(Math.random() * 15) + 1;
            }
            while (compLayout == currentLayout); // Make sure it's different from user's

            info.setComputerLayout(compLayout);

            timer.start();
            cards.show(panelCards, "FrenchQuestion");
        }
    }

    /* This panel displays a black box with the timer text
     * It uses paintComponent to draw the background - Jason
     */
    class TimerPanel extends JPanel
    {
        public TimerPanel()
        {
            setBackground(Color.CYAN);
            setPreferredSize(new Dimension(150, 40));
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /* This listener updates the timer text every second
     * It increments the seconds counter and updates the label - Jason
     */
    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        }
    }
}

/* 
 * FrenchQuestionPanel displays French questions and evaluates user answers
 * - Jeet
 */
class FrenchQuestionPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // instance of holder class
    private Information info; // instance of information class
    private CardLayout cards; // cards instance
    private TimerPanel timerPanel; // instance of the timer panel
    private ComputersTurnPanel ctp; // instance of computers turn

    private JTextField answerField; // answer field for user
    private String correctAnswer; // String of the correct answer
    private JTextArea feedbackArea; // the feedback to give back to user
    private Timer timer; // timer
    private int seconds; // running seconds
    private JLabel timerLabel; // label of running timer
    private JButton continueButton;

    private String[] questions; // array of questions
    private String[] answers; // array of answers
    private int currentQuestionIndex; // a random number index
    private JLabel questionLabel; // label that displays the question
    private boolean[] usedQuestions; // track which questions have been used

    public FrenchQuestionPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn, ComputersTurnPanel ctpIn) 
    {
        info = infoIn;
        panelCards = ph;
        cards = cl;
        ctp = ctpIn;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        correctAnswer = "comment";
        seconds = 0;

        // Initialize timer components
        timerPanel = new TimerPanel();
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerPanel.add(timerLabel);

        // Create coin label and add it to top panel
        JLabel coinsLabel = new JLabel(info.getCoins() + " coins");
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        coinsLabel.setForeground(Color.BLACK);
        info.setLabel(coinsLabel);

        // Create timer that updates every second
        timer = info.getTimer();
        timer.addActionListener(new TimerListener());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);

        JPanel coinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        coinPanel.setBackground(Color.CYAN);
        coinPanel.add(coinsLabel);
        topPanel.add(coinPanel, BorderLayout.WEST);

        /// lots of junk to fix & organize later.
        JPanel questionPanel = new JPanel();
        questionPanel.setBackground(Color.CYAN);
        questionLabel = new JLabel("Fill in the blank: Savez vous __________");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 36));
        questionPanel.add(questionLabel);

        topPanel.add(questionPanel, BorderLayout.CENTER);
        topPanel.add(timerPanel, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));
        centerPanel.setBackground(Color.CYAN);

        JPanel answerPanel = new JPanel();
        answerPanel.setBackground(Color.CYAN);
        JLabel answerLabel = new JLabel("Your Answer: ");
        answerLabel.setFont(new Font("Arial", Font.BOLD, 36));
        answerPanel.add(answerLabel);
        answerField = new JTextField(20);
        answerPanel.add(answerField);
        centerPanel.add(answerPanel);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setBackground(Color.CYAN);
        feedbackArea = new JTextArea(2, 30);
        feedbackArea.setEditable(false);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setLineWrap(true);
        feedbackArea.setFont(new Font("Arial", Font.BOLD, 24));
        feedbackArea.setBackground(Color.CYAN);
        feedbackArea.setBorder(null);
        feedbackArea.setMargin(new Insets(5, 10, 5, 10));
        feedbackPanel.add(feedbackArea);
        centerPanel.add(feedbackPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.CYAN);

        JButton checkAnswerButton = new JButton("Check Answer");
        checkAnswerButton.setFont(new Font("Arial", Font.BOLD, 20));
        CheckAnswerButtonListener cabl = new CheckAnswerButtonListener();
        checkAnswerButton.addActionListener(cabl);
        buttonPanel.add(checkAnswerButton);

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl6 = new ContinueButtonListener();
        continueButton.addActionListener(cbl6);
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadQuestionsAndAnswers();
        usedQuestions = new boolean[questions.length]; // initialize tracking array
        loadQuestion();
    }

  /* Listener and Handler class when the user clicks the JButton, 
   * Check Answer. If it is correct, then they get coins and get to 
   * attack. If they are wrong, it tells them the right answer. 
   * (add more on WHY the user is wrong later) 
   */
    class CheckAnswerButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            String userAnswer = answerField.getText().trim().toLowerCase();
            if (userAnswer.equals(correctAnswer.trim().toLowerCase())) 
            {
                feedbackArea.setText("Your answer was correct! You get 50 coins.");
                feedbackArea.setForeground(Color.GREEN);
                info.updateCoins(50);
            } 
            else 
            {
                feedbackArea.setText("Incorrect. The correct answer was \"" + correctAnswer + "\"");
                feedbackArea.setForeground(Color.RED);
            }

            answerField.setEditable(false); // so user cant change after wrong
            continueButton.setEnabled(true); // allow user to continue
        }
    }


    /* Continues onto the AttackPanel */ 
    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            String userAnswer = answerField.getText().toLowerCase();
            if (userAnswer.equals(correctAnswer.toLowerCase())) 
            {
                feedbackArea.setText("Your answer was correct. You get 50 coins and a chance to attack.");
                feedbackArea.setForeground(Color.GREEN);
                loadQuestion();
                cards.show(panelCards, "Attack");
            } 
            else 
            {
                feedbackArea.setText("Next question!");
                feedbackArea.setForeground(Color.RED);
                loadQuestion();
                cards.show(panelCards, "ComputersTurn");
                System.out.println("hiii");
                ctp.performComputerAttack();
            }
            
            answerField.setEditable(true); // user can type again
        }
    }

    /* This panel displays a black box with the timer text
     * It uses paintComponent to draw the background - Jason
     */
    public class TimerPanel extends JPanel
    {
        public TimerPanel()
        {
            setBackground(Color.CYAN);
            setPreferredSize(new Dimension(150, 40));
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /* This listener updates the timer text every second
     * It increments the seconds counter and updates the label - Jason
     */
    public class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            seconds++;
            info.setSeconds(seconds); // Update the seconds in the info class
            timerLabel.setText("Time: " + seconds + "s");
        }
    }

  /* reads in the questions and answers from the files */
    public void loadQuestionsAndAnswers() 
    {
        // Load questions and answers using polymorphic method
        questions = info.loadFileContents("questions.txt");
        answers = info.loadFileContents("answers.txt");
    }

  // loads one question at a time and updates the labels
    public void loadQuestion() 
    {
        if (questions.length > 0) // so an error doesnt happen 
        {
            // Check if all questions have been used
            boolean allUsed = true;
            for (int i = 0; i < usedQuestions.length; i++) 
            {
                boolean used = usedQuestions[i];
                if (!used) 
                {
                    allUsed = false;
                    break;
                }
            }
            
            // If all questions used, reset the tracking
            if (allUsed) 
            {
                for (int i = 0; i < usedQuestions.length; i++) 
                {
                    usedQuestions[i] = false;
                }
            }
            
            // Keep trying until we find an unused question
            do 
            {
                currentQuestionIndex = (int)(Math.random() * questions.length);
            } 
            while (usedQuestions[currentQuestionIndex]);
            
            // Mark this question as used
            usedQuestions[currentQuestionIndex] = true;
            
            String question = questions[currentQuestionIndex];
            correctAnswer = answers[currentQuestionIndex];

            // Update the question label
            questionLabel.setText(question);
            feedbackArea.setText(""); // Clear previous feedback
        }
    }
}


/* 
* This is the Attack Panel, where the user is able to choose a point to 
* attack, as well as access thre shop. If the user buys something from the
* shop, it will show "[shop item] activated" on the attack page.  - Jason
* and Jeet
*/
class AttackPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // holder instance
    private Information info; // instance of info class
    private CardLayout cards; // card instance
    private TimerPanel timerPanel; // timer instance

    private Timer timer; // the timer
    private Timer radarTimer;
    private RadarTimerActionListener rtal;
    private int seconds; // running seconds
    private JLabel timerLabel; // label of running timer
    private JButton continueButton; // continues to the next page
    private boolean hasClicked; // only continue if a grid segment is clicked
    private JButton[][] gridButtons; // 2d array for the grid + buttons
    private JLabel statusLabel; // displays hit/miss status
    private int radarRow;
    private int radarCol;

    public AttackPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        panelCards = ph;
        cards = cl;
        info = infoIn;
        hasClicked = false;

        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        seconds = 0;

        // Initialize timer components
        timerPanel = new TimerPanel();
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setForeground(Color.WHITE);
        timerPanel.add(timerLabel);

        // Create timer that updates every second
        timer = info.getTimer();
        TimerListener tl = new TimerListener();
        timer.addActionListener(tl);

        // Create the title panel with timer
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);

        // Add title label
        JLabel titleLabel = new JLabel("Choose a coordinate to attack");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add timer and title to top panel
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.add(timerPanel, BorderLayout.EAST);

        // Create grid panel with 15x15 buttons
        JPanel gridPanel = new JPanel(new GridLayout(15, 15, 2, 2));
        gridPanel.setBackground(Color.CYAN);
        gridButtons = new JButton[15][15];

        for(int i = 0; i < 15; i++) 
        {
            for(int j = 0; j < 15; j++) 
            {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setPreferredSize(new Dimension(FirstPagePanel.getGridButtonSize(), FirstPagePanel.getGridButtonSize()));
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                GridButtonListener gbl = new GridButtonListener(i, j);
                gridButtons[i][j].addActionListener(gbl);
                // Enable button if it hasn't been hit yet
                gridButtons[i][j].setEnabled(!info.isHit(i, j));
                gridPanel.add(gridButtons[i][j]);
            }
        }

        // Create center panel to hold the grid and status label
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.CYAN);

        // Add status label
        statusLabel = new JLabel("Select a grid position to attack");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add grid panel and status label to center panel
        centerPanel.add(gridPanel, BorderLayout.CENTER);
        centerPanel.add(statusLabel, BorderLayout.SOUTH);

        // Create bottom panel with Continue and Shop buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonPanel.setBackground(Color.CYAN);

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl7 = new ContinueButtonListener();
        continueButton.addActionListener(cbl7);
        continueButton.setEnabled(false);
        buttonPanel.add(continueButton);

		ShopButtonListener sbl = new ShopButtonListener();
        JButton shopButton = new JButton("Shop");
        shopButton.setFont(new Font("Arial", Font.BOLD, 20));
        shopButton.addActionListener(sbl);
        buttonPanel.add(shopButton);

        // Add all panels to the main layout
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Update status label to show remaining attacks if triple attack is active
        if (info.isTripleAttackActive()) 
        {
            statusLabel.setText("Triple Attack Active! You have " + info.getRemainingAttacks() + " attacks remaining");
            statusLabel.setForeground(Color.GREEN);
            
            // Make sure all unclicked buttons are enabled for triple attack
            for(int i = 0; i < 15; i++) 
            {
                for(int j = 0; j < 15; j++) 
                {
                    if (!info.isHit(i, j)) 
                    {
                        gridButtons[i][j].setEnabled(true);
                    }
                }
            }
        }
    }

    /* Listens when a button in the grid is clicked */
    class GridButtonListener implements ActionListener 
    {
        private int row, col; // rows and columns of our 15x15 grid

        public GridButtonListener(int clickedRow, int clickedCol) 
        {
            row = clickedRow;
            col = clickedCol;
        }

        // Handles grid button clicks
        public void actionPerformed(ActionEvent evt) 
        {
            // Check if this position has already been hit
            if (info.isHit(row, col)) 
            {
                statusLabel.setText("You've already attacked this position!");
                return;
            }

            // Handle power-ups
            if (info.isRadarActive()) 
            {
                handleRadarScan(row, col);
                info.setRadarActive(false);
                hasClicked = true;
                continueButton.setEnabled(true);
                return;
            }

            if (info.isTorpedoActive()) 
            {
                handleTorpedoAttack();
                info.setTorpedoActive(false);
                hasClicked = true;
                continueButton.setEnabled(true);
                return;
            }

            if (info.isAirstrikeActive()) 
            {
                handleAirstrike();
                info.setAirstrikeActive(false);
                hasClicked = true;
                continueButton.setEnabled(true);
                return;
            }

            // Regular attack or triple attack
            handleRegularAttack();
        }

		// handles the 3x3 radar 
        public void handleRadarScan(int row, int col) 
        {
            // Stop any existing timer
            if (radarTimer != null) 
            {
                radarTimer.stop();
            }

            // Store the radar scan center position
            radarRow = row;
            radarCol = col;

            // Create new timer if not already created
            if (rtal == null) 
            {
                rtal = new RadarTimerActionListener();
            }
            radarTimer = new Timer(10000, rtal);

            // Check 3x3 area around clicked position
            boolean shipsFound = false;
            String detectionMessage = "Radar Scan Results:\n";
            
            for (int i = row - 1; i <= row + 1; i++) 
            {
                for (int j = col - 1; j <= col + 1; j++) 
                {
                    if (i >= 0 && i < 15 && j >= 0 && j < 15) 
                    {
                        if (info.isShipAt(i, j) && !info.isHit(i, j)) 
                        {
                            gridButtons[i][j].setBackground(Color.YELLOW);
                            detectionMessage += "Ship detected at (" + (i + 1) + "," + (j + 1) + ")\n";
                            shipsFound = true;
                        }
                    }
                }
            }

            // Update status and start timer if ships were found
            if (shipsFound) 
            {
                statusLabel.setText(detectionMessage);
                statusLabel.setForeground(Color.YELLOW);
                radarTimer.start();
            } 
            else
            {
                statusLabel.setText("No ships detected in this area.");
                statusLabel.setForeground(Color.WHITE);
            }
        }

		// handles the torpedo attack, attacking the entire horizontal row
        private void handleTorpedoAttack() 
        {
            String result = "Torpedo Attack Results:\n";
            int hits = 0;
            
            // Attack entire row
            for (int j = 0; j < 15; j++) 
            {
                if (!info.isHit(row, j)) 
                {
                    info.recordHit(row, j);
                    JButton buttonToModify = gridButtons[row][j];
                    
                    if (info.isShipAt(row, j)) 
                    {
                        buttonToModify.setBackground(Color.RED);
                        buttonToModify.setForeground(Color.RED);
                        buttonToModify.setText("HIT");
                        hits++;
                        result = result + "Hit at (" + (row + 1) + "," + (j + 1) + ")\n";
                    } 
                    else 
                    {
                        buttonToModify.setBackground(Color.BLUE);
                        buttonToModify.setForeground(Color.BLUE);
                        buttonToModify.setText("MISS");
                        result = result + "Miss at (" + (row + 1) + "," + (j + 1) + ")\n";
                    }
                }
            }
            
            result = result + "Total hits: " + hits;
            statusLabel.setText(result);
            statusLabel.setForeground(Color.ORANGE);
        }

		// handles the airstrike, 5 random positions
        private void handleAirstrike() 
        {
            String result = "Airstrike Results:\n";
            int hits = 0;
            int attacks = 0;
            boolean[][] attacked = new boolean[15][15];
            
            // Attack 5 random positions
            while (attacks < 5) 
            {
                // Use simple random number generation
                int r = (int)(Math.random() * 15);
                int c = (int)(Math.random() * 15);
                
                if (!attacked[r][c] && !info.isHit(r, c)) 
                {
                    attacked[r][c] = true;
                    info.recordHit(r, c);
                    JButton buttonToModify = gridButtons[r][c];
                    
                    if (info.isShipAt(r, c)) 
                    {
                        buttonToModify.setBackground(Color.RED);
                        buttonToModify.setForeground(Color.RED);
                        buttonToModify.setText("HIT");
                        hits++;
                        result = result + "Hit at (" + (r + 1) + "," + (c + 1) + ")\n";
                    } 
                    else 
                    {
                        buttonToModify.setBackground(Color.BLUE);
                        buttonToModify.setForeground(Color.BLUE);
                        buttonToModify.setText("MISS");
                        result = result + "Miss at (" + (r + 1) + "," + (c + 1) + ")\n";
                    }
                    attacks++;
                }
            }
            
            result = result + "Total hits: " + hits;
            statusLabel.setText(result);
            statusLabel.setForeground(Color.MAGENTA);
        }

		// regular attack
        private void handleRegularAttack() 
        {
            // Record this hit
            info.recordHit(row, col);
            JButton buttonToModify = gridButtons[row][col];

            // Check if there's a ship at this position
            if (info.isShipAt(row, col)) 
            {
                buttonToModify.setBackground(Color.RED);
                buttonToModify.setForeground(Color.RED);
                buttonToModify.setText("HIT");
                if (!info.isTripleAttackActive()) 
                {
					statusLabel.setText("HIT! You found a ship at position (" + (row+1) + "," + (col+1) + ")!");
                }
                statusLabel.setForeground(Color.RED);
            } 
            else 
            {
                buttonToModify.setBackground(Color.BLUE);
                buttonToModify.setForeground(Color.BLUE);
                buttonToModify.setText("MISS");
                if (!info.isTripleAttackActive()) 
                {
					statusLabel.setText("MISS! No ship at position (" + (row+1) + "," + (col+1) + ")");
                }
                statusLabel.setForeground(Color.BLUE);
            }

            // Handle triple attack logic
            if (info.isTripleAttackActive()) 
            {
                info.decrementRemainingAttacks();
                int remaining = info.getRemainingAttacks();
                
                if (remaining > 0) 
                {
                    // Still have attacks remaining
                    statusLabel.setText("Triple Attack activated! You have " + remaining + " attacks remaining!");
                    statusLabel.setForeground(Color.GREEN);
                    hasClicked = false; // Allow more attacks
                    return; // Don't enable continue button yet
                } 
                else 
                {
                    // No more attacks, end triple attack
                    info.setTripleAttackActive(false);
                    // Disable all buttons after triple attack is done
                    for(int i = 0; i < 15; i++) 
                    {
                        for(int j = 0; j < 15; j++)
                        {
                            gridButtons[i][j].setEnabled(false);
                        }
                    }
                }
            }
            else 
            {
                // Regular attack - disable all buttons after click
                for(int i = 0; i < 15; i++) 
                {
                    for(int j = 0; j < 15; j++) 
                    {
                        gridButtons[i][j].setEnabled(false);
                    }
                }
            }

            hasClicked = true;
            continueButton.setEnabled(true);
        }
    }

    // continues onto the next panel
    class ContinueButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt) 
        {
            if(hasClicked) 
            {
                // Check for win condition first
                if (info.areAllComputerShipsSunk()) 
                {
                    cards.show(panelCards, "Win");
                    return;
                }

                // Reset clicked state for next turn
                hasClicked = false;
                continueButton.setEnabled(false);

                // Enable all unclicked buttons for next turn
                for(int i = 0; i < 15; i++) 
                {
                    for(int j = 0; j < 15; j++) 
                    {
                        if (!info.isHit(i, j)) 
                        {
                            gridButtons[i][j].setEnabled(true);
                        }
                    }
                }

                // Move to computer's turn
                cards.show(panelCards, "ComputersTurn");
            }
        }
    }

    // goes to the shop
    class ShopButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "Shop");
        }
    }

    // panel where the timer is placed in
    class TimerPanel extends JPanel
    {
        public TimerPanel()
        {
            setBackground(Color.CYAN);
            setPreferredSize(new Dimension(150, 40));
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // listens for our timer
    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        }
    }

    class RadarTimerActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            // Reset color of highlighted buttons in the 3x3 area
            for (int i = radarRow - 1; i <= radarRow + 1; i++) 
            {
                for (int j = radarCol - 1; j <= radarCol + 1; j++) 
                {
                    if (i >= 0 && i < 15 && j >= 0 && j < 15) 
                    {
                        if (gridButtons[i][j].getBackground() == Color.YELLOW)
                        {
                            gridButtons[i][j].setBackground(Color.WHITE);
                        }
                    }
                }
            }
            radarTimer.stop();
        }
    }
}

/* 
* ShopPanel can be accessed from the Attack Panel. The user can buy items 
* of their choice to help with the attacking of the battleships. - Jeet
* and Jason
*/
class ShopPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // holder instance
    private Information info; // info instance
    private CardLayout cards; // card instance

    private Timer timer; // timer
    private int seconds; // running seconds 
    private JLabel timerLabel; // label to display running timer
    private Image backgroundImage; // background iamge in the shop (null)
    private Image radarImage; // image of radar for 3x3 radar
    private Image planeImage; // image of plane for airstrike
    private Image tripleImage; // image of 3 for triple attack
    private Image torpedoImage; // image of torpedo for torpedo attack
    private Image coinImage; // image of coin
    private JCheckBox radarCheckBox; // checkbox to buy
    private JCheckBox airstrikeCheckBox; // checkbox to buy
    private JCheckBox tripleAttackCheckBox; // checkbox to buy
    private JCheckBox torpedoCheckBox; // checkbox to buy
    private JButton buyButton; // buy & return to attack
    private JButton continueButton; // continue button to next page
    private JLabel statusLabel; // Add status label field

    public ShopPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        info = infoIn;
        panelCards = ph;
        cards = cl;
        setLayout(null); // Using absolute positioning to match the image exactly

        seconds = 0;

        // Initialize status label
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBounds(370, 480, 260, 30);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(statusLabel);

        // Load all images
        backgroundImage = info.loadImage("ShopBg.png");
        radarImage = info.loadImage("radar.png");
        planeImage = info.loadImage("plane.jpg");
        tripleImage = info.loadImage("three.png");
        torpedoImage = info.loadImage("torpedo.png");
        coinImage = info.loadImage("coin.jpg");

        // Create the timer display in top right
        timerLabel = new JLabel("Time: " + seconds + "s");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBackground(Color.BLACK);
        timerLabel.setOpaque(true);
        timerLabel.setBounds(852, 30, 150, 40);
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        add(timerLabel);

        // Create timer that updates every second
        timer = info.getTimer();
        TimerListener tl = new TimerListener();
        timer.addActionListener(tl);

        // Create title label (without white background)
        JLabel titleLabel = new JLabel("The Battle-Shop");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(320, 80, 400, 60);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel);

        // Create right coin display and use the existing coin label from info
        JLabel coinsDisplay = info.getLabel();
        coinsDisplay.setForeground(Color.WHITE);
        coinsDisplay.setBounds(740, 80, 160, 60);
        add(coinsDisplay);

        // Scale images to smaller size
        int imageWidth = 160;
        int imageHeight = 160;

        // Create item labels with white text
        JLabel radarLabel = new JLabel("3x3 radar");
        radarLabel.setFont(new Font("Arial", Font.BOLD, 28));
        radarLabel.setForeground(Color.WHITE);
        radarLabel.setBounds(60, 170, 200, 40);
        add(radarLabel);

        JLabel airstrikeLabel = new JLabel("Airstrike");
        airstrikeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        airstrikeLabel.setForeground(Color.WHITE);
        airstrikeLabel.setBounds(300, 170, 200, 40);
        add(airstrikeLabel);

        JLabel tripleAttackLabel = new JLabel("Triple Attack");
        tripleAttackLabel.setFont(new Font("Arial", Font.BOLD, 28));
        tripleAttackLabel.setForeground(Color.WHITE);
        tripleAttackLabel.setBounds(520, 170, 250, 40);
        add(tripleAttackLabel);

        JLabel torpedoLabel = new JLabel("Torpedo");
        torpedoLabel.setFont(new Font("Arial", Font.BOLD, 28));
        torpedoLabel.setForeground(Color.WHITE);
        torpedoLabel.setBounds(760, 170, 200, 40);
        add(torpedoLabel);

        // Create image labels for each item
        JLabel radarImageLabel = new JLabel();

        radarImageLabel.setBounds(40, 220, imageWidth, imageHeight);
        add(radarImageLabel);

        JLabel planeImageLabel = new JLabel();

        planeImageLabel.setBounds(280, 220, imageWidth, imageHeight);
        add(planeImageLabel);

        JLabel tripleImageLabel = new JLabel();

        tripleImageLabel.setBounds(520, 220, imageWidth, imageHeight);
        add(tripleImageLabel);

        JLabel torpedoImageLabel = new JLabel();

        torpedoImageLabel.setBounds(760, 220, imageWidth, imageHeight);
        add(torpedoImageLabel);

        // Add cost labels directly (without panels)
        JLabel radarCostLabel = createCoinLabel(400);
        radarCostLabel.setForeground(Color.WHITE);
        radarCostLabel.setBounds(40, 400, 160, 40);
        add(radarCostLabel);

        JLabel airstrikeCostLabel = createCoinLabel(800);
        airstrikeCostLabel.setForeground(Color.WHITE);
        airstrikeCostLabel.setBounds(280, 400, 160, 40);
        add(airstrikeCostLabel);

        JLabel tripleCostLabel = createCoinLabel(400);
        tripleCostLabel.setForeground(Color.WHITE);
        tripleCostLabel.setBounds(520, 400, 160, 40);
        add(tripleCostLabel);

        JLabel torpedoCostLabel = createCoinLabel(2000);
        torpedoCostLabel.setForeground(Color.WHITE);
        torpedoCostLabel.setBounds(760, 400, 160, 40);
        add(torpedoCostLabel);

        // Add checkboxes
        radarCheckBox = new JCheckBox();
        radarCheckBox.setBounds(100, 450, 30, 30);
        radarCheckBox.setOpaque(false);
        add(radarCheckBox);

        airstrikeCheckBox = new JCheckBox();
        airstrikeCheckBox.setBounds(340, 450, 30, 30);
        airstrikeCheckBox.setOpaque(false);
        add(airstrikeCheckBox);

        tripleAttackCheckBox = new JCheckBox();
        tripleAttackCheckBox.setBounds(580, 450, 30, 30);
        tripleAttackCheckBox.setOpaque(false);
        add(tripleAttackCheckBox);

        torpedoCheckBox = new JCheckBox();
        torpedoCheckBox.setBounds(820, 450, 30, 30);
        torpedoCheckBox.setOpaque(false);
        add(torpedoCheckBox);

        // Add buttons directly (without panels)
        buyButton = new JButton("Buy and continue");
        buyButton.setFont(new Font("Arial", Font.BOLD, 20));
        BuyButtonListener bbl = new BuyButtonListener();
        buyButton.addActionListener(bbl);
        buyButton.setBounds(370, 520, 260, 50);
        add(buyButton);

        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl8 = new ContinueButtonListener();
        continueButton.addActionListener(cbl8);
        continueButton.setBounds(370, 590, 260, 50);
        add(continueButton);
    }

    /* Creates a coin label with the specified amount
     * Uses the coin image and formats the text - Jason
     */
    public JLabel createCoinLabel(int amount)
    {
        JLabel label = new JLabel(amount + " coins");

        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

    }

    /* Handles the Buy and Continue button
     * Purchases selected items and moves to the next panel - Jeet
     */
    class BuyButtonListener implements ActionListener
    {
        private Timer messageTimer;
        private TimerActionListener tal;

        public BuyButtonListener()
        {
            tal = new TimerActionListener();
            messageTimer = new Timer(5000, tal);
        }

        class TimerActionListener implements ActionListener
        {
            public void actionPerformed(ActionEvent evt)
            {
                statusLabel.setText("");
                messageTimer.stop();
            }
        }

        public void actionPerformed(ActionEvent evt)
        {
            // Calculate total cost
            int totalCost = 0;
            if (tripleAttackCheckBox.isSelected()) 
            {
                totalCost += info.getPowerupCost("tripleAttack");
            }
            if (radarCheckBox.isSelected()) 
            {
                totalCost += info.getPowerupCost("radar");
            }
            if (torpedoCheckBox.isSelected()) 
            {
                totalCost += info.getPowerupCost("torpedo");
            }
            if (airstrikeCheckBox.isSelected()) 
            {
                totalCost += info.getPowerupCost("airstrike");
            }

            // Check if player has enough coins
            if (info.hasEnoughCoins(totalCost)) 
            {
                // Deduct cost and activate power-ups
                info.updateCoins(-totalCost);

                if (tripleAttackCheckBox.isSelected()) 
                {
                    info.setTripleAttackActive(true);
                    info.setRemainingAttacks(3);
                }
                if (radarCheckBox.isSelected()) 
                {
                    info.setRadarActive(true);
                }
                if (torpedoCheckBox.isSelected()) 
                {
                    info.setTorpedoActive(true);
                }
                if (airstrikeCheckBox.isSelected()) 
                {
                    info.setAirstrikeActive(true);
                }

                // Return to attack panel
                cards.show(panelCards, "Attack");
            } 
            else 
            {
                // Show error message in status label and start timer
                statusLabel.setText("Not enough coins!");
                statusLabel.setForeground(Color.RED);
                messageTimer.stop();
                messageTimer.start();
            }
        }
    }

    /* This listener updates the timer text every second
     * It increments the seconds counter and updates the label - Jason
     */
    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            seconds++;
            timerLabel.setText("Time: " + seconds + "s");
        }
    }

    /* Handles the Continue button
     * Skips purchasing and moves to the next panel - Jeet
     */
    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            cards.show(panelCards, "Attack");
        }
    }
}

class YouLosePanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // holder instance 
    private CardLayout cards; // card instance
    private Image bgImage; // background image to be added later
    private Information info; // information class instance

    public YouLosePanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        panelCards = ph;
        cards = cl;
        info = infoIn;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());

        bgImage = info.loadImage("BattleshipBg.jpg");

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("You lose!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(6, 1, 10, 10));
        contentPanel.setBackground(Color.CYAN);

        // html to display the curated message
        String message = "<html><div style='text-align: center; width: 700px;'>" +
            "Due to a combination of factors you seem to have lost. But don't give up, to<br>" +
            "succeed you need to fail, and sometimes you need to fail a lot of times.</div></html>";
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel tipsLabel = new JLabel("Some tips:");
        tipsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tipsLabel.setHorizontalAlignment(JLabel.CENTER);

        String tip1 = "• Play it like a normal game of battleship but with questions";
        String tip2 = "• Save up your money for extra powerful power-ups in the Battle-Shop";
        String tip3 = "• Refer to the help section to get some extra help on answering questions";
        String tip4 = "• Practice! Practice! Practice!";

        JLabel tip1Label = new JLabel(tip1);
        JLabel tip2Label = new JLabel(tip2);
        JLabel tip3Label = new JLabel(tip3);
        JLabel tip4Label = new JLabel(tip4);

        tip1Label.setFont(new Font("Arial", Font.PLAIN, 16));
        tip2Label.setFont(new Font("Arial", Font.PLAIN, 16));
        tip3Label.setFont(new Font("Arial", Font.PLAIN, 16));
        tip4Label.setFont(new Font("Arial", Font.PLAIN, 16));

        tip1Label.setHorizontalAlignment(JLabel.CENTER);
        tip2Label.setHorizontalAlignment(JLabel.CENTER);
        tip3Label.setHorizontalAlignment(JLabel.CENTER);
        tip4Label.setHorizontalAlignment(JLabel.CENTER);

        contentPanel.add(messageLabel);
        contentPanel.add(tipsLabel);
        contentPanel.add(tip1Label);
        contentPanel.add(tip2Label);
        contentPanel.add(tip3Label);
        contentPanel.add(tip4Label);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.CYAN);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        ExitButtonListener ebl = new ExitButtonListener();
        exitButton.addActionListener(ebl);

        JButton homeButton = new JButton("Go to home");
        homeButton.setFont(new Font("Arial", Font.BOLD, 20));
        homeButton.addActionListener(new HomeButtonListener());

        buttonPanel.add(exitButton);
        buttonPanel.add(homeButton);

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    


    // exits the program if the user clicks exit. 
    class ExitButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            System.exit(1);
            /// maybe in the future: a rectangle pops up, saying, "are you
            /// sure you want to exit?" so the user doesnt lose progress.
        }
    }

    // listens for home button
    class HomeButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            // Reset the game state
            info.resetGame();
            
            cards.show(panelCards, "First");
        }
    }
}

/* 
 * ComputersTurnPanel handles the computer's turn when the player answers incorrectly.
 * It shows a random attack on the player's grid. - Jason
 */
class ComputersTurnPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards; // holder instance
    private CardLayout cards; // card instance
    private Information info; // information class instance
    private JLabel statusLabel; // need to update, fv
    private JButton[][] gridButtons; // need to update, fv
    private boolean hasAttacked; // true false if user has attacked yet
    private JPanel statusPanel; // need to update, fv
    private JPanel bottomPanel; // need to update, fv
    private JButton continueButton; // continue button reference

    public ComputersTurnPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        panelCards = ph;
        cards = cl;
        info = infoIn;
        hasAttacked = false;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Computer's Turn");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);
        
        // Create grid panel
        JPanel gridPanel = new JPanel(new GridLayout(15, 15, 2, 2));
        gridPanel.setBackground(Color.CYAN);
        gridButtons = new JButton[15][15];
        
        // Initialize grid buttons
        for(int i = 0; i < 15; i++) 
        {
            for(int j = 0; j < 15; j++) 
            {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setPreferredSize(new Dimension(FirstPagePanel.getGridButtonSize(), FirstPagePanel.getGridButtonSize()));
                gridButtons[i][j].setBackground(Color.WHITE);
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(gridButtons[i][j]);
            }
        }
        
        // Create bottom panel to hold status and continue button
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.CYAN);
        
        // Create status panel
        statusPanel = new JPanel();
        statusPanel.setBackground(Color.CYAN);
        statusLabel = new JLabel("Computer is taking its turn...");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusPanel.add(statusLabel);
        
        // Create continue button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.CYAN);
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl9 = new ContinueButtonListener();
        continueButton.addActionListener(cbl9);
        buttonPanel.add(continueButton);
        
        // Add status and button panels to bottom panel
        bottomPanel.add(statusPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add components to main panel
        add(gridPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Perform computer's attack when panel is shown
        performComputerAttack();
    }
    
    public void performComputerAttack() 
    {
		// Generate random coordinates that haven't been attacked yet
		int row, col;
		do 
		{
			row = (int)(Math.random() * 15);
			col = (int)(Math.random() * 15);
		} while (info.isComputerHit(row, col));
		
		// Record the hit
		info.recordComputerHit(row, col);
		
		// Check if there's a ship at this position
		if (info.isUserShipAt(row, col)) 
		{
			// Hit a ship
			gridButtons[row][col].setBackground(Color.RED);
			gridButtons[row][col].setForeground(Color.RED);
			gridButtons[row][col].setText("HIT");
			statusLabel.setText("HIT! Computer found your ship at position (" + (row+1) + "," + (col+1) + ")!");
			statusLabel.setForeground(Color.RED);
		} 
		else 
		{
			// Missed
			gridButtons[row][col].setBackground(Color.BLUE);
			gridButtons[row][col].setForeground(Color.BLUE);
			gridButtons[row][col].setText("MISS");
			statusLabel.setText("MISS! Computer attacked position (" + (row+1) + "," + (col+1) + ")");
			statusLabel.setForeground(Color.BLUE);
		}
		
		hasAttacked = true;
		
		// Enable the continue button
		continueButton.setEnabled(true);
        
    }
    
    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
			System.out.println("Je suis button");
			System.out.println("Je ne sais pas");
			System.out.println(info.areAllUserShipsSunk());
			// Check for lose condition
			if (info.areAllUserShipsSunk()) 
			{
				cards.show(panelCards, "YouLose");
			}
			else
			{
				// Reset attack state
				hasAttacked = false;
				
				// Move to French question panel
				cards.show(panelCards, "FrenchQuestion");
			} 
        }
        
    }
}

/* 
 * WinPanel displays when the player wins by sinking all computer ships.
 * It allows entering a name for the high score list. - Jeet
 */
class WinPanel extends JPanel 
{
    private FrenchBattleshipHolder panelCards;
    private CardLayout cards;
    private Information info;
    private JTextField nameField;
    
    public WinPanel(FrenchBattleshipHolder ph, CardLayout cl, Information infoIn) 
    {
        panelCards = ph;
        cards = cl;
        info = infoIn;
        setBackground(Color.CYAN);
        setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.CYAN);
        JLabel titleLabel = new JLabel("Victory!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titlePanel.add(titleLabel);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1, 10, 10));
        contentPanel.setBackground(Color.CYAN);
        
        String message = "<html><div style='text-align: center; width: 700px;'>" +
            "Congratulations! You've sunk all the computer's ships!<br>" +
            "Enter your name below to be added to the high scores.</div></html>";
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(Color.CYAN);
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        
        String scoreMessage = "<html><div style='text-align: center; width: 700px;'>" +
            "Your final score: " + info.getCoins() + " coins</div></html>";
        JLabel scoreLabel = new JLabel(scoreMessage);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        
        contentPanel.add(messageLabel);
        contentPanel.add(namePanel);
        contentPanel.add(scoreLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.CYAN);
        
        JButton continueButton = new JButton("Continue to High Scores");
        continueButton.setFont(new Font("Arial", Font.BOLD, 20));
        ContinueButtonListener cbl10 = new ContinueButtonListener();
        continueButton.addActionListener(cbl10);
        buttonPanel.add(continueButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
    
    class ContinueButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent evt) 
        {
            String playerName = nameField.getText().trim();
            if (playerName.length() > 0) 
            {
                try 
                {
                    // Create a new high score entry with time and powerups used
                    // Use the seconds value directly from the timer panel
                    int seconds = info.getSeconds(); // Get elapsed time in seconds
                    int powerupsUsed = calculatePowerupsUsed();
                    String newScore = playerName + " - " + info.getCoins() + " coins - Time: " + 
                                     seconds + " seconds - Powerups: " + powerupsUsed;
                    
                    // Open the high scores file
                    File highScoreFile = new File("highScores.txt");
                    
                    // First count number of lines
                    Scanner countScanner = new Scanner(highScoreFile);
                    int lineCount = 0;
                    while (countScanner.hasNextLine()) 
                    {
                        countScanner.nextLine();
                        lineCount++;
                    }
                    countScanner.close();

                    // Read existing scores into array
                    String[] scores;
                    if (lineCount > 0) 
                    {
                        scores = new String[lineCount + 1]; // +1 for new score
                        Scanner scanner = new Scanner(highScoreFile);
                        for (int i = 0; i < lineCount; i++) 
                        {
                            scores[i] = scanner.nextLine();
                        }
                        scanner.close();
                        scores[lineCount] = newScore;
                    } 
                    else 
                    {
                        // No existing scores, just add the new one
                        scores = new String[1];
                        scores[0] = newScore;
                    }
                    
                    // Sort scores by coin count (descending) using bubble sort
                    for (int i = 0; i < scores.length - 1; i++) 
                    {
                        for (int j = 0; j < scores.length - i - 1; j++) 
                        {
                            if (scores[j] != null && scores[j + 1] != null) 
                            {
                                // Parse coin values from score strings
                                int coinsA = 0;
                                int coinsB = 0;
                                
                                // Find index of " - " in each score string
                                int dashIndexA = scores[j].indexOf(" - ");
                                int dashIndexB = scores[j + 1].indexOf(" - ");
                                
                                if (dashIndexA >= 0 && dashIndexB >= 0) 
                                {
                                    // Extract substring after " - " up to " coins"
                                    String scoreA = scores[j].substring(dashIndexA + 3, scores[j].indexOf(" coins"));
                                    String scoreB = scores[j + 1].substring(dashIndexB + 3, scores[j + 1].indexOf(" coins"));
                                    
                                    // Parse digits only
                                    coinsA = 0;
                                    for (int k = 0; k < scoreA.length(); k++) 
                                    {
                                        char c = scoreA.charAt(k);
                                        if (c >= '0' && c <= '9') 
                                        {
                                            coinsA = coinsA * 10 + (c - '0');
                                        }
                                    }
                                    
                                    coinsB = 0; 
                                    for (int k = 0; k < scoreB.length(); k++) 
                                    {
                                        char c = scoreB.charAt(k);
                                        if (c >= '0' && c <= '9') 
                                        {
                                            coinsB = coinsB * 10 + (c - '0');
                                        }
                                    }
                                }
                                if (coinsA < coinsB) 
                                {
                                    // Swap
                                    String temp = scores[j];
                                    scores[j] = scores[j + 1];
                                    scores[j + 1] = temp;
                                }
                            }
                        }
                    }
                    
                    // Write top 10 scores back to file
                    PrintWriter writer = new PrintWriter("highScores.txt");
                    int maxScores = Math.min(10, scores.length);
                    for (int i = 0; i < maxScores; i++) 
                    {
                        writer.println(scores[i]);
                    }
                    writer.close();
                    
                } 
                catch (FileNotFoundException e) 
                {
                    System.out.println("Error updating high scores: " + e);
                }
            }
            
            // Get the HighScoresPanel and reload the scores
            HighScoresPanel hsp = panelCards.getHighScoresPanel();
            hsp.loadHighScores();
            
            // Reset the game state
            info.resetGame();
            
            cards.show(panelCards, "HighScores");
        }
        
        // Calculate how many powerups the player used
        public int calculatePowerupsUsed()
        {
            // Get the actual count of powerups used from the info class
            return info.getPowerupsUsed();
        }
    }
}
