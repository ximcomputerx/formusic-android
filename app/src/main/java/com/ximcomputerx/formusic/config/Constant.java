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
    public static final String PLAY_LIST_RADIO = "PLAY_LIST_RADIO";

    public static final String SPLASH_URL = "SPLASH_URL";
    public static final String NIGHT_MODE = "NIGHT_MODE";

    public static final String EXTRA_NOTIFICATION = "com.ximcomputerx.music.notification";

    // 播放列表类型
    public static final int RANK_TYPE_official = 1;
    public static final int RANK_TYPE_recommend = 2;
    public static final int RANK_TYPE_world = 3;
    public static final int RANK_TYPE_other = 4;
    public static final String RANK_TYPE_official_1 = "飙升榜";
    public static final String RANK_TYPE_official_2 = "新歌榜";
    public static final String RANK_TYPE_official_3 = "原创榜";
    public static final String RANK_TYPE_official_4 = "热歌榜";

    public static final String RANK_TYPE_recommend_1 = "网络热歌榜";
    public static final String RANK_TYPE_recommend_6 = "BEAT排行榜";
    public static final String RANK_TYPE_recommend_2 = "云音乐电音榜";
    public static final String RANK_TYPE_recommend_4 = "听歌识曲榜";
    public static final String RANK_TYPE_recommend_5 = "KTV唛榜";
    public static final String RANK_TYPE_recommend_3 = "潜力爆款榜";

    public static final String RANK_TYPE_world_1 = "美国Billboard榜";
    public static final String RANK_TYPE_world_2 = "UK排行榜周榜";
    public static final String RANK_TYPE_world_3 = "日本Oricon榜";
    public static final String RANK_TYPE_world_4 = "法国 NRJ Vos Hits 周榜";
    public static final String RANK_TYPE_world_5 = "Beatport全球电子舞曲榜";
    public static final String RANK_TYPE_world_6 = "云音乐韩语榜";

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

    public static final String URL_SERVICE = "http://www.formusic.me:3000";
    //public static final String URL_SERVICE = "http://39.99.235.220:3000";
    public static final String URL_SERVICE_UPDATE = "http://39.99.235.220:8080/version";

    //排行榜
    public static final String URL_SUB_RANK = "/toplist";
    //歌单
    public static final String URL_SUB_MIX = "/top/playlist";
    public static final String URL_SUB_SONGLIST = "/playlist/detail";
    public static final String URL_SUB_SONGDETAIL = "/song/detail";

    public static final String URL_SUB_SONGURL = "/song/url";
    public static final String URL_SUB_SONGLRC = "/lyric";
    public static final String URL_SUB_RECOMMEND = "/personalized";
    //搜索
    public static final String URL_SUB_SEARCH_HOST = "/search/hot/detail";
    public static final String URL_SUB_SEARCH_DETAIL = "/search";
    // 歌手
    public static final String URL_SUB_SINGER = "/top/artists";
    public static final String URL_SUB_SINGER_HOT = "/artist/top/song";
    public static final String URL_SUB_VERSION = "/version";
    // 电台
    public static final String URL_SUB_RADIO = "/dj/radio/hot";
    public static final String URL_SUB_RADIO_DETAIL = "/dj/program";

    public static final int TASK_NOTIFY = 10000;

    public static final int UPDATE_NOTIFY = 20000;
}