import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class getConf {
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("H:\\Users\\Administrator\\Desktop\\工作学习文件\\Web_Service\\src\\confs\\confs.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String value = (String) prop.get(key);

        return value;
    }
    }
