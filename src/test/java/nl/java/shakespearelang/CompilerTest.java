package nl.java.shakespearelang;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class CompilerTest {

    @Test
    public void compile() throws IOException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("hello.spl");

        String theString = readFile("hello.spl");

        Compiler compiler = new Compiler(theString);
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

}