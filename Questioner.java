/**
 * PURPOSE: Creates and stores a binary tree that contains the 20 questions knowledge, and creates a game for the user
 */

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

public class Questioner {

    /**
     * PURPOSE: Implement each node in the 20 questions binary tree. Stores a question or answer as the node item, and has links 
     *          to left and right children which pertain to the parent nodes question, or null for each if the item is an answer.
     */
    private class DTNode {

        public String item;
        public DTNode left, right;

        public DTNode(String item) {
            
            this.item = item;
            //left and right links are set to null by default
        }

        public DTNode(String item, DTNode left, DTNode right) {

            this.item = item;
            this.left = left;
            this.right = right;
        }

        /**
         * PURPOSE: Return a boolean value indicating if the node is a leaf
         * @return True if the node is a leaf, false otherwise
         */
        public boolean isLeaf() {

            return (left == null && right == null); //if both (or one) of the node's children are null, it is a leaf
        }
    }

    private DTNode root;

    /**
     * PURPOSE: Construct a default hardcoded 20 questions tree for if the user doesn't want to read in from a file
     */
    public Questioner() {
        
        DTNode leafOne = new DTNode("human");
        DTNode leafTwo = new DTNode("shark");
        DTNode leafThree = new DTNode("carrot");
        DTNode leafFour = new DTNode("diamond");
        DTNode interiorOne = new DTNode("Is it a mammal?", leafOne, leafTwo);
        DTNode interiorTwo = new DTNode("Is it a plant?", leafThree, leafFour);
        root = new DTNode("Is it an animal?", interiorOne, interiorTwo);
    }

    /**
     * PURPOSE: Play a round of 20 questions using either the default tree or from file input, prompting the user to interact
     *          with the game through JOptionPane
     */
    public void playRound() {

        DTNode prev = null;
        DTNode curr = root;

        if(curr != null) {
            while(!curr.isLeaf()) { //continuously ask questions until reaching an answer to guess
                int currAnswer = JOptionPane.showConfirmDialog(null,
                                                               curr.item,
                                                               "20 Questions",
                                                               JOptionPane.YES_NO_OPTION,
                                                               JOptionPane.QUESTION_MESSAGE); //ask the user the question stored in the current node
                prev = curr; //keep track of the parent node
                if(currAnswer == JOptionPane.YES_OPTION) { //the user answers yes to the current question
                    curr = curr.left; //move to the current nodes left child
                }
                else if(currAnswer == JOptionPane.NO_OPTION) { //the user answers no to the current question
                    curr = curr.right; //move to the current nodes right child
                }
            }
            processLeaf(curr, prev); //when reaching a leaf, process it
        }
    }

    /**
     * PURPOSE: Ask the user if the answer at the leaf is the item they were thinking of, if so, tell them you guessed correctly, otherwise add to the tree
     */
    private void processLeaf(DTNode leaf, DTNode parent) {

        int currAnswer = JOptionPane.showConfirmDialog(null,
                                                       "Are you thinking of (a/an) " + leaf.item + "?",
                                                       "20 Questions",
                                                       JOptionPane.YES_NO_OPTION,
                                                       JOptionPane.QUESTION_MESSAGE); //ask the user if the item at the leaf is what they were thinking of
        if(currAnswer == JOptionPane.YES_OPTION) { //the item was what they were thinking of
            JOptionPane.showMessageDialog(null, "I guessed correctly!");
        }
        else if(currAnswer == JOptionPane.NO_OPTION) { //the item was not what they were thinking of
            insertNewQuestion(leaf, parent); //update the tree to include the item the user was thinking of
        }
    }

