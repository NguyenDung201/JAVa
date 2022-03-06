package com.example.quanlybanhang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HoadonController implements Initializable {
    @FXML
    private TableView<hoadon> table;
    @FXML
    private TableColumn<hoadon, String> maCol;
    @FXML
    private TableColumn<hoadon, String> tenCol;
    @FXML
    private TableColumn<hoadon, String> ckCol;
    @FXML
    private TableColumn<hoadon, String> TTCol;


    private ObservableList<hoadon> listHD;

    //textField
    @FXML
    private TextField mhdText;
    @FXML
    private TextField tenspText;
    @FXML
    private TextField chietkhauText;
    @FXML
    private TextField tongtienText;


    public void btnAdd(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Xác Nhận");
        alert.setHeaderText("Xác nhận thêm?");
        alert.setContentText("Bạn có xác nhận thêm?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        Optional<ButtonType> result1 = alert.showAndWait();
        if (result1.get() == buttonTypeYes) {
            hoadon newHD = new hoadon();
            newHD.setMahd(mhdText.getText());
            newHD.setTensp(tenspText.getText());
            newHD.setChietKhau(Double.parseDouble(chietkhauText.getText()));
            newHD.setTongtien(Double.parseDouble(tongtienText.getText()));
            listHD.add(newHD);

            writeFile();
            resetText();

        } else {
            resetText();
            Alert alertAdd = new Alert(Alert.AlertType.INFORMATION);
            alertAdd.setTitle("Information");
            alertAdd.setContentText("Chưa thêm");
            alertAdd.show();
            writeFile();
        }
    }

    public void btnEdit(ActionEvent actionEvent) {
        Alert alertEdit1 = new Alert(Alert.AlertType.INFORMATION);
        alertEdit1.setTitle("Xác Nhận");
        alertEdit1.setHeaderText("Xác nhận sửa thông tin?");
        alertEdit1.setContentText("Bạn có xác nhận sửa thông tin này?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertEdit1.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
        alertEdit1.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        Optional<ButtonType> result = alertEdit1.showAndWait();
        if (result.get() == buttonTypeYes) {
            hoadon select = table.getSelectionModel().getSelectedItem();
            hoadon newHD = new hoadon();
            for (hoadon hd : listHD) {
                if (hd == select) {
                    newHD.setMahd(mhdText.getText());
                    newHD.setTensp(tenspText.getText());
                    newHD.setChietKhau(Double.parseDouble(chietkhauText.getText()));
                    newHD.setTongtien(Double.parseDouble(tongtienText.getText()));
                    listHD.set(listHD.indexOf(hd), newHD);
                }
            }
        } else {
            resetText();
            Alert alertEdit2 = new Alert(Alert.AlertType.INFORMATION);
            alertEdit2.setTitle("Information");
            alertEdit2.setHeaderText("Sửa thành công");
            alertEdit2.show();

            write();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        Alert alertDel = new Alert(Alert.AlertType.INFORMATION);
        alertDel.setTitle("Xác Nhận");
        alertDel.setHeaderText("Xác nhận xoá thông tin?");
        alertDel.setContentText("Bạn có xác nhận xoá thông tin này?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertDel.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
        alertDel.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        Optional<ButtonType> result = alertDel.showAndWait();
        if (result.get() == buttonTypeYes) {
            hoadon select = table.getSelectionModel().getSelectedItem();
            listHD.remove(select);
        } else {
            Alert alertDel2 = new Alert(Alert.AlertType.INFORMATION);
            alertDel2.setTitle("Information");
            alertDel2.setHeaderText("Xoá thành công");
            alertDel2.show();

            write();
        }
    }

    public void btnBack(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("quanlybanhang.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void resetText() {
        mhdText.setText("");
        tenspText.setText("");
        chietkhauText.setText("");
        tongtienText.setText("");
    }
    private static List<hoadon> readSP(String filename) throws IOException {
        List<hoadon> hd = new ArrayList<hoadon>();
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("hoadon.txt")));
        while ((temp = reader.readLine()) != null) {
            String[] str = temp.split(",");
            hd.add(new hoadon(str[0], str[1], Double.parseDouble(str[2]), Double.parseDouble(str[3])));
        }
        return hd;
    }
    public void write() {
        try {
            WRITE("hoadon.txt", listHD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WRITE(String filename, ObservableList<hoadon> listHD) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (hoadon hd : listHD) {
            writer.write(hd.getMahd() + "," + hd.getTensp() + "," + hd.getChietKhau() + "," + hd.getTongtien() + "\n");
        }
        writer.close();

    }

    public List<hoadon> readlistSP() {
        List<hoadon> inputhoadon = null;
        try {
            inputhoadon = readSP("hoadon.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputhoadon;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadHD();
    }

    public void loadHD(){
        sanpham newSP = new sanpham();
        listHD = FXCollections.observableArrayList(
                new hoadon("as1212", "banh ngot", 30, 4)
        );
        maCol.setCellValueFactory(new PropertyValueFactory<hoadon, String>("Mahd"));
        tenCol.setCellValueFactory(new PropertyValueFactory<hoadon, String>("Tensp"));
        ckCol.setCellValueFactory(new PropertyValueFactory<hoadon, String>("ChietKhau"));
        TTCol.setCellValueFactory(new PropertyValueFactory<hoadon, String>("tongtien"));
        table.setItems(listHD);
    }

    public void handleClick(MouseEvent mouseEvent) {
        hoadon hd = table.getSelectionModel().getSelectedItem();
        String str1 = String.valueOf(hd.getChietKhau());
        String str2 = String.valueOf(hd.getTongtien());
        if (hd != null) {
            mhdText.setText(hd.getMahd());
            tenspText.setText(hd.getTensp());
            chietkhauText.setText(str1);
            tongtienText.setText(str2);
        }
    }

    public void writeFile() throws IOException {
        if(!listHD.isEmpty()){
            FileOutputStream f = null;
            ObjectOutputStream oStream = null;
            try {
                f = new FileOutputStream("hoadon.txt");
                oStream = new ObjectOutputStream(f);

                for (int i = 0; i < listHD.size(); i++) {
                    oStream.writeObject(listHD.get(i));
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                f.close();
                oStream.close();
            }
        }
    }

//    public void readFile() throws IOException, ClassNotFoundException {
//        FileInputStream f = null;
//        ObjectInputStream iStream = null;
//        listHD.clear();
//        try {
//            f = new FileInputStream("hoadon.txt");
//            iStream = new ObjectInputStream(f);
//
//            Object obj = null;
//            while ((obj = iStream.readObject()) != null) {
//                if (obj instanceof hoadon) {
//                    hoadon dangKyHoc = (hoadon) obj;
//                    listHD.add(dangKyHoc);
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        } catch (EOFException ex) {
//            ex.printStackTrace();
//        } finally {
//            f.close();
//            iStream.close();
//            loadHD();
//        }
//    }


}
