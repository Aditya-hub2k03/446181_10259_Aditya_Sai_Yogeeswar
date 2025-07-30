package demo;

import org.apache.logging.log4j.*;


public class HelloWorld {

    static {
        System.setProperty("className", HelloWorld.class.getSimpleName());
    }

    private static final Logger logger = LogManager.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        logger.info("Hello, World from Log4j in a Modular Project!");
        System.out.println("Console message: Hello, World!");
    }
}
