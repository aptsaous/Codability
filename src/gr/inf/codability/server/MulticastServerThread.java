package gr.inf.codability.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MulticastServerThread implements Runnable
{
    private final static String INET_ADDR = "224.0.0.3";
    private final static int INTELLIJ_PORT = 8888;
    private final static int MCAST_PORT = 6666;

    @Override
    public void run()
    {
        InetAddress addr = null;

        try
        {
            addr = InetAddress.getByName( INET_ADDR );
        }
        catch ( UnknownHostException e )
        {
            e.printStackTrace();
        }

        while ( true )
        {
            try ( DatagramSocket serverSocket = new DatagramSocket() )
            {
                String bcastMsg = String.valueOf( INTELLIJ_PORT );
                DatagramPacket msgPacket = new DatagramPacket( bcastMsg.getBytes(), bcastMsg.getBytes().length, addr, MCAST_PORT );

                serverSocket.send( msgPacket );

                Thread.sleep(5000 ); // sleep((long)(Math.random() * FIVE_SECONDS));

            }
            catch ( IOException | InterruptedException e )
            {
                e.printStackTrace();
                // java.io.IOException: Network is down (sendto failed)
            }


        }

    }
}
