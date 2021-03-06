// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();  
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the555 UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
    	if(message.contentEquals("#quit")) {
    		clientUI.display(">SYS> Terminating Client...");
    		quit();
    	} else if (message.contentEquals("#logoff")) {
    		clientUI.display(">SYS> Deconecting Client.");
    		closeConnection();
    	} else if (message.startsWith("#sethost") && !(isConnected())) {
    		clientUI.display(">SYS> setting host "+message.split(" ")[1]);
    		setHost(message.split(" ")[1]);
    	} else if (message.startsWith("#setport") && !(isConnected())) {
    		clientUI.display(">SYS> setting port "+message.split(" ")[1]);
    		setPort(Integer.parseInt(message.split(" ")[1]));
    	} else if (message.contentEquals("#login") && !(isConnected())) {
    		clientUI.display(">SYS> Connecting to "+getHost()+"/"+getPort());
    		openConnection();
    	} else if (message.contentEquals("#gethost")) {
    		clientUI.display(">SYS> Returning host");
    		clientUI.display(getHost());
    	} else if (message.contentEquals("#getport")) {
    		clientUI.display(">SYS> returning port");
    		clientUI.display(Integer.toString(getPort()));
    	}
    	
    	else if(message.startsWith("SERVER MSG>#quit"))
    	
    	
    	
    	else {
    		sendToServer(message);
    	}
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  
  protected void connectionException(Exception exception) {
	  clientUI.display("Connection to server has been lost, closing client.");
	  quit();
  }
  
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
