package com.ximcomputerx.formusic.config;

/**
 * @AUTHOR HACKER
 */
public class Constant {
    public static final String DB_NAME = "music";
    public static final String PREFERENCES = "config";

    public static final String PLAY_POSITION = "PLAY_POSITION";
    public static final String PLAY_MODE = "PLAY_MODE";

    public static final String PLAY_LIST_SONG = "PLAY_LIST_SONG";
    public static final String PLAY_LIST_SINGER = "PLAY_LIST_SINGER";
    public static final String PLAY_LIST_SEARCH = "PLAY_LIST_SEARCH";
    public static final String PLAY_LIST_HISTORY = "PLAY_LIST_HISTORY";
    public static final String PLAY_LIST_LIKE = "PLAY_LIST_LIKE";

    public static final String SPLASH_URL = "SPLASH_URL";
    public static final String NIGHT_MODE = "NIGHT_MODE";

    public static final String EXTRA_NOTIFICATION = "com.ximcomputerx.music.notification";

    // 播放列表类型
    public static final int RANK_TYPE_official = 1;
    public static final int RANK_TYPE_recommend = 2;
    public static final int RANK_TYPE_world = 3;
    public static final int RANK_TYPE_other = 4;
    public static final String RANK_TYPE_official_1 = "云音乐飙升榜";
    public static final String RANK_TYPE_official_2 = "云音乐新歌榜";
    public static final String RANK_TYPE_official_3 = "网易原创歌曲榜";
    public static final String RANK_TYPE_official_4 = "云音乐热歌榜";

    public static final String RANK_TYPE_recommend_1 = "云音乐说唱榜";
    public static final String RANK_TYPE_recommend_6 = "云音乐古典音乐榜";
    public static final String RANK_TYPE_recommend_2 = "云音乐电音榜";
    public static final String RANK_TYPE_recommend_4 = "抖音排行榜";
    public static final String RANK_TYPE_recommend_5 = "云音乐ACG音乐榜";
    public static final String RANK_TYPE_recommend_3 = "云音乐欧美新歌榜";

    public static final String RANK_TYPE_world_1 = "英国Q杂志中文版周榜";
    public static final String RANK_TYPE_world_2 = "UK排行榜周榜";
    public static final String RANK_TYPE_world_3 = "美国Billboard周榜";
    public static final String RANK_TYPE_world_4 = "Beatport全球电子舞曲榜";
    public static final String RANK_TYPE_world_5 = "iTunes榜";
    public static final String RANK_TYPE_world_6 = "日本Oricon周榜";

    // 播放列表类型
    public static final int LIST_TYPE_ONLINE = 1;   //专辑播放列表
    public static final int LIST_TYPE_LOCAL = 2;    //本地播放列表
    public static final int LIST_TYPE_DOWNLOAD = 3; //下载播放列表
    public static final int LIST_TYPE_LOVE = 4;     //收藏播放列表
    public static final int LIST_TYPE_HISTORY = 5;  //历史播放列表

    // 播放顺序
    public static final int PLAY_ORDER = 0;         //顺序播放
    public static final int PLAY_SINGLE = 1;        //单曲循环
    public static final int PLAY_RANDOM = 2;        //随机播放

    //public static final String URL_SERVICE = "http://192.168.0.102:3000";
    //public static final String URL_SERVICE = "http://192.168.5.181:3000";
    //public static final String URL_SERVICE = "http://47.240.71.185:3000";
    public static final String URL_SERVICE = "http://39.99.235.220:3000";

    //排行榜
    public static final String URL_SUB_RANK = "/toplist";
    //歌单
    public static final String URL_SUB_MIX = "/top/playlist";
    public static final String URL_SUB_SONGLIST = "/playlist/detail";
    public static final String URL_SUB_SONGURL = "/song/url";
    public static final String URL_SUB_SONGLRC = "/lyric";
    public static final String URL_SUB_RECOMMEND = "/personalized";

    //搜索
    public static final String URL_SUB_SEARCH_HOST = "/search/hot/detail";
    public static final String URL_SUB_SEARCH_DETAIL = "/search";

    // 歌手
    public static final String URL_SUB_SINGER = "/top/artists";
    public static final String URL_SUB_SINGER_HOT = "/artist/top/song";
}