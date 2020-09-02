package com.impl.novels.job;

import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.factory.ChapterDetailSpiderFactory;
import com.interfaces.IChapterDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author smile
 * @version 1.0
 * @date 2020/9/1 15:34
 */
public class DownloadJob implements Callable<Boolean> {

    private List<Chapter> chapters;
    private String path;
    private Integer tries;
    private Boolean flag = true;
    private static final Logger log = LoggerFactory.getLogger(DownloadJob.class);

    public DownloadJob(String path, List<Chapter> chapters ,int tries) {
        this.path = path;
        this.chapters = chapters;
        this.tries = tries;
    }

    @Override
    public Boolean call() {
        try (PrintWriter out = new PrintWriter(new File(path), "UTF-8")) {
            for (Chapter chapter : chapters) {
                IChapterDetail spider = ChapterDetailSpiderFactory.getChapterDetail(chapter.getOriUrl());
                ChapterDetail detail = null;
                //线程下载任务失败，尝试重新下载j次之后放弃
                for (int j = 0; j < tries; j++) {
                    try {
                        detail = spider.getChapterDetail(chapter.getOriUrl());
                        out.println(detail.getTitle());
                        out.println(detail.getContent());
                        break;
                    } catch (Exception e) {
                        log.error("尝试第[" + (j + 1) + "/" + tries + "]次下载失败了！");
                        if ((j + 1) == tries) flag = false;
                    }
                } if (!flag) break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }
}
