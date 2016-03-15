/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.shellfire.vpn;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import com.apple.eawt.AppEvent.SystemSleepEvent;
import com.apple.eawt.Application;
import com.apple.eawt.SystemSleepListener;

import de.shellfire.vpn.gui.ConnectionStateChangedEvent;
import de.shellfire.vpn.gui.ConnectionStateListener;
import de.shellfire.vpn.gui.ShellfireVPNMainForm;
import de.shellfire.vpn.gui.Util;
import de.shellfire.vpn.gui.Util.ExceptionThrowingReturningRunnable;
import de.shellfire.vpn.gui.VpnConsole;
import de.shellfire.vpn.gui.VpnException;

/**
 * 
 * @author bettmenn
 */
public class Controller {

	private static Controller instance;
	private final ShellfireVPNMainForm view;
	private final ShellfireService service;
	private Connection connection;
	private LinkedList<ConnectionStateListener> connectionStateListeners = new LinkedList<ConnectionStateListener>();
	protected boolean disconnectedDueToSleep;
	protected Server lastServerConnectedTo;
	private Boolean sleepBeingHandled = false;

	private Controller(ShellfireVPNMainForm view, ShellfireService service) {
		this.view = view;
		this.service = service;
	}

	public static Controller getInstance(ShellfireVPNMainForm view, ShellfireService service) {
		if (instance == null) {
			instance = new Controller(view, service);
		}
		return instance;
	}
	
	public void connect(Server server, Reason reason) {
		Protocol procotol = this.view.getSelectedProtocol();

		this.connect(server, procotol, reason);
	}

	public void connect(Server server, Protocol protocol, Reason reason) {

		System.out.println("connect(Server, Protocol, Reason) - setting connected");

		try {
			this.connection = new Connection(this, reason);
			this.connection.setConnecting();
			this.connectionStateChanged();

			class ConnectionPreparer extends Thread {

				private Server server;
				private Protocol protocol;
				private Reason reason;

				public ConnectionPreparer(Server server, Protocol protocol, Reason reason) {
					this.server = server;
					this.protocol = protocol;
					this.reason = reason;
				}

				@Override
				public void run() {
					Vpn vpn = service.getVpn();

					boolean success = true;
					boolean downloadAndStoreCertificates = false;

					if (!service.certificatesDownloaded())
						downloadAndStoreCertificates = true;

					// change server if required, protocol will be unchanged
					// here
					if (vpn.getServerId() != server.getServerId()) {
						success &= switchServerTo(server);
						downloadAndStoreCertificates = true;
					}

					if (vpn.getProtocol() != protocol) {
						boolean switchProtocolSuccesful = switchProtocolTo(protocol);

						if (switchProtocolSuccesful) {
							success &= switchProtocolSuccesful;
							vpn.setProtocol(protocol);
						}

						downloadAndStoreCertificates = true;
					}

					if (success) {
						connect(downloadAndStoreCertificates, reason);
					} else {
						// inform view that we are disconnected right now
					  
						try {
              connectionStateChanged();
            } catch (RemoteException e) {
              Util.handleException(e);
            }
					}
				}
			}

			(new ConnectionPreparer(server, protocol, reason)).start();
		} catch (Exception e) {
			Util.handleException(e);
		}

	}
	

	

	

	private void connect(boolean downloadAndStoreCertificates, Reason reason) {
		if (downloadAndStoreCertificates) {
			service.downloadAndStoreCertificates();
		}

		Vpn vpn = this.service.getVpn();
		this.connection.setVpn(vpn);

		String params = this.service.getParametersForOpenVpn();

		this.connection.setParametersForOpenVpn(params);
		this.connection.start();

	}

	public ConnectionState getCurrentConnectionState() throws RemoteException {
		if (this.connection == null) {
			try {
				this.connection = new Connection(this, Reason.NoConnectionYet);
			} catch (Exception e) {
				Util.handleException(e);
			}
		}
		if (this.connection == null) {
			return ConnectionState.Disconnected;
		} else {
			return this.connection.getConnectionState();
		}
		
	}

	public void connectionStateChanged() throws RemoteException {
		ConnectionStateChangedEvent e = new ConnectionStateChangedEvent(this.getCurrentConnectionState());
		if (this.connection != null) {
			e.setServer(this.connection.getServer());
		}

		for (ConnectionStateListener listener : this.connectionStateListeners) {
			listener.connectionStateChanged(e);
		}

	}

	public void disconnect(Reason reason) throws RemoteException {
		if (this.connection != null) {
			this.connection.disconnect(reason);
		}

		this.connectionStateChanged();
	}

	public Reason getReasonForStateChange() {
		if (this.connection == null) {
			return Reason.None;
		} else {
			return this.connection.getReasonForStateChange();
		}

	}

	/**
	 * switches to the specified server
	 * 
	 * @return returns true if switch okay, false if not allowed to or other
	 *         error
	 */
	private boolean switchServerTo(Server server) {
		boolean maySwitch = this.service.maySwitchToServer(server);

		if (!maySwitch) {
			return false;
		}

		boolean switchWorked = this.service.setServerTo(server);
		System.out.println("Switch to server worked: " + switchWorked);

		if (!switchWorked) {
			return false;
		}

		return true;
	}

	/**
	 * switches to the specified Protocol
	 * 
	 * @return returns true if switch okay, false if not allowed to or other
	 *         error
	 */
	private boolean switchProtocolTo(Protocol protocol) {
		boolean switchWorked = this.service.setProtocolTo(protocol);

		return switchWorked;
	}

	public Server connectedTo() throws RemoteException {
		if (this.connection == null)
			return null;
		else if (this.connection.getConnectionState() != ConnectionState.Connected)
			return null;
		else
			return this.connection.getServer();
	}

	public void setConnection(Connection c) {
		this.connection = c;
	}

	public void registerConnectionStateListener(ConnectionStateListener listener) {
		if (!this.connectionStateListeners.contains(listener)) {
			this.connectionStateListeners.add(listener);
		}
	}
	

}