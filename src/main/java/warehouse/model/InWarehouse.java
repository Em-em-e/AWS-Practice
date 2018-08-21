package warehouse.model;

import java.util.Date;

public class InWarehouse {
    private Integer id;

    private Date inDate;

    private String inNumber;

    private String supplierNo;

    private String supplierName;

    private Double length;

    private Double width;

    private Float height;

    private String productNo;

    private String productName;

    private Integer quantity;

    private Double purchasePrice;

    private Double purchaseAmount;

    private Double cube;

    private String transporterNo;

    private String transporterName;

    private Double transCubePrice;

    private Double transAmount;

    private Double transPrice;

    private Double purchaseCostPrice;

    private String remark;

    private String remark1;

    private String remark2;

    private Date opTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getInNumber() {
        return inNumber;
    }

    public void setInNumber(String inNumber) {
        this.inNumber = inNumber == null ? null : inNumber.trim();
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo == null ? null : supplierNo.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Double getCube() {
        return cube;
    }

    public void setCube(Double cube) {
        this.cube = cube;
    }

    public String getTransporterNo() {
        return transporterNo;
    }

    public void setTransporterNo(String transporterNo) {
        this.transporterNo = transporterNo == null ? null : transporterNo.trim();
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName == null ? null : transporterName.trim();
    }

    public Double getTransCubePrice() {
        return transCubePrice;
    }

    public void setTransCubePrice(Double transCubePrice) {
        this.transCubePrice = transCubePrice;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Double getTransPrice() {
        return transPrice;
    }

    public void setTransPrice(Double transPrice) {
        this.transPrice = transPrice;
    }

    public Double getPurchaseCostPrice() {
        return purchaseCostPrice;
    }

    public void setPurchaseCostPrice(Double purchaseCostPrice) {
        this.purchaseCostPrice = purchaseCostPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}