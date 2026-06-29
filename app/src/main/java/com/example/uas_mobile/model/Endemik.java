package com.example.uas_mobile.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "endemik")
public class Endemik {
    @PrimaryKey
    @NonNull
    private String id;
    private String nama;
    @SerializedName("nama_latin")
    private String namaLatin;
    private String famili;
    private String genus;
    @SerializedName("tipe")
    private String kategori;
    @SerializedName("foto")
    private String gambar;
    private String deskripsi;
    private String asal;
    private String sebaran;
    @SerializedName("sumber_foto")
    private String sumberFoto;
    private String vidio;
    @SerializedName("sumber_vidio")
    private String sumberVidio;
    private String status;

    public Endemik(@NonNull String id, String nama, String namaLatin, String famili, String genus, 
                   String kategori, String gambar, String deskripsi, String asal, String sebaran, 
                   String sumberFoto, String vidio, String sumberVidio, String status) {
        this.id = id;
        this.nama = nama;
        this.namaLatin = namaLatin;
        this.famili = famili;
        this.genus = genus;
        this.kategori = kategori;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.asal = asal;
        this.sebaran = sebaran;
        this.sumberFoto = sumberFoto;
        this.vidio = vidio;
        this.sumberVidio = sumberVidio;
        this.status = status;
    }

    @Ignore
    public Endemik(@NonNull String id, String nama, String kategori, String gambar, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }

    @NonNull
    public String getId() { return id; }
    public void setId(@NonNull String id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNamaLatin() { return namaLatin; }
    public void setNamaLatin(String namaLatin) { this.namaLatin = namaLatin; }
    public String getFamili() { return famili; }
    public void setFamili(String famili) { this.famili = famili; }
    public String getGenus() { return genus; }
    public void setGenus(String genus) { this.genus = genus; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }
    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public String getAsal() { return asal; }
    public void setAsal(String asal) { this.asal = asal; }
    public String getSebaran() { return sebaran; }
    public void setSebaran(String sebaran) { this.sebaran = sebaran; }
    public String getSumberFoto() { return sumberFoto; }
    public void setSumberFoto(String sumberFoto) { this.sumberFoto = sumberFoto; }
    public String getVidio() { return vidio; }
    public void setVidio(String vidio) { this.vidio = vidio; }
    public String getSumberVidio() { return sumberVidio; }
    public void setSumberVidio(String sumberVidio) { this.sumberVidio = sumberVidio; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
