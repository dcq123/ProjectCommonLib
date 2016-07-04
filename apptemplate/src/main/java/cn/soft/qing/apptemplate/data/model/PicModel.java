package cn.soft.qing.apptemplate.data.model;

import java.util.List;

import cn.qing.soft.networklib.BaseModel;

/**
 * Created by dcq on 2016/4/20 0020.
 */
public class PicModel extends BaseModel {

    /**
     * IndexCode : 索引号
     * NativeTitle : 标题
     * NativeUrl : http://www.qq.com
     * Url : http://c.hiphotos.baidu.com/image/w%3D400/sign=c2318ff84334970a4773112fa5c8d1c0/b7fd5266d0160924c1fae5ccd60735fae7cd340d.jpg
     */

    private List<DataEntity> Data;

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        private String IndexCode;
        private String NativeTitle;
        private String NativeUrl;
        private String Url;

        public String getIndexCode() {
            return IndexCode;
        }

        public void setIndexCode(String IndexCode) {
            this.IndexCode = IndexCode;
        }

        public String getNativeTitle() {
            return NativeTitle;
        }

        public void setNativeTitle(String NativeTitle) {
            this.NativeTitle = NativeTitle;
        }

        public String getNativeUrl() {
            return NativeUrl;
        }

        public void setNativeUrl(String NativeUrl) {
            this.NativeUrl = NativeUrl;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "IndexCode='" + IndexCode + '\'' +
                    ", NativeTitle='" + NativeTitle + '\'' +
                    ", NativeUrl='" + NativeUrl + '\'' +
                    ", Url='" + Url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PicModel{" +
                "Message='" + getMessage() + '\'' +
                ", Status='" + getStatus() + '\'' +
                ", Data=" + Data +
                '}';
    }
}
