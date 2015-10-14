package client.login;

import client.base.*;
import client.facade.Facade;
import client.misc.*;
import client.state.IStateBase;
import client.state.StateManager;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

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

	@Override
	public void signIn() {
		
		String username = this.getLoginView().getLoginUsername();
		String password = this.getLoginView().getLoginPassword();
		
		// If log in succeeded
		getLoginView().closeModal();
		loginAction.execute();
	}

	@Override
	public void register() {
		// TODO: Check what state we're in
		// TODO: register new user (which, if successful, also logs them in)
		IStateBase state = stateManager.getCurrentState();
		
		//TODO Pass in the username and password
		
		String username = this.getLoginView().getLoginUsername();
		String password = this.getLoginView().getLoginPassword();
	
		boolean registered = state.register(username, password);
		// If register succeeded
		if( registered ) {
			getLoginView().closeModal();
			loginAction.execute();
		} else {
			//TODO
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

