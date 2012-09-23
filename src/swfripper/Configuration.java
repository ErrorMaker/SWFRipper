package swfripper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 16/09/12
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {
    public static Properties config;
    public static boolean isworking = false;

    public static Properties lerConfig() {
        try {
            config = new Properties();
            config.load(new FileInputStream("config.properties"));
            isworking = true;
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            isworking = false;
        }
        return config;
    }
}
