import java.util.Arrays;
import java.util.Random;
import javax.sound.midi.*;
import java.util.*;

public class Main {

    final static char emptyChar = '.';
    final static char wallChar = '#';
    final static char cheeseChar = '%';
    final static char mouseChar = '@';
    final static int ROOM_HEIGHT = 10;
    final static int ROOM_WIDTH = 20;

    public static void main(String[] args){
        
        // Initalization
        Scanner sc = new Scanner(System.in);
        Random randGen = new Random();
        char[][] room = new char[ROOM_HEIGHT][ROOM_WIDTH];
        int[][] cheesePositions = new int[10][2];
        
        // Create board
        placeWalls(room, randGen.nextInt(20), randGen);
        placeCheeses(cheesePositions, room, randGen);
        
        // Place mouse
        int mouseX = randGen.nextInt(room[0].length);
        int mouseY = randGen.nextInt(room.length);
        
        while (room[mouseY][mouseX] != cheeseChar && room[mouseY][mouseX] != wallChar) {
            mouseX = randGen.nextInt(room[0].length);
            mouseY = randGen.nextInt(room.length);
        }
        
        System.out.println("Welcome to the Cheese Eater simulation.");
        System.out.println("=======================================");
        
        System.out.print("Enter the number of steps for this simulation to run: ");
        int steps = sc.nextInt();
        int count = 0;
        
        System.out.println("\nThe mouse has eaten " + count + " cheese!");
        
        printRoom(room, cheesePositions, mouseX, mouseY);
        
        while (steps > 0) {
            
            System.out.print("Enter the next step you'd like the mouse to take (WASD): ");
            char move = sc.next().charAt(0);
            System.out.println("\nThe mouse has eaten " + count + " cheese!");
            
            int[] mousePosition = moveMouse(mouseX, mouseY, room, move);
            
            while (mousePosition[0] == mouseX && mousePosition[1] == mouseY) {
                printRoom(room, cheesePositions, mouseX, mouseY);
                System.out.print("Enter the next step you'd like the mouse to take (WASD): ");
                move = sc.next().charAt(0);
                System.out.println();
                mousePosition = moveMouse(mouseX, mouseY, room, move);
            }
            
            room[mouseY][mouseX] = emptyChar; // Replace old space with emptyChar
            
            mouseX = mousePosition[0]; // New Mouse positions
            mouseY = mousePosition[1];
            
            boolean cheeseEaten = tryToEatCheese(mouseX, mouseY, cheesePositions);
            
            if (cheeseEaten) {
                count++;
            }
            
            printRoom(room, cheesePositions, mouseX, mouseY);a
            
            
            steps--;
        }
        System.out.println("==================================================");
        System.out.println("Thank you for running the Cheese Eater simulation");
    }
    public static void placeWalls(char[][] room, int numberOfWalls, Random randGen){

        // Initialize every space with emptyChar
        for (int i = 0 ; i < room.length ; i++){
            for (int j = 0 ; j < room[i].length ; j++){
                room[i][j] = emptyChar;
            }
        }
        // Place random number of walls
        for (int i = 0; i < numberOfWalls ; i++){
            room[randGen.nextInt(room.length)][randGen.nextInt(room[0].length)] = wallChar;
        }
    }
    public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY){

        // Place mouse
        room[mouseY][mouseX] = mouseChar;
        
        // Place cheese
        for (int i = 0 ; i < cheesePositions.length ; i++) {
            if (cheesePositions[i][0] == -1 && cheesePositions[i][1] == -1) {
                room[mouseY][mouseX] = mouseChar;
            }
            else {
                room[cheesePositions[i][1]][cheesePositions[i][0]] = cheeseChar;
            }
        }
        
        // Print room 
        for (int i = 0 ; i < room.length ; i++){
            for (int j = 0 ; j < room[i].length ; j++){
                System.out.print(room[i][j]);
            }
            System.out.println();
        }

    }
    public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen){
        
        for (int i = 0 ; i < cheesePositions.length ; i++) {
            int row = randGen.nextInt(room.length);
            int col = randGen.nextInt(room[0].length);
            
            while (room[row][col] != emptyChar && room[row][col] != cheeseChar) {
                row = randGen.nextInt(room.length);
                col = randGen.nextInt(room[0].length);
            }
            cheesePositions[i][0] = col;
            cheesePositions[i][1] = row;
        }
        
    }
    
    public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move){
        
        int[] mousePosition = new int[2];
        
        switch (move) {
            case 'W':
                if (mouseY == 0) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
    
                mousePosition[0] = mouseX;
                mousePosition[1] = --mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = ++mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
                
            case 'w':
                if (mouseY == 0) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = mouseX;
                mousePosition[1] = --mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = ++mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
            case 'A':
                if (mouseX == 0) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = --mouseX;
                mousePosition[1] = mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = ++mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
            case 'a':
                if (mouseX == 0) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = --mouseX;
                mousePosition[1] = mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = ++mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
            case 'S':
                if (mouseY == room.length - 1) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = mouseX;
                mousePosition[1] = ++mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = --mouseY;
                    return mousePosition;
                }
                return mousePosition;
                
            case 's':
                if (mouseY == room.length - 1) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = mouseX;
                mousePosition[1] = ++mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = --mouseY;
                    return mousePosition;
                }
                return mousePosition;
            case 'D':
                if (mouseX == room[0].length - 1) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = ++mouseX;
                mousePosition[1] = mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = --mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
            case 'd':
                if (mouseX == room[0].length - 1) {
                    System.out.println("WARNING: Mouse cannot move outside the room.");
                    mousePosition[0] = mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                mousePosition[0] = ++mouseX;
                mousePosition[1] = mouseY;
                
                if (room[mousePosition[1]][mousePosition[0]] == wallChar) {
                    System.out.println("WARNING: Mouse cannot move into wall.");
                    mousePosition[0] = --mouseX;
                    mousePosition[1] = mouseY;
                    return mousePosition;
                }
                
                return mousePosition;
            default:
                System.out.println("WARNING: Didn't recognize move command: " + move);
                return mousePosition;
        }
    }
    
    public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
        
        for (int i = 0 ; i < cheesePositions.length ; i++) {
            if (mouseX == cheesePositions[i][0] && mouseY == cheesePositions[i][1]) {
                cheesePositions[i][0] = -1;
                cheesePositions[i][1] = -1;
                return true;
            }
        }
        return false;
    }
}