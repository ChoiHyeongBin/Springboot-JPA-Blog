package com.cos.blog.config.oauth.provider;

public interface OAuth2UserInfo {
	String getProviderId();	// facebooke PK Id, ....
	String getProvider();		// google, facebook, ....
	String getEmail();
	String getName();
}
