import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{

    private static final int BYTES_SIZE=1024;
    private static final int DEFAULT_LEN = -1;

    public static void main(String[] args) {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        try {
            Class clazz = helloClassLoader.loadClass("Hello");
            Object newInstance = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("hello",null);
            method.setAccessible(true);
            method.invoke(newInstance,null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = fileConvert2Bytes();
        return defineClass(name,bytes,0,bytes.length);
    }

    private byte[] fileConvert2Bytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            File file = new File(System.getProperty("user.dir")+"\\Hello.xlass");
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[BYTES_SIZE];
            int len = DEFAULT_LEN;
            while ((len=fis.read(bytes))!= DEFAULT_LEN) {
                for (int i=0;i<len;i++){
                    bytes[i]= (byte) (255-bytes[i]);
                }
                baos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
