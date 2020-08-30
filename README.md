# RicardoMinesweeper
Minesweeper game with 3 difficulty levels and 3 modes of game, including practice.

CIS 120 Game Project

  2D arrays are used in order to model the board since it is a 2D concept and every component of it
  is a spot with unique characteristics

  Recursion is used in order to identify the groups of adjacent zeros in the board.

  A linkedlist is used to be able to implement the practice mode, the state of each move is saved in a LinkedList, so that
  when the user goes back, it can retrieve the last element of the list. 
  
  The class Game is the one that manages if you see the game panel or the instructions panel and
  handles the functionality of the buttons ready and reset, as well as the settings buttons. 
  The class Arena is where the board is created, with all the spots, taking into account the settings 
  provided using the buttons in Game. It populates the board with mines, and handles the event when
  the user clicks in a spot by changing the correct characteristics of the spots.
  The class Spot creates every single spot with different fields and methods.
  The class Instruction creates an instruction panel with text outlining the directions to play 
  
  Finished in Spring 2020
