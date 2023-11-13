import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void printBoard(ArrayList numsLeft){
        System.out.printf("|     |     |     |\n");
        System.out.printf("|  "+numsLeft.get(0)+"  |  "+numsLeft.get(1)+"  |  "+numsLeft.get(2)+"  |\n");
        System.out.printf("-------------------\n");
        System.out.printf("|     |     |     |\n");
        System.out.printf("|  "+numsLeft.get(3)+"  |  "+numsLeft.get(4)+"  |  "+numsLeft.get(5)+"  |\n");
        System.out.printf("-------------------\n");
        System.out.printf("|     |     |     |\n");
        System.out.printf("|  "+numsLeft.get(6)+"  |  "+numsLeft.get(7)+"  |  "+numsLeft.get(8)+"  |\n");;
    }

    public static void winCheck(ArrayList numsLeft, Scanner scanner){
        ArrayList usersPicks = new ArrayList<>();

        for(int i=0; i<numsLeft.size(); i++){
            if(numsLeft.get(i) == "X"){
                usersPicks.add(i+1);
            }
        }

        if(usersPicks.contains(1) && usersPicks.contains(2) && usersPicks.contains(3)){
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(4) && usersPicks.contains(5) && usersPicks.contains(6)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(7) && usersPicks.contains(8) && usersPicks.contains(9)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(1) && usersPicks.contains(4) && usersPicks.contains(7)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(2) && usersPicks.contains(5) && usersPicks.contains(8)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(3) && usersPicks.contains(6) && usersPicks.contains(9)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(1) && usersPicks.contains(5) && usersPicks.contains(9)) {
            System.out.println("You Won!");
            scanner.close();
        } else if (usersPicks.contains(3) && usersPicks.contains(5) && usersPicks.contains(7)) {
            System.out.println("You Won!");
            scanner.close();
        }else {
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe!\n Pick a space!");
        ArrayList numsLeft = new ArrayList<>();
        numsLeft.add(1);
        numsLeft.add(2);
        numsLeft.add(3);
        numsLeft.add(4);
        numsLeft.add(5);
        numsLeft.add(6);
        numsLeft.add(7);
        numsLeft.add(8);
        numsLeft.add(9);

        printBoard(numsLeft);

        int totalTurns = 0;
        Scanner scanner = new Scanner(System.in);

        while(totalTurns < 9){
            int userCh = scanner.nextInt();
            int index = userCh-1;
            numsLeft.set(index, "X");
            winCheck(numsLeft, scanner);
            printBoard(numsLeft);
            totalTurns++;
        }
    }
}