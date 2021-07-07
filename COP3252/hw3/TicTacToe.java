import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

/* 
    Static Variables/Functions
*/

    public static Random random;
    public static Scanner scanner;
    public static TicTacToe instance;

    public static void main(String[] args){
        byte computerPlayer = 0;
        boolean advanced = false;
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-a"))
                advanced = true;
            if (args[i].equals("-c"))
                try {
                    // Computer player is argument after "-c"
                    computerPlayer = (byte) Integer.parseInt(args[i+1]);
                } catch (Exception e) {
                    // If the argument is out of bounds or not an integer then there are 2 computer players
                    computerPlayer = -1;
                }
        }
        random = new Random();
        scanner = new Scanner(System.in);
        instance = new TicTacToe(computerPlayer,advanced);
    }

/* 
    Member Variables/Functions
*/

    private boolean advanced;
    private byte computerPlayer;
    private final char[] id = {' ','X','O'};
    private byte[] board = new byte[9];
    // All winning combinations : 
    public final byte[][] winners = {
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
    };

    public TicTacToe(byte computer, boolean advanced){    
        this.computerPlayer = computer;
        this.advanced = advanced;
        play();
    }

    public void play() {
        System.out.println("\nNew TicTacToe Game");
        byte winner = 0;
        byte turn = 1;
        print();
        while (winner == 0) {
            if (computerPlayer == turn || computerPlayer == -1)
                board[computerPlay(turn)] = turn;
            else
                board[playerPlay(turn)] = turn;
            print();
            turn += turn == 1 ? 1 : -1;
            winner = checkWinner();
        }

        if (winner > 0)
            System.out.println("Player " + winner + " won!");
        else
            System.out.println("It's a draw!");

        // END OF CODE
        return;
    }

    public byte playerPlay(byte turn){
        System.out.print("Player " + turn + ", please enter a move (1-9): ");
        // Check if input is valid (Is a byte)
        if (!scanner.hasNextByte()){
            System.out.println("Invalid input");
            scanner.next();
            return playerPlay(turn);
        }
        byte move = scanner.nextByte();
        // Check if move is valid (between 1-9 and isn't already taken)
        if (move > 9 || move < 1 || board[--move] != 0){
            System.out.println("Invalid move.");
            return playerPlay(turn);
        }
        return move;
    }

    public byte computerPlay(byte turn){
        byte move = -1;

        // Go through every winning combination and check:
        //      1. If the computer can win in one move
        //      2. If the opponent can win in one move
        byte winningMove = -1;
        byte blockingMove = -1;
        for (byte[] win : winners){
            byte me = 0, opp = 0, empt = 0;
            for (byte b : win){
                if (board[b] == 0)
                    empt++;
                else if (board[b] == turn)
                    me++;
                else
                    opp++;
            }
            if (me == 2 && empt == 1)
                for (byte b : win)
                    if (board[b] == 0) {
                        winningMove = b;
                        break;
                    }
            if (opp == 2 && empt == 1)
                for (byte b : win)
                    if (board[b] == 0) {
                        blockingMove = b;
                    break;
                }
        }

        // Play winning move if exists
        if (winningMove != -1)
            move = winningMove;
        // If none exists, playing blocking move if exists
        else if (blockingMove != -1)
            move = blockingMove;

        // If neither winning move or blocking move exists, take center square if empty
        else if (board[4] == 0)
            move = 4;

        else {
            byte[] options = new byte[0];

            // If in advanced, prefer corner squares
            final byte[] corners = {0,2,6,8};
            if (advanced)
                options = getEmpty(corners);

            // If not or no corners are open, then choose randomly among empty squares
            if (!advanced || options.length == 0) {
                byte[] range = new byte[9];
                for (byte b = 0; b < 9; b++)
                    range[b] = b;
                options = getEmpty(range);
            }

            move = options[random.nextInt(options.length)]; 
        }

        System.out.println("Player " + turn + " (computer) chooses position " + (move+1));
        return move;
    }

    // Returns list of empty squares from a range of potential squares
    // Not most efficient way, but it works
    private byte[] getEmpty(byte[] range){
        byte[] empty = new byte[0];
        for (byte b : range) {
            if (board[b] == 0){
                byte[] temp = new byte[empty.length+1];
                for (byte t = 0; t < empty.length; t++)
                    temp[t] = empty[t];
                temp[empty.length] = b;
                empty = temp;
            }
        }
        return empty;
    }

    public byte checkWinner(){
        // Check all winning combinations
        for (byte[] win : winners){
            // If a player has all 3 spots, they are the winner
            if (board[win[0]] != 0 && board[win[0]] == board[win[1]] && board[win[1]] == board[win[2]])
                return board[win[0]];
        }
        // Check if there are any empty cells
        for (byte b = 0; b < board.length; b++)
            // If there are, then the game continues
            if (board[b] == 0)
                return 0;
        // If not, it is a draw
        return -1;
    }

    // Print the game board in ASCII format
    public void print() {
        System.out.println("\n");
        System.out.print("Game Board: \t");
        System.out.println("Positions:");
        for (int i = 0; i < 3; i++) {
            int s = i*3;
            System.out.print(id[board[s]] + "|" + id[board[s+1]] + "|" + id[board[s+2]]);
            System.out.print("\t\t");
            System.out.println((s+1) + "|" + (s+2) + "|" + (s+3));
            System.out.println(i == 2 ? "" : "--------\t--------");
        }
        System.out.println();
    }


}