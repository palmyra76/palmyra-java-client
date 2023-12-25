package com.palmyralabs.palmyra.async;

import java.util.function.Consumer;

public interface ResponseHandler<R> extends AbstractHandler<R>, Consumer<R>{
	
}