package org.mule.modules.t24outbound.strategy;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.modules.t24outbound.api.PersonServiceProxyClient;

/**
 * Connection Management Strategy
 *
 * @author MuleSoft, Inc.
 */
@ConnectionManagement(configElementName = "config-type", friendlyName = "Connection Managament type strategy")
public class ConnectorConnectionStrategy {
	
	private PersonServiceProxyClient client;
 
	/**
     * Connect
     *
     * @param username
     *            A username. We need a connection key to use connection manager, even if we don't use it internally.
     * @throws ConnectionException
     */
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password) throws ConnectionException {

    	PersonServiceProxyClient client = new PersonServiceProxyClient();
    	try{
        	client.initialize();
        	setClient(client);
    	}catch(ConnectionException e){
    		throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, e.getMessage(), "Connection error");
    	}
    	
    	/* Check credentials -  but this example service does not support authorization 
        try {
            client.login(username, password);
        } catch (InvalidCredentialsException e) {
            throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), "Invalid credentials");
        }
        */
    }
    
    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
        client = null;
    }
    
    /**
     * Are we connected?
     */
    @ValidateConnection
    public boolean isConnected() {
        return client != null;
    }
    
    /**
     * Are we connected?
     */
    @ConnectionIdentifier
    public String connectionId() {
        return "001";
    }
    
	public PersonServiceProxyClient getClient() {
		return client;
	}

	public void setClient(PersonServiceProxyClient client) {
		this.client = client;
	}
	
}
