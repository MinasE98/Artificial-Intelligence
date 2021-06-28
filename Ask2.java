//MINAS ELEFTHERIOU AM:4245
//LEFTERIS KIRKOS AM:4215
import java.util.Scanner;

class grid {
	public grid[] kids = new grid[16];
	public int next_move; 
    public int empty_boxes; 
    public char[][] boxes = new char[3][3]; 
}
class player{   
    public static int max = 1; 
    public static int min = -1; 
    public static int tie = 0;
}
public class Ask2 { 
    public static void copy_boxes(char[][] array1, char[][] array2){
        int i;
        int j;
        for (i = 0;i < 3;i++){
            for (j = 0;j < 3;j++){
                array2[i][j] = array1[i][j];
            }
        }

    }
	public static boolean check(char[][] boxes) 
    {
        while (
			(boxes[0][0]=='S' && boxes[0][1]=='O' && boxes[0][2]=='S') ||
			(boxes[2][0]=='S' && boxes[2][1]=='O' && boxes[2][2]=='S') ||
			(boxes[0][0]=='S' && boxes[1][0]=='O' && boxes[2][0]=='S') ||
			(boxes[0][1]=='S' && boxes[1][1]=='O' && boxes[2][1]=='S') ||
			(boxes[0][2]=='S' && boxes[1][2]=='O' && boxes[2][2]=='S') ||
			(boxes[0][0]=='S' && boxes[1][1]=='O' && boxes[2][2]=='S') ||
			(boxes[2][0]=='S' && boxes[1][1]=='O' && boxes[0][2]=='S')  )
				return true;
		return false;
    }
    public static int MINIMAX(grid tree, int turn){
        int next_player;
        int position = 0;
        int[] test = new int[16]; 
        int best_move;
		int newposition;
        int i,j;
        if (turn == player.max && check(tree.boxes) == true){	
            return (player.min); 
        }else if (turn == player.min && check(tree.boxes) == true){	
            return (player.max);
        }else if (tree.empty_boxes == 0){	
            return (player.tie);
        }
        if (turn == player.max){
            next_player = player.min;
        }else{
            next_player = player.max;
        }

        for (i = 0;i < 3;i++) {
            for (j = 0;j < 3;j++){
                if (tree.boxes[i][j]=='.'){
                    tree.kids[position] = new grid();
                    tree.kids[position].empty_boxes = tree.empty_boxes - 1;
                    copy_boxes(tree.boxes, tree.kids[position].boxes);
                    tree.kids[position].boxes[i][j] = 'S';
                    test[position] = MINIMAX(tree.kids[position],next_player);  // anadromi xanakali ton eafto tis
                    position++;
                    tree.kids[position] = new grid(); 
                    tree.kids[position].empty_boxes = tree.empty_boxes - 1;
                    copy_boxes(tree.boxes, tree.kids[position].boxes); 
                    tree.kids[position].boxes[i][j] = 'O';
                    test[position] = MINIMAX(tree.kids[position], next_player); 
                    position++;
                }
            }
        }
        if (turn == player.min){
            best_move = test[0];  
			newposition=0;
            for (i = 1;i < position;i++){
                if (test[i] < best_move){
                    best_move = test[i]; 
					newposition=i;
                }
            }
        }else{
            best_move = test[0];
			newposition=0;
            for (i = 0;i < position;i++){
                if (test[i] > best_move){
                    best_move = test[i]; 
					newposition=i;
                }
            }
        }
        tree.next_move =  newposition; 
        return best_move;
    }
    public static void print_boxes(char[][] array1){
        int i;
        int j;
        for (i = 0;i < 3;i++){
            for (j = 0;j < 3;j++){
                System.out.printf("%s ",array1[i][j]);
            }
            System.out.print("\n");
        }
    }
    public static void main(String[] args){ 
        grid root = new grid();
        int turn = player.max;
        int i;
        int j;
        int line;
        int column;
        char character;
        Scanner scan = new Scanner(System.in);
        for (i = 0;i < 3;i++){
            for (j = 0;j < 3;j++){
				root.boxes[i][j]='.';
            }
        }
		root.boxes[1][0]='O';  
        root.empty_boxes = 8;
        System.out.print("GAME START\n");
        print_boxes(root.boxes);
        while(true){  
			if(turn==player.max && check(root.boxes)== true){
			  System.out.print("YOU WIN\n");
              System.out.print("Press A Key\n");
              new java.util.Scanner(System.in).nextLine();
              break;
           }else if(turn==player.min && check(root.boxes)==true){
              System.out.print("GAME OVER\n");
              System.out.print("Press A Key\n");
              new java.util.Scanner(System.in).nextLine();
              break;
           }else if(root.empty_boxes==0){
              System.out.print("TIE\n");
              System.out.print("Press A Key\n");
              new java.util.Scanner(System.in).nextLine();
              break;
           }
            if(turn==player.max){
               System.out.print("COMPUTER'S TURN\n");
               MINIMAX(root,player.max); 
               root=root.kids[root.next_move];
               print_boxes(root.boxes);
               turn=player.min;
            }else{
				System.out.print("YOUR TURN\n"); 
                System.out.print("Choose:S or O\n"); 
                character=scan.next().charAt(0);
                System.out.print("Choose line:\n");
                line = scan.nextInt();
                scan.nextLine();
                System.out.print("Choose column:\n");
                column = scan.nextInt();
                scan.nextLine();
                System.out.print("\n");
                root.boxes[line-1][column-1]= character; 
                root.empty_boxes= root.empty_boxes-1;
                print_boxes(root.boxes);
                turn=player.max;
            }
        }
    }
}
