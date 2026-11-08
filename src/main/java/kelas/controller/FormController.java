package kelas.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class FormController {

    @FXML
    private TextField namaField, hargaField;
    @FXML
    private ComboBox<String> jenisBox;
    @FXML
    private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;

    @FXML
    private void initialize() {
        jenisBox.getItems().addAll("Makanan", "Minuman");
        jenisBox.setValue("Makanan");
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
    }

    @FXML
    private void simpan() {
        Produk p;
        try {
            double harga = Double.parseDouble(hargaField.getText().trim());
            p = data.tambah(namaField.getText(), jenisBox.getValue(), harga);
        } catch (NumberFormatException e) {
            pesanLabel.setText("Harga harus berupa bilangan desimal/angka positif yang valid.");
            return;
        } catch (IllegalArgumentException e) {
            pesanLabel.setText(e.getMessage());
            return;
        }
        try {
            navigator.bukaDetail(p);
        } catch (IOException e) {
            data.hapus(p);
            pesanLabel.setText("Halaman detail gagal dibuka; data dibatalkan. Periksa FXML.");
            e.printStackTrace();
        }
    }

    @FXML
    private void bukaDaftar() {
        try {
            navigator.bukaDaftar();
        } catch (IOException e) {
            pesanLabel.setText("Gagal membuka daftar.");
            e.printStackTrace();
        }
    }
}