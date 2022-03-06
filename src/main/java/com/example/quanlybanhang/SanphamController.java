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

public class SanphamController implements Initializable {

    @FXML
    private TableView<sanpham> table;
    @FXML
    private TableColumn<sanpham, String> maCol;
    @FXML
    private TableColumn<sanpham, String> tenCol;
    @FXML
    private TableColumn<sanpham, String> giaCol;
    @FXML
    private TableColumn<sanpham, String> SLCol;


    private ObservableList<sanpham> listSP;

    //textField
    @FXML
    private TextField mspText;
    @FXML
    private TextField tenspText;
    @FXML
    private TextField giaspText;
    @FXML
    private TextField SLText;


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
            sanpham newSP = new sanpham();
            newSP.setMasp(mspText.getText());
            newSP.setTensp(tenspText.getText());
            newSP.setGiasp(Double.parseDouble(giaspText.getText()));
            newSP.setSoluong(Double.parseDouble(SLText.getText()));
            listSP.add(newSP);

            writeFile();
            resetText();

        } else {
            resetText();
            Alert alertAdd = new Alert(Alert.AlertType.INFORMATION);
            alertAdd.setTitle("Information");
            alertAdd.setContentText("Chưa thêm");
            alertAdd.show();
            write();
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
            sanpham select = table.getSelectionModel().getSelectedItem();
            sanpham newSP = new sanpham();
            for (sanpham sp : listSP) {
                if (sp == select) {
                    newSP.setMasp(mspText.getText());
                    newSP.setTensp(tenspText.getText());
                    newSP.setGiasp(Double.parseDouble(giaspText.getText()));
                    newSP.setSoluong(Double.parseDouble(SLText.getText()));
                    listSP.set(listSP.indexOf(sp), newSP);
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
            sanpham select = table.getSelectionModel().getSelectedItem();
            listSP.remove(select);
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
        mspText.setText("");
        tenspText.setText("");
        giaspText.setText("");
        SLText.setText("");
    }
    private static List<sanpham> readSP(String filename) throws IOException {
        List<sanpham> sp = new ArrayList<sanpham>();
        String temp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("sanpham.txt")));
        while ((temp = reader.readLine()) != null) {
            String[] str = temp.split(",");
            sp.add(new sanpham(str[0], str[1], Double.parseDouble(str[2]), Double.parseDouble(str[3])));
        }
        return sp;
    }

    public void write() {
        try {
            WRITE("sanpham.txt", listSP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WRITE(String filename, ObservableList<sanpham> listSP) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (sanpham sp : listSP) {
            writer.write(sp.getMasp() + "," + sp.getTensp() + "," + sp.getGiasp() + "," + sp.getSoluong() + "\n");
        }
        writer.close();

    }

    public List<sanpham> readlistSP() {
        List<sanpham> inputSanPham = null;
        try {
            inputSanPham = readSP("sanpham.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputSanPham;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSP();
    }
    public void loadSP(){
        sanpham newSP = new sanpham();
        listSP = FXCollections.observableArrayList(
                new sanpham("as1212", "banh ngot", 30000, 4)
        );
        maCol.setCellValueFactory(new PropertyValueFactory<sanpham, String>("Masp"));
        tenCol.setCellValueFactory(new PropertyValueFactory<sanpham, String>("Tensp"));
        giaCol.setCellValueFactory(new PropertyValueFactory<sanpham, String>("Giasp"));
        SLCol.setCellValueFactory(new PropertyValueFactory<sanpham, String>("soluong"));
        table.setItems(listSP);
    }

    public void handleClick(MouseEvent mouseEvent) {
        sanpham sp = table.getSelectionModel().getSelectedItem();
        String str1 = String.valueOf(sp.getGiasp());
        String str2 = String.valueOf(sp.getSoluong());
        if (sp != null) {
            mspText.setText(sp.getMasp());
            tenspText.setText(sp.getTensp());
            giaspText.setText(str1);
            SLText.setText(str2);
        }
    }

    public void writeFile() throws IOException {
        if(!listSP.isEmpty()){
            FileOutputStream f = null;
            ObjectOutputStream oStream = null;
            try {
                f = new FileOutputStream("hoadon.txt");
                oStream = new ObjectOutputStream(f);

                for (int i = 0; i < listSP.size(); i++) {
                    oStream.writeObject(listSP.get(i));
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
}
