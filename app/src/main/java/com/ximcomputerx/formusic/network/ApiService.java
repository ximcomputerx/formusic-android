package com.ximcomputerx.formusic.network;

import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.model.RadioDetailListInfo;
import com.ximcomputerx.formusic.model.RadioListInfo;
import com.ximcomputerx.formusic.model.RemoteReturnData;
import com.ximcomputerx.formusic.model.SingerHotListInfo;
import com.ximcomputerx.formusic.model.SingerListInfo;
import com.ximcomputerx.formusic.model.LrcInfo;
import com.ximcomputerx.formusic.model.LrcListInfo;
import com.ximcomputerx.formusic.model.MixListInfo;
import com.ximcomputerx.formusic.model.RankListInfo;
import com.ximcomputerx.formusic.model.RecommendListIinfo;
import com.ximcomputerx.formusic.model.SearchHotListInfo;
import com.ximcomputerx.formusic.model.SearchListInfo;
import com.ximcomputerx.formusic.model.SongListInfo;
import com.ximcomputerx.formusic.model.SongUrlInfo;
import com.ximcomputerx.formusic.model.SongUrlListInfo;
import com.ximcomputerx.formusic.model.VersionInfo;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @AUTHOR HACKER
 */
public interface ApiService {

    /**
     * 排行榜所有榜单
     * @return
     */
    @POST(Constant.URL_SUB_RANK)
    Observable<RankListInfo<Object>> rank();

    /**
     * 所有歌单
     * @param limit
     * @param order
     * @param cat
     * @return
     */
    @POST(Constant.URL_SUB_MIX)
    Observable<MixListInfo<Object>> mix(@Query("limit") int limit, @Query("order") String order, @Query("cat") String cat, @Query("offset") int offset);

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    @POST(Constant.URL_SUB_SONGLIST)
    Observable<SongListInfo<Object>> songList(@Query("id") String id);

    /**
     * 获取歌曲地址
     * @param id
     * @return
     */
    @POST(Constant.URL_SUB_SONGURL)
    Observable<SongUrlListInfo<SongUrlInfo>> songUrlList(@Query("id") String id);

    /**
     * 获取歌曲歌词
     * @param id
     * @return
     */
    @POST(Constant.URL_SUB_SONGLRC)
    Observable<LrcListInfo<LrcInfo>> lrcList(@Query("id") String id);

    /**
     * 热搜列表
     * @return
     */
    @POST(Constant.URL_SUB_SEARCH_HOST)
    Observable<SearchHotListInfo> searchHot();

    /**
     * 搜索
     * @param keywords
     * @return
     */
    @POST(Constant.URL_SUB_SEARCH_DETAIL)
    Observable<SearchListInfo> searchList(@Query("keywords") String keywords, @Query("limit") int limit, @Query("offset") int offset);

    /**
     * 推荐歌单
     * @param limit
     * @return
     */
    @POST(Constant.URL_SUB_RECOMMEND)
    Observable<RecommendListIinfo> recommendList(@Query("limit") String limit);

    /**
     * 热门歌手
     * @param limit
     * @param offset
     * @return
     */
    @POST(Constant.URL_SUB_SINGER)
    Observable<SingerListInfo> singerList(@Query("limit") int limit, @Query("offset") int offset);

    /**
     * 歌手热门歌曲
     * @param id
     * @return
     */
    @POST(Constant.URL_SUB_SINGER_HOT)
    Observable<SingerHotListInfo> singerHotList(@Query("id") String id);

    /**
     * 版本更新
     * @param type
     * @param name
     * @param version
     * @return
     */
    @POST(Constant.URL_SUB_VERSION)
    Observable<RemoteReturnData<VersionInfo>> check(@Query("type") String type, @Query("name") String name, @Query("version") String version);
}