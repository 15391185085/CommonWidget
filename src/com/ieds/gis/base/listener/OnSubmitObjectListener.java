package com.ieds.gis.base.listener;

public abstract class OnSubmitObjectListener<T> {

	public abstract void onSuccess(T o) throws Exception;

	public abstract void onFailure(T o) throws Exception;

}
