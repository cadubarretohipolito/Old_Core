package br.cadu.pro.master.nms;

public enum ServerVersion {
    v1_16_R3,
    v1_19_R3,
    REFLECTED,
    UNKNOWN;

    @Override
    public String toString() {
        return name();
    }
}
