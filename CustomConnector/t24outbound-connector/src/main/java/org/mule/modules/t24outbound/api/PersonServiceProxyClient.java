package org.mule.modules.t24outbound.api;


import java.net.URL;
import java.util.List;

import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.t24outbound.definition.Person;
import org.mule.modules.t24outbound.definition.PersonServiceImpl;
import org.mule.modules.t24outbound.definition.PersonServiceImplService;

public class PersonServiceProxyClient {
	
	private PersonServiceImpl port;
    
    public PersonServiceProxyClient() {
    	/* might be in use for another initialization */
    }
     
    public void initialize() throws ConnectionException {
    		
    	/* pick up the WSDL from the location in the JAR */
		URL url = PersonServiceImplService.class.getClassLoader().getResource("wsdl/PersonServiceImpl.wsdl");
		
		/* Fault URL handling */
		if(url==null){
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, "", "Illegal URL address");
		}
		
		/* Create an instance of PersonServiceImplService with the given URL */
		PersonServiceImplService svc = new PersonServiceImplService(url);

		/* Get the WebEndPoint and assign it to PersonServiceImpl */
		port = svc.getPersonServiceImpl();
		
		/* Fault endPoind handling */
		if(port == null){
			throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "", "Destination host unreachable");
		}			
	}
    
    public void initialize(String wsdlLocation) throws ConnectionException {
		
    	/* pick up the WSDL from the location in the JAR */
		URL url = PersonServiceImplService.class.getClassLoader().getResource(wsdlLocation);
		
		/* Fault URL handling */
		if(url==null){
			throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, "", "Illegal URL address");
		}
		
		/* Create an instance of PersonServiceImplService with the given URL */
		PersonServiceImplService svc = new PersonServiceImplService(url);

		/* Get the WebEndPoint and assign it to PersonServiceImpl */
		port = svc.getPersonServiceImpl();
		
		/* Fault endPoind handling */
		if(port == null){
			throw new ConnectionException(ConnectionExceptionCode.CANNOT_REACH, "", "Destination host unreachable");
		}			
	}
    
    /* Web methods */
    
    public Person getPersonById(int id) {    	
        return port.getPerson(id);
    }
    
    public boolean addPerson(Person p){        
    	return port.addPerson(p);
    }
    
    public List<Person> getAllPersons(){
    	return port.getAllPersons();
    }
    
    public boolean deletePersonById(int id){
    	return port.deletePerson(id);
    }
    
}
