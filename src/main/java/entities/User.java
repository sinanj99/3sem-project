/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Obaydah Mohamad
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "username", length = 25)
    private String username;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    @JoinTable(
        name = "roles_assigned", 
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")}, 
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @ManyToMany
    private List<Role> roleList;
    
    public User(){}
    
    public User(String username, String password){
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        roleList = new ArrayList<>();
    }
    
    public boolean verifyPassword(String pw){
        return(BCrypt.checkpw(pw, password));
    }
    
    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
          return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roleList) {
          rolesAsStrings.add(role.getRole());
        }
        return rolesAsStrings;
    }
    
    public void addRole(Role r){
        roleList.add(r);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
