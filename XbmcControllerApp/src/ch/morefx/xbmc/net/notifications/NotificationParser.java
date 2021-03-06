package ch.morefx.xbmc.net.notifications;

import org.json.JSONException;
import org.json.JSONObject;

import ch.morefx.xbmc.XbmcExceptionHandler;

public class NotificationParser {

	private static final String TAG = NotificationParser.class.getSimpleName();

	/**
	 * Creates a Notification object from json.
	 * @param json The json string received from XBMC
	 * @return Notification
	 * @throws Exception
	 */
	public static Notification parse(String json) 
			throws NotificationParserException {

		String method = null;
		JSONObject jsonObject = null;

		try 
		{
			jsonObject = new JSONObject(json);
			method = jsonObject.getString("method");
		} catch (JSONException e) {
			XbmcExceptionHandler.handleException(TAG, e);
		}
		
		XbmcNotification notification = null;

		if (method.equals(PlayerOnPlayNotification.METHOD))
			notification = new PlayerOnPlayNotification();

		if (method.equals(PlayerOnPauseNotification.METHOD))
			notification = new PlayerOnPauseNotification();

		if (method.equals(PlayerOnStopNotification.METHOD))
			notification = new PlayerOnStopNotification();
		
		if (method.equals(PlayerOnSeekNotification.METHOD))
			notification = new PlayerOnSeekNotification();
		
		if (method.equals(PlayerOnSpeedChangedNotification.METHOD))
			notification = new PlayerOnSpeedChangedNotification();
		
		if ((VideoLibraryNotification.isVideoLibraryNotification(method)))
			notification = new VideoLibraryNotification();
		
		if (SystemNotification.isSystemNotification(method))
			notification =  new SystemNotification();
		
		if (NothingToDoNotification.isNothingToDo(method))
			return new NothingToDoNotification();

		if (notification != null) {
			try {
				notification.setJSONObject(jsonObject);
				return notification;
			} catch (JSONException jsonex) {
				throw new NotificationParserException("Json Exception", jsonex);
			}
		}
		
		throw new NotificationParserException("Unable to create Notification from method " + method);
	}
}
