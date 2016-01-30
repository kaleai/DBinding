package kale.db.model;

/**
 * @author Kale
 * @date 2016/1/6
 */
public class NewsInfo {

    /**
     * 新闻的标题
     */
    public String title;

    /**
     * 新闻的点赞文字
     */
    public String isLikeText;

    /**
     * 其他的字段
     */
    public NewsDetail newsDetail;
    
    public int[] picResIdArr; // 业务模型中支持了多个图片，但实际可能只需要显示一张

    public static class NewsDetail {
        public String content;

    }

    public NewsInfo(String title, String isLikeText, int[] picResIdArr) {
        this.title = title;
        this.isLikeText = isLikeText;
        this.picResIdArr = picResIdArr;
    }
}
