package client.windows;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import client.manager.RequestManager;
import client.manager.ThreadManager;
import shared.messages.*;
import shared.options.Options;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



/**
 * Login and Register window
 */
public class LoginAndRegister {

	private JFrame frame; // Frame of this window
	private JTextField loginLoginField; // Text field for login form in login form
	private JPasswordField loginPasswordField; // Text field for password form in login form
	private JTextField registerLoginField; // Text field for login form in register form
	private JPasswordField registerPasswordField; // Text field for password form in register form
	private Message dataMessage;
	private CheckConnectionMessage checkConnectionMessage;
	private Timer updateDataTimer; // Timer that checks if data was updated
	private Timer checkConnectionTimer;
	private JPanel loginConnectionIndicator;
	private JPanel registerConnectionIndicator;
	private ThreadManager threadManager;
	
	MainContentWindow mainContentWindow;

	/**
	 * Create the application.
	 */
	public LoginAndRegister(MainContentWindow mainContentWindow) {
		initialize();
		frame.setVisible(true);
		this.mainContentWindow = mainContentWindow;
	}

	protected Message getDataMessage() {
		return dataMessage;
	}

	protected void setDataMessage(Message dataMessage) {
		this.dataMessage = dataMessage;
	}

	protected CheckConnectionMessage getCheckConnectionMessage() {
		return checkConnectionMessage;
	}

	protected void setCheckConnectionMessage(CheckConnectionMessage checkConnectionMessage) {
		this.checkConnectionMessage = checkConnectionMessage;
	}

	private ThreadManager getThreadManager() {
		return threadManager;
	}

	private void setThreadManager(ThreadManager threadManager) {
		this.threadManager = threadManager;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println("CLIENT_SIDE:Initializing application");
		this.setDataMessage(new CheckConnectionMessage());
		this.setCheckConnectionMessage(new CheckConnectionMessage());
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(64, 139, 64));
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel loginWindowPanel = new JPanel();
		loginWindowPanel.setBackground(new Color(64, 139, 64));
		frame.getContentPane().add(loginWindowPanel, "loginWindowPanel");
		SpringLayout sl_loginWindowPanel = new SpringLayout();
		loginWindowPanel.setLayout(sl_loginWindowPanel);
		
