package com.cuahangnongsan.util;

import com.cuahangnongsan.config.firebase.FirebaseInitializer;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ProcessImage {
    public static synchronized String upload(MultipartFile image) throws IOException {
        if(!Objects.equals(image.getOriginalFilename(), "")){
            FirebaseInitializer.getFirebaseApp();
            // Lấy đối tượng Storage từ Firebase
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            String bucketName = StorageClient.getInstance().bucket().getName();
            // Đọc dữ liệu từ MultipartFile

//        byte[] data = image.getBytes();
            byte[] data = reduceSize(image, 600, 600);

            String contentType = image.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream"; // Đặt loại nội dung mặc định nếu không có
            }

            // Tạo BlobInfo với tên, đường dẫn tệp và loại nội dung
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, "users/" + image.getOriginalFilename())
                    .setContentType(contentType) // Xác định loại nội dung ở đây
                    .build();
            // Tải ảnh lên Firebase Storage
            Blob blob = storage.create(blobInfo, data);


            // Lấy liên kết tải xuống
            return "https://firebasestorage.googleapis.com/v0/b/"+ blobInfo.getBucket()+"/o/"+ blob.getName().replace("/", "%2F")+"?alt=media";
        }else {
            return null;
        }



    }

    public static byte[] reduceSize(MultipartFile file, int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Thay đổi kích thước ảnh và chuyển đổi định dạng ảnh sang "jpg"
        Thumbnails.of(file.getInputStream())
                .size(width, height)
                .outputFormat("jpeg")
                .toOutputStream(baos);

        // Trả về dữ liệu ảnh dưới dạng byte array
        return baos.toByteArray();
    }
}
