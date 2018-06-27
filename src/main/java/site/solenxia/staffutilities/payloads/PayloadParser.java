package site.solenxia.staffutilities.payloads;

import org.bson.Document;
import site.solenxia.staffutilities.StaffUtilities;
import site.solenxia.staffutilities.payloads.types.*;

public class PayloadParser{

	public static void parse(Document document){
		String server = document.getString("server");
		String type = document.getString("type");

		Payload payload = getPayloadFromType(type);
		if(payload == null){
			StaffUtilities.getInstance().getLogger().severe("Failed to parse payload - " + document.toJson());
			return;
		}

		payload.fromDocument(document);
		payload.broadcast();

	}

	private static Payload getPayloadFromType(String type){
		switch(type.toLowerCase()){
			case "staffchat":
				return new StaffChatPayload();

			case "report":
				return new ReportPayload();

			case "request":
				return new RequestPayload();

			case "serverswitch":
				return new ServerSwitchPayload();

		}
		return null;
	}

}
