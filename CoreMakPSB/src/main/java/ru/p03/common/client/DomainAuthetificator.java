/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.p03.common.client;

import java.net.URI;
import ru.p03.common.model.DomainUser;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author timofeevan
 */
public class DomainAuthetificator {
    
    private URI uri;
    
    public DomainAuthetificator(URI uri){
        this.uri = uri;
    }
    
    public DomainUser authenticate(String login, String password) throws Exception{
        if (uri == null){
            throw new Exception("URI cannot be null!");
        }
        
        DomainUser sample = new DomainUser();
        sample.setLogin(login);
        sample.setPassword(password);
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri);
        DomainUser user = target.request().post(
                Entity.entity(sample, MediaType.APPLICATION_JSON), DomainUser.class);
        return user;
    }
     
}
