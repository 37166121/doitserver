package file.service.impl;

import file.mapper.FileMapper;
import file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * FileServiceImpl
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 4:52
 **/
@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;
    @Override
    public void setHead(String uri, int uid) {
        fileMapper.setHead(uri,uid);
    }

    @Override
    public void uploadFile(int uid, String mediaid, String videouri, String imageuri, String otheruri, String label, String title, String value, boolean ispublic, int like, int collect, int dislike, int great, int seen, Date addtime) {
        fileMapper.uploadFile(uid, mediaid, videouri, imageuri, otheruri, label, title, value, ispublic, like, collect, dislike, great, seen, addtime);
    }
}
