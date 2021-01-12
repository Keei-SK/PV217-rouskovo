package cz.muni.fi.pv217.rouskovo.objects;

import java.util.List;

public class MicroServiceHealth {
    public String name;
    public String url;
    public String status;
    public List<Check> checks;

    public MicroServiceHealth() {}

    public MicroServiceHealth(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
