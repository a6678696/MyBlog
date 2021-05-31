import com.ledao.util.StringUtil;

import java.io.IOException;

/**
 * @author LeDao
 * @company
 * @create 2021-05-31 7:20
 */
public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(StringUtil.readCodeStyle());
        StringUtil.changeCodeStyle("github");
        System.out.println(StringUtil.readCodeStyle());
    }
}
