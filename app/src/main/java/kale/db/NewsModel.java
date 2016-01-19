package kale.db;

/**
 * @author Kale
 * @date 2016/1/6
 */
public class NewsModel {

    public String title;

    public NewsDetail newsDetail;
    
    public String[] picUrlArr; // 业务模型中支持了多个图片，但实际可能只需要显示一张

    public static class NewsDetail {
        public String content;

    }
    
}