    /**
     * PURPOSE: Insert the item the user was thinking of into the tree along with a question that differentiates it from the item guessed
     * @param leaf The node containing the item that was guessed
     * @param parent The parent node of the leaf that contained the item guessed
     */
    private void insertNewQuestion(DTNode leaf, DTNode parent) {
        
        String newThing = JOptionPane.showInputDialog("What were you thinking of?"); //ask the user what they were thinking of
        String newQuestion = JOptionPane.showInputDialog("Please give me a yes/no question that distinguishes " + newThing + " (yes answer) and " + leaf.item + " (no answer)"); //ask for a question that distinguishes the programs guess and the users answer
        
        if(parent == null) { //means root is a leaf (guess on first try) 
            root = new DTNode(newQuestion, new DTNode(newThing), leaf); //insert the new question as the root, and make the user's answer the left child (yes answer) and the programs's guess (no answer) right child
        }
        else {
            if(leaf == parent.left) { //the leaf that was guessed was its parents left child
                parent.left = new DTNode(newQuestion, new DTNode(newThing), leaf); //insert the new question with its yes/no answers as the left child of the old leaf's parent
            }
            else { //the leaf that was guessed was its parents right child
                parent.right = new DTNode(newQuestion, new DTNode(newThing), leaf); //insert the new question with its yes/no answers as the right child of the old leaf's parent
            }
        }
    }

    /**
     * PURPOSE: Prompt the user to select a file to read in, create a new tree based on the data, and replace the hardcoded tree with it
     * @throws FileNotFoundException If the file the user entered can not be found
     */
    public void readTree() throws FileNotFoundException {

        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null); //give the user access to their file explorer to select the input file
        File inFile = jfc.getSelectedFile(); //store the file selected by the user as a File object
        Scanner sc = null;

        if(inFile != null) { //ensure that the user didn't hit cancel
            sc = new Scanner(inFile); //exception is propogated here and caught in main helper
            root = readInput(sc); //store the root of the tree synthesized from the file input data
            sc.close();
        }
    }

    /**
     * PURPOSE: A recursive helper method that reads in the file data and creates a 20 questions tree from it 
     * @param sc The scanner being used to read the file
     * @return The node created from that current line in the file (ultimately the root of the tree is returned at the end of all recursive calls)
     */
    private DTNode readInput(Scanner sc) {

        DTNode curr = null;

        if(sc.hasNext("<")) { //the line contains a question or answer if it starts with "<"
            sc.next(); //skip the token containing "<"
            curr = new DTNode(sc.nextLine().strip()); //create a new node with the data from the line by reading it in and stripping off leading/trailing whitespace
            curr.left = readInput(sc); //recursively call this method to attach left children at each call
            curr.right = readInput(sc); //recursively call this method to attach right children at each call
            sc.nextLine(); //skip lines containing ">" after both recursive calls finish to ensure that we are properly traversing the file given the format
        }
        return curr;
    }

    /**
     * PURPOSE: Prompt the user to select a file to save the current tree to, and call a helper method that writes it to the file
     */
    public void writeTree() {

        JFileChooser jfc = new JFileChooser();
        jfc.showSaveDialog(null); // display the user's file explorer so they can select (or create) a file to save to
        File newFile = jfc.getSelectedFile(); //store the file selected by the user as a File object

        if(newFile != null) { //avoid NullPointerException if user hits cancel
            try {
                PrintWriter outFile = new PrintWriter(newFile); //create a PrintWriter to write data to the file
                preOrderTraversal(root, outFile); //call the method that writes out the data
                outFile.close(); //close the PrintWriter
            }
            catch(FileNotFoundException fnf) {
                JOptionPane.showMessageDialog(null, "File could not be found"); //Display a message to the user indicating that the file could not be found 
            }
        }
    }

    /**
     * PURPOSE: Traverse the tree and print the contents to the file selected with proper format
     * @param curr The current node in the tree
     * @param outFile The PrintWriter that is being used to write to the file
     */
    private void preOrderTraversal(DTNode curr, PrintWriter outFile) {

        if(curr != null) {
            //perform a preorder traversal of the tree and print the contents of the tree in proper format
            outFile.println("< " + curr.item); 
            preOrderTraversal(curr.left, outFile);
            preOrderTraversal(curr.right, outFile);
            outFile.println(">");
        }
    }
    
}