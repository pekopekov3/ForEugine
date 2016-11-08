package org.mule.modules.t24outbound;

import java.util.List;

import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;

import org.mule.modules.t24outbound.config.ConnectorConfig;
import org.mule.modules.t24outbound.definition.Person;

@Connector(name="t24outbound", friendlyName="T24outbound")
public class T24outboundConnector {

    @Config
    ConnectorConfig config;
    
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

     
    @Processor
    public String test(){
        /*
         * 
         * In the real T24 out-bound connector we should implement some data upload
         * So this method should have input parameters (the upload data) in future;
         * then we will check connectivity to the T24 soap service with
         * config.getPersonServiceProxyClient().isConnected(), then 
         * transform the input data in the desired format invoke the RMI upload method 
         * and return successful message.
         * Possible faults are:
         * 1. is not connected to soap service for some reason;
         * 2. cannot invoke the RMI method (lost connection to RMI server)
         * 3. the remote method returned unsuccessful response
         * 4. cannot transform the input data
         * At first glance it seems that we don't use the T24 soap service for 
         * any CRUD operation except validating the connectivity to that service.
         * I suppose that in the end will, use the service just to commit that 
         * every thing went OK!  
         */
    	return "This is a test!";
    }

    @Processor
    public Person getPersonById(int id){
    	return config.getPersonServiceProxyClient().getPersonById(id);
    }
    
    @Processor
    public boolean addPerson(Person p){
    	return config.getPersonServiceProxyClient().addPerson(p);
    }
    
    @Processor
    public boolean deletePersonById(int id){
    	return config.getPersonServiceProxyClient().deletePersonById(id);
    }
    
    @Processor
    public List<Person> getAllPersons(){
    	return config.getPersonServiceProxyClient().getAllPersons();
    }

}