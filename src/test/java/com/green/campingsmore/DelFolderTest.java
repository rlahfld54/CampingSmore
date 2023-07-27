package com.green.campingsmore;

import com.green.campingsmore.community.board.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DelFolderTest {
    @Test
    public void delfoler(){
        String path = FileUtils.getAbsolutePath("/home2");

//        File folder = new File(path);
//        if (folder.exists()){
//            folder.delete();
//        }
        FileUtils.delFolder(path);
    }
}
