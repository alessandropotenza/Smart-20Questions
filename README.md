# Smart 20 Questions Game

## Description
A standard game of 20 questions that uses a binary tree to store game data. Each incorrect guess is incorporated into the tree to build it smarter.

## Instructions
- When the game is started, a prompt is displayed which asks if you'd like to import game data from a text file. The format required to for this data is shown in "sampleInput.txt". If you don't read from a file, the default question tree is used.
- When the program guesses incorrectly, you will be asked to enter what you were thinking of and provide a yes/no question that differentiates between it (yes answer) and what the game guessed (no answer). This information will then be incorporated into the tree.
- When the game is completed, you can play again with the smarter tree or exit. If you choose to exit, you'll be asked if you want to save current game data to a text file which can be used for subsequent games, and a file explorer will be opened if you select yes. You can either enter the name of a non-existent file and it will be created at the chosen path, or you can overwite an existing text file. The format of the data written to this file will again be the same as that in "sampleInput.txt".