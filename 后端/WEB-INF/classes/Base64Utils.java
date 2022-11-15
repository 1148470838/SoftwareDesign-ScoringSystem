import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Utils {

    /*加密一个字符串*/
    public static String encode(String str)  {
        String encode = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        return encode;
    }

    //  解码
    public static String decode(String str)  {
        String decode = new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
        return decode;
    }

}