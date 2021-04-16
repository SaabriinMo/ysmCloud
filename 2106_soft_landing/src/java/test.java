import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class test implements ActionListener {

    public test(){
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        JButton button = new JButton("upload");
        button.addActionListener(this);

        JButton button2 = new JButton("download");


        panel.add(button);
        panel.add(button2);
        listObjects();

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Gui");
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new test();
    }

    public static void uploadObject() throws IOException {
        // The ID of your GCP project
         String projectId = "red-road-309816";

        // The ID of your GCS bucket
        String bucketName = "media.ysmcloud.net";

        // The ID of your GCS object
        String objectName = "test";

        // The path to your file to upload
        String filePath = "C:\\Users\\yasin\\Downloads\\test.txt";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println(
                "File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        downloadObject();
    }



    public static void listObjects() {
        // The ID of your GCP project
        String projectId = "red-road-309816";

        // The ID of your GCS bucket
        String bucketName = "media.ysmcloud.net";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Blob> blobs = storage.list(bucketName);

        for (Blob blob : blobs.iterateAll()) {
            System.out.println(blob.getName());
        }
    }

    public static void downloadObject() {
        // The ID of your GCP project
        String projectId = "red-road-309816";

        // The ID of your GCS bucket
        String bucketName = "media.ysmcloud.net";

        // The ID of your GCS object
        String objectName = "test";

        // The path to which the file should be downloaded
         String destFilePath = "C:\\Users\\yasin\\Downloads\\test.txt";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);
    }
}