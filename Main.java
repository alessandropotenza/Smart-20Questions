/**
 * 
 * PURPOSE: Implement a game of 20 questions using a binary tree and interactive user prompts for the user to communicate with the game. Allow
 *          the user to read in a new game from a file (or play with the default game), and save any changes they make to a new file.
 */

import javax.swing.JOptionPane;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null, "Let's play 20 Questions!!!");

        simulateQuestioner();

        JOptionPane.showMessageDialog(null, "20 Questions ends");
    }

    /**
     * PURPOSE: Simulate the 20 questions game, prompting the user to play again until they choose not to, and asking them to save changes to a new file
     */
    private static void simulateQuestioner() {

        boolean gameOver = false;
        Questioner game = new Questioner();
        
        int selectFile = JOptionPane.showConfirmDialog(null, "Read knowledge from a file?"); //ask the user if they want to read knowledge from a file
        if(selectFile == JOptionPane.YES_OPTION) { //the user selects YES to reading from a file
            try {
                game.readTree(); //construct the tree using the file input data
            }
            catch(FileNotFoundException fnf) { //catch the FileNotFoundException thrown from game.readTree() if the user tries to read knowledge from a non-existing file
                gameOver = true; //terminate the game if the file is not found
                JOptionPane.showMessageDialog(null, "File could not be found"); //tell the user the file could not be found
            }
        }
        while(!gameOver) { //continuously start new rounds until the user selects NO to playing again
            game.playRound(); //play a round
            int currAnswer = JOptionPane.showConfirmDialog(null, 
                                                            "I get better every time I play. Play Again?",
                                                            "20 Questions",
                                                            JOptionPane.YES_NO_OPTION,
                                                            JOptionPane.QUESTION_MESSAGE); //ask the user to play again
            if(currAnswer == JOptionPane.NO_OPTION) { //if the user selects NO to playing again, end the game
                gameOver = true;
                int saveFile = JOptionPane.showConfirmDialog(null,
                                                            "Thank you!\nSave current knowledge to a file?",
                                                            "20 Questions",
                                                            JOptionPane.YES_NO_OPTION,
                                                            JOptionPane.QUESTION_MESSAGE); //ask the user if they want to save changes to a file
                if(saveFile == JOptionPane.YES_OPTION) { //the user selects YES to save changes 
                    game.writeTree(); //write changes to a new file
                }
            }
        }
    }

}