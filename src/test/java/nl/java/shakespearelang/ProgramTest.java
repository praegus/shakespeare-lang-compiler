package nl.java.shakespearelang;

import nl.java.shakespearelang.executor.Executor;
import nl.java.shakespearelang.parser.Program;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ProgramTest {

    @Test
    public void compile() throws Exception {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("hello.spl");

        String theString = readFile("hellosimple.spl");

        Program program = new Program(theString);

        Executor executor = new Executor();
        executor.executeProgram(program);
    }

    private String readFile(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(filename);

        return IOUtils.toString(is, "UTF8");
    }

}