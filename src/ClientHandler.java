import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    public static ArrayList numsLeft = new ArrayList<>();
    public static ArrayList player1choices = new ArrayList<>();
    public static  ArrayList player2choices = new ArrayList<>();
    public void printBoard(ArrayList numsLeft){
        broadcastMessage("|     |     |     |");
        broadcastMessage("|  "+numsLeft.get(0)+"  |  "+numsLeft.get(1)+"  |  "+numsLeft.get(2)+"  |");
        broadcastMessage("-------------------");
        broadcastMessage("|     |     |     |");
        broadcastMessage("|  "+numsLeft.get(3)+"  |  "+numsLeft.get(4)+"  |  "+numsLeft.get(5)+"  |");
        broadcastMessage("-------------------");
        broadcastMessage("|     |     |     |");
        broadcastMessage("|  "+numsLeft.get(6)+"  |  "+numsLeft.get(7)+"  |  "+numsLeft.get(8)+"  |");
    }
    public boolean winCheck(ArrayList usersPicks) throws IOException {
        if(usersPicks.contains(1) && usersPicks.contains(2) && usersPicks.contains(3)){
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(4) && usersPicks.contains(5) && usersPicks.contains(6)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(7) && usersPicks.contains(8) && usersPicks.contains(9)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(1) && usersPicks.contains(4) && usersPicks.contains(7)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(2) && usersPicks.contains(5) && usersPicks.contains(8)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(3) && usersPicks.contains(6) && usersPicks.contains(9)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(1) && usersPicks.contains(5) && usersPicks.contains(9)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        } else if (usersPicks.contains(3) && usersPicks.contains(5) && usersPicks.contains(7)) {
            bufferedWriter.write("You Won!\n");
            bufferedWriter.flush();
            broadcastMessage(clientUsername +" Won!");
            return true;
        }else {
            return false;
        }
    }

    public ClientHandler(Socket socket){
        try{
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();

            clientHandlers.add(this);

            broadcastMessage("SERVER: "+ clientUsername + " has entered the chat!");
            if(clientHandlers.size() == 2){
                bufferedWriter.write("Welcome to Tic Tac Toe!");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                bufferedWriter.write("Waiting on " + clientHandlers.get(0).clientUsername + " to choose a spot!");
                bufferedWriter.newLine();
                bufferedWriter.flush();
                broadcastMessage("Pick a spot:");
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
            }

        }catch(IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                int numChosen = Integer.parseInt(String.valueOf(messageFromClient.charAt(clientUsername.length() + 19)));

                String xorO;

                if(clientUsername == clientHandlers.get(0).clientUsername){
                    player1choices.add(numChosen);
                    winCheck(player1choices);
                    xorO = "O";
                } else {
                    player2choices.add(numChosen);
                    winCheck(player2choices);
                    xorO = "X";
                }

                numsLeft.set(numChosen-1, xorO);

                broadcastMessage(messageFromClient);
                if (!winCheck(player1choices) && !winCheck(player2choices)){
                    printBoard(numsLeft);
                }
//                else {
//                    player1choices.clear();
//                    player2choices.clear();
//                    numsLeft.clear();
//                    numsLeft.add(1);
//                    numsLeft.add(2);
//                    numsLeft.add(3);
//                    numsLeft.add(4);
//                    numsLeft.add(5);
//                    numsLeft.add(6);
//                    numsLeft.add(7);
//                    numsLeft.add(8);
//                    numsLeft.add(9);
//                }



            } catch(IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler : clientHandlers){

            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
