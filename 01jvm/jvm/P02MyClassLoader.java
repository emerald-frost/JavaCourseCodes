import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class P02MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        P02MyClassLoader myClassLoader = new P02MyClassLoader();
        Object instance = myClassLoader.findClass("Hello").newInstance();
        Method method = instance.getClass().getMethod("hello");
        method.invoke(instance);
    }

    @Override
    protected Class<?> findClass(String name) {
        File currentDir = new File(".");
        File clzFile = new File(currentDir, "Hello.xlass");

        try {
            FileInputStream inputStream = new FileInputStream(clzFile);
            byte[] bytes = new byte[(int) clzFile.length()];
            inputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
