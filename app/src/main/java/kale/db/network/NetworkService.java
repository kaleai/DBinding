package kale.db.network;

import java.util.ArrayList;
import java.util.List;

import kale.db.R;
import kale.db.model.NewsInfo;

/**
 * @author Kale
 * @date 2016/2/19
 */
public class NetworkService {

    /**
     * 为了说明的简单，这里模拟网络加载数据的步骤，还没做网络层。
     */
    public static List<NewsInfo> loadDataFromNetwork() {
        ArrayList<NewsInfo> list = new ArrayList<>();
        list.add(new NewsInfo("Hitman 47", "Like", new int[]{R.drawable.hit_man, R.drawable.hit_man_icon}));
        list.add(new NewsInfo("Assassins Creed", "Like", new int[]{R.drawable.assassin_s_creed, R.drawable.assassin_s_creed_icon}));
        list.add(new NewsInfo("StarCraft Ⅱ", "Like", new int[]{R.drawable.star_craft,R.drawable.star_craft_icon}));
        list.add(new NewsInfo("Watch Dogs", "Like", new int[]{R.drawable.watch_dogs, R.drawable.watch_dogs_icon}));
        return list;
    }
}
