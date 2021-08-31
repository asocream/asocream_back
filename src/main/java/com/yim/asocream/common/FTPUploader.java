package com.yim.asocream.common;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


public class FTPUploader {

    FTPClient ftp = null;

    //param( host server ip, username, password )
    public FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.setControlEncoding("euc-kr");
        int reply;
        ftp.connect(host);//호스트 연결
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);//로그인
        ftp.setFileType(FTP.BINARY_FILE_TYPE);

        ftp.enterLocalPassiveMode();
    }

    //param( 보낼파일경로+파일명, 호스트에서 받을 파일 이름, 호스트 디렉토리 )
    public void uploadFile(MultipartFile multipartFile,String uuid,String pathName)
            throws Exception {

        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        String fileName = multipartFile.getOriginalFilename();

        boolean isExist =  this.ftp.changeWorkingDirectory("web");
        if(!isExist){
            this.ftp.makeDirectory("web");
            this.ftp.changeWorkingDirectory("web");
        }


        boolean isExist_ =  this.ftp.changeWorkingDirectory("profile");
        if(!isExist_){
            this.ftp.makeDirectory("profile");
            this.ftp.changeWorkingDirectory("profile");
        }

        if(!(pathName==null)||!pathName.isEmpty()){
            String[] pathList = pathName.split("/");
            ftp.deleteFile(pathList[pathList.length-1]);
        }


        try(InputStream input = new FileInputStream(file)){
            this.ftp.storeFile(uuid+fileName, input);
            //storeFile() 메소드가 전송하는 메소드
        }
        fos.close();
        file.delete();

    }

    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }

}
