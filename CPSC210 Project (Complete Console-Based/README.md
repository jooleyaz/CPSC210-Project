# CPSC210 Project - Julia Zhong

## Song Prompt Guessing Game

The project I am proposing to create is a multiplayer song guessing game. I was first inspired to create this project
after playing a game called Photo Roulette with my friends, where a photo from someone's camera roll pops up and everyone guesses whose photo
it is. I really enjoy listening to music and sharing songs with others, so I thought that a song-based game with
similar elements would be really fun to create. This application is perfect for anyone who wants to have fun getting to know their friends a little
better while finding new music recommendations!!

This app gives player 1, the *"chooser"*, 3 types of prompt lists they can choose from (situational, mood, and theme). They then
assign a song of their choice to each prompt in the randomly-generated prompt list, and it will do this for 10 prompts
per game. Then, the game will get you to pass the device to the second player, the *"guesser"*, and a randomized song from the 10
that the chooser selected will pop up on the screen. The guesser gets a list of the 10 prompts and
tries to match the song to the prompt, and this process continues until all the songs are gone, which is when their answers and the correct answers are displayed.
The guesser and the chooser can now swap roles and play again.

## User Stories
- As a user, I want to be able to input player names
- As a user, I want to be able to select a type of prompt list (player 1 - chooser)
- As a user, I want to be able to create my own custom prompt list where I add an arbitrary amount of Prompts to a PromptList (player 1 - chooser)
- As a user, I want to be able to input a song when given a prompt from that prompt list so that the song is linked to that prompt (player 1 - chooser)
- As a user, I want to be able to select the prompt that I think a given song is associated with, from a list of possible prompts (player 2 - guesser)
- As a user, I want to be able to see a list of the correct song-prompt pairings after I finish guessing, as well as my own guesses so I can compare results (player 2 - guesser)
- As a user, I want to be able to see my score at the end of how many I got right (player 2 - guesser)
- As a user, I want to be able to save the prompt list that I choose or make (if I so choose)
- As a user, I want to be able to be able to load up the last prompt list that I chose/made to use it again (if I so choose)

## Notes to Markers/Attributing Sources
- Method length was suppressed for the Game generate methods, since those methods are so long due to adding elements to list
- Prompts were generated using ChatGPT
- runGame(), gameMenu(), and processCommand() in Game all have basic elements from TellerApp to set up the game properly
- persistence package and json-related methods in Prompt and PromptList all have code based on JsonSerializationDemo
- persistence tests have code based on JsonSerializationDemo

## Phase 4: Task 2
Below is what the event log looks like after the program is run, 2 song-prompt pairings are entered, the button to display the list is clicked, and the button to shuffle the list is clicked.
These actions represent adding X to Y, and doing 2 things in relation to X and Y.

Thu Apr 13 16:17:02 PDT 2023: Song added.

Thu Apr 13 16:17:02 PDT 2023: Prompt added.

Thu Apr 13 16:17:09 PDT 2023: Song added.

Thu Apr 13 16:17:09 PDT 2023: Prompt added.

Thu Apr 13 16:17:12 PDT 2023: Returned prompt-song list.

Thu Apr 13 16:17:13 PDT 2023: Prompt list shuffled.

Thu Apr 13 16:17:13 PDT 2023: Returned prompt-song list.

## Phase 4: Task 3

This project contains a lot of duplication. This can be fixed by refactoring the LoadedPrompts and AddPromptsGUI ui classes 
so that they both extend an abstract class, since they contain a lot of duplicate methods and the only difference is
the addition of a load button in LoadedPrompts. Also, in the console-based Game class, methods such as chooseTenPrompts could have
implemented loops instead of manually writing out virtually the same line of code 10 times, in order to improve readability and clarity. 
Additionally, in the GUI classes, the entire setup of the GUI was done in the constructor. This demonstrates weak cohesion,
so to improve upon this, it would help to instead create various setup methods and call them in the constructor.
