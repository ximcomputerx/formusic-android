package com.ximcomputerx.formusic.model;

import java.io.Serializable;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public class SongListInfo<DataType> implements Serializable {
    private int code;
    private String message;
    private DataType data;
    private Playlist playlist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public class Playlist implements Serializable {
        /*private List<SongInfo> trackIds;

        public List<SongInfo> getTrackIds() {
            return trackIds;
        }

        public void setTrackIds(List<SongInfo> trackIds) {
            this.trackIds = trackIds;
        }*/
        private List<TrackIdInfo> trackIds;

        public List<TrackIdInfo> getTrackIds() {
            return trackIds;
        }

        public void setTrackIds(List<TrackIdInfo> trackIds) {
            this.trackIds = trackIds;
        }
    }
}
