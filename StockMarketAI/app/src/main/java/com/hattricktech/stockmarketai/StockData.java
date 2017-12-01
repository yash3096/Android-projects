package com.hattricktech.stockmarketai;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

  class Dataset {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("dataset_code")
    @Expose
    private String datasetCode;
    @SerializedName("database_code")
    @Expose
    private String databaseCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("refreshed_at")
    @Expose
    private String refreshedAt;
    @SerializedName("newest_available_date")
    @Expose
    private String newestAvailableDate;
    @SerializedName("oldest_available_date")
    @Expose
    private String oldestAvailableDate;
    @SerializedName("column_names")
    @Expose
    private List<String> columnNames = null;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("premium")
    @Expose
    private boolean premium;
    @SerializedName("limit")
    @Expose
    private Object limit;
    @SerializedName("transform")
    @Expose
    private Object transform;
    @SerializedName("column_index")
    @Expose
    private Object columnIndex;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("data")
    @Expose
    private List<List<String>> data = null;
    @SerializedName("collapse")
    @Expose
    private Object collapse;
    @SerializedName("order")
    @Expose
    private Object order;
    @SerializedName("database_id")
    @Expose
    private long databaseId;

    /**
     * No args constructor for use in serialization
     */
    public Dataset() {
    }

    /**
     * @param limit
     * @param startDate
     * @param premium
     * @param data
     * @param refreshedAt
     * @param oldestAvailableDate
     * @param endDate
     * @param datasetCode
     * @param frequency
     * @param type
     * @param id
     * @param databaseId
     * @param transform
     * @param newestAvailableDate
     * @param order
     * @param databaseCode
     * @param description
     * @param name
     * @param columnIndex
     * @param columnNames
     * @param collapse
     */
    public Dataset(long id, String datasetCode, String databaseCode, String name, String description, String refreshedAt, String newestAvailableDate, String oldestAvailableDate, List<String> columnNames, String frequency, String type, boolean premium, Object limit, Object transform, Object columnIndex, String startDate, String endDate, List<List<String>> data, Object collapse, Object order, long databaseId) {
        super();
        this.id = id;
        this.datasetCode = datasetCode;
        this.databaseCode = databaseCode;
        this.name = name;
        this.description = description;
        this.refreshedAt = refreshedAt;
        this.newestAvailableDate = newestAvailableDate;
        this.oldestAvailableDate = oldestAvailableDate;
        this.columnNames = columnNames;
        this.frequency = frequency;
        this.type = type;
        this.premium = premium;
        this.limit = limit;
        this.transform = transform;
        this.columnIndex = columnIndex;
        this.startDate = startDate;
        this.endDate = endDate;
        this.data = data;
        this.collapse = collapse;
        this.order = order;
        this.databaseId = databaseId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatasetCode() {
        return datasetCode;
    }

    public void setDatasetCode(String datasetCode) {
        this.datasetCode = datasetCode;
    }

    public String getDatabaseCode() {
        return databaseCode;
    }

    public void setDatabaseCode(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefreshedAt() {
        return refreshedAt;
    }

    public void setRefreshedAt(String refreshedAt) {
        this.refreshedAt = refreshedAt;
    }

    public String getNewestAvailableDate() {
        return newestAvailableDate;
    }

    public void setNewestAvailableDate(String newestAvailableDate) {
        this.newestAvailableDate = newestAvailableDate;
    }

    public String getOldestAvailableDate() {
        return oldestAvailableDate;
    }

    public void setOldestAvailableDate(String oldestAvailableDate) {
        this.oldestAvailableDate = oldestAvailableDate;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Object getLimit() {
        return limit;
    }

    public void setLimit(Object limit) {
        this.limit = limit;
    }

    public Object getTransform() {
        return transform;
    }

    public void setTransform(Object transform) {
        this.transform = transform;
    }

    public Object getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Object columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

    public Object getCollapse() {
        return collapse;
    }

    public void setCollapse(Object collapse) {
        this.collapse = collapse;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

     @Override
     public String toString() {
         return "Dataset{" +
                 "id=" + id +
                 ", datasetCode='" + datasetCode + '\'' +
                 ", databaseCode='" + databaseCode + '\'' +
                 ", name='" + name + '\'' +
                 ", description='" + description + '\'' +
                 ", refreshedAt='" + refreshedAt + '\'' +
                 ", newestAvailableDate='" + newestAvailableDate + '\'' +
                 ", oldestAvailableDate='" + oldestAvailableDate + '\'' +
                 ", columnNames=" + columnNames +
                 ", frequency='" + frequency + '\'' +
                 ", type='" + type + '\'' +
                 ", premium=" + premium +
                 ", limit=" + limit +
                 ", transform=" + transform +
                 ", columnIndex=" + columnIndex +
                 ", startDate='" + startDate + '\'' +
                 ", endDate='" + endDate + '\'' +
                 ", data=" + data +
                 ", collapse=" + collapse +
                 ", order=" + order +
                 ", databaseId=" + databaseId +
                 '}';
     }
 }

public class StockData {

    @SerializedName("dataset")
    @Expose
    private Dataset dataset;

    /**
     * No args constructor for use in serialization
     */
    public StockData() {
    }

    /**
     * @param dataset
     */
    public StockData(Dataset dataset) {
        super();
        this.dataset = dataset;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    @Override
    public String toString() {
        return "StockData{" +
                "dataset=" + dataset +
                '}';
    }
}