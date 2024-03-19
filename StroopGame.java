//CSCI 185 Fall 2023
//Hwan Lee, Lewi Gao, and Richard Martinez-Mejia
//M11: Final Programming Project
//Wenjia Li
//December 18, 2023

// Import necessary Java Swing libraries
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

//Main class representing the Stroop Game
public class StroopGame {

    //Array of color choices
    private static String[] colors = {"Red", "Blue", "Green", "Yellow", "Orange", "Purple"};
    private static String correctChoice = colorGen(colors); //starting color
    private static int points = 0;

    //Timer related data fields
    private static Timer gameTimer;
    private static int remainingTimeInSeconds;

    // Leaderboard related data field
    private static ArrayList<LeaderboardEntry> leaderboardEntries = new ArrayList<>();

    // Nested static class representing a LeaderboardEntry
    private static class LeaderboardEntry {

        //Data fields for LeaderboardEntry static class
        private int rank;
        private String playerName;
        private int playerScore;

        //Fully loaded constructor for LeaderboardEntry static class
        public LeaderboardEntry(int rank, String playerName, int playerScore) {
            this.rank = rank;
            this.playerName = playerName;
            this.playerScore = playerScore;
        }

        //Set and Get methods for the data fields
        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public void setPlayerScore(int playerScore) {
            this.playerScore = playerScore;
        }

        public int getPlayerScore() {
            return playerScore;
        }

        public int getRank() {
            return rank;
        }

        public String getPlayerName() {
            return playerName;
        }

    }

    //Main method of the program

    public static void main(String[] args) {
        JFrame frame = new JFrame("Dr.Stroop's Color Trials :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Calling the main menu method
        mainMenu(frame);

    }

    // Method to display the main menu
    private static void mainMenu(JFrame frame) {

        // Creating a panel for the main menu
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));

        // Creating "How to play" button and add action listener
        JButton howToPlayButton = new JButton("How To Play");
        howToPlayButton.addActionListener(e -> showHowToPlay(frame));

        // Creating top panel with "How to play" button
        JPanel topPanel = new JPanel();
        topPanel.add(howToPlayButton);

        //Creating "Play," "High scores," and "Quit" buttons
        JButton playButton = new JButton("Play");
        JButton LeaderboardButton = new JButton("Leaderboard");
        JButton quitButton = new JButton("Quit");

        // Creates the title image
        ImageIcon titleImage = new ImageIcon("src/TitleImage.png");//Please set this to a relative location
        JLabel imageLabel = new JLabel(titleImage);

        //Creating bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(playButton);
        bottomPanel.add(LeaderboardButton);
        bottomPanel.add(quitButton);

        // Main Panel Code
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        //Properties for main panel
        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();

        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Setting fonts for buttons
        playButton.setFont(new Font("Arial", Font.PLAIN, 30));
        LeaderboardButton.setFont(new Font("Arial", Font.PLAIN, 30));
        quitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        howToPlayButton.setFont(new Font("Arial", Font.PLAIN, 30));

