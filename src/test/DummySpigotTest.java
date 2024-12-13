import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DummySpigotTest extends DummyAbstractServer implements InvocationHandler {
    @Override
    protected InvocationHandler main() {
        return new DummySpigotTest();
    }

    private static boolean run = false;

    @Test
    void test() {
        if (run) throw new IllegalStateException("Server is already running");
        run = true;
        DummyAbstractServer.print("Running tests... with Java " + System.getProperty("java.version"));
        runServer();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        switch (method.getName()) {
            case "getItemFactory":
                return TinyReflection.CraftItemFactoryInstance;
            case "getName":
                return "XSeries Unit Test";
            case "getVersion":
                return TinyReflection.IMPL_VER + " (MC: " + TinyReflection.getMCVersion() + ')';
            case "getBukkitVersion":
                return TinyReflection.BUKKIT_VER;
            case "getLogger":
                return TinyReflection.LOGGER;
            case "getUnsafe":
                return TinyReflection.getCraftMagicNumberInstance();
            case "createBlockData":
                break; // Todo
        }

        throw new UnsupportedOperationException(String.valueOf(method));
    }
}
