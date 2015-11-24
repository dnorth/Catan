package client.login;

import client.base.*;
import client.facade.Facade;
import client.misc.*;
import client.state.IStateBase;
import client.state.JoinGameState;
import client.state.StateManager;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import server.proxy.ClientException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	private StateManager stateManager;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView, StateManager stateManager) {
		super(view);
		this.messageView = messageView;
		this.stateManager = stateManager;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		getLoginView().showModal();
	}

	/**
	 * get username and password from the view
	 * Send verification request to server
	 * if it works:
	 * Create any data objects you need for a player in the pre-game state
	 * close theModal, loginAction.execute
	 * else
	 * show a dialogue to reprompt user for info
	 */
	@Override
	public void signIn() {
		
		IStateBase state = stateManager.getState();
		
		String username = this.getLoginView().getLoginUsername();
		String password = this.getLoginView().getLoginPassword();
		boolean loggedIn;
		String errorMessage = "";
		try {
			loggedIn = state.login(username, password);
		} catch (ClientException e) {
			errorMessage = e.getResponse();
			loggedIn = false;
		}
		
		// If log in succeeded
		if( loggedIn ) {
			login(state, stateManager);
		} else {
			this.messageView.setMessage(errorMessage);
			this.messageView.setTitle("Login Failed.");
			this.messageView.showModal();
		}
	}

	
	@Override
	public void register() {
		// TODO: Check what state we're in
		// TODO: register new user (which, if successful, also logs them in)
		IStateBase state = stateManager.getState();
		
		//TODO Pass in the username and password
		
		String username = this.getLoginView().getRegisterUsername();
		String password = this.getLoginView().getRegisterPassword();
		String validatedPassword = this.getLoginView().getRegisterPasswordRepeat();
	
		boolean registered;
		String errorMessage = "";
		if (!password.equals(validatedPassword)) registered = false;
		else
			try {
				registered = state.register(username, password);
			} catch (ClientException e) {
				errorMessage = e.getResponse();
				registered = false;
			}
		// If register succeeded
		if( registered ) {
			login(state, stateManager);
		} else {
			this.messageView.setMessage(errorMessage);
			this.messageView.setTitle("Register Failed.");
			this.messageView.showModal();
		}
	}
	
	public void login(IStateBase state, StateManager stateManager) {
		getLoginView().closeModal();
		loginAction.execute();
		stateManager.setState(new JoinGameState(state.getFacade()));
	}

	@Override
	public void update(Observable o, Object arg) {

	}

}

