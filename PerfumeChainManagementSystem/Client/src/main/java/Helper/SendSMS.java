package Helper;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SendSMS {

    public static final String ACCOUNT_SID = "ACf8bdf065d6d01b9fea1b7dadf72bd14a";
    public static final String AUTH_TOKEN = "9051ea81e32a30dbd6fa382ef81f4d84";

    public static void sendMessage(String message1) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+40747092905"),
                        new com.twilio.type.PhoneNumber("+12543212258"),
                        message1)
                .create();

        System.out.println("Message was sent");
    }
}