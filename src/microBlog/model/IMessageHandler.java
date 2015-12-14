package microBlog.model;

public interface IMessageHandler {
	void onMessage(String tag, String message);
}
