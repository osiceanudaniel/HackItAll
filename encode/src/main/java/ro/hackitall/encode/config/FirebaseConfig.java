package ro.hackitall.encode.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@Configuration
public class FirebaseConfig {

    @Bean
    public DatabaseReference firebaseDatabse() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${firebase.database.url}")
    private String databaseUrl;

    @Value("${firebase.config.path}")
    private String configPath;

    @Value("${firebase.accountkey}")
    String firebaseKey;

    @PostConstruct
    public void init() throws IOException {

        /**
         * https://firebase.google.com/docs/server/setup
         *
         * Create service account , download json
         **/

        File file = new ClassPathResource(configPath).getFile();
        FileInputStream serviceAccount = new FileInputStream(file);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();
        FirebaseApp.initializeApp(options);

    }
}
