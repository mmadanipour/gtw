package com.gtw.common;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.gtw.rest.GameRestService;

public class GTWApplication extends Application{

    private Set<Object> singletons = new HashSet<>();

	
	public GTWApplication() {
		this.singletons.add(new GameRestService());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
