package com.example.albunsifinal;

public class Information {

    private String Name;
    private String Address;
    private String Lat;
    private String Long;
    private String DetaliiStatieFormatate;

    public Information() {
    }

    public Information(String name, String address, String lat, String aLong, String detaliiStatieFormatate) {
        Name = name;
        Address = address;
        Lat = lat;
        Long = aLong;
        DetaliiStatieFormatate=detaliiStatieFormatate;
    }

    public void setDetaliiStatieFormatate(String tipuriMufe, String estePazita, int outputKW, int nrLocuriParcare) {
        DetaliiStatieFormatate = "Mufe: "+tipuriMufe+"\nOutputkW/h: "+outputKW+"kW/h\nPazita: "+estePazita+ "\nNr locuri parcare: "+nrLocuriParcare;
    }

    public String getDetaliiStatieFormatate() {
        return DetaliiStatieFormatate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }
}
