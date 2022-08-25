package homework.hw1;

import java.util.Scanner;

/**
 * Plays the game of Frogs and Toads.
 *
 * @author Drue Coles
 */
public class FrogsAndToadsDriver {

   public static void main(String[] args) {
      System.out.println("Welcome to Frogs and Toads.\n");
      FrogsAndToads game = new FrogsAndToads();
      System.out.println(game);

      Scanner in = new Scanner(System.in);

      while (true) {

         System.out.print("Enter q(uit), u(ndo), or a valid move (row col): ");
         String[] input = in.nextLine().split(" ");

         if (input[0].toLowerCase().equals("q")) {
            System.out.println("Goodbye.");
            return;
         }

         if (input[0].toLowerCase().equals("u")) {
            game.undo();
         } else {
            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            game.move(row, col);
         }
         System.out.println();
         System.out.println(game);

         if (game.over()) {
            System.out.println("You have solved the puzzle!");
            return;
         }
         
         if (!game.canMove()) {
            System.out.println("You are stuck!");
            System.out.println("Undo one or more moves to continue.");
         }
      }
   }
}