package business.dto;

import dataAccess.entity.User;

public class UserDTO extends AccountDTO {

	private boolean blocked;
	
    public UserDTO() {
        super();
    }

    public UserDTO(Integer id, String username, byte[] password, String email, boolean blocked) {

        super(id, username, password, email);
        this.blocked = blocked;
    }

    public UserDTO(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getBlocked());
    }

    public boolean getBlocked() {
    	return blocked;
    }
    
    public void setBlocked(boolean b) {
    	this.blocked = b;
    }
    
    @Override
    public String toString() {
        return this.getUsername();
    }

}