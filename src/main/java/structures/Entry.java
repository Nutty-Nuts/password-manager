package structures;

/**
 * EntryNode
 */
public class Entry {
    public String service;
    public String account;

    public Entry(String service, String account) {
        this.service = service;
        this.account = account;
    }

    public String key() {
        return service + account;
    }
}