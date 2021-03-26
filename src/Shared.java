
import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

public class Shared {

    public static final String IMAGES_FOLDER = "D:\\CI_GI_2_2021";
    
    public static final Key SECRET_KEY = 
            new SecretKeySpec( "Une clé secrête : chut !!!!!!!".getBytes(), "AES" );
       
}