package client.main;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import client.windows.LoginAndRegister;
import client.windows.MainContentWindow;

/**
 * Enter class for Client side of the application
 */
public class ClientMain {
	public static LoginAndRegister loginAndRegisterWindow;
	public static MainContentWindow mainContentWindow;
	
	public static void main(String[] args) {
		setLookAndFeel();
		loginAndRegisterWindow = new LoginAndRegister(mainContentWindow); // Creates a new login and register window
	}
	
	/**
	 * Set look and feel for whole application
	 * (Right now sets to "Nimbus" look and feel)
	 */
	public static void setLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    System.out.println("\n\n Error setting look and feel:\n\n");
		    System.out.println(e);
		}
	}

}