        // Adding action listeners using Lambda expressions for buttons
        playButton.addActionListener(e -> showDifficulty(frame));
        LeaderboardButton.addActionListener(e -> showLeaderboard(frame));
        quitButton.addActionListener(e -> quitGame(frame));
    }

    // Method to display "How to play" instructions
    private static void showHowToPlay(JFrame frame) {
        JOptionPane.showMessageDialog(frame,
                "Welcome to Dr.Stroop's Color Trials! Here you'll learn how to play!"
                        + "\nIt's a simple game. You'll be shown a colored text, like the word 'Green' in red."
                        + "\nYou need to press the button that corresponds with the color of the text, not what color the text describes."
                        + "\nSo for the example provided above, press the 'Red' button instead of the 'Green' button"
                        + "\nIf you answer fast enough, you get points! If you get an incorrect answer or run out of time, well that's game over."
                        + "\nIf you still don't get it, consider playing the easier difficulty to get a grip!",
                "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to display difficulty selection
    private static void showDifficulty(JFrame frame) {

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel GameDifficulties = new JLabel("Choose Your Difficulty");
        GameDifficulties.setFont(new Font("Arial", Font.BOLD, 50));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        // Add difficulty buttons and the Back button
        JButton easyButton = createDifficultyButton("I'm Too Young To Test!");
        JButton mediumButton = createDifficultyButton("Test Me Plenty");
        JButton hardButton = createDifficultyButton("Ultra-Test");
        JButton backButton = createBackButton("Back");

        // Add action listeners to the difficulty buttons and the back button
        easyButton.addActionListener(e -> startGameWithDifficulty("Easy", frame));
        mediumButton.addActionListener(e -> startGameWithDifficulty("Medium", frame));
        hardButton.addActionListener(e -> startGameWithDifficulty("Hard", frame));
        backButton.addActionListener(e -> mainMenu(frame));

        buttonsPanel.add(Box.createVerticalStrut(20));
        buttonsPanel.add(easyButton);
        buttonsPanel.add(Box.createVerticalStrut(50));
        buttonsPanel.add(mediumButton);
        buttonsPanel.add(Box.createVerticalStrut(50));
        buttonsPanel.add(hardButton);
        buttonsPanel.add(Box.createVerticalStrut(210));
        buttonsPanel.add(backButton);

        // Add difficulty buttons to the panel
        difficultyPanel.add(Box.createVerticalStrut(50));
        difficultyPanel.add(GameDifficulties);
        difficultyPanel.add(Box.createVerticalStrut(75));
        difficultyPanel.add(buttonsPanel);

        // Switch the content of the frame to the difficulty options panel
        switchPanel(frame, difficultyPanel);
    }

    private static JButton createDifficultyButton(String text) {
        JButton diffbutton = new JButton(text);
        diffbutton.setFont(new Font("Arial", Font.PLAIN, 50));
        diffbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return diffbutton;
    }

    private static JButton createBackButton(String text) {
        JButton backbutton = new JButton(text);
        backbutton.setFont(new Font("Arial", Font.PLAIN, 50));
        backbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return backbutton;
    }

    // Method to start the game based on the selected difficulty
    private static void startGameWithDifficulty(String difficulty, JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Start the game with " + difficulty + " difficulty?", "Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "You have chosen " + difficulty + " difficulty!", "Good luck!", JOptionPane.INFORMATION_MESSAGE);
            // throws user into the game
            gamePortion(frame, difficulty);
        } else {
            showDifficulty(frame);
        }
    }

    // Method to handle the game portion
    private static void gamePortion(JFrame frame, String difficulty) {
        points = 0;
        JPanel gamePanel = new JPanel(new BorderLayout());

        //too many repeating colors if just one array
        Random randSet = new Random();
        String[] colors1 = {"Red", "Yellow", "Purple"};
        String[] colors2 = {"Blue", "Green", "Orange"};

        JLabel questionLabel = new JLabel(correctChoice);
        questionLabel.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 40));
        gamePanel.add(questionLabel, BorderLayout.CENTER);
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);

        //sets text color
        setFG(questionLabel, correctChoice);

        JLabel timerLabel = new JLabel("Timer: ");
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(timerLabel);
        topPanel.add(Box.createHorizontalStrut(450));
        topPanel.add(scoreLabel);
        gamePanel.add(topPanel, BorderLayout.NORTH);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(1, 6));

        for (String color : colors) {
            JButton colorButton = new JButton(color);
            colorButton.setFont(new Font("Arial", Font.PLAIN, 25)); // Adjust the font size as needed
            colorButton.setPreferredSize(new Dimension(110, 75)); // Adjust the preferred size as neede


            colorButton.addActionListener(e -> {
                String playerChoice = colorButtonClick(e);

                boolean scored = correctColor(correctChoice, playerChoice);

                if (scored) {


                    int arr = randSet.nextInt(3);

                    switch (arr) {
                        //setBG(gamePanel);
                        case 1:
                            correctChoice = colorGen(colors1);
                            questionLabel.setText(colorGen(colors));
                            //setBG(gamePanel);
                            setFG(questionLabel, correctChoice);
                            break;
                        case 2:
                            correctChoice = colorGen(colors2);
                            questionLabel.setText(colorGen(colors));
                            //setBG(gamePanel);
                            setFG(questionLabel, correctChoice);
                            break;
                        default:
                            correctChoice = colorGen(colors);
                            questionLabel.setText(colorGen(colors));
                            //setBG(gamePanel);
                            setFG(questionLabel, correctChoice);
                            break;
                    }
                    //System.out.println(correctChoice + " " + arr);
                    switch (difficulty) {
                        case "Easy":
                            remainingTimeInSeconds = 10;
                            points += 50;
                            break;
                        case "Medium":
                            remainingTimeInSeconds = 7;
                            points += 75;
                            break;
                        case "Hard":
                            remainingTimeInSeconds = 3;
                            points += 150;
                            break;
                        default:
                            remainingTimeInSeconds = 60;
                            break;
                    }
                } else {
                    gameTimer.stop();
                    showGameOver(frame);
                }
                updateScoreLabel(scoreLabel);
            });
            colorPanel.add(colorButton);
        }

        gamePanel.add(colorPanel, BorderLayout.SOUTH);

        switchPanel(frame, gamePanel);

        switch (difficulty) {
            case "Easy":
                remainingTimeInSeconds = 10;
                break;
            case "Medium":
                remainingTimeInSeconds = 7;
                break;
            case "Hard":
                remainingTimeInSeconds = 3;
                break;
            default:
                remainingTimeInSeconds = 60;
                break;
        }

        gameTimer = new Timer(1000, e -> updateTimer(timerLabel, frame));
        gameTimer.setInitialDelay(0);
        gameTimer.start();
    }

    // Method to update the game timer label
    private static void updateTimer(JLabel timerLabel, JFrame frame) {
        try {
            timerLabel.setText("Timer: " + remainingTimeInSeconds);

            if (remainingTimeInSeconds <= 0) {
                timerLabel.setText("Timer: 0");
                gameTimer.stop();
                showGameOver(frame);
            }

            remainingTimeInSeconds--;

        } catch (NumberFormatException e) {
            System.err.println("Error updating timer label: " + e.getMessage());
        }
    }

    // Method to handle game over scenario
    private static void showGameOver(JFrame frame) {

        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel accumulatedScoreLabel = new JLabel("Your Score: " + points);
        accumulatedScoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        accumulatedScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel enterUrName = new JLabel("Enter your name: ");
        enterUrName.setFont(new Font("Arial", Font.PLAIN, 16));
        enterUrName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 30));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.addActionListener(e -> submitScore(nameField.getText(), frame));

        gameOverPanel.add(Box.createVerticalStrut(50));
        gameOverPanel.add(gameOverLabel);
        gameOverPanel.add(Box.createVerticalStrut(20));
        gameOverPanel.add(accumulatedScoreLabel);
        gameOverPanel.add(Box.createVerticalStrut(20));
        gameOverPanel.add(enterUrName);
        gameOverPanel.add(Box.createVerticalStrut(2));
        gameOverPanel.add(nameField);
        gameOverPanel.add(Box.createVerticalStrut(20));
        gameOverPanel.add(submitButton);

        switchPanel(frame, gameOverPanel);
    }

    // Method to switch between panels in the frame
    private static void switchPanel(JFrame frame, JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // Method to update the score label
    private static void updateScoreLabel(JLabel scoreLabel) {
        scoreLabel.setText("Score: " + points);
    }

    //Method to gets the color from the clicked button
    private static String colorButtonClick(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        return clickedButton.getText();
    }

    // Method to generate a random color from the given array
    private static String colorGen(String[] colors) {
        Random randColor = new Random();
        return colors[randColor.nextInt(colors.length)];
    }

    // Method to check if the selected color is correct
    private static boolean correctColor(String rightColor, String playerColor) {
        return rightColor.equals(playerColor);
    }

    // Method to submit the player's score to the leaderboard
    private static void submitScore(String playerName, JFrame frame) {
        if (playerName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter your name before submitting.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int playerScore = points;
            updateLeaderboard(playerName, playerScore);
            showLeaderboard(frame);
        }
    }

    // Method to update the leaderboard with a new entry
    private static void updateLeaderboard(String playerName, int playerScore) {
        LeaderboardEntry newEntry = new LeaderboardEntry(0, playerName, playerScore);
        leaderboardEntries.add(newEntry);
        sortLeaderboard();
    }

    // Method to sort the leaderboard entries based on score
    private static void sortLeaderboard() {
        // Sort the entries by score in descending order
        Collections.sort(leaderboardEntries, Comparator.comparingInt(LeaderboardEntry::getPlayerScore).reversed());

        // Update the rank in the sorted list
        for (int i = 0; i < leaderboardEntries.size(); i++) {
            leaderboardEntries.get(i).setRank(i + 1);
        }
    }

    // Method to set the foreground color of a JLabel based on the correct choice
    private static JLabel setFG(JLabel jl, String correctChoice) {
        switch (correctChoice) {
            case "Red":
                jl.setForeground(Color.red);
                return jl;
            case "Blue":
                jl.setForeground(Color.blue);
                return jl;
            case "Yellow":
                jl.setForeground(Color.yellow);
                return jl;
            case "Green":
                jl.setForeground(Color.green);
                return jl;
            case "Purple":
                jl.setForeground(Color.magenta);
                return jl;
            case "Orange":
                jl.setForeground(Color.orange);
                return jl;
            default:
                return jl;
        }
    }

    // Method to display the leaderboard
    private static void showLeaderboard(JFrame frame) {

        JFrame leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setSize(700, 700);
        leaderboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable leaderboardTable = UpdateTable();
        leaderboardTable.setRowHeight(60);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 24));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = leaderboardTable.getTableHeader();
        header.setDefaultRenderer(centerRenderer);

        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            TableColumn column = leaderboardTable.getColumnModel().getColumn(i);
            column.setCellEditor(new DefaultCellEditor(new JTextField()) {
                @Override
                public boolean isCellEditable(EventObject anEvent) {
                    return false;
                }
            });
        }


        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        JLabel currentLeaderboard = new JLabel("Current Leaderboard");
        currentLeaderboard.setFont(new Font("Arial", Font.BOLD, 30));
        JButton ReturnButton = new JButton("Return to Main Menu");
        ReturnButton.setFont(new Font("Arial", Font.BOLD, 40));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(currentLeaderboard, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(ReturnButton, BorderLayout.SOUTH);


        ReturnButton.addActionListener(e -> {
            leaderboardFrame.dispose();  // Close the leaderboard window
            mainMenu(frame);  // Return to the main menu
        });

        currentLeaderboard.setHorizontalAlignment(SwingConstants.CENTER);
        currentLeaderboard.setVerticalAlignment(SwingConstants.CENTER);

        leaderboardFrame.add(panel);
        leaderboardFrame.setLocationRelativeTo(null);

        // Set up the frame properties and make it visible
        leaderboardFrame.setLocationRelativeTo(frame);
        leaderboardFrame.setVisible(true);

    }

    // Method to update the leaderboard table
    private static JTable UpdateTable() {
        Object[][] data = {

        };

        ArrayList<LeaderboardEntry> entries = leaderboardEntries;
        Collections.sort(entries, Comparator.comparingInt(LeaderboardEntry::getPlayerScore).reversed());
        // Populate the entries list from the data array
        for (Object[] row : data) {
            int rank = (int) row[0];
            String playerName = (String) row[1];
            int playerScore = (int) row[2];
            entries.add(new LeaderboardEntry(rank, playerName, playerScore));
        }

        // Sort the entries by score in descending order
        int numRowsToShow = Math.min(entries.size(), 10);
        Object[][] sortedData = new Object[numRowsToShow][3];
        for (int i = 0; i < numRowsToShow; i++) {
            LeaderboardEntry entry = entries.get(i);
            sortedData[i][0] = i + 1;  // Rank
            sortedData[i][1] = entry.playerName;  // Name
            sortedData[i][2] = entry.playerScore;  // High Score
        }


        String[] columnNames = {"Rank", "Name", "High Score"};

        // Create a table model with the data and column names
        DefaultTableModel model = new DefaultTableModel(sortedData, columnNames);

        // Create a JTable with the model
        JTable leaderboardTable = new JTable(model);
        return leaderboardTable;
    }

    // Method to handle quitting the game
    private static void quitGame(JFrame frame) {
        int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}