package com.palmyralabs.palmyra.async;

import java.util.List;
import java.util.function.Consumer;

public interface ListResponseHandler<R> extends AbstractHandler<R>{

	public Consumer<List<R>> getHandler();
}