		JLabel loginHeadLabel = new JLabel("Sign in to your account or");
		loginHeadLabel.setForeground(Color.BLACK);
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginHeadLabel, 20, SpringLayout.NORTH, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginHeadLabel, 101, SpringLayout.WEST, loginWindowPanel);
		loginHeadLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		loginWindowPanel.add(loginHeadLabel);
		
		JButton loginRegisterNewAccountButton = new JButton("register a new account");

		loginRegisterNewAccountButton.addActionListener(new LoginRegisterNewAccountButtonListener());
		
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginRegisterNewAccountButton, 9, SpringLayout.SOUTH, loginHeadLabel);
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginRegisterNewAccountButton, 109, SpringLayout.WEST, loginWindowPanel);
		loginRegisterNewAccountButton.setOpaque(false);
		loginRegisterNewAccountButton.setForeground(new Color(184, 134, 11));
		loginRegisterNewAccountButton.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		loginRegisterNewAccountButton.setFocusPainted(false);
		loginRegisterNewAccountButton.setContentAreaFilled(false);
		loginRegisterNewAccountButton.setBorderPainted(false);
		loginWindowPanel.add(loginRegisterNewAccountButton);
		
		JButton loginSignInButton = new JButton("Sign in");
		loginSignInButton.addActionListener(new LoginSignInButtonListener());
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginSignInButton, -133, SpringLayout.SOUTH, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginSignInButton, 178, SpringLayout.WEST, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginSignInButton, -20, SpringLayout.SOUTH, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginSignInButton, -178, SpringLayout.EAST, loginWindowPanel);
		loginSignInButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		loginWindowPanel.add(loginSignInButton);
		
		loginLoginField = new JTextField();
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginLoginField, 37, SpringLayout.SOUTH, loginRegisterNewAccountButton);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginLoginField, 127, SpringLayout.SOUTH, loginRegisterNewAccountButton);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginLoginField, -30, SpringLayout.EAST, loginWindowPanel);
		loginLoginField.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		loginLoginField.setColumns(10);
		loginLoginField.setBackground(new Color(239, 239, 239));
		loginWindowPanel.add(loginLoginField);
		
		loginPasswordField = new JPasswordField();
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginPasswordField, 30, SpringLayout.SOUTH, loginLoginField);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginPasswordField, 120, SpringLayout.SOUTH, loginLoginField);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginPasswordField, -30, SpringLayout.EAST, loginWindowPanel);
		loginPasswordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		loginPasswordField.setBackground(new Color(239, 239, 239));
		loginWindowPanel.add(loginPasswordField);
		
		JLabel loginAnonimousPersonIcon = new JLabel("");
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginAnonimousPersonIcon, -464, SpringLayout.EAST, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginLoginField, 0, SpringLayout.EAST, loginAnonimousPersonIcon);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginAnonimousPersonIcon, 0, SpringLayout.SOUTH, loginLoginField);
		loginAnonimousPersonIcon.setIcon(new ImageIcon(LoginAndRegister.class.getResource("/client/resources/Anonimous user.png")));
		loginWindowPanel.add(loginAnonimousPersonIcon);
		
		JLabel loginKeyIcon = new JLabel("");
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginPasswordField, 0, SpringLayout.EAST, loginKeyIcon);
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginKeyIcon, 0, SpringLayout.NORTH, loginPasswordField);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginKeyIcon, 0, SpringLayout.EAST, loginAnonimousPersonIcon);
		loginKeyIcon.setIcon(new ImageIcon(LoginAndRegister.class.getResource("/client/resources/Key.png")));
		loginWindowPanel.add(loginKeyIcon);
		
		loginConnectionIndicator = new JPanel();
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginConnectionIndicator, 454, SpringLayout.WEST, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginConnectionIndicator, -20, SpringLayout.SOUTH, loginWindowPanel);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, loginConnectionIndicator, -10, SpringLayout.EAST, loginWindowPanel);
		loginConnectionIndicator.setBackground(Color.RED);
		loginWindowPanel.add(loginConnectionIndicator);
		
		JLabel loginServerAccesebilityServerLabel = new JLabel("Server");
		sl_loginWindowPanel.putConstraint(SpringLayout.WEST, loginServerAccesebilityServerLabel, 46, SpringLayout.EAST, loginSignInButton);
		sl_loginWindowPanel.putConstraint(SpringLayout.SOUTH, loginServerAccesebilityServerLabel, -105, SpringLayout.SOUTH, loginWindowPanel);
		loginServerAccesebilityServerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		loginWindowPanel.add(loginServerAccesebilityServerLabel);
		
		JLabel registerServerAccesebilityAccesebilityLabel_1 = new JLabel("accesebility:");
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, loginConnectionIndicator, 6, SpringLayout.SOUTH, registerServerAccesebilityAccesebilityLabel_1);
		sl_loginWindowPanel.putConstraint(SpringLayout.NORTH, registerServerAccesebilityAccesebilityLabel_1, 6, SpringLayout.SOUTH, loginServerAccesebilityServerLabel);
		sl_loginWindowPanel.putConstraint(SpringLayout.EAST, registerServerAccesebilityAccesebilityLabel_1, -10, SpringLayout.EAST, loginWindowPanel);
		registerServerAccesebilityAccesebilityLabel_1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		loginWindowPanel.add(registerServerAccesebilityAccesebilityLabel_1);
		
		JPanel registerWindowPanel = new JPanel();
		registerWindowPanel.setBackground(new Color(64, 139, 64));
		frame.getContentPane().add(registerWindowPanel, "registerWindowPanel");
		SpringLayout sl_registerWindowPanel = new SpringLayout();
		registerWindowPanel.setLayout(sl_registerWindowPanel);
		
		JLabel registerHeadLabel = new JLabel("Create a new account or");
		registerHeadLabel.setForeground(Color.BLACK);
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerHeadLabel, 20, SpringLayout.NORTH, registerWindowPanel);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerHeadLabel, 112, SpringLayout.WEST, registerWindowPanel);
		registerHeadLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		registerWindowPanel.add(registerHeadLabel);
		
		JButton registerGoBackToSignInButton = new JButton("go back to sign in window");
		
		registerGoBackToSignInButton.addActionListener(new RegisterGoBackToSignInButtonListener());
		
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerGoBackToSignInButton, 7, SpringLayout.SOUTH, registerHeadLabel);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerGoBackToSignInButton, 90, SpringLayout.WEST, registerWindowPanel);
		registerGoBackToSignInButton.setOpaque(false);
		registerGoBackToSignInButton.setForeground(new Color(184, 134, 11));
		registerGoBackToSignInButton.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		registerGoBackToSignInButton.setFocusPainted(false);
		registerGoBackToSignInButton.setContentAreaFilled(false);
		registerGoBackToSignInButton.setBorderPainted(false);
		registerWindowPanel.add(registerGoBackToSignInButton);
		
		registerLoginField = new JTextField();
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerLoginField, 39, SpringLayout.SOUTH, registerGoBackToSignInButton);
		sl_registerWindowPanel.putConstraint(SpringLayout.EAST, registerLoginField, -30, SpringLayout.EAST, registerWindowPanel);
		registerLoginField.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		registerLoginField.setColumns(10);
		registerLoginField.setBackground(new Color(239, 239, 239));
		registerWindowPanel.add(registerLoginField);
		
		registerPasswordField = new JPasswordField();
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerPasswordField, 30, SpringLayout.SOUTH, registerLoginField);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerPasswordField, 0, SpringLayout.WEST, registerLoginField);
		sl_registerWindowPanel.putConstraint(SpringLayout.EAST, registerPasswordField, 0, SpringLayout.EAST, registerLoginField);
		registerPasswordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
		registerPasswordField.setBackground(new Color(239, 239, 239));
		registerWindowPanel.add(registerPasswordField);
		
		JLabel registerAnonimousPersonIcon = new JLabel("");
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerLoginField, 0, SpringLayout.EAST, registerAnonimousPersonIcon);
		sl_registerWindowPanel.putConstraint(SpringLayout.SOUTH, registerLoginField, 0, SpringLayout.SOUTH, registerAnonimousPersonIcon);
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerAnonimousPersonIcon, 39, SpringLayout.SOUTH, registerGoBackToSignInButton);
		sl_registerWindowPanel.putConstraint(SpringLayout.EAST, registerAnonimousPersonIcon, -464, SpringLayout.EAST, registerWindowPanel);
		registerAnonimousPersonIcon.setIcon(new ImageIcon(LoginAndRegister.class.getResource("/client/resources/Anonimous user.png")));
		registerWindowPanel.add(registerAnonimousPersonIcon);
		
		JLabel registerKeyIcon = new JLabel("");
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerKeyIcon, 30, SpringLayout.SOUTH, registerAnonimousPersonIcon);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerKeyIcon, 0, SpringLayout.WEST, registerAnonimousPersonIcon);
		registerKeyIcon.setIcon(new ImageIcon(LoginAndRegister.class.getResource("/client/resources/Key.png")));
		registerWindowPanel.add(registerKeyIcon);
		
		JButton registerSignUpButton = new JButton("Sign up");
		sl_registerWindowPanel.putConstraint(SpringLayout.SOUTH, registerPasswordField, -56, SpringLayout.NORTH, registerSignUpButton);
		registerSignUpButton.addActionListener(new RegisterSignUpButtonListener());
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerSignUpButton, -133, SpringLayout.SOUTH, registerWindowPanel);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerSignUpButton, 178, SpringLayout.WEST, registerWindowPanel);
		sl_registerWindowPanel.putConstraint(SpringLayout.SOUTH, registerSignUpButton, -20, SpringLayout.SOUTH, registerWindowPanel);
		sl_registerWindowPanel.putConstraint(SpringLayout.EAST, registerSignUpButton, -178, SpringLayout.EAST, registerWindowPanel);
		registerSignUpButton.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		registerWindowPanel.add(registerSignUpButton);
		
		registerConnectionIndicator = new JPanel();
		sl_registerWindowPanel.putConstraint(SpringLayout.SOUTH, registerConnectionIndicator, -20, SpringLayout.SOUTH, registerWindowPanel);
		sl_registerWindowPanel.putConstraint(SpringLayout.EAST, registerConnectionIndicator, -10, SpringLayout.EAST, registerWindowPanel);
		registerConnectionIndicator.setBackground(Color.RED);
		registerWindowPanel.add(registerConnectionIndicator);
		
		JLabel registerServerAccesebilityServerLabel = new JLabel("Server");
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerConnectionIndicator, 0, SpringLayout.WEST, registerServerAccesebilityServerLabel);
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerServerAccesebilityServerLabel, 0, SpringLayout.NORTH, registerSignUpButton);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerServerAccesebilityServerLabel, 46, SpringLayout.EAST, registerSignUpButton);
		registerServerAccesebilityServerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		registerWindowPanel.add(registerServerAccesebilityServerLabel);
		
		JLabel registerServerAccesebilityAccesebilityLabel = new JLabel("accesebility:");
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerConnectionIndicator, 6, SpringLayout.SOUTH, registerServerAccesebilityAccesebilityLabel);
		sl_registerWindowPanel.putConstraint(SpringLayout.NORTH, registerServerAccesebilityAccesebilityLabel, 6, SpringLayout.SOUTH, registerServerAccesebilityServerLabel);
		sl_registerWindowPanel.putConstraint(SpringLayout.WEST, registerServerAccesebilityAccesebilityLabel, 46, SpringLayout.EAST, registerSignUpButton);
		registerServerAccesebilityAccesebilityLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		registerWindowPanel.add(registerServerAccesebilityAccesebilityLabel);
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setThreadManager(new ThreadManager(this.getCheckConnectionMessage()));
		
		updateDataTimer = new Timer(Options.CHECK_FOR_UPDATED_DATA_INTERVAL, new UpdateDataTimerListener());
		checkConnectionTimer = new Timer(Options.CHECK_FOR_CONNECTION_INTERVAL, new CheckConnectionTimerListener());
		updateDataTimer.start();
		checkConnectionTimer.start();
		
		System.out.println("CLIENT_SIDE:Check info timer has started");
		System.out.println("CLIENT_SIDE:Client side initialized successfully");
		for (int i = 0; i < 1000; i++) {
			System.out.print("-");
		}
		System.out.println("/n");
	}

	/**
	 * Listener class for loginRegisterNewAccountButton button
	 */
	private class LoginRegisterNewAccountButtonListener implements ActionListener{
		
		/**
		 * Executes when loginRegisterNewAccountButton button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
			cardLayout.show(frame.getContentPane(), "registerWindowPanel");
		}
		
	}
	
	/**
	 * Listener class for registerGoBackToSignInButton button
	 */
	private class RegisterGoBackToSignInButtonListener implements ActionListener{
		
		/**
		 * Executes when registerGoBackToSignInButton button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
			cardLayout.show(frame.getContentPane(), "loginWindowPanel");
		}
		
	}
	
	/**
	 * Listener class for loginSignInButton button
	 */
	private class LoginSignInButtonListener implements ActionListener{
		
		/**
		 * Executes when loginSignInButton button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("CLIENT_SIDE-ACTION:Sign in button pressed");
			if(loginLoginField.getText().length() < 3) {
				JOptionPane.showMessageDialog(frame, "Login must be at least 3 digits", "Login too small", JOptionPane.WARNING_MESSAGE);
			} else if (loginPasswordField.getPassword().length < 3){
				JOptionPane.showMessageDialog(frame, "Password must be at least 3 digits", "Password too small", JOptionPane.WARNING_MESSAGE);
			} else {
				setDataMessage(new SignInMessage(loginLoginField.getText(),
						String.valueOf(loginPasswordField.getPassword())));
				getThreadManager().sendMessage(getDataMessage());
			}
		}
	}
	
	/**
	 * Listener class for registerSignUpButton button
	 */
	private class RegisterSignUpButtonListener implements ActionListener{
		
		/**
		 * Executes when registerSignUpButton button is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("CLIENT_SIDE-ACTION:Sign up button pressed");
			if(registerLoginField.getText().length() < 3) {
				JOptionPane.showMessageDialog(frame, "Login must be at least 3 digits", "Login too small", JOptionPane.WARNING_MESSAGE);
			} else if (registerPasswordField.getPassword().length < 3){
				JOptionPane.showMessageDialog(frame, "Password must be at least 3 digits", "Password too small", JOptionPane.WARNING_MESSAGE);
			} else {
				setDataMessage(new SignUpMessage(registerLoginField.getText(),
						String.valueOf(registerPasswordField.getPassword())));
				getThreadManager().sendMessage(getDataMessage());
			}
		}
	}
	
	/**
	 * Timer that checks if data is updated
	 */
	private class UpdateDataTimerListener implements ActionListener{
		/**
		 * This part of code is called by UpdateDataTimer
		 * Checks whether data was updated
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(getCheckConnectionMessage().isDataUpdated()) { // If data  in check connection message is updated
				if(getCheckConnectionMessage().getResponseState() == ResponseState.SUCCESS) { // And response is successful
					loginConnectionIndicator.setBackground(Color.GREEN); // Set connection indicators to green
					registerConnectionIndicator.setBackground(Color.GREEN);	
				} else {
					loginConnectionIndicator.setBackground(Color.RED); // Else to red
					registerConnectionIndicator.setBackground(Color.RED);
				}
				getCheckConnectionMessage().setDataUpdated(false); // Uncheck data update
			}
			if(dataMessage.isDataUpdated()) { // If data was updated 
				if(dataMessage instanceof SignInMessage) { // Deal with sign in response
					System.out.println("CLIENT_SIDE:SignInData received:");
					SignInMessage castedData = (SignInMessage) dataMessage;
					if(castedData.getResponseState() == ResponseState.SUCCESS) {
						mainContentWindow = new MainContentWindow();
						frame.dispose();
					} else if (castedData.getResponseState() == ResponseState.NO_SUCH_ENTRY_IN_DATABASE) {
						JOptionPane.showMessageDialog(frame, "Invalid login or password, try again", "No such account", JOptionPane.WARNING_MESSAGE);
					} else if (castedData.getResponseState() == ResponseState.ERROR){
						JOptionPane.showMessageDialog(frame, "Could not sign in, error on server happened", "Sign in error", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame, "Unknown error occured", "Unknown", JOptionPane.WARNING_MESSAGE);
					}
					
				} else if (dataMessage instanceof SignUpMessage) { // Deal with sign up response
					System.out.println("CLIENT_SIDE:SignUpData received:");
					SignUpMessage castedData = (SignUpMessage) dataMessage;
					
					if(castedData.getResponseState() == ResponseState.SUCCESS) {
						JOptionPane.showMessageDialog(frame, "Account was successfuly created\nNow you can go back to sign in window", "Account was created", JOptionPane.INFORMATION_MESSAGE);
					} else if (castedData.getResponseState() == ResponseState.LOGIN_AND_PASSWORD_ARE_TOO_SMALL) {
						JOptionPane.showMessageDialog(frame, "Both password and login must be at least 3 digits", "Small password or login", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frame, "Could not sign up, error happened, try again", "Sign up error", JOptionPane.WARNING_MESSAGE);
					}
					
				} else {
					System.out.println("CLIENT_SIDE:ERROR:Unknown data received from server!!!");
					System.out.println(dataMessage);
				}
				System.out.println(dataMessage);
				dataMessage.setDataUpdated(false); // Uncheck data update
			}
		}
	}
	
	/**
	 * Timer that checks if data is updated
	 */
	private class CheckConnectionTimerListener implements ActionListener{
		/**
		 * This part of code is called every 50 milliseconds to check if 
		 * data was updated
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(getThreadManager().isCheckConnectionMessageThreadAlive()) { // Check connection thread is alive		
				if(getThreadManager().getCheckConnectionThreadDuration() >= Options.MAX_TIME_RESPOND) { // Time has expired
					System.out.println("CLIENT_SIDE:ERROR:Time for response expired waiting for reconnect");
					loginConnectionIndicator.setBackground(Color.RED);
					registerConnectionIndicator.setBackground(Color.RED);	
				} 	
			} else { // Check connection thread is dead
				setCheckConnectionMessage(new CheckConnectionMessage()); // Create a new check connection message
				getThreadManager().sendMessage(getCheckConnectionMessage());	// And send it to server			
			}
			
		}
		
	}
}
