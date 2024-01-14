import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import dao.PanelDAO;
import servicios.PanelAPIRest;

import java.io.FileInputStream;


public class Servidor {
    public static void main(String[] args) {

//        FileInputStream serviceAccount =
//                new FileInputStream("./serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("gs://paneles-solares-hibernate.appspot.com")
//                .build();
//
//        FirebaseApp.initializeApp(options);


        PanelAPIRest api = new PanelAPIRest(new PanelDAO());
    }
}
