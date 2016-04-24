package cn.qing.soft.projectcommonlib.network.model;

import java.util.List;

import cn.qing.soft.networklib.BaseModel;

/**
 * Created by dcq on 2016/4/24 0024.
 */
public class StatisticsInfoModel extends BaseModel {


    /**
     * ExportSumAmount : 100
     * OrderCount : 10
     * WaitSignCount : 22
     */

    private List<DataEntity> Data;

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity {
        private String ExportSumAmount;
        private String OrderCount;
        private String WaitSignCount;

        public String getExportSumAmount() {
            return ExportSumAmount;
        }

        public void setExportSumAmount(String ExportSumAmount) {
            this.ExportSumAmount = ExportSumAmount;
        }

        public String getOrderCount() {
            return OrderCount;
        }

        public void setOrderCount(String OrderCount) {
            this.OrderCount = OrderCount;
        }

        public String getWaitSignCount() {
            return WaitSignCount;
        }

        public void setWaitSignCount(String WaitSignCount) {
            this.WaitSignCount = WaitSignCount;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "ExportSumAmount='" + ExportSumAmount + '\'' +
                    ", OrderCount='" + OrderCount + '\'' +
                    ", WaitSignCount='" + WaitSignCount + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Status=" + Status + ",StatisticsInfoModel{" +
                "Data=" + Data +
                '}';
    }
}
