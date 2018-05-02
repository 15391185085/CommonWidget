package com.ieds.gis.base.listener;

public abstract class OnSubmitStyleListener {

	public abstract void onSuccess(String message) throws Exception;

	public abstract void onFailure(String message) throws Exception;

}
