package aud.io.fontyspublisher;

public class SharedData {
    private static final int PORT = 1099;
    private static final String REGISTRY_NAME = "145.93.164.112";
    private static final String SERVER_NAME = "partyServer";
    private static final String PUBLISHER_NAME = "serverPublisher";

    public static int getPort() {
        return PORT;
    }

    public static String getRegistryName() {
        return REGISTRY_NAME;
    }

    public static String getServerName() {
        return SERVER_NAME;
    }

    public static String getPublisherName() {
        return PUBLISHER_NAME;
    }
}
