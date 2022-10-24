import com.cassey.house.command.FundCommand;
import com.cassey.house.env.Environment;

import java.util.TimeZone;

public class Entry {
    private static void executeWithoutArgs() throws Exception {
        Environment.on(local)
                .execute(FundCommand.class)
                .exit(0);
    }

    private static void executeWithArgs(String[] args) throws Exception {
        String config = args[0], command = args[1];
        String[] params = new String[args.length - 2];
        if (params.length > 0) {
            System.arraycopy(args, 2, params, 0, params.length);
        }
        Environment.on(config).execute(command, params).exit(0);
    }

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("PST"));
        if (args.length > 0) {
            executeWithArgs(args);
        } else {
            executeWithoutArgs();
        }
    }

    private static String CONFIG_ROOT = "/Users/dealmoon/WorkSpace/GIT_LOCAL/house-test/src/main/resources";

    //private static String CONFIG_ROOT = "/home/opt/java/local-test/conf";
    static {
        if (System.getProperty("configurationPath") != null) CONFIG_ROOT = System.getProperty("configurationPath");
    }

    private static final String local = CONFIG_ROOT + "/local.properties";
}
