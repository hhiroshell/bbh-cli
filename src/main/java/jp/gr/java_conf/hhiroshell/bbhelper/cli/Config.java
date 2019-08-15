package jp.gr.java_conf.hhiroshell.bbhelper.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class Config {

    /**
     * Name of a directory where a config parameter file and logs are stored.
     */
    private static final String CONFIG_DIR_NAME = ".bbh";
    /**
     * Name of a config parameter file.
     */
    private static final String CONFIG_FILE_NAME = "config";
    /**
     * File object of a directory where a config parameter file and logs are stored.
     */
    private static final File configDir = new File(System.getProperty("user.home") + "/" + CONFIG_DIR_NAME);
    /**
     * File object of a config parameter file.
     */
    private static final File configFile = new File(configDir.getAbsolutePath() + "/" + CONFIG_FILE_NAME);
    /**
     * Beehive server URL (e.g. https://example.beehive.com/)
     */
    private URL host;
    /**
     * Beehive account user name.
     */
    private String user;
    /**
     * Beehive account password.
     */
    private String password;

    /**
     * Provide a Config object made from properties in current config file.
     * This method returns null if a config file have not been created.
     *
     * @return Provide a Config object made from properties in current config file.
     * @throws IOException failed to create Config object.
     */
    static Config loadCurrentConfig() throws IOException {
        if (!configFile.exists()) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(configFile, Config.class);
    }

    /**
     * Remove config file form file system.
     */
    static void flashCurrentConfig() {
        if (configFile.exists()) {
            configFile.delete();
        }
    }

    /**
     * Persist configuration properties to a file on a platform file system.
     *
     * @throws IOException failed to create a config file.
     */
    void persist() throws IOException {
        if (this.host == null || user == null || user.isEmpty() || password == null || password.isEmpty()) {
            throw new NullPointerException("insufficient parameter.");
        }
        if (!configDir.exists() || !configDir.isDirectory()) {
            configDir.mkdir();
        }
        new ObjectMapper(new YAMLFactory()).writeValue(configFile, this);
    }

    public URL getHost() {
        return host;
    }

    Config setHost(URL host) {
        this.host = host;
        return this;
    }

    public String getUser() {
        return user;
    }

    Config setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    Config setPassword(String password) {
        this.password = password;
        return this;
    }

}
