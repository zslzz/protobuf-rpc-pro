/**
 *   Copyright 2010-2014 Peter Klauser
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
*/
package com.googlecode.protobuf.pro.duplex.execute;

import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;

public class AsyncRpcCallback implements RpcCallback<Message> {

	private volatile boolean done = false;
	private Message message;
    private Runnable onFinish;

    public AsyncRpcCallback(Runnable onFinish){
        this.onFinish = onFinish;
    }

	public void run(Message message) {
		this.message = message;
		synchronized (this) {
			done = true;
			notify();
		}
        onFinish.run();
	}

	public Message getMessage() {
		return message;
	}
	public boolean isDone() {
		return done;
	}

}
