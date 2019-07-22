import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;


public class FastDFS {

    @Test
    public void upload() {
        //获取URL   http://47.100.196.156/data/M00/00/00/rBN54F0De6uAWnpNAABNt-jECl4457.png
        String local_filename = "C:\\Users\\Administrator\\Desktop\\fish.png";
        try {
            ClientGlobal.initByProperties("FastDFS.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", local_filename);
            String fileId = client.upload_file1(local_filename, "png", metaList);
            System.out.println("upload success. file id is: " + fileId);
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void query() {
        try {
            ClientGlobal.initByProperties("FastDFS.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            FileInfo fileInfo = client.query_file_info1("group1/M00/00/00/rBN54Fy8E8KAMvEpAAAAAeXVvg0457.txt");

            System.out.println("query success.file info is: " + fileInfo);

            NameValuePair[] metadata1 = client.get_metadata1("group1/M00/00/00/rBN54Fy8E8KAMvEpAAAAAeXVvg0457.txt");
            System.out.println("query success.metadata1 name is: " + metadata1[0].getName()+" value:"+metadata1[0].getValue());

            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void download() {
        try {
            ClientGlobal.initByProperties("FastDFS.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            FileInfo fileInfo = client.query_file_info1("group1/M00/00/00/rBN54Fy8E8KAMvEpAAAAAeXVvg0457.txt");

            System.out.println("query success.file info is: " + fileInfo);
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
