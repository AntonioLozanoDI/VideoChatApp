package application.controller.ws;

import com.diproject.commons.model.Payload;
import com.diproject.commons.model.message.MessageType;
import com.diproject.commons.model.message.types.AcceptCall;
import com.diproject.commons.model.message.types.InitCall;
import com.diproject.commons.model.message.types.PauseCall;
import com.diproject.commons.model.message.types.StopCall;
import com.diproject.commons.utils.Utils;
import com.diproject.commons.utils.ws.WebSocketClient;
import com.diproject.commons.utils.ws.payload.PayloadFactory;
import com.diproject.commons.utils.ws.payload.PayloadHandler;
import com.google.gson.Gson;

public class VideoChatHandler implements PayloadHandler {

	public interface OnMessageReceived<T> {
		void onReceive(T msg);
	}

	private static VideoChatHandler instance;

	private WebSocketClient webSocketClient;
	
	private OnMessageReceived<AcceptCall> accept;

	private OnMessageReceived<InitCall> init;

	private OnMessageReceived<PauseCall> pause;

	private OnMessageReceived<StopCall> stop;

	public static VideoChatHandler getInstance() {
		return instance == null ? instance = new VideoChatHandler() : instance;
	}

	@Override
	public void handlePayload(Payload payload) {
		System.out.println(Utils.JSON.toJson(payload));
		MessageType type = MessageType.valueOf(payload.getType());
		switch (type) {
		case ACCEPT_CALL:
			if (accept != null)
				accept.onReceive(PayloadFactory.extract(payload));
			break;
		case INIT_CALL:
			if (init != null)
				init.onReceive(PayloadFactory.extract(payload));
			break;
		case PAUSE_CALL:
			if (pause != null)
				pause.onReceive(PayloadFactory.extract(payload));
			break;
		case STOP_CALL:
			if (stop != null)
				stop.onReceive(PayloadFactory.extract(payload));
			break;
		default:
			break;
		}

	}

	@Override
	public void setWebSocketClient(WebSocketClient webSocketClient) {
		this.webSocketClient = webSocketClient;
	}

	public void setOnAcceptCall(OnMessageReceived<AcceptCall> callback) {
		accept = callback;
	}

	public void setOnInitCall(OnMessageReceived<InitCall> callback) {
		init = callback;
	}

	public void setOnPauseCall(OnMessageReceived<PauseCall> callback) {
		pause = callback;
	}

	public void setOnStopCall(OnMessageReceived<StopCall> callback) {
		stop = callback;
	}
}
