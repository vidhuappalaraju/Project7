package assignment7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientMain extends Application{
	private BufferedReader reader;
	private PrintWriter writer;
	private TextField outgoing = new TextField();
	private TextArea incoming = new TextArea();
	public static void main(String[] args) {
		launch(args);
		
	}
//	public void run() throws Exception{
//		setUpNetworking();
//	}
	
	
	
	@Override
	public void start(Stage primaryStage){
		try {
			setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
		primaryStage.setTitle("User");
		Button Send = new Button();
		Button StartAChat = new Button();
		StartAChat.setText("Start a new chat");
		StartAChat.setLayoutX(350);
		StartAChat.setLayoutY(200);
		Send.setText("Send");
		Send.setLayoutX(275);
		Send.setLayoutY(200);
		Send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
					writer.println(outgoing.getText());
					writer.flush();
					outgoing.clear();
					
			}
		});
		
		incoming.setEditable(false);
		outgoing.setPromptText("Enter something here");
		Pane pane = new Pane();
		outgoing.setPrefSize(250, 15);
		outgoing.setLayoutX(0);
		outgoing.setLayoutY(200);
		incoming.setPrefSize(500, 200);
		pane.getChildren().addAll(Send, StartAChat, outgoing, incoming);
		primaryStage.setScene(new Scene(pane, 500, 500));
		primaryStage.show();
		
	}
	 ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
	 Dialog<String> dialog = new Dialog<>();
	
	
	
	
	 private String getName() {
		 
		 
		 
		 
	       return new String();
	    }
	
	class IncomingReader implements Runnable {
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					
						incoming.appendText(message + "\n");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	

	
//	public void SendMessage(){
//		writer.println(outgoing.getText());
//		writer.flush();
//		outgoing.setText("");
//		outgoing.requestFocus();
//		
//	}
	private void setUpNetworking() throws Exception {
		@SuppressWarnings("resource")
		Socket sock = new Socket("127.0.0.1", 4242);
		InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
		reader = new BufferedReader(streamReader);
		writer = new PrintWriter(sock.getOutputStream());
		System.out.println("networking established");
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	}
}
