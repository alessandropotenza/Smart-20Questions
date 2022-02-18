# Smart 20 Questions Game

## Description
A standard game of 20 Questions that uses a binary tree to store game data. Each incorrect guess is incorporated into the tree to build it smarter.

## Instructions
- When the game is started, a prompt is displayed asking if you'd like to import game data from a text file. The format required for this data is shown in "sampleInput.txt". If you don't read from a file, the default question tree is used.
- When the program guesses incorrectly, you will be asked to enter what you were thinking of and provide a yes/no question that differentiates between it (yes answer) and what the game guessed (no answer). This information will then be incorporated into the tree.
- When the game is completed, you can play again with the smarter tree or exit. If you choose to exit, you will be asked to save the current game data to a text file, and a file explorer will be opened if you select yes. The format of the data written to this file will be the same as that in "sampleInput.txt".