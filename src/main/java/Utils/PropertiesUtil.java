package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    static Properties properties = new Properties();

    public void loadProperties(){
        try {
            InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load((inputStream));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
        String secret = System.getenv(key);

        if (secret == null || secret.isEmpty()){
            if (properties.isEmpty()){
                new PropertiesUtil().loadProperties();
            }
            return properties.getProperty(key);
        }
        else
            return secret;
    }
}
