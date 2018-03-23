package org.jivesoftware.openfire.plugin.rest.service;

import com.rong.RongCloud;
import com.rong.models.TokenResult;
import org.jivesoftware.openfire.plugin.rest.entity.RongTokenEntity;
import org.jivesoftware.openfire.plugin.rest.enums.ErrEnum;
import org.jivesoftware.openfire.plugin.rest.exceptions.ServiceException;
import org.jivesoftware.openfire.plugin.rest.manager.RongCloudManager;
import org.jivesoftware.openfire.plugin.rest.utils.MediaType;
import org.jivesoftware.openfire.plugin.rest.utils.ResultUtils;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("restapi/v1/rong")
public class RongCloudService {

	private RongCloudManager rongCloudManager;
	private RongCloud rongCloud;
	private static final Logger Log = LoggerFactory.getLogger(RongCloudService.class);
	@PostConstruct
	public void init() {
		rongCloudManager = RongCloudManager.getInstance();
		rongCloud = 
				RongCloud.getInstance(JiveGlobals.getProperty("rongAppKey"),
						JiveGlobals.getProperty("rongAppSecret"));
	}
	@POST
	@Path("findRongToken")
	@Produces({ MediaType.APPLICATION_JSON_CHARSET_UTF8})
	public String findRongToken(@FormParam("username")String username,
			@FormParam("nickName")String nickName,
			@FormParam("picUrl")String picUrl ) throws ServiceException{
		if(username==null||username.isEmpty()){
			return ResultUtils.fail("用户名为空", ErrEnum.ERR_USERNAME_NULL.getValue());
		}
		if(nickName==null){
			nickName = "";
		}
		if(picUrl==null){
			picUrl="";
		}
		RongTokenEntity rt= rongCloudManager.findRongTokenByUsername(username);
		if(rt!=null){
			return ResultUtils.success(rt);
		}
		TokenResult userGetTokenResult =null;
		try {
			userGetTokenResult = rongCloud.user.getToken(username, nickName, picUrl);
		} catch (Exception e) {
			Log.error("service findRongToken err: " + e.getMessage());
			return ResultUtils.fail("服务器错误,请重试", ErrEnum.ERR_SERVER_ERR.getValue());
		}
		if(userGetTokenResult==null||userGetTokenResult.getCode()!=200){
			return ResultUtils.fail("服务器错误,请重试", ErrEnum.ERR_SERVER_ERR.getValue());
		}
		rongCloudManager.saveRongToken(username, userGetTokenResult.getToken());
		rt = new RongTokenEntity();
		rt.setUsername(username);
		rt.setToken(userGetTokenResult.getToken());
		return ResultUtils.success(rt);
	}
	@POST
	@Path("updateRongToken")
	@Produces({ MediaType.APPLICATION_JSON_CHARSET_UTF8})
	public String updateRongToken(@FormParam("username")String username,
			@FormParam("nickName")String nickName,
			@FormParam("picUrl")String picUrl ) throws ServiceException{
		if(username==null||username.isEmpty()){
			return ResultUtils.fail("用户名为空", ErrEnum.ERR_USERNAME_NULL.getValue());
		}
		if(nickName==null){
			nickName = "";
		}
		if(picUrl==null){
			picUrl="";
		}
		TokenResult userGetTokenResult =null;
		try {
			userGetTokenResult = rongCloud.user.getToken(username, nickName, picUrl);
		} catch (Exception e) {
			Log.error("service findRongToken err: " + e.getMessage());
			return ResultUtils.fail("服务器错误,请重试", ErrEnum.ERR_SERVER_ERR.getValue());
		}
		if(userGetTokenResult==null||userGetTokenResult.getCode()!=200){
			return ResultUtils.fail("服务器错误,请重试", ErrEnum.ERR_SERVER_ERR.getValue());
		}
		boolean result = rongCloudManager.updateRongToken(username, userGetTokenResult.getToken());
		if(!result){
			return ResultUtils.fail("服务器错误,请重试", ErrEnum.ERR_SERVER_ERR.getValue());
		}
		RongTokenEntity rt = new RongTokenEntity();
		rt.setUsername(username);
		rt.setToken(userGetTokenResult.getToken());
		return ResultUtils.success(rt);
	}
}