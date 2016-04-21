package myserver.api.modules;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import myserver.api.modules.service.UserService;

@Component
public class FacadeService {

	@Resource(name="userService")
	protected UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
