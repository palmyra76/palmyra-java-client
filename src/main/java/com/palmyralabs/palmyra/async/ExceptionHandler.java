package com.palmyralabs.palmyra.async;

import java.util.function.Function;

public interface ExceptionHandler extends Function<Throwable, Void> {
	public void handle(Throwable t);
	
	@Override
	public default Void apply(Throwable t) {
		handle(t);
		return null;
	}
}
