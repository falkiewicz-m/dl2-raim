package com.company;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class ThreadServer extends  Thread
{
    BufferedReader input;
    PrintStream output;
    Socket socket;
    String[] request= null;
    String response = null;

    public  ThreadServer(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintStream(socket.getOutputStream());
            String inputrequest = input.readLine();
            request = inputrequest.split(" ");

            //sprawdza, czy GET został odebrany poprawnie
            if(request[0].equals("GET") && request[1].equals("/") && request[request.length-1].equals("HTTP/1.1"))
            {
                Date date = new Date();
                String time_date =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
                response = "<html>\n<title>Wynikowa strona internetowa</title>\n<body>Odpowiedz z serwera: <br>Żądanie: "
                        + inputrequest + "<br>Data, godzina: " + time_date + "<br>Adres IP: " + socket.getLocalAddress()
                        + "<br>Numer portu: " + socket.getPort() + "\n</body>\n</html>";
                System.out.println("Pomyślnie wysłano!");
                output.println(response);
                output.close();
                input.close();
            }
            else
            {
                output.println("Niepoprawne żądanie!");
                output.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


public class RAiMServer {


    public static void main(String[] args)
    {

        ServerSocket server;
        Socket socket;
        ThreadServer t=null;
        int maxThreads = 5;

        String ioe = "Blad strumienia!";
        String se = "Blad gniazda - zerwano polaczenie!";
        String wait = "Oczekiwanie...";
        String connected = "Polaczono z: ";

        try
        {
            server = new ServerSocket(50000, 0, InetAddress.getByName(null));
            while (true)
            {
                try
                {
                    if(maxThreads !=0) //sprawdza dostępność wątków
                    {
                        System.out.println(wait);
                        socket = server.accept();
                        t = new ThreadServer(socket);
                        maxThreads--;
                        t.start();
                        if(t.isAlive() == false)
                            maxThreads++;
                    }
                }
                catch (SocketException e)
                {
                    System.out.println(se);
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(ioe);
            e.printStackTrace();
        }
    }
}
