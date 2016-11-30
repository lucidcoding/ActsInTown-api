package uk.co.luciditysoftware.actsintown.api.security;

import org.springframework.security.core.GrantedAuthority;
//NO LONGER NEEDED?
public class CustomGrantedAuthority implements GrantedAuthority{

    private static final long serialVersionUID = 1L;
    private String name;
     
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getAuthority() {
        return this.name;
    }
}