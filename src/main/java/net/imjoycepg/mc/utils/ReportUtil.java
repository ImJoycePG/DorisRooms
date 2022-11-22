package net.imjoycepg.mc.utils;

import com.google.api.client.http.FileContent;
import net.imjoycepg.mc.DorisRooms;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ReportUtil {


    private void reportsExport(String ruta, String file) {
        try {
            Map parametro = new HashMap();
            parametro.put("C_ID-ORDER", DorisRooms.getInstance().getOrderProductTemp().getIdOrder());

            JasperPrint inform = JasperFillManager.fillReport(ruta, parametro, DorisRooms.getInstance().getMySQL().getConnection());
            JasperExportManager.exportReportToPdfFile(inform, file);


            JasperViewer view = new JasperViewer(inform, false);
            view.setTitle("INFORM");
            view.setVisible(false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    private void reportsExportVoucher(String ruta, String file) {
        try {
            Map parametro = new HashMap();
            parametro.put("C_ID-RENTAL", DorisRooms.getInstance().getRoomsRentalTemp().getIdRental());

            JasperPrint inform = JasperFillManager.fillReport(ruta, parametro, DorisRooms.getInstance().getMySQL().getConnection());
            JasperExportManager.exportReportToPdfFile(inform, file);


            JasperViewer view = new JasperViewer(inform, false);
            view.setTitle("INFORM");
            view.setVisible(false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }


    private void uploadDrive(File JFC) {
        try {
            File fileName = new File(JFC.getAbsolutePath() + ".pdf");


            com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
            fileMetadata.setName(JFC.getName());
            FileContent fileContent = new FileContent("application/pdf", fileName);
            DorisRooms.getInstance().getDrive().files().create(
                    fileMetadata, fileContent).setFields("id").execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void FactureReportPDF() {
        try {
            String ruta = "src/main/resources/META-INF/reports/Ticket.jasper";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Files *.PDF", "pdf", "PDF"));
            int selection = fileChooser.showSaveDialog(null);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File JFC = fileChooser.getSelectedFile();
                String PATH = JFC.getAbsolutePath();

                try (PrintWriter printWriter = new PrintWriter(JFC)) {
                    printWriter.print(ruta);
                }
                reportsExport(ruta, PATH);
                if (!(PATH.endsWith(".pdf"))) {
                    File temp = new File(PATH + ".pdf");
                    JFC.renameTo(temp);
                }

                uploadDrive(JFC);

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void FactureVoucherPDF() {
        try {
            String ruta = "src/main/resources/META-INF/reports/Voucher.jasper";

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Files *.PDF", "pdf", "PDF"));
            int selection = fileChooser.showSaveDialog(null);

            if (selection == JFileChooser.APPROVE_OPTION) {
                File JFC = fileChooser.getSelectedFile();
                String PATH = JFC.getAbsolutePath();

                try (PrintWriter printWriter = new PrintWriter(JFC)) {
                    printWriter.print(ruta);
                }
                reportsExportVoucher(ruta, PATH);
                if (!(PATH.endsWith(".pdf"))) {
                    File temp = new File(PATH + ".pdf");
                    JFC.renameTo(temp);
                }

                uploadDrive(JFC);

            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }


}
