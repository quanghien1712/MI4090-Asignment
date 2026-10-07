/*
    Trần Quang Hiển
    202418893
 */

package Assignment_5;

public interface INetworkable {
    void connect(String ipAddress);
    void disconnect();
    String getIpAddress();
    boolean isConnected();
}