package org.mule.modules.t24outbound.config;

//import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
//import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;
import org.mule.modules.t24outbound.api.PersonServiceProxyClient;



//@Configuration(friendlyName = "Configuration")
@ConnectionManagement(friendlyName = "Configuration")
public class ConnectorConfig {

	
	private PersonServiceProxyClient personServiceProxyClient;
	
	
	
    public PersonServiceProxyClient getPersonServiceProxyClient() {
		return personServiceProxyClient;
	}

	public void setPersonServiceProxyClient(PersonServiceProxyClient personServiceProxyClient) {
		this.personServiceProxyClient = personServiceProxyClient;
	}
	
      
    @Configurable
    @Default("http://localhost:8080/SOAPExample/services/PersonServiceImpl") 
    private String address;
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Connect method and
     * Test connectivity 
     * @parameters - credentials
     */       
    @Connect
    @TestConnectivity
    public void connect(@ConnectionKey String username, @Password String password) throws ConnectionException { 
    	
    	if(personServiceProxyClient == null){
	    	PersonServiceProxyClient client = new PersonServiceProxyClient();
	    	try{
	        	client.initialize(); //alternate: client.initialize("wsdl/PersonServiceImpl.wsdl");
	        	setPersonServiceProxyClient(client);
	    	}catch(ConnectionException e){
	    		throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, e.getMessage(), "Connection error");
	    	}
    	}
    	
    	if(!validateCredntials(username,  password)){
    		throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, "", "Invalid credentials");
    	}
    }
    
    public boolean validateCredntials(String username, String password)  {
    	/* Check credentials -  but this example service does not support authorization 
        try {
        	boolean result = getPersonServiceProxyClient().login(username, password);
        	if(result){
        		setPass(password);
        		setName(name);
        	}
           return getPersonServiceProxyClient().login(username, password);
        } catch (InvalidCredentialsException e) {
            
        }
        */
    	return true;
    }
    
   
    /**
     * Disconnect
     */
    @Disconnect
    public void disconnect() {
    	setPersonServiceProxyClient(null);
    }
    
    /**
     * Check connection state
     */
    @ValidateConnection
    public boolean isConnected() {
        return getPersonServiceProxyClient() != null;
    }



}