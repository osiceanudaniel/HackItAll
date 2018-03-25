package ro.hackitall.encode.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hackitall.encode.service.EncodeService;
import ro.hackitall.encode.service.notification.AndroidPushNotificationsService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by Andrei-Daniel Ene on 3/25/2018.
 */
//@Scheduled(fixedRate = 3000)
@Controller
public class NotificationController {

    private final String TOPIC = "JavaSampleApproach";

    @Autowired
    AndroidPushNotificationsService androidPushNotificationsService;

    @Autowired
    EncodeService encodeService;

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Verificare produse");

//TODO - get produse care expira
        List<String> notificationProducts = encodeService.getProductsNotificationToday();
        StringBuilder message = new StringBuilder();
        message.append("Produsele dumneavoastra care vor expira in urmatoarele zile sunt: ");
        for(String item : notificationProducts) {
            message.append(item + " ");
        }
        message.append(". \n Verificati aplicatia! :)");


        notification.put("body", message);

        JSONObject data = new JSONObject();
        data.put("Key-1", "JSA Data 1");
        data.put("Key-2", "JSA Data 2");

        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        System.out.println("--- Send notification!");
        System.out.println("        Title: " + notification.get("title"));
        System.out.println("        Body : " + notification.get("body"));

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
