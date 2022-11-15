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
import com.ximcomputerx.formusic.model.TrackListInfo;
import com.ximcomputerx.formusic.model.VersionInfo;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @AUTHOR HACKER
 */
public class ApiWrapper extends RetrofitHelper {
    private static ApiWrapper apiWrapper;

    public ApiWrapper() {
    }

    public static ApiWrapper getInstance() {
        if (apiWrapper == null) {
            apiWrapper = new ApiWrapper();
        }
        return apiWrapper;
    }

    /**
     * 排行榜所有榜单
     * @return
     */
    public Observable<RankListInfo<Object>> rank() {
        return getApiService().rank();
    }

    /**
     * 所有歌单
     * @param limit
     * @param order
     * @param cat
     * @return
     */
    public Observable<MixListInfo<Object>> mix(int limit, String order, String cat, int offset) {
        return getApiService().mix(limit, order, cat, offset);
    }

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    public Observable<SongListInfo<Object>> songList(String id) {
        return getApiService().songList(id);
    }

    /**
     * 获取歌单详情
     * @param ids
     * @return
     */
    public Observable<TrackListInfo<Object>> songDetail(String ids) {
        return getApiService().songDetail(ids);
    }

    /**
     * 获取歌曲地址
     * @param id
     * @return
     */
    public Observable<SongUrlListInfo<SongUrlInfo>> songUrlList(String id) {
        return getApiService().songUrlList(id);
    }

    /**
     * 获取歌曲歌词
     * @param id
     * @return
     */
    public Observable<LrcListInfo<LrcInfo>> lrcList(String id) {
        return getApiService().lrcList(id);
    }

    /**
     * 热搜列表
     * @return
     */
    public Observable<SearchHotListInfo> searchHot() {
        return getApiService().searchHot();
    }

    /**
     * 搜索
     * @param keywords
     * @return
     */
    public Observable<SearchListInfo> searchList(String keywords, int limit, int offset) {
        return getApiService().searchList(keywords, limit, offset);
    }

    /**
     * 推荐歌单
     * @param limit
     * @return
     */
    public Observable<RecommendListIinfo> recommendList(String limit) {
        return getApiService().recommendList(limit);
    }

    /**
     * 热门歌手
     * @param limit
     * @param offset
     * @return
     */
    public Observable<SingerListInfo> singerList(int limit, int offset) {
        return getApiService().singerList(limit, offset);
    }

    /**
     * 歌手热门歌曲
     * @param id
     * @return
     */
    public Observable<SingerHotListInfo> singerHotList(String id) {
        return getApiService().singerHotList(id);
    }

    /**
     * 版本更新
     * @param type
     * @param name
     * @param version
     * @return
     */
    public Observable<RemoteReturnData<VersionInfo>> check(String type, String name, String version) {
        return getApiService().check(type, name, version);
    }
}




