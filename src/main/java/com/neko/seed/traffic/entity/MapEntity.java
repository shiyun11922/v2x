package com.neko.seed.traffic.entity;

import java.util.List;

public class MapEntity {

    private String BS;
    private String DLBM;
    private Double DLKD;
    private String JC;
    private String LXBM;
    private String NAME;
    private Integer OBJECTID;
    private String SXXBS;
    private Double Shape_Leng;
    private String UserID;
    private Integer flow;
    private List<Double[]> shape;

    public String getBS() {
        return BS;
    }

    public void setBS(String BS) {
        this.BS = BS;
    }

    public String getDLBM() {
        return DLBM;
    }

    public void setDLBM(String DLBM) {
        this.DLBM = DLBM;
    }

    public Double getDLKD() {
        return DLKD;
    }

    public void setDLKD(Double DLKD) {
        this.DLKD = DLKD;
    }

    public String getJC() {
        return JC;
    }

    public void setJC(String JC) {
        this.JC = JC;
    }

    public String getLXBM() {
        return LXBM;
    }

    public void setLXBM(String LXBM) {
        this.LXBM = LXBM;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Integer getOBJECTID() {
        return OBJECTID;
    }

    public void setOBJECTID(Integer OBJECTID) {
        this.OBJECTID = OBJECTID;
    }

    public String getSXXBS() {
        return SXXBS;
    }

    public void setSXXBS(String SXXBS) {
        this.SXXBS = SXXBS;
    }

    public Double getShape_Leng() {
        return Shape_Leng;
    }

    public void setShape_Leng(Double shape_Leng) {
        Shape_Leng = shape_Leng;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public List<Double[]> getShape() {
        return shape;
    }

    public void setShape(List<Double[]> shape) {
        this.shape = shape;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    @Override
    public String toString() {
        return "MapEntity{" +
                "BS='" + BS + '\'' +
                ", DLBM='" + DLBM + '\'' +
                ", DLKD=" + DLKD +
                ", JC='" + JC + '\'' +
                ", LXBM='" + LXBM + '\'' +
                ", NAME='" + NAME + '\'' +
                ", OBJECTID=" + OBJECTID +
                ", SXXBS='" + SXXBS + '\'' +
                ", Shape_Leng=" + Shape_Leng +
                ", UserID='" + UserID + '\'' +
                ", shape=" + shape +
                '}';
    }
}
